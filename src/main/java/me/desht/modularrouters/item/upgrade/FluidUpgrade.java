package me.desht.modularrouters.item.upgrade;

import me.desht.modularrouters.block.tile.TileEntityItemRouter;
import me.desht.modularrouters.client.util.ClientUtil;
import me.desht.modularrouters.client.util.TintColor;
import me.desht.modularrouters.config.ConfigHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;

public class FluidUpgrade extends ItemUpgrade {
    @Override
    public Object[] getExtraUsageParams() {
        return new Object[] { ConfigHandler.ROUTER.mBperFluidUpgade.get() };
    }

    @Override
    public void addUsageInformation(ItemStack itemstack, List<ITextComponent> list) {
        super.addUsageInformation(itemstack, list);
        TileEntityItemRouter router = ClientUtil.getOpenItemRouter();
        if (router != null) {
            list.add(new TranslationTextComponent("itemText.usage.item.fluidUpgradeRouter", router.getFluidTransferRate()));
        }
    }

    @Override
    public TintColor getItemTint() {
        return new TintColor(84, 138, 255);
    }
}
