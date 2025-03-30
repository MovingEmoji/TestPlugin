package jp.nagua.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketArmorStand {

    private EntityArmorStand stand;

    public PacketArmorStand(Location location, String str) {
        WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
        stand = new EntityArmorStand(world);
        stand.setLocation(location.getX(), location.getY(), location.getZ(), 0, 0);
        stand.setBasePlate(false);
        stand.setCustomName(str);
        stand.setCustomNameVisible(true);
        stand.setGravity(false);
        stand.setInvisible(true);
    }

    public void sendSpawn(Player player) {
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
        PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), true);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(metadata);
    }

    public void sendTeleport(Player player, int x, int y, int z) {
        PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(stand.getId(), x, y + 2, z, (byte) 0, (byte) 0, true);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public void sendMove(Player player, byte x, byte y, byte z) {
        PacketPlayOutEntity.PacketPlayOutRelEntityMove packet = new PacketPlayOutEntity.PacketPlayOutRelEntityMove(stand.getId(), x, y, z, true);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
