package com.crazicrafter1.lce.tabcompleters;

import com.crazicrafter1.lce.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class TabLCE extends BaseTabCompleter {

    public TabLCE(Main main) {
        super(main, "lce");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> list = new ArrayList<>();

        list.add("game");
        list.add("ruins");

        return list;
    }
}
