package com.mcstrike.mcstrike.listeners;

import com.mcstrike.mcstrike.Entities.StrikePlayer;
import com.mcstrike.mcstrike.McStrike;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // register player
        McStrike plugin = McStrike.getPlugin();
        Player player = event.getPlayer();
        plugin.addPlayer(player);

        // Welcome message
        String message = plugin.getConfig().getString("JoinMessage");
        event.setJoinMessage(ChatColor.GREEN + message);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        // unregister player
        McStrike plugin = McStrike.getPlugin();
        Player player = event.getPlayer();
        plugin.removePlayer(player);
    }

    /*
    *
    * ----------------------------------------------------------------------------------------------
    *
    * */

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        StrikePlayer strikePlayer = McStrike.getPlugin().getPlayer(player);
        if (event.isSneaking()) {
            strikePlayer.sneakOn();
        } else {
            strikePlayer.sneakOff();
        }
    }

    @EventHandler
    public void onMushroom2(BlockCanBuildEvent event) {
        String name = event.getEventName();

        if (event.getMaterial() == Material.RED_MUSHROOM) {
            // check if mushroom on adjacent block (diagonals not included)
            System.out.println("ASHDJSAHDJSHAJDHSAJDHJ");
            event.setBuildable(true);
//            Block block = event.getBlock();
//            event.setBuildable(false);
//            block.setType(Material.RED_MUSHROOM);
        }

        System.out.println(event.getMaterial());

        System.out.println("Event: " + name);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow bullet) {
            Player shooter = (Player) bullet.getShooter();

            if (shooter != null) {
                double damage = event.getDamage();
//                shooter.sendMessage("You hit something:" + damage);

                // TODO: change to Player
                if (event.getEntity() instanceof Villager target) {
                    double health = target.getHealth();

                    if (health - damage <= 0) {
                        shooter.sendMessage("You have killed a Villager!");

                    } else {
                        shooter.sendMessage("Remaining Health" + (health - damage));
                        target.setHealth(health - damage);
                    }
                }

            }

        } else {
            System.out.println("HIT:" + event.getEntity().getName());
        }
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        Block block = event.getHitBlock();
        Entity hitEntity = event.getHitEntity();
        Projectile entity = event.getEntity();
        if (entity instanceof Arrow) {
            if (block != null) {
                if(block.getType() == Material.GLASS_PANE) {
                    block.breakNaturally();
                    if ( entity.getShooter() instanceof Player shooter) {
                        shooter.playSound(shooter.getLocation(), Sound.BLOCK_GLASS_BREAK, 1, 1);
                    }
                }
            } else if (hitEntity != null) {
                if (event.getEntity().getShooter() instanceof Player shooter) {
                    shooter.sendMessage("you hit an entity");
                }
            }
            entity.remove();
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        StrikePlayer strikePlayer = McStrike.getPlugin().getPlayer(player);

        if (strikePlayer.hasClass()) {
            event.setCancelled(true);
        }
    }

}
