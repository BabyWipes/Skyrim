package me.HugoDaBosss.skyrim.shouts;

import me.HugoDaBosss.skyrim.util.RegionUtils;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;

public class InhaleBoost extends Shout {

	public InhaleBoost() 
	{
		this.word1 = "Su'um";
		this.word2 = "Het";
		this.word3 = "Horvutah";
		this.sound = Sound.GHAST_FIREBALL;
		this.volume = 10;
		this.pitch = 0.4;
		this.shout = new org.bukkit.scheduler.BukkitRunnable()
		{

			@Override
			public void run() {
				for(Entity e : p.getNearbyEntities(13, 13, 13))
				{
					if(e == null)return;
					if(!RegionUtils.isInSpawn(e.getLocation()))
					{
						org.bukkit.util.Vector v = p.getLocation().toVector().subtract(e.getLocation().toVector()).normalize();
						e.setVelocity(v.multiply(1.2).setY(1));
					}
				}
			}
			
		};
	}

}
