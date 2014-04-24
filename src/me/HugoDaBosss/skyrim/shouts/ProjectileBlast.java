package me.HugoDaBosss.skyrim.shouts;

import me.HugoDaBosss.skyrim.Skyrim;

import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitRunnable;

public class ProjectileBlast extends Shout
{

	public ProjectileBlast() 
	{
		this.word1 = "Krii";
		this.word2 = "Vaaz";
		this.word3 = "Ronaaz";
		this.sound = Sound.GHAST_FIREBALL;
		this.volume = 10;
		this.pitch = 0.4;
		this.shout = new org.bukkit.scheduler.BukkitRunnable()
		{

			@Override
			public void run() {
				new BukkitRunnable()
				{
					int i = 0;
					@Override
					public void run() {
						Arrow a = p.launchProjectile(Arrow.class);
						a.setVelocity(a.getVelocity().multiply(3));
						i++;
						if(i == 5)
							cancel();
					}
					
				}.runTaskTimer(Skyrim.getPlugin(), 0L, 7L);
			}
			
		};
	}
}
