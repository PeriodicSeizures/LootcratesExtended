package com.crazicrafter1.lce;

import com.crazicrafter1.lce.util.Util;
import com.crazicrafter1.lootcrates.crate.Crate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class GenerationHandler {

    private Main plugin;

    GenerationHandler(Main plugin) {
        this.plugin = plugin;

        //int rand =
        int end = 0;
        for (String crateType : plugin.config.crateTypeWorldChances.keySet()) {
            for (int i=0; i<plugin.config.crateTypeWorldChances.get(crateType); i++) {
                randMap[end] = crateType;
                end++;
            }
        }
    }

    private String[] randMap = new String[100];

    public void spawnCrate(Location loc) {

        if (Main.getServerTPS() < plugin.config.getMinTPS()) return;

        //Location loc = new Location(e.getWorld(), x, e.getWorld().getHighestBlockYAt(x, z), z, 0, 0);
        World w = loc.getWorld();

        int x = loc.getBlockX();
        int z = loc.getBlockZ();
        //int y = loc.getWorld().getHighestBlockYAt(x, z);
        int y = loc.getBlockY();

        while (y > 0 && w.getBlockAt(x, y, z).getType() == Material.AIR)
            y--;

        y++;


        //ArmorStand as = w.spawn(new Location(loc.getWorld(), x+1.15, y-.65, z+.25), ArmorStand.class);
        //ArmorStand as = w.spawn(new Location(loc.getWorld(), x+1.15, y-.35, z+.25), ArmorStand.class);
        ArmorStand as = w.spawn(new Location(loc.getWorld(), x + .5, y - 1.4, z + .5), ArmorStand.class);

        //e.getWorld().dropItemNaturally(loc, randomCrate());

        as.setArms(true);
        as.setBasePlate(false);
        as.setVisible(false);

        // IF 1.8
        //Class clazz = as.getClass();

        //Field field = clazz.getDeclaredField("invulnerable");

        as.setHelmet(randomCrate());
        //as.setChestplate(randomCrate());
        //as.setRightArmPose(new EulerAngle(-Math.PI/12.0, Math.PI/4.0, 0));
        as.setGravity(false);

        as.setCustomName("crateRuinsArmorStand");

        if (Bukkit.getVersion().contains("1.8")) {



            //EntityArmorStand e = ((CraftArmorStand) as).getHandle();

            try {
                Class<?> craftArmorStandClass = Class.forName("org.bukkit.craftbukkit." + plugin.nmsver + ".entity.CraftArmorStand");
                //Class<?> entityArmorStandClass = as.getClass().asSubclass(Class.forName("org.bukkit.craftbukkit." + plugin.nmsver + ".EntityArmorStand"));

                Object craftArmorStand = craftArmorStandClass.cast(as);
                Method craftArmorStandClassHandleMethod = craftArmorStandClass.getDeclaredMethod("getHandle");
                Object entityArmorStand = craftArmorStandClassHandleMethod.invoke(craftArmorStand);



                Field field = entityArmorStand.getClass().getSuperclass().getSuperclass().getDeclaredField("invulnerable");
                field.setAccessible(true);
                field.set(entityArmorStand, true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            //Entity

            //as.setIn
            //return;

            as.setInvulnerable(true);
            //as.setItemInHand(randomCrate());

            //as.setAI(false);
            //as.setCollidable(false);
        }
    }

    private ItemStack randomCrate() {
        String c = randMap[Util.randomRange(0,99)];
        plugin.debug("crate: " + c);

        Crate crate = com.crazicrafter1.lootcrates.Main.crates.getOrDefault(c, null);
        if (crate != null) {
            /*
                TODO:
                fix seasonal in LootCrates
             */
            return crate.getPreppedItemStack(false, 1);
        } else {
            plugin.error("Crate " + c + " doesn't exist");
        }
        return null;
    }

    public void spawnCrateRuins(Location loc) {

        if (Main.getServerTPS() < plugin.config.getMinTPS()) return;

        //Location loc = new Location(e.getWorld(), x, e.getWorld().getHighestBlockYAt(x, z), z, 0, 0);
        World w = loc.getWorld();

        int x = loc.getBlockX();
        int z = loc.getBlockZ();
        //int y = loc.getWorld().getHighestBlockYAt(x, z);
        int y = loc.getBlockY();

        while (y > 0 && w.getBlockAt(x,y,z).getType() == Material.AIR)
            y--;

        y++;


        //ArmorStand as = w.spawn(new Location(loc.getWorld(), x+1.15, y-.65, z+.25), ArmorStand.class);
        //ArmorStand as = w.spawn(new Location(loc.getWorld(), x+1.15, y-.35, z+.25), ArmorStand.class);
        ArmorStand as = w.spawn(new Location(loc.getWorld(), x+.5, y-1.4, z+.5), ArmorStand.class);

        //e.getWorld().dropItemNaturally(loc, randomCrate());

        as.setArms(true);
        as.setBasePlate(false);
        as.setVisible(false);
        as.setHelmet(randomCrate());
        //as.setChestplate(randomCrate());
        //as.setRightArmPose(new EulerAngle(-Math.PI/12.0, Math.PI/4.0, 0));
        as.setGravity(false);

        as.setCustomName("crateRuinsArmorStand");

        if (Bukkit.getVersion().contains("1.8")) {

            try {
                Class<?> craftArmorStandClass = Class.forName("org.bukkit.craftbukkit." + plugin.nmsver + ".entity.CraftArmorStand");
                //Class<?> entityArmorStandClass = as.getClass().asSubclass(Class.forName("org.bukkit.craftbukkit." + plugin.nmsver + ".EntityArmorStand"));

                Object craftArmorStand = craftArmorStandClass.cast(as);
                Method craftArmorStandClassHandleMethod = craftArmorStandClass.getDeclaredMethod("getHandle");
                Object entityArmorStand = craftArmorStandClassHandleMethod.invoke(craftArmorStand);



                Field field = entityArmorStand.getClass().getSuperclass().getSuperclass().getDeclaredField("invulnerable");
                field.setAccessible(true);
                field.set(entityArmorStand, true);
                field.setAccessible(false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            //Entity

            //as.setIn
            //return;

            as.setInvulnerable(true);
            //as.setItemInHand(randomCrate());

            //as.setAI(false);
            //as.setCollidable(false);
        }



        //plugin.important("det ruining?");
        if (plugin.config.areCratesRuined()) {
            //plugin.important("ruining...");
            //plugin.debug("Y: " + y);

            //NMSHandler.setBlock(Blocks.CAULDRON, w, x, y, z);
            //w.getBlockAt(x, y, z).setType(Material.CAULDRON);
            //Block[] blocks = new Block[] {Blocks.COBBLESTONE, Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS, Blocks.OBSIDIAN};
            Material[] materials = new Material[]{Material.COBBLESTONE, Material.STONE, Material.OBSIDIAN};

            int r = 4;

            for (int rx = -r; rx < r; rx++) {

                for (int rz = -r; rz < r; rz++) {

                    for (int ry = -r; ry < r; ry++) {

                        int h = y + ry;

                        //plugin.debug("VERSION: " + Bukkit.getVersion());

                        if (Bukkit.getVersion().contains("1.8")) {
                            if (!(w.getBlockAt(x + rx, h, z + rz).getType() == Material.GRASS ||
                                    w.getBlockAt(x + rx, h, z + rz).getType() == Material.SAND)) {
                                //plugin.important("skipping...");
                                continue;
                            }
                        } else
                            if (!(w.getBlockAt(x + rx, h, z + rz).getType() == Material.GRASS_BLOCK ||
                                    w.getBlockAt(x + rx, h, z + rz).getType() == Material.SAND)) {
                                //plugin.important("skipping...");
                                continue;
                            }

                        // fill with blocks
                        int sqDist = Util.sqDist(rx, rz, 0, 0);

                        float n = ((float) ((r * r) - sqDist + 1));
                        float d = (float) (r * r);

                        if (sqDist <= r * r) {
                            if (Util.randomChance(n / d)) {

                                //Block randBlock = blocks[(int) (Math.random() * blocks.length)];
                                //NMSHandler.setBlock(randBlock, w, x + rx, h, z + rz);


                                Material randMaterial = materials[(int) (Math.random() * materials.length)];

                                w.getBlockAt(x + rx, h, z + rz).setType(randMaterial);

                            }
                            if (Util.randomChance((((float) (r * r) - n) / d) - .3f)) {

                                //NMSHandler.setBlock(Blocks.IRON_BARS, w, x + rx, h+1, z + rz);

                                //w.getBlockAt(x + rx, h + 1, z + rz).setType(Material.IRON_BARDING);

                            }
                        }
                    }
                }
            }
        }

    }


}
