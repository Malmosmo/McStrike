package com.mcstrike.mcstrike.commands;

import com.mcstrike.mcstrike.Entities.StrikePlayer;
import com.mcstrike.mcstrike.McStrike;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClassCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                String className = args[0];
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Selected " + className);

                StrikePlayer strikePlayer = McStrike.getPlugin().getPlayer(player);
                strikePlayer.setClass(className);
            } else {
                player.sendMessage(ChatColor.YELLOW + "Please provide a class!");
            }
        }
        return false;
    }
}

