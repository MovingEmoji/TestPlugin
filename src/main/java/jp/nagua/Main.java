package jp.nagua;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketTypeEnum;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.reflect.ClassPath;
import jp.nagua.api.ScoreboardAPI;
import jp.nagua.commands.PacketArmorStand;
import jp.nagua.commands.PingCommand;
import net.minecraft.server.v1_8_R3.*;
import org.apache.logging.log4j.core.net.Protocol;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.bukkit.scoreboard.Scoreboard;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class Main extends JavaPlugin {

    public static Scoreboard scoreboard;

    public static ConcurrentMap<Player , PacketArmorStand> standMap = new ConcurrentHashMap<>();
    public static ConcurrentMap<PacketArmorStand, PacketContainer> packetMap = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        getLogger().info("Enabled!!");
        Bukkit.getPluginManager().registerEvents(new EventListener(), JavaPlugin.getPlugin(Main.class));
        scoreboard = ScoreboardAPI.createScoreboard();
        ScoreboardAPI.initalizeScoreboard(scoreboard);
        try {
            Set<Class<?>> classes = ClassPath.from(this.getClassLoader()).getTopLevelClasses("jp.nagua.commands").stream().map(info -> info.load()).collect(Collectors.toSet());
            for(Class<?> commandclass : classes) {
                Class.forName(commandclass.getName(), true, this.getClassLoader());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    ScoreboardAPI.refreshScoreboard();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
        PacketType[] packets = {
                PacketType.Play.Client.POSITION,
                PacketType.Play.Client.POSITION_LOOK
        };
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(
                getPlugin(),
                ListenerPriority.MONITOR,
                packets
        ) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                for(Player player : getServer().getOnlinePlayers()) {
                    if(event.getPlayer() != player) {
                        double x = event.getPacket().getDoubles().read(0);
                        double y = event.getPacket().getDoubles().read(1);
                        double z = event.getPacket().getDoubles().read(2);
                        standMap.get(event.getPlayer()).sendTeleport(player, new Location(event.getPlayer().getWorld(), x, y, z,0 ,0));
                        PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(
                                ((CraftPlayer) event.getPlayer()).getHandle().getId(),
                                MathHelper.floor(x * 32.0),
                                MathHelper.floor(y * 32.0),
                                MathHelper.floor(z * 32.0),
                                (byte)((int)(event.getPlayer().getLocation().getYaw() * 256.0F / 360.0F)),
                                (byte)((int)(event.getPlayer().getLocation().getPitch() * 256.0F / 360.0F)),
                                event.getPlayer().isOnGround()
                        );
                        //((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                    }
                }
            }
        });
    }

    public static Main getPlugin() {
        return JavaPlugin.getPlugin(Main.class);
    }
}