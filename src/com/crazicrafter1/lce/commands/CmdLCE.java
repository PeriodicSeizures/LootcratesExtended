package com.crazicrafter1.lce.commands;

import com.crazicrafter1.lce.Main;
import com.crazicrafter1.lce.config.Config;
import com.crazicrafter1.lootcrates.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CmdLCE extends BaseCommand{

    public CmdLCE(Main plugin) {
        super(plugin, "lce");

        //Bukkit.getPluginCommand("crates").getExecutor().

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) return false;

        switch (args[0].toLowerCase()) {

            case "ruins": {

                if (!(sender instanceof Player)) return error(sender, "Only a player may execute this command");

                Player p = (Player)sender;

                plugin.generation.spawnCrateRuins(p.getLocation());

                //p.getInventory().addItem(me.zombie_striker.qg.api.QualityArmory.getGunByName("10mmpistol").getItemStack());
                //p.getInventory().addItem(me.zombie_striker.qg.api.QualityArmory.getAmmoByName("10mmpistol").getItemStack());

                //p.sendMessage("You received a 10mmpistol!");

                return feedback(sender, "Spawned a crate ruins");
            }

            case "game": {


                if (!(sender instanceof Player)) return error(sender, "Only a player may execute this command");
                Player p = (Player)sender;

                int[] bounds = plugin.config.getNaturalGenBounds();

                int x = Util.randomRange(bounds[0], bounds[2]);
                int z = Util.randomRange(bounds[1], bounds[3]);

                int y = p.getWorld().getHighestBlockYAt(x, z);

                plugin.generation.spawnCrateRuins(
                        new Location(p.getWorld(), x, y, z));

                String message = ChatColor.translateAlternateColorCodes('&',
                        plugin.config.getBroadcastMessage().replaceAll("\\{world}", p.getWorld().getName())
                                .replaceAll("\\{location}", String.format("%d %d", x, z)));

                Bukkit.broadcastMessage(message);

                return feedback(sender, "Spawned the crate at " + x + " " + y + " " + z);


            }

            case "reload": {
                feedback(sender, "Reloading config...");
                //plugin.saveConfig();
                plugin.config.load();
                return feedback(sender, "Config was reloaded. Should usually restart to avoid issues.");
            }

            case "timer": {

                if (args.length == 2)
                {
                    boolean isOn = args[1].equals("on");
                    //if (args[1].equals("off")) isOn = false;

                    plugin.config.setTimedSpawn(isOn);

                    return plugin.feedback(sender, "Timer was set to " + isOn);

                    //
                }

            }

        }


        return false;
    }
}
