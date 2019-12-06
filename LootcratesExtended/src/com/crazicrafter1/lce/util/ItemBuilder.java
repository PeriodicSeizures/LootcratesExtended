package com.crazicrafter1.lce.util;

import net.minecraft.server.v1_14_R1.*;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static ItemBuilder builder(Material material) {
        return new ItemBuilder(material);
    }

    public static ItemBuilder builder(ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder name(String name)
    {
        if (name == null) return getBuilder();

        ItemStack item = new ItemStack(itemStack);
        ItemMeta meta = item.getItemMeta();

        //noinspection ConstantConditions
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&r"+name));
        item.setItemMeta(meta);
        return new ItemBuilder(item);
    }

    public ItemBuilder lore(List<String> lores)
    {
        if (lores == null) return getBuilder();

        ItemStack item = new ItemStack(itemStack);

        ItemMeta meta = item.getItemMeta();

        ArrayList<String> loreList = new ArrayList<>(lores);

        for (int i=0; i<loreList.size(); i++)
        {
            loreList.set(i, ChatColor.translateAlternateColorCodes('&', "&r"+loreList.get(i)));
        }

        meta.setLore(loreList);

        item.setItemMeta(meta);
        return new ItemBuilder(item);
    }

    public ItemBuilder lore(String[] lores)
    {
        if (lores == null) return getBuilder();

        ItemStack item = new ItemStack(itemStack);

        ItemMeta meta = item.getItemMeta();

        ArrayList<String> loreList = new ArrayList<>(Arrays.asList(lores));

        for (int i=0; i<loreList.size(); i++)
        {
            loreList.set(i, ChatColor.translateAlternateColorCodes('&', "&r"+loreList.get(i)));
        }

        meta.setLore(loreList);

        item.setItemMeta(meta);
        return new ItemBuilder(item);
    }


    public ItemBuilder dye(int r, int g, int b) {
        ItemStack item = new ItemStack(itemStack);

        if (!(item.getItemMeta() instanceof LeatherArmorMeta)) return getBuilder();

        LeatherArmorMeta meta = ((LeatherArmorMeta)item.getItemMeta());

        meta.setColor(Color.fromRGB(r, g, b));
        item.setItemMeta(meta);

        return new ItemBuilder(itemStack);
    }

    public ItemBuilder dye(Color color) {
        ItemStack item = new ItemStack(itemStack);

        if (!(item.getItemMeta() instanceof LeatherArmorMeta)) return getBuilder();

        LeatherArmorMeta meta = ((LeatherArmorMeta)item.getItemMeta());

        meta.setColor(color);
        item.setItemMeta(meta);

        return new ItemBuilder(item);
    }

    public ItemBuilder unbreakable() {
        ItemStack item = new ItemStack(itemStack);

        ItemMeta meta = item.getItemMeta();
        meta.setUnbreakable(true);
        item.setItemMeta(meta);
        return new ItemBuilder(item);
    }

    public ItemBuilder enchant(Enchantment e, int level) {

        itemStack.addUnsafeEnchantment(e, level);

        return new ItemBuilder(itemStack);
    }

    public ItemBuilder hideFlags(ItemFlag ... flags) {

        ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(flags);
        itemStack.setItemMeta(meta);
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder glow(){
        itemStack.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 0);
        ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        return new ItemBuilder(itemStack);
    }

    public ItemBuilder fast( ){
        ItemStack item = new ItemStack(itemStack);

        net.minecraft.server.v1_14_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);

        NBTTagCompound nbt = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();

        NBTTagList nbtTags = new NBTTagList();
        NBTTagCompound speed = new NBTTagCompound();

        speed.set("AttributeName", new NBTTagString("generic.attackSpeed"));
        speed.set("Name", new NBTTagString("Blah"));
        speed.set("Amount", new NBTTagDouble(9.8));
        speed.set("Operation", new NBTTagInt(0));
        speed.set("UUIDLeast", new NBTTagInt(1));
        speed.set("UUIDMost", new NBTTagInt(1));

        nbtTags.add(speed);
        nbt.set("AttributeModifiers", nbtTags);
        nmsStack.setTag(nbt);
        return new ItemBuilder(CraftItemStack.asCraftMirror(nmsStack));
    }

    public ItemStack toItem() {
        return itemStack;
    }

    public ItemBuilder getBuilder() {
        return new ItemBuilder(itemStack);
    }

}
