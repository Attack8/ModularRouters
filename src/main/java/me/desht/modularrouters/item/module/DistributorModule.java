package me.desht.modularrouters.item.module;

import me.desht.modularrouters.block.tile.TileEntityItemRouter;
import me.desht.modularrouters.client.util.TintColor;
import me.desht.modularrouters.container.ContainerModule;
import me.desht.modularrouters.core.ModContainerTypes;
import me.desht.modularrouters.logic.ModuleTarget;
import me.desht.modularrouters.logic.compiled.CompiledDistributorModule;
import me.desht.modularrouters.logic.compiled.CompiledModule;
import me.desht.modularrouters.util.MiscUtil;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DistributorModule extends SenderModule2 {
    @Override
    public void addSettingsInformation(ItemStack itemstack, List<ITextComponent> list) {
        super.addSettingsInformation(itemstack, list);

        CompiledDistributorModule cdm = new CompiledDistributorModule(null, itemstack);
        list.add(MiscUtil.xlate("guiText.tooltip.distributor.strategy").func_240702_b_(": ").func_240699_a_(TextFormatting.YELLOW)
                .func_230529_a_(MiscUtil.xlate(cdm.getDistributionStrategy().getTranslationKey())).func_240699_a_(TextFormatting.AQUA));
    }

    @Override
    public ContainerType<? extends ContainerModule> getContainerType() {
        return ModContainerTypes.CONTAINER_MODULE_DISTRIBUTOR.get();
    }

    @Override
    public CompiledModule compile(TileEntityItemRouter router, ItemStack stack) {
        return new CompiledDistributorModule(router, stack);
    }

    @Override
    public List<ModuleTarget> getStoredPositions(@Nonnull ItemStack stack) {
        Set<ModuleTarget> targets = TargetedModule.getTargets(stack, false);
        return new ArrayList<>(targets);
    }

    @Override
    public TintColor getItemTint() {
        return new TintColor(240, 240, 60);
    }

    @Override
    protected int getMaxTargets() {
        return 8;
    }

    @Override
    public int getRenderColor(int index) {
        return 0x80B0FF90;
    }
}
