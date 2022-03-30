package com.mcstrike.mcstrike.commands;

import com.mcstrike.mcstrike.Classes.StrikeClass;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClassCommandCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], StrikeClass.availableClasses, completions);

        //sort the list
        Collections.sort(completions);
        return completions;
    }
}
