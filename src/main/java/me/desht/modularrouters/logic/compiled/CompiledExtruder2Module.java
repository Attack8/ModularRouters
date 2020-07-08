package me.desht.modularrouters.logic.compiled;

import me.desht.modularrouters.block.tile.TileEntityItemRouter;
import me.desht.modularrouters.block.tile.TileEntityTemplateFrame;
import me.desht.modularrouters.container.ContainerExtruder2Module.TemplateHandler;
import me.desht.modularrouters.core.RegistrarMR;
import me.desht.modularrouters.item.augment.ItemAugment;
import me.desht.modularrouters.util.BlockUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;

import java.util.ArrayList;
import java.util.List;

public class CompiledExtruder2Module extends CompiledExtruderModule {
    private static final ItemStack TEMPLATE_STACK = new ItemStack(RegistrarMR.TEMPLATE_FRAME);
    private final List<ItemStack> blockList;
    private final ItemStack pick = new ItemStack(Items.DIAMOND_PICKAXE);

    public CompiledExtruder2Module(TileEntityItemRouter router, ItemStack stack) {
        super(router, stack);

        blockList = new ArrayList<>();

        TemplateHandler handler = new TemplateHandler(stack);
        for (int i = 0; i < handler.getSlots() && blockList.size() < getRange(); i++) {
            ItemStack stack1 = handler.getStackInSlot(i);
            for (int j = 0; j < stack1.getCount(); j++) {
                ItemStack copy = stack1.copy();
                copy.setCount(1);
                blockList.add(copy);
                if (blockList.size() >= getRange()) break;
            }
        }
    }

    @Override
    public boolean execute(TileEntityItemRouter router) {
        boolean extend = shouldExtend(router);
        World world = router.getWorld();

        if (extend && distance < blockList.size()) {
            // try to extend
            if (!(blockList.get(distance).getItem() instanceof ItemBlock)) {
                // non-block item; it's a spacer so just skip over
                router.getExtData().setInteger(NBT_EXTRUDER_DIST + getFacing(), ++distance);
            } else {
                BlockPos placePos = router.getPos().offset(getFacing(), distance + 1);
                IBlockState state = BlockUtil.tryPlaceAsBlock(TEMPLATE_STACK, world, placePos, getFacing(), getRouterFacing());
                if (state != null) {
                    TileEntityTemplateFrame te = TileEntityTemplateFrame.getTileEntitySafely(world, placePos);
                    if (te != null) {
                        te.setCamouflage(blockList.get(distance));
                        te.setExtendedMimic(getAugmentCount(ItemAugment.AugmentType.MIMIC) > 0);
                        if (te.extendedMimic()) {
                            // in case we're mimicking a redstone emitter
                            world.notifyNeighborsOfStateChange(placePos, state.getBlock(), true);
                        }
                    }
                    router.playSound(null, placePos,
                            state.getBlock().getSoundType(state, world, placePos, null).getPlaceSound(),
                            SoundCategory.BLOCKS, 1.0f, 0.5f + distance * 0.1f);
                    router.getExtData().setInteger(NBT_EXTRUDER_DIST + getFacing(), ++distance);
                    tryPushEntities(router.getWorld(), placePos, getFacing());
                    return true;
                }
            }
        } else if (!extend && distance > 0) {
            BlockPos breakPos = router.getPos().offset(getFacing(), distance);
            IBlockState oldState = world.getBlockState(breakPos);
            router.getExtData().setInteger(NBT_EXTRUDER_DIST + getFacing(), --distance);
            if (okToBreak(oldState, world, breakPos)) {
                BlockUtil.BreakResult breakResult = BlockUtil.tryBreakBlock(world, breakPos, getFilter(), pick);
                if (breakResult.isBlockBroken()) {
                    router.playSound(null, breakPos,
                            RegistrarMR.TEMPLATE_FRAME.getSoundType(oldState, world, breakPos, null).getBreakSound(),
                            SoundCategory.BLOCKS, 1.0f, 0.5f + distance * 0.1f);
                }
                return true;
            }
        }

        return false;
    }

    private boolean okToBreak(IBlockState state, World world, BlockPos pos) {
        Block b = state.getBlock();
        return b.isAir(state, world, pos) || b == RegistrarMR.TEMPLATE_FRAME || b instanceof BlockLiquid || b instanceof IFluidBlock;
    }
}
