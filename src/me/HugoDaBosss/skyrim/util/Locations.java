package me.HugoDaBosss.skyrim.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import me.HugoDaBosss.skyrim.Skyrim;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Locations 
{

	private static FileConfiguration locations;
	private static File f;
	private static HashMap<String, Location> cache;
	
	public static void loadConfig()
	{
		f = new File(Skyrim.getPlugin().getDataFolder(), "locations.yml");
		if(!f.exists())
		{
			try {
				f.createNewFile();
			} catch (IOException e) {
				Skyrim.warn("Unable to create locations.yml");
				e.printStackTrace();
			}
		}
		locations = YamlConfiguration.loadConfiguration(f);	
		cache = new HashMap<String, Location>();
	}
	
	public static void saveConfig()
	{
		try {
			locations.save(f);
		} catch (IOException e) {
			Skyrim.warn("Unable to save the locations in locations.yml");
			e.printStackTrace();
		}
	}
	
	public static void setLocation(Location l, String name)
	{
		if(l == null || name == null || name.equalsIgnoreCase(""))
			return;
		name = name.toLowerCase();
		locations.set(name + ".x", l.getX());
		locations.set(name + ".y", l.getY());
		locations.set(name + ".z", l.getZ());
		locations.set(name + ".world", l.getWorld().getName());
		cache.put(name.toLowerCase(), l);
	}
	
	public static Location getLocation(String name)
	{
		if(cache.containsKey(name.toLowerCase()))
			return cache.get(name.toLowerCase());
		if(locations.contains(name.toLowerCase()))
		{
			double x = locations.getDouble(name + ".x");
			double y = locations.getDouble(name + ".y");
			double z = locations.getDouble(name + ".z");
			String world = locations.getString(name + ".world");
			return new Location(Bukkit.getWorld(world),x,y,z);
		}
		return null;
	}
	
	public static Location getLocation(Names names)
	{
		if(cache.containsKey(names.toString()))
			return cache.get(names.toString());
		if(locations.contains(names.toString()))
		{
			String name = names.toString();
			double x = locations.getDouble(name + ".x");
			double y = locations.getDouble(name + ".y");
			double z = locations.getDouble(name + ".z");
			String world = locations.getString(name + ".world");
			return new Location(Bukkit.getWorld(world),x,y,z);
		}
		return null;
	}
	
	public enum Names
	{
		SPAWN("spawn"),
		SHOP("shop"),
		HORSE("horse");
		
		private String name;
		
		Names(String s)
		{
			name = s;
		}
		
		public String toString()
		{
			return name.toLowerCase();
		}
	}
	
}
