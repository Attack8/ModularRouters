package me.desht.modularrouters.block;

import me.desht.modularrouters.block.tile.ICamouflageable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

//@Optional.Interface (iface = "team.chisel.ctm.api.IFacade", modid = "ctm-api")
public abstract class BlockCamo extends Block /*implements IFacade*/ {
    public static final ModelProperty<BlockState> CAMOUFLAGE_STATE = new ModelProperty<>();

    BlockCamo(Properties props) {
        super(props);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext ctx) {
        ICamouflageable camo = getCamoState(reader, pos);
        return camo != null ? camo.getCamouflage().getCollisionShape(reader, pos) : super.getCollisionShape(state, reader, pos, ctx);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext ctx) {
        ICamouflageable camo = getCamoState(reader, pos);
        return camo != null ? camo.getCamouflage().getShape(reader, pos) : super.getShape(state, reader, pos, ctx);
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        ICamouflageable camo = getCamoState(world, pos);
        return camo == null || !camo.extendedMimic() ? super.getLightValue(state, world, pos) : camo.getCamouflage().getLightValue(world, pos);
    }

    @Override
    public int getOpacity(BlockState state, IBlockReader world, BlockPos pos) {
        ICamouflageable camo = getCamoState(world, pos);
        return camo == null ? super.getOpacity(state, world, pos) : camo.getCamouflage().getOpacity(world, pos);
    }
    @Override
    public float getBlockHardness(BlockState blockState, IBlockReader worldIn, BlockPos pos) {
        ICamouflageable camo = getCamoState(worldIn, pos);
        return camo == null || !camo.extendedMimic() ? super.getBlockHardness(blockState, worldIn, pos) : camo.getCamouflage().getBlockHardness(worldIn, pos);
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        ICamouflageable camo = getCamoState(worldIn, pos);
        return camo == null || !camo.extendedMimic() ? super.isNormalCube(state, worldIn, pos) : camo.getCamouflage().isNormalCube(worldIn, pos);
    }

    @Override
    public float getExplosionResistance(BlockState state, IWorldReader world, BlockPos pos, @Nullable Entity exploder, Explosion explosion) {
        ICamouflageable camo = getCamoState(world, pos);
        return camo == null || !camo.extendedMimic() ?
                super.getExplosionResistance(state, world, pos, exploder, explosion) :
                camo.getCamouflage().getBlock().getExplosionResistance(state, world, pos, exploder, explosion);
    }

    ICamouflageable getCamoState(IBlockReader blockAccess, BlockPos pos) {
        TileEntity te = blockAccess.getTileEntity(pos);
        return te instanceof ICamouflageable && ((ICamouflageable) te).getCamouflage() != null ? (ICamouflageable) te : null;
    }

    @Override
    public boolean canProvidePower(BlockState state) {
        return true;
    }

    @Override
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        ICamouflageable camo = getCamoState(blockAccess, pos);
        return camo == null || !camo.extendedMimic() ? super.getWeakPower(blockState, blockAccess, pos, side) : camo.getCamouflage().getWeakPower(blockAccess, pos, side);
    }

    @Override
    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        ICamouflageable camo = getCamoState(blockAccess, pos);
        return camo == null || !camo.extendedMimic() ? super.getWeakPower(blockState, blockAccess, pos, side) : camo.getCamouflage().getStrongPower(blockAccess, pos, side);
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

//    @Nonnull
//    @Override
//    @Optional.Method(modid = "ctm-api")
//    public IBlockState getFacade(@Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nullable EnumFacing side) {
//        TileEntity te = world.getTileEntity(pos);
//        if (te instanceof ICamouflageable && ((ICamouflageable) te).getCamouflage() != null) {
//            return ((ICamouflageable) te).getCamouflage();
//        }
//        return world.getBlockState(pos);
//    }
}
