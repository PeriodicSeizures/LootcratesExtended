package com.crazicrafter1.lce;

import com.crazicrafter1.lce.commands.CmdLCE;
import com.crazicrafter1.lce.config.Config;
import com.crazicrafter1.lce.listeners.ListenerOnChunkLoad;
import com.crazicrafter1.lce.listeners.ListenerOnPlayerArmorStandManipulate;
import com.crazicrafter1.lce.tabcompleters.TabLCE;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    //private static Main plugin;
    private String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.WHITE + "LCE" + ChatColor.DARK_GRAY + "] ";
    //public FileConfiguration config = null;
    public Config config;
    public GenerationHandler generation;

    @Override
    public void onEnable() {
        //plugin = this;

        this.saveDefaultConfig();

        //this.config = this.getConfig();
        this.config = new Config(this);

        config.load();

        this.generation = new GenerationHandler(this);

        VersionChecker updater = new VersionChecker(this, 72032);

        try {
            if (updater.checkForUpdates()) {
                important("New update : " + updater.getLatestVersion() + ChatColor.GOLD + " (" + updater.getResourceURL() + ")");            } else {
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
