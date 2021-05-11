package me.desht.modularrouters.item.module;

import com.google.common.collect.ImmutableList;
import me.desht.modularrouters.client.render.area.IPositionProvider;
import me.desht.modularrouters.client.util.TintColor;
import me.desht.modularrouters.config.MRConfig;
import me.desht.modularrouters.core.ModItems;
import me.desht.modularrouters.logic.ModuleTarget;
import me.desht.modularrouters.logic.compiled.CompiledEnergyDistributorModule;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import java.util.List;

public class EnergyDistributorModule extends TargetedModule implements IRangedModule, IPositionProvider {
    private static final TintColor TINT_COLOR = new TintColor(128, 128, 128);

    public EnergyDistributorModule() {
        super(ModItems.defaultProps(), CompiledEnergyDistributorModule::new);
    }

    @Override
    public TintColor getItemTint() {
        return TINT_COLOR;
    }

    @Override
    public int getEnergyCost(ItemStack stack) {
        return MRConfig.Common.EnergyCosts.energydistributorModuleEnergyCost;
    }

    @Override
    public int getBaseRange() {
        return 8;
    }

    @Override
    public int getHardMaxRange() {
        return 48;
    }

    @Override
    public List<ModuleTarget> getStoredPositions(@Nonnull ItemStack stack) {
        return ImmutableList.copyOf(TargetedModule.getTargets(stack, false));
    }

    @Override
    protected boolean isValidTarget(ItemUseContext ctx) {
        TileEntity te = ctx.getLevel().getBlockEntity(ctx.getClickedPos());
        return te != null && te.getCapability(CapabilityEnergy.ENERGY, ctx.getClickedFace()).isPresent();
    }

    @Override
    protected int getMaxTargets() {
        return 8;
    }

    @Override
    public int getRenderColor(int index) {
        return 0x80E08080;
    }
}
