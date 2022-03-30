package com.mcstrike.mcstrike.Classes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Sniper {
    // public final

    public static void equipPlayer(Player player) {
        PlayerInventory inventory = player.getInventory();
        inventory.clear();
        UUID id = player.getUniqueId();

        // Main Weapon

        ItemStack item = new ItemStack(Material.GUNPOWDER, 1);
        ItemMeta data = item.getItemMeta();
        List<String> loreList = new ArrayList<>();

        loreList.add("§7 This is a very powerful Weapon");
        loreList.add("§7 It can shot only once");
        loreList.add("§7 then you will have to reload");
        loreList.add("§7 Have fun!");

        if (data != null) {
            data.setLore(loreList);
            data.setDisplayName("§6 M403 §e1");
            data.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

            item.setItemMeta(data);
        }

        inventory.setItem(0, item);

        // ...
    }
}
