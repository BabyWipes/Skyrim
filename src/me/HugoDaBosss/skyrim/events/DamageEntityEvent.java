package me.HugoDaBosss.skyrim.events;

import java.util.Random;

import me.HugoDaBosss.skyrim.Skyrim;
import me.HugoDaBosss.skyrim.util.Hologram;
import me.HugoDaBosss.skyrim.util.RegionUtils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.scheduler.BukkitRunnable;

public class DamageEntityEvent implements org.bukkit.event.Listener 
{

	public DamageEntityEvent(){}
	
	@org.bukkit.event.EventHandler
	public void damage(EntityDamageEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			if(e.getCause() == DamageCause.FALL)
			{
				if(p.isSneaking())
				{
					if(e.getDamage() < 2)
						e.setDamage(0.0);
					else
						e.setDamage(e.getDamage() / 1.2);
				}
			}
		}
		if(RegionUtils.isInSpawn(e.getEntity().getLocation()))
		{
			e.setCancelled(true);
		}
	}
	
	@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.HIGH)
	public void damage(EntityDamageByEntityEvent e) throws Exception
	{
		if(e.getEntity() instanceof Player)
		{
			Player p = (Player) e.getEntity();
			if(RegionUtils.isInSpawn(p.getLocation()))
			{
				e.setCancelled(true);
				if(e.getDamager() instanceof Player)
				{
					((Player) e.getDamager()).sendMessage(ChatColor.RED + p.getName() + " is in spawn!");
				}
			}
		}
		if(RegionUtils.isInSpawn(e.getDamager().getLocation()))
			e.setCancelled(true);
		if(RegionUtils.isInSpawn(e.getEntity().getLocation()))
			e.setCancelled(true);
		if(e.getDamager() instanceof Player && ((Player)e.getDamager()).getItemInHand().getMaxStackSize() == 1)
		{
			Material t = ((Player)e.getDamager()).getItemInHand().getType();
			if(t == Material.WOOD_AXE || t == Material.STONE_AXE || t == Material.IRON_AXE || t == Material.DIAMOND_AXE || t == Material.GOLD_AXE)
			{
				final org.bukkit.entity.Entity en = e.getEntity();
				new org.bukkit.scheduler.BukkitRunnable()
				{
					@Override
					public void run()
					{
						en.setVelocity(en.getVelocity().multiply(1.5).setY(0.2F));
					}
				}.runTaskLater(Skyrim.getPlugin(), 2L);
				
			}
		}
		if(e.getDamager() instanceof Player)
		{
			Random r = new Random();
			final Player p = (Player) e.getDamager();
			final Hologram holo = new Hologram(e.getEntity().getLocation().add(r.nextDouble() * 2 -1, 1, r.nextDouble() * 2 - 1),0,ChatColor.RED + "-" + (int)e.getDamage());
			try {
				holo.send(p);
			} catch (Exception a) {
				// TODO Auto-generated catch block
				a.printStackTrace();
			}
			new BukkitRunnable()
			{

				@Override
				public void run() {
					try {
						holo.remove(p);
					} catch (Exception a) {
						// TODO Auto-generated catch block
						a.printStackTrace();
					}
				}
				
			}.runTaskLater(Skyrim.getPlugin(), 15L);
		}
	}
	
}
