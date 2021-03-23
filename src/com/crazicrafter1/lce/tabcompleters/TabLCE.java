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

        switch (args.length) {
            case 1: {
                list.add("game");
                list.add("ruins");
                list.add("reload");
                list.add("timer");
                break;
            }case 2: {
                if (args[0].equals("timer")) {
                    list.add("on");
                    list.add("off");
                }
                break;
            }
        }

        return list;
    }
}
