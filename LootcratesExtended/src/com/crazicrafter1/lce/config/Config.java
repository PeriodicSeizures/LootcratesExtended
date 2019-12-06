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

    public boolean isQAHooked() {
        //me.zombie_striker.qg.api.QualityArmory.get
        return configFile.getBoolean("qa-hook");
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



        for (String crate : configFile.getConfigurationSection("crates").getKeys(false)) {
            ItemBuilder builder = ItemBuilder.builder(Material.matchMaterial(configFile.getString("crates."+crate+".item"))).
                    name(configFile.getString("crates."+crate+".name")).
                    lore(configFile.getStringList("crates."+crate+".lore"));

            //ItemStack item = new ItemStack(Material.matchMaterial(configFile.getString("crates."+crate+".item")));
            //Util.setName(item, configFile.getString("crates."+crate+".name"));
            //Util.setLore(item, configFile.getStringList("crates."+crate+".lore"));

            LootcratesAPI.registerCrate(new Crate(crate, builder.toItem()));



            for (String lootGroup : configFile.getConfigurationSection("crates."+crate+".chances").getKeys(false)) {
                //crates.put()
                //lootGroups.put()
                LootcratesAPI.getConfig().crates.get(crate).addChance(lootGroup, configFile.getInt("crates."+crate+".chances."+lootGroup));
                //chances.put(lootTier, configFile.getInt("crates."+crate+".chances."+lootTier));

                //tiers.add(lootGroup);
            }
            //loot_chances.put(crate, chances);

            //crates.add(crate);
        }



        //loot = new HashMap<>();
        for(String lootTier : configFile.getConfigurationSection("loot").getKeys(false)) {
            ArrayList<String> items = new ArrayList<>();
            for (String itemKey : configFile.getConfigurationSection("loot." + lootTier + ".items").getKeys(false)) {
                //Main.print("lootItem : " + itemKey);
                StringBuilder itemString = new StringBuilder();

                String itemTypeString = configFile.getString("loot." + lootTier + ".items." + itemKey + ".item");
                String qaTypeString = configFile.getString("loot." + lootTier + ".items." + itemKey + ".qa-item");
                if (itemTypeString != null) {
                    itemString.append("item: ").append(Material.matchMaterial(itemTypeString)).append(" ");
                    if (configFile.isSet("loot." + lootTier + ".items." + itemKey + ".count")) {
                        itemString.append("count: ").append(configFile.getInt("loot." + lootTier + ".items." + itemKey + ".count")).append(" ");
                    } else if (configFile.isSet("loot." + lootTier + ".items." + itemKey + ".range")) {
                        itemString.append("range: ").append(configFile.getString("loot." + lootTier + ".items." + itemKey + ".range")).append(" ");
                    } else {
                        itemString.append("count: 1 ");
                    }
                } else if (qaTypeString != null) {
                    itemString.append("qa-item: ").append(qaTypeString);

                    if (configFile.isSet("loot." + lootTier + ".items." + itemKey + ".count")) {
                        itemString.append("count: ").append(configFile.getInt("loot." + lootTier + ".items." + itemKey + ".count")).append(" ");
                    } else if (configFile.isSet("loot." + lootTier + ".items." + itemKey + ".range")) {
                        itemString.append("range: ").append(configFile.getString("loot." + lootTier + ".items." + itemKey + ".range")).append(" ");
                    } else {
                        itemString.append("count: 1 ");
                    }

                    continue;
                }







                if (configFile.isSet("loot." + lootTier + ".items." + itemKey + ".name")) {
                    itemString.append("name: \"").append(configFile.getString("loot." + lootTier + ".items." + itemKey + ".name")).append("\" ");
                }



                if (configFile.isSet("loot." + lootTier + ".items." + itemKey + ".lore")) {
                    List<String> loreList = configFile.getStringList("loot." + lootTier + ".items." + itemKey + ".lore");

                    for (String lore : loreList) {
                        itemString.append("lore: \"").append(lore).append("\" ");
                    }
                }



                if (configFile.isSet("loot." + lootTier + ".items." + itemKey + ".enchantments")) {
                    Set<String> enchantmentList = configFile.getConfigurationSection("loot." + lootTier + ".items." + itemKey + ".enchantments").getKeys(false);
                    for (String enchantment : enchantmentList) {
                        if (Util.matchEnchant(enchantment) != null)
                            itemString.append("enchantment: ").append(enchantment).append(" ");

                        if (configFile.isSet("loot." + lootTier + ".items." + itemKey + ".enchantments." + enchantment + ".level"))
                            itemString.append("level: ").append(configFile.getInt("loot." + lootTier + ".items." + itemKey + ".enchantments." + enchantment + ".level")).append(" ");
                        else if (configFile.isSet("loot." + lootTier + ".items." + itemKey + ".enchantments." + enchantment + ".range"))
                            itemString.append("range: ").append(configFile.getString("loot." + lootTier + ".items." + itemKey + ".enchantments." + enchantment + ".range")).append(" ");
                    }
                }



                if (configFile.isSet("loot." + lootTier + ".items." + itemKey + ".command")) {
                    List<String> commandList = configFile.getStringList("loot." + lootTier + ".items." + itemKey + ".command");
                    for (String s : commandList) itemString.append("command: \"").append(s).append("\" ");
                }
                items.add(itemString.toString());
            }

            if (configFile.isSet("loot." + lootTier + ".crates")) {
                for (String crate : configFile.getConfigurationSection("loot." + lootTier + ".crates").getKeys(false)) {
                    String data = "crate: " + crate + " ";

                    if (LootcratesAPI.getConfig().crates.containsKey(crate)) {
                        if (configFile.isSet("loot." + lootTier + ".crates." + crate + ".count")) {
                            data += "count: " + configFile.getInt("loot." + lootTier + ".crates." + crate + ".count");
                        } else if (configFile.isSet("loot." + lootTier + ".crates." + crate + ".range")) {
                            data += "range: " + configFile.getInt("loot." + lootTier + ".crates." + crate + ".count");
                        }
                    }
                    items.add(data);
                }
            }
            if (LootcratesAPI.getConfig().lootGroups.containsKey(lootTier))
                LootcratesAPI.getConfig().lootGroups.get(lootTier).addParsedItems(items);
            //loot.put(lootTier, items);
        }

        return true;
    }


}
