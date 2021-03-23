package com.crazicrafter1.lce.listeners;

import com.crazicrafter1.lce.Main;
//import com.crazicrafter1.lce.util.NMSHandler;
//import net.minecraft.server.v1_14_R1.Blocks;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class ListenerOnPlayerArmorStandManipulate extends BaseListener {

    public ListenerOnPlayerArmorStandManipulate(Main main) {
        super(main);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(PlayerArmorStandManipulateEvent e) {

        //e.getRightClicked().setCustomName();
        if (e.getRightClicked().getCustomName() != null && e.getRightClicked().getCustomName().equals("crateRuinsArmorStand")) {

            World w = e.getRightClicked().getLocation().getWorld();


            if (e.getPlayerItem().getType() != Material.AIR)
            {
                e.setCancelled(true);

                w.dropItemNaturally(e.getRightClicked().getLocation(), e.getRightClicked().getHelmet());
            }

            e.getRightClicked().remove();

            if (plugin.config.areCratesRuined() && plugin.config.areRuinsRemovable()) {
                int x = e.getRightClicked().getLocation().getBlockX();
                int y = e.getRightClicked().getLocation().getBlockY();
                int z = e.getRightClicked().getLocation().getBlockZ();
                //Block[] blocks = new Block[] {Blocks.COBBLESTONE, Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS, Blocks.OBSIDIAN};
                Material[] materials = new Material[]{Material.COBBLESTONE, Material.OBSIDIAN};

                int r = 4;

                for (int rx = -r; rx < r; rx++) {

                    for (int rz = -r; rz < r; rz++) {

                        for (int ry = -r - 1; ry < r + 1; ry++) {

                            int h = y + ry;

                            switch (w.getBlockAt(x + rx, h, z + rz).getType()) {

                                case CAULDRON:
                                //case IRON_BARDING:
                                    //NMSHandler.setBlock(Blocks.AIR, w, x + rx, h, z + rz);
                                    w.getBlockAt(x + rx, h, z + rz).setType(Material.AIR);
                                    break;
                                case COBBLESTONE:
                                case OBSIDIAN:
                                    //NMSHandler.setBlock(Blocks.GRASS_BLOCK, w, x + rx, h, z + rz);
                                    w.getBlockAt(x + rx, h, z + rz).setType(Material.GRASS);
                                    break;

                            }
                        }
                    }
                }
            }
        }
    }
}
