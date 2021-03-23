package com.crazicrafter1.lce.config;

import com.crazicrafter1.lce.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.HashMap;

public class Config {

    private static Main plugin;

    private static FileConfiguration file;

    private String worldName;

    public boolean isDebugEnabled() {
        return file.getBoolean("debug");
    }

    public boolean areCratesRuined() {
        return file.getBoolean("ruin-generation");
    }

    public boolean isNaturalCrateGenEnabled() {
        return file.getBoolean("generation");
    }

    public boolean areRuinsRemovable() {
        return file.getBoolean("remove-ruins");
    }

    public boolean isTimeSpawnEnabled() {
        return file.getBoolean("timed-spawn");
    }

    public int getMinSpawnTime()
    {
        return file.getInt("timed-spawn-min");
    }

    public int getMaxSpawnTime()
    {
        return file.getInt("timed-spawn-max");
    }

    public void setTimedSpawn(boolean b)
    {
        file.set("timed-spawn", b);
    }

    public int[] getNaturalGenBounds() {
        return new int[] {file.getInt("x-min"),
            file.getInt("z-min"),
            file.getInt("x-max"),
            file.getInt("z-max")};
    }

    public String getBroadcastMessage() {
        return file.getString("message");
    }

    public String getWorldName() {
        return this.worldName;
    }

    // worldname, chance
    public HashMap<String, Integer> crateSpawnWorldChances;

    // cratetype, chance
    public HashMap<String, Integer> crateTypeWorldChances;

    public Config(Main plugin) {
        Config.plugin = plugin;

        file = plugin.getConfig();
    }

    public int getMinTPS() {
        return file.getInt("spawn-tps");
    }

    public boolean load() {
        if (!new File(plugin.getDataFolder(), "config.yml").exists()) {
            plugin.saveDefaultConfig();
        }
        plugin.reloadConfig();

        crateSpawnWorldChances = new HashMap<>(); // = new ArrayList<>(file.getConfigurationSection("world-generation").getKeys(false));
        crateTypeWorldChances = new HashMap<>();

        for (String worldName : file.getConfigurationSection("generation-settings").getKeys(false)) {
            crateSpawnWorldChances.put(worldName, file.getInt("generation-settings."+worldName+".chance"));
            for (String crateType : file.getConfigurationSection("generation-settings."+worldName+".crates").getKeys(false)) {
                crateTypeWorldChances.put(crateType, file.getInt("generation-settings."+worldName+".crates."+crateType));
            }
            this.worldName = worldName;
        }

        return true;
    }


}
