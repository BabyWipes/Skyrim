package me.HugoDaBosss.skyrim.shouts;

import me.HugoDaBosss.skyrim.util.RegionUtils;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class RageBlast extends Shout
{

	public RageBlast() 
	{
		this.word1 = "Ruz";
		this.word2 = "Su";
		this.word3 = "Rahgol";
		this.sound = Sound.GHAST_FIREBALL;
		this.volume = 10;
		this.pitch = 0.4;
		this.shout = new org.bukkit.scheduler.BukkitRunnable()
		{

			@Override
			public void run() {
				for(Entity e : p.getNearbyEntities(6, 6, 6))
				{
					if(e == null)return;
					if(e instanceof LivingEntity)
					{
						if(RegionUtils.isInSpawn(e.getLocation()))return;
						((LivingEntity) e).damage(6.0, p);
						e.setVelocity(new org.bukkit.util.Vector(0.0D, 1.0D, 0.0D));
						
					}
				}
			}
			
		};
	}

}
