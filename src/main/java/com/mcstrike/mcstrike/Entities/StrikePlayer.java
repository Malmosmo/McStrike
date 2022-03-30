package com.mcstrike.mcstrike.Entities;

import com.mcstrike.mcstrike.Classes.StrikeClass;
import com.mcstrike.mcstrike.Classes.Weapon;
import com.mcstrike.mcstrike.Classes.WeaponType;
import com.mcstrike.mcstrike.McStrike;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StrikePlayer {
    final Player player;

    // additional Data
    boolean afk;
    StrikeClass strikeClass;
    long lastShot;
    int firingTaskID = -1;


    public StrikePlayer(Player player) {
        this.player = player;
    }

    // ----------------------------------------

    public void sneakOn() {

        if (this.hasClass()) {
            ItemStack item = this.player.getInventory().getItemInMainHand();
            Weapon weapon = strikeClass.getWeaponByItem(item);
            if (weapon != null) {
//                if (weapon.hasScope() && weapon.canFire()) {
                if (weapon.hasScope()) {
                    PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 20000, 6, false, false);
                    this.player.addPotionEffect(effect);
//                    this.player.setWalkSpeed(-0.15F);

                    ItemStack scope = new ItemStack(Material.CARVED_PUMPKIN, 1);
                    this.player.getInventory().setHelmet(scope);
                }
            }
        }
    }

    public void sneakOff() {
        this.player.removePotionEffect(PotionEffectType.SLOW);
        this.player.getInventory().setHelmet(null);
//        player.setWalkSpeed(0.2F);
    }

    public void leftClick(ItemStack item) {
        if (this.strikeClass != null) {
            Weapon weapon = strikeClass.getWeaponByItem(item);
            if (weapon != null){
                if (!(weapon.isFull() || weapon.isReloading())) this.reload(weapon);
            }
        }
    }

    public void rightClick(ItemStack item) {
        // item in Hand
        if (this.strikeClass != null) {
            Weapon weapon = strikeClass.getWeaponByItem(item);

            if (weapon != null && weapon.getWeaponType() == WeaponType.GUN) {
                if (!weapon.isReloading()) {
                    this.lastShot = System.currentTimeMillis();

                    if (this.firingTaskID < 0) {
                        this.firingTaskID = player.getServer().getScheduler().scheduleSyncRepeatingTask(McStrike.getPlugin(), () -> {
                            // weapon.canFire... buggy fix
                            // PlayerInteractInvent gets fired about every 200ms if button is hold
                            if (System.currentTimeMillis() - this.lastShot > 225 || !weapon.canFire()) {
                                this.stopFiringTask();
                            } else {
                                shoot(weapon);
                            }
                        }, 0, weapon.firingSpeed);
                    }
                }
            }
        }
    }

    private void stopFiringTask() {
        McStrike.getPlugin().getServer().getScheduler().cancelTask(this.firingTaskID);
        this.firingTaskID = -1;
    }

    private void shoot(Weapon weapon) {
        Location location = this.player.getLocation();

        if(this.player.isSneaking()) {
            location.setY(location.getY() + 1.25);
        } else {
            location.setY(location.getY() + 1.55);
        }

        Arrow bullet = this.player.getWorld().spawnArrow(location, location.getDirection(), 10, 0);

        // TODO: fix damage (10x higher than should be)
        bullet.setDamage((float) weapon.damage / 10);
        bullet.setShooter(this.player);
        bullet.setGravity(false);

        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_DEATH, 1, 1);

        weapon.shoot();
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(weapon.magazine + "/" + weapon.magazineSize));

        if (weapon.isEmpty()) {
            this.reload(weapon);
        }
    }

    private void reload(Weapon weapon) {
        weapon.reloadStatus = 0;
        weapon.reloadTaskID = player.getServer().getScheduler().scheduleSyncRepeatingTask(McStrike.getPlugin(), () -> {
            // reload only when weapon in hand
            if (player.getInventory().getItemInMainHand().getType() == weapon.getItemType()) {
                if (weapon.reloadStatus < weapon.reloadTime) {
                    weapon.reloadStatus++;

                    int percent = (weapon.reloadStatus * 20) / weapon.reloadTime;
                    String text = ChatColor.GREEN + "\u258d".repeat(Math.max(0, percent)) + ChatColor.RED + "\u258d".repeat(Math.max(0, 20 - percent));
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
                } else {
                    weapon.stopReloadTask();
                    weapon.reload();

                    // clear actionbar
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(""));
                }
            }
        }, 0, 1);
    }

    // ----------------------------------------

    public boolean isAfk() {
        return this.afk;
    }

    public void setAfk(boolean afk) {
        this.afk = afk;
    }

    public void setClass(String className) {
        StrikeClass strikeClass = StrikeClass.getClass(className);

        if (strikeClass != null) {
            strikeClass.equip(this.player);
            this.strikeClass = strikeClass;
        } else {
            this.player.sendMessage("class " + className + " is not available");
        }
    }

    public boolean hasClass() {
        return this.strikeClass != null;
    }

//    private void fireGun(Weapon weapon, ItemStack item) {
//        Location location = this.player.getLocation();
//
//        if(this.player.isSneaking()) {
//            location.setY(location.getY() + 1.25);
//        } else {
//            location.setY(location.getY() + 1.55);
//        }
//
//        Arrow bullet = this.player.getWorld().spawnArrow(location, location.getDirection(), 10, 2);
//
//        // TODO: set to weapon damage
//        bullet.setDamage(20);
//        bullet.setShooter(this.player);
//        bullet.setGravity(false);
//
//        // play sound
//        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_DEATH, 1, 1);
//
//        // TODO: RELOAD
////        if (item.getAmount() == 1) {
////            weapon.reloadStatus = 0;
////
////            weapon.reloadTaskID = player.getServer().getScheduler().scheduleSyncRepeatingTask(McStrike.getPlugin(), () -> {
////                if (weapon.reloadStatus < weapon.reloadTime) {
////                    weapon.reloadStatus++;
////                    int percent = (weapon.reloadStatus * 20) / weapon.reloadTime;
////                    String text = ChatColor.GREEN + ":".repeat(Math.max(0, percent)) + ChatColor.RED + ":".repeat(Math.max(0, 20 - percent));
////                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(text));
////                } else {
////                    player.sendMessage("fin");
////                    weapon.stopReloadTask();
////                    item.setAmount(weapon.stackSize);
////                }
////            }, 0, 1);
////
////        } else {
////            // reduce item stack
////            item.setAmount(item.getAmount() - 1);
////        }
//
//    }
//
////    public void shoot(ItemStack item) {
////        // get by type... bc item and weapon.item might not have the same amount
////        Weapon weapon = selectedClass.getWeaponByType(item.getType());
////
////        if (weapon != null && weapon.getWeaponType() == WeaponType.GUN) {
////            if (!weapon.isReloading()) {
////                this.lastShot = System.currentTimeMillis();
////
////                this.firingTaskID = player.getServer().getScheduler().scheduleSyncRepeatingTask(McStrike.getPlugin(), () -> {
////                    if (System.currentTimeMillis() - this.lastShot > 500) {
////                        this.stopFiringTask();
////                    } else {
////                        this.fireGun(weapon, item);
////                        player.sendMessage("Stopped firing");
////                    }
////                }, 0, 5);
////            }
////        } else {
////            // DEBUG
////            this.player.sendMessage("You shoot with no gun????");
////        }
////        player.updateInventory();
////    }
}
