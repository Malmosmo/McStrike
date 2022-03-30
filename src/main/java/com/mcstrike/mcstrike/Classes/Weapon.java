package com.mcstrike.mcstrike.Classes;

import com.mcstrike.mcstrike.McStrike;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Weapon {
    WeaponType weaponType;
    ItemStack item;

    public int firingSpeed;  // in ticks
    public int magazine;
    public int magazineSize;

    public boolean isAutomatic;
    public boolean scope;

    public int reloadTime;
    public int reloadStatus;
    public int reloadTaskID;
    public int damage;
    public int bulletCooldown;


    public Weapon(WeaponType weaponType, String displayName, ItemStack item, String lore, int magazineSize, int reloadTime, int firingSpeed, boolean isAutomatic, int damage, boolean scope) {
        this.weaponType = weaponType;

        this.item = item;

        ItemMeta meta = this.item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.RESET + displayName);
            meta.setLore(List.of(lore));

            this.item.setItemMeta(meta);
        }
//        this.item.setAmount(magazineSize);

        this.isAutomatic = isAutomatic;

        this.firingSpeed = firingSpeed;
        this.magazine = magazineSize;
        this.magazineSize = magazineSize;
        this.reloadTime = reloadTime;
        this.reloadStatus = reloadTime;
        this.damage = damage;
        this.scope = scope;
//        this.bulletCooldown = bulletCooldown;
    }

    public boolean isEmpty() {
        return this.magazine == 0;
    }

    public void shoot() {
        this.magazine--;
    }

    public void reload() {
        this.magazine = this.magazineSize;
    }

    public WeaponType getWeaponType() {
        return this.weaponType;
    }

    public Material getItemType() {
        return this.item.getType();
    }

    public void stopReloadTask() {
        McStrike.getPlugin().getServer().getScheduler().cancelTask(this.reloadTaskID);
    }

    public boolean isReloading() {
        return this.reloadStatus != this.reloadTime;
    }

    public boolean isFull() {
        return this.magazine == this.magazineSize;
    }

    public boolean hasScope() {
        return this.scope;
    }

    public boolean canFire() {
        return this.magazine > 0 && !this.isReloading();
    }
}