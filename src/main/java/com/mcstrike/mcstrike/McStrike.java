package com.mcstrike.mcstrike;

import com.mcstrike.mcstrike.Classes.StrikeClass;
import com.mcstrike.mcstrike.Entities.PlayerManager;
import com.mcstrike.mcstrike.Entities.StrikePlayer;
import com.mcstrike.mcstrike.commands.AFKCommand;
import com.mcstrike.mcstrike.commands.ClassCommand;
import com.mcstrike.mcstrike.commands.ClassCommandCompleter;
import com.mcstrike.mcstrike.listeners.JoinListener;
import com.mcstrike.mcstrike.listeners.RightClickListener;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class McStrike extends JavaPlugin {

    private static McStrike plugin;

    private final PlayerManager playerManager = new PlayerManager();

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        // register all online players
        for(Player player : plugin.getServer().getOnlinePlayers()) {
            playerManager.addPlayer(player);
        }

        // load classes from config.yml
        StrikeClass.loadClassNames();

        // register config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // console message
        String message = getConfig().getString("JoinMessage");
        System.out.println("McStrike has now started! > " + message);

        // register Event Listener
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new RightClickListener(), this);

        // register Commands
        PluginCommand afkCommand = getCommand("afk");
        if (afkCommand != null) {
            afkCommand.setExecutor(new AFKCommand());
        }

        PluginCommand classCommand = getCommand("class");
        if (classCommand != null) {
            classCommand.setExecutor(new ClassCommand());
            classCommand.setTabCompleter(new ClassCommandCompleter());
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("McStrike shutting down!");
    }

    public static McStrike getPlugin() {
        return plugin;
    }

    public void addPlayer(Player player) {
        playerManager.addPlayer(player);
    }

    public void removePlayer(Player player) {
        playerManager.removePlayer(player);
    }

    public StrikePlayer getPlayer(Player player) {
        return playerManager.getPlayer(player);
    }
}
