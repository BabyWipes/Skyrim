package me.HugoDaBosss.skyrim.shops;

import org.bukkit.inventory.ItemStack;

public class Shop 
{

	public int x,y,z;
	public ItemStack is;

	public Shop(int x, int y, int z, ItemStack is)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.is = is;
	}
	
	public boolean is(int x, int y, int z)
	{
		if(this.x == x)
			if(this.y == y)
				if(this.z == z)
					return true;
		return false;
	}
	
}
