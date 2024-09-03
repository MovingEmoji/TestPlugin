package jp.nagua;

import com.google.common.reflect.ClassPath;
import jp.nagua.commands.PingCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Enabled!!");
        Bukkit.getPluginManager().registerEvents(new EventListener(), JavaPlugin.getPlugin(Main.class));
        try {
            Set<Class<?>> classes = ClassPath.from(this.getClassLoader()).getTopLevelClasses("jp.nagua.commands").stream().map(info -> info.load()).collect(Collectors.toSet());
            for(Class<?> commandclass : classes) {
                Class.forName(commandclass.getName(), true, this.getClassLoader());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Main getPlugin() {
        return JavaPlugin.getPlugin(Main.class);
    }
}