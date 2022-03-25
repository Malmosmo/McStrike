package com.mcstrike.mcstrike;

import com.mcstrike.mcstrike.listeners.JoinListener;
import com.mcstrike.mcstrike.listeners.RightClickListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class McStrike extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("McStrike has now started!");

        // register Event Listener
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new RightClickListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("McStrike shutting down!");
    }
}
