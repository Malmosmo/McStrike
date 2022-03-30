package com.mcstrike.mcstrike.commands;

import com.mcstrike.mcstrike.Entities.StrikePlayer;
import com.mcstrike.mcstrike.McStrike;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AFKCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            McStrike plugin = McStrike.getPlugin();
            StrikePlayer strPlayer = plugin.getPlayer(player);

            if (strPlayer != null) {
                if (strPlayer.isAfk()) {
                    strPlayer.setAfk(false);

                    String message = plugin.getConfig().getString("AfkMessageOff");
                    if (message != null) {
                        player.sendMessage(message);
                    }
                } else {
                    strPlayer.setAfk(true);

                    String message = plugin.getConfig().getString("AfkMessageOn");
                    if (message != null) {
                        player.sendMessage(message);
                    }
                }
            }
        }
        return true;
    }
}
