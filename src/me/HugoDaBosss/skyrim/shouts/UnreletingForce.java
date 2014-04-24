package me.HugoDaBosss.skyrim.shouts;

import me.HugoDaBosss.skyrim.util.RegionUtils;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;

public class UnreletingForce extends Shout
{
	
	public UnreletingForce() 
	{
		this.word1 = "Fus";
		this.word2 = "Ro";
		this.word3 = "Dah";
		this.sound = Sound.GHAST_FIREBALL;
		this.volume = 10;
		this.pitch = 0.4;
		this.shout = new org.bukkit.scheduler.BukkitRunnable()
		{

			@Override
			public void run() {
				for(Entity e : p.getNearbyEntities(15, 15, 15))
				{
					if(e == null)return;
					if(RegionUtils.isInSpawn(e.getLocation()))return;
					if(!(e instanceof org.bukkit.entity.LivingEntity))return;
					((org.bukkit.entity.LivingEntity)e).damage(0.1, p);
					org.bukkit.util.Vector v = e.getLocation().toVector().subtract(p.getLocation().toVector()).normalize();
					e.setVelocity(v.multiply(2.3).setY(1));
					org.bukkit.util.BlockIterator bi = new org.bukkit.util.BlockIterator(p.getLocation().getWorld(), p.getLocation().toVector(), e.getLocation().toVector().subtract(p.getLocation().toVector()).normalize(),0 ,10);
					while(bi.hasNext())
					{
						org.bukkit.block.Block b = bi.next();
						org.bukkit.Location l = b.getLocation();
						l.setY(l.getY() + 1);
						b.getWorld().playEffect(l, Effect.SMOKE,0);
						b.getWorld().playEffect(l, Effect.SMOKE,0);
						b.getWorld().playEffect(l, Effect.SMOKE,0);
						b.getWorld().playEffect(l, Effect.SMOKE,0);
					}
				}
			}
			
		};
	}

}
