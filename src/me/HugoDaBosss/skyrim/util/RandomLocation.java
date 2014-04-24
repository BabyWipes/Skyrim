package me.HugoDaBosss.skyrim.util;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class RandomLocation 
{
	
	private int xmaxout, xminout, zmaxout, zminout;
	//, xmaxin, xminin, zmaxin, zminin;
	private String world;
	
	public RandomLocation()
	{
		calculate();
	}
	
	public void calculate()
	{
		Location out1 = Locations.getLocation("rl_out1");
		Location out2 =	Locations.getLocation("rl_out2");
		//Location in1 = Skyrim.getLocation("rl_in1");
		//Location in2 = Skyrim.getLocation("rl_in2");
		//if(out1 == null || out2 == null || in1 == null || in2 == null)return;
		world = out1.getWorld().getName();
		xmaxout = Math.max(out1.getBlockX(),out2.getBlockX());
		xminout =  Math.min(out1.getBlockX(),out2.getBlockX());
		zmaxout = Math.max(out1.getBlockZ(),out2.getBlockZ());
		zminout = Math.min(out1.getBlockZ(),out2.getBlockZ());
		//xmaxin = Math.max(in1.getBlockX(),in2.getBlockX());
		//xminin = Math.min(in1.getBlockX(),in2.getBlockX());
		//zmaxin = Math.max(in1.getBlockZ(),in2.getBlockZ());
		//zminin = Math.min(in1.getBlockZ(),in2.getBlockZ());
	}
	
	public void teleportPlayer(org.bukkit.entity.Player p)
	{
		Random r = new Random();
		int x,y,z;
		while(true)
		{
			boolean s = true;
			//x = r.nextInt(xmaxout - xminout) + xminout;
			x = r.nextInt(xmaxout-xminout) + xminout;
			//z = r.nextInt(zmaxout - zminout) + zminout;
			z = r.nextInt(zmaxout-zminout) + zminout;
			//if(x >= xminin && x <= xmaxin && z >= zminin && x <= zmaxin)
			//	s = false;
			Block b = Bukkit.getWorld(world).getHighestBlockAt(x, z);
			if(RegionUtils.isInSpawn(b.getLocation()))s = false;
			if(RegionUtils.isOutsideMap(b.getLocation()))s = false;
			if(b.getType() == Material.WATER)s = false;
			if(b.getType() == Material.LEAVES || b.getType() == Material.LEAVES_2)s = false;	
			y = b.getLocation().getBlockY();
			//Skyrim.getPlugin().getLogger().warning(x + " " + y + " " + z + " " + world);
			if(s)
			break;
		}
		//Skyrim.getPlugin().getLogger().info(x + " " + y + " " + z + " " + world);
		p.teleport(new Location(Bukkit.getWorld(world),x,y + 2,z));		
	}	
	
	public Location getRandomLocation()
	{
		Random r = new Random();
		int x,y,z;
		while(true)
		{
			boolean s = true;
			//x = r.nextInt(xmaxout - xminout) + xminout;
			x = r.nextInt(xmaxout-xminout) + xminout;
			//z = r.nextInt(zmaxout - zminout) + zminout;
			z = r.nextInt(zmaxout-zminout) + zminout;
			//if(x >= xminin && x <= xmaxin && z >= zminin && x <= zmaxin)
			//	s = false;
			Block b = Bukkit.getWorld(world).getHighestBlockAt(x, z);
			if(RegionUtils.isInSpawn(b.getLocation()))s = false;
			if(RegionUtils.isOutsideMap(b.getLocation()))s = false;
			if(b.getType() == Material.WATER)s = false;
			if(b.getType() == Material.LEAVES || b.getType() == Material.LEAVES_2)s = false;	
			y = b.getLocation().getBlockY();
			//Skyrim.getPlugin().getLogger().warning(x + " " + y + " " + z + " " + world);
			if(s)
			break;
		}
		return new Location(Bukkit.getWorld(world),x,y + 2,z);
	}
}
