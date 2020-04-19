package com.crazicrafter1.lce.config;

import com.crazicrafter1.lce.Main;
import com.crazicrafter1.lootcrates.ItemBuilder;
import com.crazicrafter1.lootcrates.LootcratesAPI;
import com.crazicrafter1.lootcrates.Util;
import com.crazicrafter1.lootcrates.config.Crate;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Config {

    private static Main plugin;

    private FileConfiguration configFile;

    public boolean isDebugEnabled() {
        return configFile.getBoolean("debug");
    }

    public boolean areCratesRuined() {
        return configFile.getBoolean("ruin-generation");
    }

    public boolean isNaturalCrateGenEnabled() {
        return configFile.getBoolean("generation");
    }

    public boolean areRuinsRemovable() {
        return configFile.getBoolean("remove-ruins");
    }

    public int[] getNaturalGenBounds() {
        return new int[] {configFile.getInt("x-min"),
            configFile.getInt("z-min"),
            configFile.getInt("x-max"),
            configFile.getInt("z-max")};
    }

    public String getBroadcastMessage() {
        return configFile.getString("message");
    }

    // worldname, chance
    public HashMap<String, Integer> crateSpawnWorldChances;

    // cratetype, chance
    public HashMap<String, Integer> crateTypeWorldChances;

    public Config(Main plugin) {
        Config.plugin = plugin;

        plugin.saveDefaultConfig();
        configFile = plugin.getConfig();
    }

    public void reload() {
        //configFile.
        plugin.reloadConfig();
        load();
    }

    public boolean load() {
        crateSpawnWorldChances = new HashMap<>(); // = new ArrayList<>(configFile.getConfigurationSection("world-generation").getKeys(false));
        crateTypeWorldChances = new HashMap<>();
        for (String worldName : configFile.getConfigurationSection("generation-settings").getKeys(false)) {
            crateSpawnWorldChances.put(worldName, configFile.getInt("generation-settings."+worldName+".chance"));
            for (String crateType : configFile.getConfigurationSection("generation-settings."+worldName+".crates").getKeys(false)) {
                crateTypeWorldChances.put(crateType, configFile.getInt("generation-settings."+worldName+".crates."+crateType));
            }
        }

        return true;
    }


}
