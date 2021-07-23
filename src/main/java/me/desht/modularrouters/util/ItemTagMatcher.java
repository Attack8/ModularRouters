package me.desht.modularrouters.util;

import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Set;

public class ItemTagMatcher {
    private final Set<ResourceLocation> tags;

    public ItemTagMatcher(ItemStack stack) {
        this.tags = stack.getItem().getTags();
    }

    public boolean match(ItemStack stack) {
        Set<ResourceLocation> tags1 = stack.getItem().getTags();
        return !Sets.intersection(tags, tags1).isEmpty();
    }
}
