package me.HugoDaBosss.skyrim.shouts;

import java.util.Random;

import me.HugoDaBosss.skyrim.util.RegionUtils;

import org.bukkit.Sound;

public class FireBreath extends Shout {
	
	public FireBreath() 
	{
		this.word1 = "Yol";
		this.word2 = "Toor";
		this.word3 = "Shul";
		this.sound = Sound.GHAST_FIREBALL;
		this.volume = 10;
		this.pitch = 0.4;
		this.shout = new org.bukkit.scheduler.BukkitRunnable()
		{

			@Override
			public void run() {
				Random r = new Random();
				for(org.bukkit.entity.Entity e : p.getNearbyEntities(6, 6, 6))
				{
					
					if(e instanceof org.bukkit.entity.LivingEntity)
					{
						if(!RegionUtils.isInSpawn(e.getLocation()))
						{
							((org.bukkit.entity.LivingEntity) e).damage(0.1, p);
							int i = r.nextInt(60);
							e.setFireTicks(100 + i);
						}
					}
				}
			}
			
		};
	}

}
