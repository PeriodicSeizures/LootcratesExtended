package com.crazicrafter1.lce.commands;

import com.crazicrafter1.lce.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand implements CommandExecutor {

    protected static Main plugin;

    public BaseCommand(Main main, String name) {

        if (plugin == null) plugin = main;

        plugin.getCommand(name).setExecutor(this);
    }

    boolean error(CommandSender sender, String message) {
        return plugin.error(sender, message);
    }

    boolean feedback(CommandSender sender, String message) {
        return plugin.feedback(sender, message);
    }

    @Override
    public abstract boolean onCommand(CommandSender sender, Command command, String label, String[] args);

}
