package me.HugoDaBosss.skyrim.shouts;

import me.HugoDaBosss.skyrim.Skyrim;
import me.HugoDaBosss.skyrim.util.RegionUtils;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public class FireBlast extends Shout
{

	public FireBlast() 
	{
		this.word1 = "Su";
		this.word2 = "Yol";
		this.word3 = "Rahgol";
		this.sound = Sound.GHAST_FIREBALL;
		this.volume = 10;
		this.pitch = 0.4;
		this.shout = new org.bukkit.scheduler.BukkitRunnable()
		{

			@Override
			public void run() {
				for(final Entity e : p.getNearbyEntities(6, 6, 6))
				{
					if(e == null)return;
					final World w = p.getWorld();
					if(e instanceof LivingEntity)
					{
						if(RegionUtils.isInSpawn(e.getLocation()))return;
						((LivingEntity) e).damage(6.0, p);
						e.setFireTicks(60);
						e.setVelocity(new org.bukkit.util.Vector(0.0D, 1.0D, 0.0D));
						w.playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
						new org.bukkit.scheduler.BukkitRunnable()
						{
							int i = 0;
							@Override
							public void run() {
								i++;
								if(i < 12)
									w.playEffect(e.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
								else
									this.cancel();
							}
							
						}.runTaskTimer(Skyrim.getPlugin(), 5L, 5L);
					}
				}
			}
			
		};
	}

	
	
}
