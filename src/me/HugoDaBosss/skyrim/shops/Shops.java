package me.HugoDaBosss.skyrim.shops;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class Shops 
{

	private Plugin plugin;
	private File f;
	private FileConfiguration config;
	private ArrayList<Shop> shops;

	public Shops(Plugin plugin, me.HugoDaBosss.skyrim.Skyrim skyrim)
	{
		this.plugin = plugin;
		f = new File(plugin.getDataFolder().getPath(), "shops.yml");
		if(!f.exists())
		{
			try 
			{
				f.createNewFile();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(f);
		shops = new ArrayList<Shop>();
		boolean t = true;
		for(int i = 0; t; i++)
		{
			if(!config.contains(i + ".x")){t = false; return;}
			int x = config.getInt(i + ".x");
			int y = config.getInt(i + ".y");
			int z = config.getInt(i + ".z");
			ItemStack is = config.getItemStack(i + ".item");
			shops.add(new Shop(x,y,z,is));
		}
		config = null;
	}
	
	public void addShop(Location l, ItemStack is)
	{
		removeShop(l);
		shops.add(new Shop(l.getBlockX(), l.getBlockY(), l.getBlockZ(), is));
	}
	
	public void removeShop(Location l)
	{
		@SuppressWarnings("unchecked")
		ArrayList<Shop> d = (ArrayList<Shop>) shops.clone();
		int x,y,z;
		x = l.getBlockX();
		y = l.getBlockY();
		z = l.getBlockZ();
		for(Shop s : d)
		{
			if(s.is(x,y,z))
				shops.remove(s);
		}
		d = null;
	}
	
	public ItemStack getItem(Location l)
	{
		int x,y,z;
		x = l.getBlockX();
		y = l.getBlockY();
		z = l.getBlockZ();
		for(Shop s : shops)
		{
			if(s.is(x,y,z))
				return s.is;
		}
		return null;
	}
	
	public void saveShops()
	{
		f.delete();
		f = new File(plugin.getDataFolder().getPath(), "shops.yml");
		if(!f.exists())
		{
			try 
			{
				f.createNewFile();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		FileConfiguration c = YamlConfiguration.loadConfiguration(f);
		int i = 0;
		for(Shop s : shops)
		{
			Block b = Bukkit.getWorld("world").getBlockAt(s.x, s.y, s.z);
			if(b.getType() == Material.WALL_SIGN || b.getType() == Material.SIGN_POST)
			{
				/*Sign sign = (Sign) b;
				if(sign.getLine(2).contains("buy for") && sign.getLine(3).contains("G"))
				{*/
					c.set(i + ".x", s.x);
					c.set(i + ".y", s.y);
					c.set(i + ".z", s.z);
					c.set(i + ".item", s.is);
					i++;
				//}
			}
		}
		try {
			c.save(f);
		} catch (IOException e) {
			plugin.getLogger().info("[Skyrim] Unable to save the shops to shops.yml!");
			e.printStackTrace();
		}
	}
	
}
