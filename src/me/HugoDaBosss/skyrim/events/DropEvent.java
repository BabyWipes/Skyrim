package me.HugoDaBosss.skyrim.events;

import me.HugoDaBosss.skyrim.Skyrim;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

public class DropEvent implements org.bukkit.event.Listener
{

	public DropEvent(){}
	
	@EventHandler
	public void drop(org.bukkit.event.player.PlayerDropItemEvent e)
	{
		if(e.getItemDrop().getItemStack().getType() == Material.NETHER_STAR)
		{
			e.getItemDrop().remove();
			Skyrim.Users.getUserPlayer(e.getPlayer()).reloadShouts();			
		}
	}
	
}
