package me.HugoDaBosss.skyrim.shouts;

import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WhirlwindSprint extends Shout
{

	public WhirlwindSprint() 
	{
		this.word1 = "Wuld";
		this.word2 = "Nah";
		this.word3 = "Rah";
		this.sound = Sound.GHAST_FIREBALL;
		this.volume = 10;
		this.pitch = 0.4;
		this.shout = new org.bukkit.scheduler.BukkitRunnable()
		{

			@Override
			public void run() {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,200,1));
			}
			
		};
	}
	
}
