package com.crazicrafter1.lce.listeners;

import com.crazicrafter1.lce.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class BaseListener implements Listener {

    protected static Main plugin = null; //Main.getInstance();

    public BaseListener(Main main) {
        if (plugin == null) plugin = main;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

}
