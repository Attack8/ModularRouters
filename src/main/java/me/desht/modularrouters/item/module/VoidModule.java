package me.desht.modularrouters.item.module;

import me.desht.modularrouters.block.tile.TileEntityItemRouter;
import me.desht.modularrouters.client.util.TintColor;
import me.desht.modularrouters.core.ModItems;
import me.desht.modularrouters.logic.compiled.CompiledModule;
import me.desht.modularrouters.logic.compiled.CompiledVoidModule;
import net.minecraft.item.ItemStack;

public class VoidModule extends ItemModule {
    public VoidModule() {
        super(ModItems.defaultProps());
    }

    @Override
    public CompiledModule compile(TileEntityItemRouter router, ItemStack stack) {
        return new CompiledVoidModule(router, stack);
    }

    @Override
    public boolean isDirectional() {
        return false;
    }

    @Override
    public TintColor getItemTint() {
        return new TintColor(255, 0, 0);
    }
}
