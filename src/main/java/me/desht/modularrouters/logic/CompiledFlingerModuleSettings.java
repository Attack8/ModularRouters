package me.desht.modularrouters.logic;

import me.desht.modularrouters.item.module.Module;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CompiledFlingerModuleSettings extends CompiledModuleSettings {
    public static final String NBT_SPEED = "Speed";
    public static final String NBT_PITCH = "Pitch";
    public static final String NBT_YAW = "Yaw";

    private final float speed, pitch, yaw;

    public CompiledFlingerModuleSettings(ItemStack stack) {
        super(stack);

        NBTTagCompound compound = Module.validateNBT(stack);
        for (String key : new String[] { NBT_SPEED, NBT_PITCH, NBT_YAW }) {
            if (!compound.hasKey(key)) {
                compound.setFloat(key, 0.0f);
            }
        }

        speed = compound.getFloat(NBT_SPEED);
        pitch = compound.getFloat(NBT_PITCH);
        yaw = compound.getFloat(NBT_YAW);
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public float getSpeed() {
        return speed;
    }
}
