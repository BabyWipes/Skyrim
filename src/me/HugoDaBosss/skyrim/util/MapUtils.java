package me.HugoDaBosss.skyrim.util;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import me.HugoDaBosss.skyrim.Skyrim;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;
@SuppressWarnings("deprecation")
public class MapUtils 
{

	public MapUtils()
	{}
	
	public static void writeText(Player p, String text)
	{
		ItemStack i = p.getInventory().getItemInHand();
		if(i.getType() == Material.MAP)
		{
			MapView mv = Bukkit.createMap(Bukkit.getWorlds().get(0));
			for(MapRenderer r : mv.getRenderers())
			{
				mv.removeRenderer(r);
			}
			mv.addRenderer(new TestRenderer(text));
			i.setDurability(mv.getId());
		}
	}
	
}
class TestRenderer extends MapRenderer
{

	private final String text;
	
	public TestRenderer(String text)
	{
		super();
		this.text = text;
	}
	
	public static final int MAGIC_NUMBER = Integer.MAX_VALUE - 395742;
	
	@Override
	public void render(MapView mv, MapCanvas mc, Player p) {
		try
		{
			File f = new File(Skyrim.getPlugin().getDataFolder(), "stonebrick.png");
			if(f.exists())
			{
				BufferedImage bi = ImageIO.read(f);
				bi = MapPalette.resizeImage(bi);
				mc.drawImage(0, 0, bi);
			}
		}catch(Exception e)
		{
			
		}
		mc.drawText(10, 60, MinecraftFont.Font, text);
		mv.setCenterX(MAGIC_NUMBER);
		mv.setCenterZ(0);
	}
	
}
