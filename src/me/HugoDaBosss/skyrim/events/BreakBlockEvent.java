package me.HugoDaBosss.skyrim.events;

public class BreakBlockEvent implements org.bukkit.event.Listener
{

	public BreakBlockEvent(){}
	
	@org.bukkit.event.EventHandler
	public void breakblock(org.bukkit.event.block.BlockBreakEvent e)
	{
		if(e.getPlayer().getGameMode() != org.bukkit.GameMode.CREATIVE)
			e.setCancelled(true);
		//START BUG FIX
		//Add durability on kill not working after destroying a block, relog needed
		if(e.getPlayer().getGameMode() == org.bukkit.GameMode.ADVENTURE)
		{
			if(e.getPlayer().getInventory().getItemInHand().getMaxStackSize() == 1)
			{
				e.getPlayer().setItemInHand(e.getPlayer().getItemInHand());
			}
		}
		//END BUG FIX
	}
	
	@org.bukkit.event.EventHandler
	public void hit(org.bukkit.event.entity.ProjectileHitEvent e)
	{
		if(e.getEntity() instanceof org.bukkit.entity.Arrow)
			e.getEntity().remove();
	}
	
}
