package me.HugoDaBosss.skyrim.shouts;

import me.HugoDaBosss.skyrim.Skyrim;
import me.HugoDaBosss.skyrim.util.RegionUtils;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class FireCircle extends Shout
{

	public FireCircle() 
	{
		this.word1 = "Su";//TODO CHANGE WORDS//TODO CHANGE WORDS//TODO CHANGE WORDS//TODO CHANGE WORDS
		this.word2 = "Yol";//TODO CHANGE WORDS//TODO CHANGE WORDS//TODO CHANGE WORDS//TODO CHANGE WORDS
		this.word3 = "Rahgol";//TODO CHANGE WORDS//TODO CHANGE WORDS//TODO CHANGE WORDS//TODO CHANGE WORDS
		this.sound = Sound.GHAST_FIREBALL;
		this.volume = 10;
		this.pitch = 0.4;
		this.shout = new org.bukkit.scheduler.BukkitRunnable()
		{
			@Override
			public void run() {
				int r = 4;
				int x;
				int z;
				Location loc = p.getLocation();
				final World w;
				for (double i = 0.0; i < 360.0; i += 30) {
			        double angle = i * Math.PI / 180;
			            x = (int)(loc.getX() + r * Math.cos(angle));
			            z = (int)(loc.getZ() + r * Math.sin(angle));
			            //this.getServer().getWorld("world").getBlockAt(x, y, z).setType(m);
			        }
			}
			
		};
	}

}
