package me.desht.modularrouters.util;

import com.mojang.authlib.GameProfile;
import me.desht.modularrouters.ModularRouters;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.play.ServerPlayNetHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.ref.WeakReference;
import java.util.UUID;

@Mod.EventBusSubscriber
public class FakePlayerManager {
    private static final GameProfile GAME_PROFILE = new GameProfile(UUID.nameUUIDFromBytes(ModularRouters.MODID.getBytes()), "[" + ModularRouters.MODNAME + "]");
    private static WeakReference<RouterFakePlayer> theFakePlayer = new WeakReference<>(null);

    /**
     * Get the fake player instance, putting it at the given position & world.
     *
     * @param world the world
     * @param pos position in the world
     * @return the fake player, or null; be prepared to deal with a null return value
     */
    public static RouterFakePlayer getFakePlayer(ServerWorld world, BlockPos pos) {
        RouterFakePlayer fakePlayer = theFakePlayer.get();
        if (fakePlayer == null) {
            fakePlayer = new RouterFakePlayer(world);
            fakePlayer.connection = new ServerPlayNetHandler(world.getServer(), new NetworkManager(PacketDirection.SERVERBOUND), fakePlayer);
            theFakePlayer = new WeakReference<>(fakePlayer);
        }
        fakePlayer.world = world;
        fakePlayer.setRawPosition(pos.getX(), pos.getY(), pos.getZ());

        return fakePlayer;
    }

    public static class RouterFakePlayer extends FakePlayer {
        RouterFakePlayer(World world) {
            super((ServerWorld) world, GAME_PROFILE);
        }

        @Override
        protected void playEquipSound(ItemStack stack) {
            // silence annoying sound effects when fake player equips the buffer item
        }
    }

    @SubscribeEvent
    public static void onTick(TickEvent.WorldTickEvent event) {
        if (!event.world.isRemote && theFakePlayer.get() != null) {
            theFakePlayer.get().getCooldownTracker().tick();
        }
    }
}
