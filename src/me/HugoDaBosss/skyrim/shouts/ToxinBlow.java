package me.HugoDaBosss.skyrim.shouts;

import me.HugoDaBosss.skyrim.util.RegionUtils;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ToxinBlow extends Shout
{

	public ToxinBlow()
	{
		this.word1 = "Lah";
		this.word2 = "Munax";
		this.word3 = "Nahkriin";
		this.sound = Sound.GHAST_FIREBALL;
		this.volume = 10;
		this.pitch = 0.4;
		this.shout = new org.bukkit.scheduler.BukkitRunnable()
		{
	
			@Override
			public void run() {
				for(Entity e : p.getNearbyEntities(10, 10, 10))
				{
					if(!RegionUtils.isInSpawn(e.getLocation()) && e instanceof LivingEntity)
						((LivingEntity)e).addPotionEffect(new PotionEffect(PotionEffectType.POISON,100,1));
				}
			}
			
		};
	}
	
}
