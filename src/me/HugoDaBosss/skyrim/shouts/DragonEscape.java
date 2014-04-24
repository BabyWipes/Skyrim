package me.HugoDaBosss.skyrim.shouts;

import java.lang.reflect.Field;
import java.util.Random;

import me.HugoDaBosss.skyrim.Skyrim;
import me.HugoDaBosss.skyrim.util.RegionUtils;
import net.minecraft.server.v1_7_R3.PacketPlayOutWorldParticles;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class DragonEscape extends Shout 
{

	public DragonEscape() 
	{
		this.word1 = "Drun";
		this.word2 = "Feim";
		this.word3 = "Dovah";
		this.sound = Sound.GHAST_FIREBALL;
		this.volume = 10;
		this.pitch = 0.4;
		this.shout = new org.bukkit.scheduler.BukkitRunnable()
		{

			@Override
			public void run() {
				final org.bukkit.World w = p.getLocation().getWorld();
				w.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
				w.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
				w.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
				w.playEffect(p.getLocation().add(0, 1.0, 0), Effect.MOBSPAWNER_FLAMES, 0);
				w.playEffect(p.getLocation().add(0, 1.0, 0), Effect.MOBSPAWNER_FLAMES, 0);
				w.playEffect(p.getLocation().add(0, 1.0, 0), Effect.MOBSPAWNER_FLAMES, 0);
				spawnExplosionParticles(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,100,10));
				new org.bukkit.scheduler.BukkitRunnable()
				{
					int i = 0;
					final Vector v = new Vector(0.0, 0.2, 0.0);
					@Override
					public void run() {
						if(i < 60)
						{
							p.setVelocity(v);
							//w.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
							w.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 0);
							//w.playEffect(p.getLocation().add(0, 1.0, 0), Effect.MOBSPAWNER_FLAMES, 0);
							w.playEffect(p.getLocation().add(0, 1.0, 0), Effect.MOBSPAWNER_FLAMES, 0);
						}
						else
						{
							Random r = new Random();
							p.setVelocity(new Vector(r.nextBoolean() ? r.nextDouble() + 1.0 : -1 - r.nextDouble(), 
									0.5, 
									r.nextBoolean() ? r.nextDouble() + 1.0 : -1 - r.nextDouble()));
							for(Entity e : p.getNearbyEntities(13, 13, 13))
							{
								if(e == null)return;
								if(!RegionUtils.isInSpawn(e.getLocation()))
								{
									org.bukkit.util.Vector v = p.getLocation().toVector().subtract(e.getLocation().toVector()).normalize();
									e.setVelocity(v.multiply(1.2).setY(1));
								}
							}
							this.cancel();
						}
						i++;
					}
					
				}.runTaskTimer(Skyrim.getPlugin(), 1L, 1L);
			}
			
		};
	}
	
	public void spawnExplosionParticles(Player p)
    {
        // Make an instance of the packet!
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles();
        for (Field field : packet.getClass().getDeclaredFields())
        {
            try
            {
                field.setAccessible(true);
                String fieldName = field.getName();
                switch (fieldName)
                {
                    case "a":
                        field.set(packet, "hugeexplosion"); //Particle name
                        break;
                    case "b":
                        field.setFloat(packet, p.getLocation().getBlockX()); //Block X
                        break;
                    case "c":
                        field.setFloat(packet, p.getLocation().getBlockY() - (float)1.0); //Block Y
                        break;
                    case "d":
                        field.setFloat(packet, p.getLocation().getBlockZ()); //Block Z
                        break;
                    case "e":
                        field.setFloat(packet, 1); //Random X Offset
                        break;
                    case "f":
                        field.setFloat(packet, 1); //Random Y Offset
                        break;
                    case "g":
                        field.setFloat(packet, 1); //Random Z Offset
                        break;
                    case "h":
                        field.setFloat(packet, 0); //Speed/data of particles
                        break;
                    case "i":
                        field.setInt(packet, 15); //Amount of particles
                        break;
                }
            } 
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                return;
            }
        }
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
        for(Entity e : p.getNearbyEntities(50, 50, 50))
        {
        	if(e instanceof Player)
        	{
        		 ((CraftPlayer)(Player)p).getHandle().playerConnection.sendPacket(packet);
        	}
        }
    }
	
}
