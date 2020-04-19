package com.crazicrafter1.lce.util;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Random;

public class Util {


    public static boolean inRange(int i, int min, int max) {
        return (i <= max && i >= min);
    }


    public static Color matchColor(String c)
    {
        String color = c.toUpperCase();
        // BLUE, RED, WHITE, GRAY, GREEN, YELLOW, AQUA, BLACK, FUCHSIA, LIME, MAROON, NAVY, OLIVE
        // ORANGE, PURPLE, SILVER, TEAL
        if (color.equals("BLUE"))
            return Color.BLUE;
        if (color.equals("RED"))
            return Color.RED;
        if (color.equals("WHITE"))
            return Color.WHITE;
        if (color.equals("GRAY"))
            return Color.GRAY;
        if (color.equals("GREEN"))
            return Color.GREEN;
        if (color.equals("YELLOW"))
            return Color.YELLOW;
        if (color.equals("AQUA"))
            return Color.AQUA;
        if (color.equals("BLACK"))
            return Color.BLACK;
        if (color.equals("FUCHSIA"))
            return Color.FUCHSIA;
        if (color.equals("LIME"))
            return Color.LIME;
        if (color.equals("MAROON"))
            return Color.MAROON;
        if (color.equals("NAVY"))
            return Color.NAVY;
        if (color.equals("OLIVE"))
            return Color.OLIVE;
        if (color.equals("ORANGE"))
            return Color.ORANGE;
        if (color.equals("PURPLE"))
            return Color.PURPLE;
        if (color.equals("SILVER"))
            return Color.SILVER;
        if (color.equals("TEAL"))
            return Color.TEAL;
        return null;
    }

    public static void setName(ItemStack item, String name)
    {
        /*
        name
         */
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&r"+name));
        //item.setItemMeta(meta);


        item.setItemMeta(meta);
    }

    public static void setLore(ItemStack item, List<String> lore)
    {
        ItemMeta meta = item.getItemMeta();

        List<String> loreList = lore;

        for (int i=0; i<loreList.size(); i++)
        {
            loreList.set(i, ChatColor.translateAlternateColorCodes('&', "&r"+loreList.get(i)));
        }

        meta.setLore(lore);

        item.setItemMeta(meta);
    }

