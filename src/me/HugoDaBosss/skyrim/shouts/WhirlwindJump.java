package me.HugoDaBosss.skyrim.shouts;

import org.bukkit.Sound;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WhirlwindJump extends Shout 
{

	public WhirlwindJump() 
	{
		this.word1 = "Wuld";
		this.word2 = "Nah";
		this.word3 = "Kest";
		this.sound = Sound.GHAST_FIREBALL;
		this.volume = 10;
		this.pitch = 0.4;
		this.shout = new org.bukkit.scheduler.BukkitRunnable()
		{

			@Override
			public void run() {
				p.setVelocity(p.getVelocity().normalize().multiply(4).setY(1.3));
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,40,4));
			}
			
		};
	}
	
}
