package me.HugoDaBosss.skyrim.events;

import me.HugoDaBosss.skyrim.Skyrim;
import me.HugoDaBosss.skyrim.User;
import me.HugoDaBosss.skyrim.shouts.ShoutType;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class InventoryEvent implements org.bukkit.event.Listener 
{

	public InventoryEvent(){}
	
	@org.bukkit.event.EventHandler
	public void inventory(org.bukkit.event.inventory.InventoryClickEvent e)
	{
		if(e.getCurrentItem() == null)return;
		String name = e.getInventory().getName();
		if(e.getCurrentItem().getType() == Material.NETHER_STAR)
		{
			e.setCancelled(true);
		}
		if(name.toLowerCase().contains("please select a shout"))
		{
			e.setCancelled(true);
			String dis = e.getCurrentItem().getItemMeta().getDisplayName();
			dis = ChatColor.stripColor(dis);
			ShoutType st = Skyrim.Shouts.getShoutFull(dis);
			Player p = (Player) e.getWhoClicked();
			User u = Skyrim.Users.getUserPlayer(p);
			u.setActiveShout(p.getInventory().getHeldItemSlot() - 5, st);
			p.closeInventory();
			p.sendMessage(ChatColor.GREEN + "Selected " + ChatColor.GOLD + st.getFullName());
		}
		else if(name.toLowerCase().contains("random") || name.toLowerCase().contains("you won"))
		{
			e.setCancelled(true);
		}
	}
	
}
