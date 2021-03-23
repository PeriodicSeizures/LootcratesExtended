package com.crazicrafter1.lce;

import com.crazicrafter1.lce.commands.CmdLCE;
import com.crazicrafter1.lce.config.Config;
import com.crazicrafter1.lce.listeners.ListenerOnChunkLoad;
import com.crazicrafter1.lce.listeners.ListenerOnPlayerArmorStandManipulate;
import com.crazicrafter1.lce.tabcompleters.TabLCE;
import com.crazicrafter1.lce.util.Util;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {

    //private static Main plugin;
    private String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "LCE" + ChatColor.DARK_GRAY + "] ";
    //public FileConfiguration config = null;
    public Config config;
    public GenerationHandler generation;

    private boolean TIMED_SPAWN_SYNC_ENABLED = false;

    private static int serverTPS = 0;
    private long second = 0;

    public String nmsver = "";
    public boolean useOldMethods = false;

    public static int getServerTPS() {
        return serverTPS;
    }

    private static Main instance;


    @Override
    public void onEnable() {
        instance = this;

        this.config = new Config(this);
        config.load();

        this.generation = new GenerationHandler(this);

        VersionChecker updater = new VersionChecker(this, 72032);

        try {
            if (updater.checkForUpdates()) {
                important("New update : " + updater.getLatestVersion() + ChatColor.GOLD + " (" + updater.getResourceURL() + ")");
            } else {
                feedback("No updates were found!");
            }
        } catch (Exception e) {
            error("Unable to check for updates! " + e.getCause());
            e.printStackTrace();
        }

        new ListenerOnChunkLoad(this);
        new ListenerOnPlayerArmorStandManipulate(this);

        new CmdLCE(this);

        new TabLCE(this);

        // GET SERVER TPS
        new BukkitRunnable() {
            long sec;
            int ticks;

            @Override
            public void run()
            {
                sec = (System.currentTimeMillis() / 1000);

                if(second == sec)
                {
                    ticks++;
                }
                else
                {
                    second = sec;
                    serverTPS = (serverTPS == 0 ? ticks : ((serverTPS + ticks) / 2));
                    ticks = 0;
                }
            }
        }.runTaskTimer(this, 20, 1);

        if (config.isTimeSpawnEnabled()) setITimedSpawning(true);

        new BukkitRunnable() {
            @Override
            public void run() {
                World w = Bukkit.getWorld(config.getWorldName());

                if (w == null) {
                    instance.error("World '" + config.getWorldName() + "'in config doesnt exist");
                    return;
                }

                for (ArmorStand a : w.getEntitiesByClass(ArmorStand.class)) {

                    if (a.getCustomName() != null && a.getCustomName().equals("crateRuinsArmorStand")) {
                        if (a.getHelmet().getType() == Material.AIR) {
                            a.remove();
                            instance.debug("Removing armorstand");
                        }
                    } else instance.debug("Found armorstand (but failed to remove)");
                }
            }
        }.runTaskTimer(this, 10, 10*20);

        nmsver = Bukkit.getServer().getClass().getPackage().getName();
        nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
        if (nmsver.equalsIgnoreCase("v1_8_R1") || nmsver.startsWith("v1_7_")) {
            useOldMethods = true;
        }

    }

    @Override
    public void onDisable() {
        //getConfig().set("timed-spawn-min", config.getMinSpawnTime());

        saveConfig();

        stopITimedSpawn();
    }

    public void setITimedSpawning(boolean b)
    {
        if (b && !TIMED_SPAWN_SYNC_ENABLED) startITimedSpawn();
        else stopITimedSpawn();
    }

    private void stopITimedSpawn()
    {
        config.setTimedSpawn(false);
        this.getServer().getScheduler().cancelTasks(this);
        TIMED_SPAWN_SYNC_ENABLED = false;
    }

    private void startITimedSpawn()
    {
        config.setTimedSpawn(true);

        new BukkitRunnable() {
            @Override
            public void run() {

                // now spawn

                int[] b = config.getNaturalGenBounds();

                int x = Util.randomRange(b[0], b[2]);
                int z = Util.randomRange(b[1], b[3]);

                Location randLoc = new Location(Bukkit.getWorld(getConfig().getString("timed-spawn-world")), x, 255, z);

                generation.spawnCrate(randLoc);

                if (!getConfig().getString("timed-spawn.message").isEmpty())
                {
                    // then broadcast

                    String m = getConfig().getString("timed-spawn-message");

                    m.replaceAll("\\{}", "" + x + " " + z);

                    m = ChatColor.translateAlternateColorCodes(
                            '&', m);
                    
                    Bukkit.broadcast(m
                            , "lce.cratespawn");
                }

                startITimedSpawn();
            }
        }.runTaskLater(this,
                Util.randomRange(config.getMinSpawnTime(), config.getMaxSpawnTime()));
    }

    public boolean feedback(String s) {
        return feedback(this.getServer().getConsoleSender(), s);
    }

    public boolean important(String s) {
        return important(this.getServer().getConsoleSender(), s);
    }

    public boolean error(String s) {
        return error(this.getServer().getConsoleSender(), s);
    }

    public boolean feedback(CommandSender sender, String s) {
        sender.sendMessage(prefix + ChatColor.GRAY + s);
        return true;
    }

    public boolean important(CommandSender sender, String s) {
        sender.sendMessage(prefix + ChatColor.AQUA + s);
        return true;
    }

    public boolean error(CommandSender sender, String s) {
        sender.sendMessage(prefix + ChatColor.RED + s);
        return true;
    }

    public void debug(String s) {
        if (config.isDebugEnabled()) this.getServer().getConsoleSender().sendMessage(prefix + ChatColor.YELLOW + s);
    }

}
