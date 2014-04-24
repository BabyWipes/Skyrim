package me.HugoDaBosss.skyrim.util;

import me.HugoDaBosss.skyrim.Skyrim;

import org.bukkit.Location;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;


public class RegionUtils 
{

	public RegionUtils()
	{
		super();
	}
	
	@SuppressWarnings("unused")
	private static RegionUtils instance;
	{
		instance = this;
	}
	
	public static boolean isInSpawn(Location l)
	{
		for(ProtectedRegion r : Skyrim.getRegionManager().getApplicableRegions(l))
		{
			if(r.getId().toLowerCase().contains("spawn"))
				return true;
		}
		return false;
	}
	
	public static boolean isOutsideMap(Location l)
	{
		for(ProtectedRegion r :  Skyrim.getRegionManager().getApplicableRegions(l))
		{
			if(r != null)
				return false;
		}
		return true;
	}
	
}
