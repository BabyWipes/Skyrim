package me.HugoDaBosss.skyrim.events;

import net.minecraft.server.v1_7_R3.EntityComplexPart;

import org.bukkit.GameMode;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;

public class MiscEvent implements org.bukkit.event.Listener
{

	public MiscEvent(){}
	
	@EventHandler
	public void weather(org.bukkit.event.weather.WeatherChangeEvent e)
	{
		if(e.toWeatherState())
			e.setCancelled(true);
	}
	
	@EventHandler
	public void food(org.bukkit.event.entity.FoodLevelChangeEvent e)
	{
		if(e.getFoodLevel() < 20)
			e.setFoodLevel(20);
	}
	
	@EventHandler
	public void painting(org.bukkit.event.hanging.HangingBreakEvent e)
	{
		if(e.getCause() == RemoveCause.ENTITY || e.getCause() == RemoveCause.EXPLOSION)
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler(priority = org.bukkit.event.EventPriority.HIGH)
	public void paintingbyEntity(org.bukkit.event.hanging.HangingBreakByEntityEvent e) 
	{
		if(e.getEntity() instanceof Player)
			if(((Player)e.getEntity()).getGameMode() == GameMode.CREATIVE)
				e.setCancelled(false);
	}
	
	@EventHandler
	public void stopDragonDamage(EntityExplodeEvent e)
	{
		if(e.getEntity() instanceof EntityComplexPart)
		{
			e.setCancelled(true);
		}
		else if(e.getEntity() instanceof EnderDragon)
		{
			e.setCancelled(true);
		}
	}
}
