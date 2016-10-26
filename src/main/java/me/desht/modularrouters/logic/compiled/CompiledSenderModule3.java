package me.desht.modularrouters.logic.compiled;

import me.desht.modularrouters.ModularRouters;
import me.desht.modularrouters.block.tile.TileEntityItemRouter;
import me.desht.modularrouters.item.module.Module;
import me.desht.modularrouters.network.ParticleBeamMessage;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import java.awt.*;

public class CompiledSenderModule3 extends CompiledSenderModule2 {
    public CompiledSenderModule3(TileEntityItemRouter router, ItemStack stack) {
        super(router, stack);
    }

    @Override
    public boolean isRangeLimited() {
        return false;
    }

    @Override
    protected void playParticles(TileEntityItemRouter router, BlockPos targetPos, float val) {
        double x = router.getPos().getX() + 0.5;
        double y = router.getPos().getY() + 0.5;
        double z = router.getPos().getZ() + 0.5;
        EnumFacing facing = router.getAbsoluteFacing(Module.RelativeDirection.FRONT);
        double x2 = x + facing.getFrontOffsetX() * 1.5;
        double z2 = z + facing.getFrontOffsetZ() * 1.5;
        NetworkRegistry.TargetPoint point = new NetworkRegistry.TargetPoint(router.getWorld().provider.getDimension(), x, y, z, 32);
        ModularRouters.network.sendToAllAround(new ParticleBeamMessage(x, y, z, x2, y, z2, Color.getHSBColor(0.83333f, 1.0f, 0.8f)), point);
    }

}