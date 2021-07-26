package me.desht.modularrouters.item.augment;

import me.desht.modularrouters.container.handler.AugmentHandler;
import me.desht.modularrouters.core.ModItems;
import me.desht.modularrouters.item.MRBaseItem;
import me.desht.modularrouters.item.module.ModuleItem;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AugmentItem extends MRBaseItem {
    public static final int SLOTS = 4;

    public AugmentItem() {
        super(ModItems.defaultProps());
    }

    @Override
    protected void addExtraInformation(ItemStack stack, List<Component> list) {
    }

    public abstract int getMaxAugments(ModuleItem moduleType);

    public String getExtraInfo(int c, ItemStack moduleStack) {
        return "";
    }

    public static class AugmentCounter {
        private final Map<ResourceLocation, Integer> counts = new HashMap<>();

        public AugmentCounter(ItemStack moduleStack) {
            refresh(moduleStack);
        }

        public void refresh(ItemStack moduleStack) {
            Validate.isTrue(moduleStack.getItem() instanceof ModuleItem, "item is not a ItemModule: " + moduleStack);

            AugmentHandler h = new AugmentHandler(moduleStack, null);
            counts.clear();
            for (int i = 0; i < h.getSlots(); i++) {
                ItemStack augmentStack = h.getStackInSlot(i);
                if (augmentStack.getItem() instanceof AugmentItem) {
                    ResourceLocation k = augmentStack.getItem().getRegistryName();
                    counts.put(k, counts.getOrDefault(k, 0) + augmentStack.getCount());
                }
            }
        }

        public Collection<AugmentItem> getAugments() {
            return counts.keySet().stream().map(ForgeRegistries.ITEMS::getValue).map(i -> (AugmentItem)i).collect(Collectors.toList());
        }

        public int getAugmentCount(Item type) {
            if (type == null) return 0;
            return counts.getOrDefault(type.getRegistryName(), 0);
        }
    }
}