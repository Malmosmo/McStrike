package com.mcstrike.mcstrike.Classes;

import com.mcstrike.mcstrike.McStrike;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public record StrikeClass(StrikeClassType type, List<Weapon> weaponList) {
    public static final List<String> availableClasses = new ArrayList<>();

    public static void loadClassNames() {
        FileConfiguration config =  McStrike.getPlugin().getConfig();
        List<?> classNames = config.getList("classes");
        if (classNames != null) {
            for (Object className : classNames) {
                availableClasses.add((String) className);
            }
        }
    }
    
    public void equip(Player player) {
        Inventory inventory = player.getInventory();
        inventory.clear();

        for (int i = 0; i < this.weaponList.size(); i++) {
            inventory.setItem(i, weaponList.get(i).item);
        }

        player.updateInventory();
    }
//
//    public StrikeClassType getType() {
//        return type;
//    }
//
//    public Weapon getWeaponByType(Material itemType) {
//        for (Weapon weapon: this.weaponList) {
//            if (weapon.item.getType().equals(itemType)) {
//                return weapon;
//            }
//        }
//        return null;
//    }

    public Weapon getWeaponByItem(ItemStack item) {
        for (Weapon weapon: this.weaponList) {
            if (weapon.item.equals(item)) {
                return weapon;
            }
        }
        return null;
    }

    public static StrikeClass getClass(String className) {
        if (StrikeClass.availableClasses.contains(className)) {
            switch (className) {
                case "sniper": {
                    return StrikeClass.getSniper();
                }
                case "assault": {
                    return StrikeClass.getAssault();
                }
                case "infiltrator": {
                    return StrikeClass.getInfiltrator();
                }
                default: return null;
            }
        }
        return null;
    }

    private static Weapon getKnife() {
        return new Weapon(
            WeaponType.KNIFE,
            "Knife",
            new ItemStack(Material.IRON_AXE),
            "this is a knife",
            1,
            0,
            0,
            false,
            0,
            false
        );
    }

    private static Weapon getPistol() {
        return new Weapon(
            WeaponType.GUN,
            "Pistol",
            new ItemStack(Material.DIAMOND_HOE),
            "This is a Pistol",
            8,
            40,
            5,
            false,
            8,
            false
        );
    }

    private static Weapon getM40a3() {
        return new Weapon(
            WeaponType.GUN,
            "M40A3",
            new ItemStack(Material.GUNPOWDER),
            "This is a sniper",
            1,
            60,
            1,
            false,
            20,
            true
        );
    }

    private static Weapon getAK47() {
        return new Weapon(
            WeaponType.GUN,
                "AK47",
            new ItemStack(Material.WOODEN_SHOVEL),
            "This is a rifle",
            30,
            40,
            3,
            true,
            10,
            false
        );
    }

    private static Weapon getP90() {
        return new Weapon(
                WeaponType.GUN,
                "P90",
                new ItemStack(Material.STONE_AXE),
                "This is a rifle",
                30,
                40,
                2,
                true,
                5,
                false
        );
    }

    public static StrikeClass getSniper() {
        return  new StrikeClass(StrikeClassType.SNIPER, List.of(
                getM40a3(), getPistol(), getKnife()
        ));
    }

    public static StrikeClass getAssault() {
        return  new StrikeClass(StrikeClassType.ASSAULT, List.of(
                getAK47(), getPistol(), getKnife()
        ));
    }

    public static StrikeClass getInfiltrator() {
        return  new StrikeClass(StrikeClassType.INFILTRATOR, List.of(
                getP90(), getPistol(), getKnife()
        ));
    }
}