    public static int randomRange(int min, int max)
    {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public static int randomRange(int min, int max, int min1, int max1)
    {
        if ((int)(Math.random()*2) == 0)
            return min + (int)(Math.random() * ((max - min) + 1));
        return min1 + (int)(Math.random() * ((max1 - min1) + 1));
    }

    public static int randomRange(int min, int max, Random random)
    {
        return random.nextInt((max - min) + 1) + min;
    }

    // argument: float 0 -> 1
    public static boolean randomChance(float i)
    {
        return i >= Math.random();
    }

    public static boolean randomChance(float i, Random random)
    {
        return i <= (float)randomRange(0, 100, random) / 100f;
    }

    public static int sqDist(int x1, int y1, int x2, int y2) {
        return (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2);
    }

    public static int safeToInt(String s)
    {
        //if ()
        // test if is numeric

        if (isNumeric(s))
        {
            return Integer.parseInt(s);
        }
        return -1;
    }

    public static boolean isNumeric(String s)
    {
        for (int i=0;i<s.length();i++)
        {
            try {
                Integer.parseInt(s.substring(i));
            }
            catch(NumberFormatException e)
            {
                return false;
            }
        }
        return true;
    }

    public static Enchantment matchEnchant(String enchant)
    {
        String e = enchant.toUpperCase();

        if (e.equals("DURABILITY") || e.equals("UNBREAKING"))
            return Enchantment.DURABILITY;
        if (e.equals("ARROW_DAMAGE") || e.equals("POWER"))
            return Enchantment.ARROW_DAMAGE;
        if (e.equals("ARROW_FIRE") || e.equals("FLAME"))
            return Enchantment.ARROW_FIRE;
        if (e.equals("ARROW_INFINITE") || e.equals("INFINITY"))
            return Enchantment.ARROW_INFINITE;
        if (e.equals("ARROW_KNOCKBACK") || e.equals("PUNCH"))
            return Enchantment.ARROW_KNOCKBACK;
        if (e.equals("BINDING_CURSE") || e.equals("CURSE_OF_BINDING"))
            return Enchantment.BINDING_CURSE;
        if (e.equals("CHANELLING"))
            return Enchantment.CHANNELING;
        if (e.equals("DAMAGE_ALL") || e.equals("SHARPNESS"))
            return Enchantment.DAMAGE_ALL;
        if (e.equals("DAMAGE_ANTHROPODS") || e.equals("BANE_OF_ANTHROPODS"))
            return Enchantment.DAMAGE_ARTHROPODS;
        if (e.equals("DAMAGE_UNDEAD") || e.equals("SMITE"))
            return Enchantment.DAMAGE_UNDEAD;
        if (e.equals("DEPTH_STRIDER"))
            return Enchantment.DEPTH_STRIDER;
        if (e.equals("DIG_SPEED") || e.equals("EFFICIENCY"))
            return Enchantment.DIG_SPEED;
        if (e.equals("DURABILITY"))
            return Enchantment.DURABILITY;
        if (e.equals("FIRE_ASPECT"))
            return Enchantment.FIRE_ASPECT;
        if (e.equals("FROST_WALKER"))
            return Enchantment.FIRE_ASPECT.FROST_WALKER;
        if (e.equals("IMPALING"))
            return Enchantment.IMPALING;
        if (e.equals("KNOCKBACK"))
            return Enchantment.KNOCKBACK;
        if (e.equals("LOOT_BONUS_BLOCKS") || e.equals("FORTUNE"))
            return Enchantment.LOOT_BONUS_BLOCKS;
        if (e.equals("LOOT_BONUS_MOBS") || e.equals("LOOTING"))
            return Enchantment.LOOT_BONUS_MOBS;
        if (e.equals("LOYALTY"))
            return Enchantment.LOYALTY;
        if (e.equals("LUCK") || e.equals("LUCK_OF_THE_SEA"))
            return Enchantment.LUCK;
        if (e.equals("LURE"))
            return Enchantment.LURE;
        if (e.equals("MENDING"))
            return Enchantment.MENDING;
        if (e.equals("MULTISHOT"))
            return Enchantment.MULTISHOT;
        if (e.equals("OXYGEN") || e.equals("RESPIRATION"))
            return Enchantment.OXYGEN;
        if (e.equals("PIERCING"))
            return Enchantment.PIERCING;
        if (e.equals("PROTECTION_ENVIRONMENTAL") || e.equals("PROTECTION"))
            return Enchantment.PROTECTION_ENVIRONMENTAL;
        if (e.equals("PROTECTION_FIRE") || e.equals("FIRE_PROTECTION"))
            return Enchantment.PROTECTION_FIRE;
        if (e.equals("PROTECTION_FALL") || e.equals("FEATHER_FALLING"))
            return  Enchantment.PROTECTION_FALL;
        if (e.equals("PROTECTION_EXPLOSIONS") || e.equals("BLAST_PROTECTION"))
            return Enchantment.PROTECTION_EXPLOSIONS;
        if (e.equals("PROTECTION_PROJECTILE") || e.equals("PROJECTILE_PROTECTION"))
            return Enchantment.PROTECTION_PROJECTILE;
        if (e.equals("QUICK_CHARGE"))
            return Enchantment.QUICK_CHARGE;
        if (e.equals("RIPTIDE"))
            return Enchantment.RIPTIDE;
        if (e.equals("SILK_TOUCH"))
            return Enchantment.SILK_TOUCH;
        if (e.equals("SWEEPING_EDGE"))
            return Enchantment.SWEEPING_EDGE;
        if (e.equals("THORNS"))
            return Enchantment.THORNS;
        if (e.equals("VANISHING_CURSE") || e.equals("CURSE_OF_VANISHING"))
            return Enchantment.VANISHING_CURSE;
        if (e.equals("WATER_WORKER") || e.equals("AQUA_AFFINITY"))
            return Enchantment.WATER_WORKER;
        return null;
    }


}
