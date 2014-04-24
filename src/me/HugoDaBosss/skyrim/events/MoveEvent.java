package me.HugoDaBosss.skyrim.events;

import java.util.ArrayList;

import me.HugoDaBosss.skyrim.Skyrim;

import org.bukkit.Material;

public class MoveEvent implements org.bukkit.event.Listener
{
	private ArrayList<String> last;
	
	public MoveEvent()
	{
		last = new ArrayList<String>();
	}
	
	//=====================================================================================
	//                        THIS LISTENER IS NOT REGISTERED
	//=====================================================================================
	
	@org.bukkit.event.EventHandler
	public void move(org.bukkit.event.player.PlayerMoveEvent e)
	{
		Material m = e.getPlayer().getLocation().getBlock().getType();
		if(m == Material.STATIONARY_WATER || m == Material.WATER)
		{
			org.bukkit.entity.Player p = e.getPlayer();
			if(last.contains(p.getName()))return;
			if(p.isDead())return;
			if(p.getGameMode() == org.bukkit.GameMode.CREATIVE)return;
			final String name = p.getName();
			last.add(name);
			new org.bukkit.scheduler.BukkitRunnable()
			{

				@Override
				public void run() {
					last.remove(name);
					
				}
				
			}.runTaskLater(Skyrim.getPlugin(), 10L);
			double health = p.getHealth();
			p.damage(1.0);
			if(health >= 1)
			p.setHealth(health - 1);
			//p.playSound(p.getLocation(), Sound.ZOMBIE_HURT, 5, 1);
		}
	}
	
	
	
}
