package com.mcstrike.mcstrike.listeners;

import com.mcstrike.mcstrike.Entities.StrikePlayer;
import com.mcstrike.mcstrike.McStrike;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


public class RightClickListener implements Listener {
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();

        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if (item != null) {
                StrikePlayer strikePlayer = McStrike.getPlugin().getPlayer(player);
                strikePlayer.rightClick(item);
            }
        } else if (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
            if (item != null) {
                StrikePlayer strikePlayer = McStrike.getPlugin().getPlayer(player);
                strikePlayer.leftClick(item);
            }
        }

//        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("This is in your actionbar"));
//
//        if (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
//            if (item !=null && item.getType() == Material.RED_MUSHROOM && action.equals(Action.RIGHT_CLICK_BLOCK)) {
//                player.sendMessage("wanna place a Mushuu");
//                Block block = event.getClickedBlock();
//
//                if (block != null) {
//                    BlockFace face = event.getBlockFace();
//
//                    Block next = block.getRelative(face);
//
//                    if (next.getType() == Material.AIR) {
//                        next.setType(Material.RED_MUSHROOM);
//                    }
//                }
//            } else if (item != null) {
//                StrikePlayer strikePlayer = McStrike.getPlugin().getPlayer(player);
//
//                if (strikePlayer != null) {
//                    if (strikePlayer.hasClass()) {
//                        strikePlayer.shoot(item);
//                    }
//                }
//            } else  {
//                player.sendMessage("item==null");
//            }
//        }
    }
}
