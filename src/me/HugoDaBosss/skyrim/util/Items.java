package me.HugoDaBosss.skyrim.util;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items 
{

	public Items()
	{
		super();
	}
	
	public static ItemStack createItem(Material m, String name, int amount)
	{
		ItemStack is = new ItemStack(m,amount);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
		is.setItemMeta(im);
		return is;
	}
	
	private static ItemStack Item(Material m, String name)
	{
		ItemStack is = new ItemStack(m,1);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&l" + name));
		is.setItemMeta(im);
		return is;
	}
	
	static boolean a;
	static ItemStack[] random;
	
	@SuppressWarnings("deprecation")
	public static ItemStack getRandomItem()
	{
		if(a == false)
		{
			random = new ItemStack[]{new ItemStack(Material.SUGAR),
					Item(Material.WOOD_AXE,"Wooden Axe"),
					Item(Material.STONE_AXE,"Stone Axe"),
					Item(Material.WOOD_SWORD,"Wooden Sword"),
					Item(Material.STONE_SWORD,"Stone Sword"),
					Item(Material.IRON_AXE,"Iron Axe"),
					Item(Material.BOW,"Bow"),
					Item(Material.FISHING_ROD,"Fishing Rod"),
					Item(Material.GOLD_AXE,"Golden Axe"),
					Item(Material.WOOD_SPADE,"Wooden Shovel"),
					new ItemStack(Material.ARROW,64),
					new ItemStack(Material.LEATHER_BOOTS),
					new ItemStack(Material.LEATHER_CHESTPLATE),
					new ItemStack(Material.LEATHER_HELMET),
					new ItemStack(Material.LEATHER_LEGGINGS),
					new ItemStack(Material.FISHING_ROD),
					new ItemStack(Material.INK_SACK,1,(short)0, (byte)(15 - DyeColor.PINK.getData())),
					new ItemStack(Material.GOLD_LEGGINGS),
					new ItemStack(Material.GOLD_CHESTPLATE),
					new ItemStack(Material.GOLD_BOOTS),
					new ItemStack(Material.GOLD_HELMET)
			};
			a = true;
		}
		Random r = new Random();
		return random[r.nextInt(random.length)];
	}
	
}
