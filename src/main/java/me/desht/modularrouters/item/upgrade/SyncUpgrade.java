package me.desht.modularrouters.item.upgrade;

import me.desht.modularrouters.ModularRouters;
import me.desht.modularrouters.block.tile.TileEntityItemRouter;
import me.desht.modularrouters.config.ConfigHandler;
import me.desht.modularrouters.sound.MRSoundEvents;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class SyncUpgrade extends Upgrade {
    private static final String NBT_TUNING = "Tuning";

    @Override
    boolean hasExtraInformation() {
        return true;
    }

    @Override
    public void addExtraInformation(ItemStack itemstack, World player, List<String> list, ITooltipFlag advanced) {
        list.add(I18n.format("itemText.sync.tuning", getTunedValue(itemstack)));
    }

    @Override
    public void onCompiled(ItemStack stack, TileEntityItemRouter router) {
        router.setTunedSyncValue(getTunedValue(stack));
    }

    public static int getTunedValue(ItemStack stack) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null) {
            return 0;
        }
        return compound.getInteger(NBT_TUNING);
    }

    public static void setTunedValue(ItemStack stack, int newValue) {
        NBTTagCompound compound = stack.getTagCompound();
        if (compound == null) {
            stack.setTagCompound(compound = new NBTTagCompound());
        }
        compound.setInteger(NBT_TUNING, newValue);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {
        if (hand != EnumHand.MAIN_HAND) {
            return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
        }
        if (worldIn.isRemote && !playerIn.isSneaking()) {
            BlockPos pos = playerIn.getPosition();
            playerIn.openGui(ModularRouters.instance, ModularRouters.GUI_SYNC_UPGRADE, worldIn, pos.getX(), pos.getY(), pos.getZ());
        } else if (playerIn.isSneaking()) {
            if (!worldIn.isRemote) {
                setTunedValue(itemStackIn, new Random().nextInt(ConfigHandler.router.baseTickRate));
                playerIn.sendStatusMessage(new TextComponentTranslation("itemText.sync.tuning", getTunedValue(itemStackIn)), false);
            } else {
                playerIn.playSound(MRSoundEvents.success, 1.0f, 1.5f);
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStackIn);
    }
}
