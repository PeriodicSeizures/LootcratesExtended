package com.crazicrafter1.lce.tabcompleters;

import com.crazicrafter1.lce.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public abstract class BaseTabCompleter implements TabCompleter {

    protected static Main plugin = null;

    public BaseTabCompleter(Main main, String name) {

        if (plugin == null) plugin = main;

        plugin.getCommand(name).setTabCompleter(this);
    }

    @Override
    public abstract List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args);

    /*
    protected ArrayList<String> auto(String arg, String[] args) {

        ArrayList<String> list = new ArrayList<>();

        for (String s : args) {
            if (arg.length() > 0) {
                if (s.equalsIgnoreCase(arg)) return new ArrayList<>(Collections.singletonList(s));
                else if (s.length() > arg.length()){

                    if (s.substring(0, arg.length()+1).equals(arg))
                        list.add(s);
                }
            } else list.add(s);
        }

        return list;
    }

     */
}
