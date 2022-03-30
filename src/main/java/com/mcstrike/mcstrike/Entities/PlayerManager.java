package com.mcstrike.mcstrike.Entities;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {
    final HashMap<UUID, StrikePlayer> playersOnline = new HashMap<>();

    public void addPlayer(Player player) {
        UUID key = player.getUniqueId();

        if (!playersOnline.containsKey(key)) {
            // add nonexistent strike-player
            StrikePlayer strPlayer = new StrikePlayer(player);
            playersOnline.put(key, strPlayer);
        }

        // TODO:
        // Load data from database
    }

    public void removePlayer(Player player) {
        UUID key = player.getUniqueId();

        // remove existing key (ignored if key does not exist)
        playersOnline.remove(key);

        // TODO:
        // Update data to database
    }

    public StrikePlayer getPlayer(Player player) {
        UUID key = player.getUniqueId();

        return playersOnline.get(key);
    }
}
