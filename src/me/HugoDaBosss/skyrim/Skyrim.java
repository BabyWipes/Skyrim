package me.HugoDaBosss.skyrim;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import me.HugoDaBosss.skyrim.Users.Rank;
import me.HugoDaBosss.skyrim.commands.CommandGm;
import me.HugoDaBosss.skyrim.commands.CommandLocation;
import me.HugoDaBosss.skyrim.commands.CommandMoney;
import me.HugoDaBosss.skyrim.commands.CommandRename;
import me.HugoDaBosss.skyrim.commands.CommandShop;
import me.HugoDaBosss.skyrim.commands.CommandShouts;
import me.HugoDaBosss.skyrim.commands.CommandSpawn;
import me.HugoDaBosss.skyrim.commands.CommandUser;
import me.HugoDaBosss.skyrim.events.BreakBlockEvent;
import me.HugoDaBosss.skyrim.events.ChatEvent;
import me.HugoDaBosss.skyrim.events.DamageEntityEvent;
import me.HugoDaBosss.skyrim.events.DeathEvent;
import me.HugoDaBosss.skyrim.events.DropEvent;
import me.HugoDaBosss.skyrim.events.InteractEvent;
import me.HugoDaBosss.skyrim.events.InventoryEvent;
import me.HugoDaBosss.skyrim.events.MiscEvent;
import me.HugoDaBosss.skyrim.events.PlaceBlockEvent;
import me.HugoDaBosss.skyrim.shops.Shops;
import me.HugoDaBosss.skyrim.shouts.ShoutType;
import me.HugoDaBosss.skyrim.util.BossBar;
import me.HugoDaBosss.skyrim.util.Items;
import me.HugoDaBosss.skyrim.util.Locations;
import me.HugoDaBosss.skyrim.util.RandomLocation;
import me.HugoDaBosss.skyrim.util.RankUtils;
import me.HugoDaBosss.skyrim.util.Locations.Names;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;

public class Skyrim extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener 
{
	
	public static Users Users;
	public static me.HugoDaBosss.skyrim.shouts.Shouts Shouts;
	public static WorldGuardPlugin WorldGuard;
	public static Shops Shops;
	public static Mobs mobs;
	public ItemStack noshout;
	public static ItemStack disableshout;
	private static Skyrim instance;
	{
		instance = this;
	}
	private static RandomLocation rl;
	private static Random random;
	private static Scoreboard board;
	//private Objective objective;
	private static Team owner;
	private static Team admin;
	private static Team moderator;
	private static Team user;
	
	@Override
	public void onEnable()
	{
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		owner = board.registerNewTeam("Owner");
		owner.setPrefix(ChatColor.LIGHT_PURPLE + "Owner " + ChatColor.BOLD);
		admin = board.registerNewTeam("Admin");
		admin.setPrefix(ChatColor.RED + "Admin " + ChatColor.BOLD);
		moderator = board.registerNewTeam("Moderator");
		moderator.setPrefix(ChatColor.YELLOW + "Mod " + ChatColor.BOLD);
		user = board.registerNewTeam("User");
		user.setPrefix(ChatColor.WHITE + "" + ChatColor.BOLD);
		random = new Random();
		Users = new Users();
		mobs = new Mobs();
		mobs.initializeMobs();
		Bukkit.getScheduler().runTaskLater(this, new Runnable()
		{
			@Override
			public void run()
			{
				Users.loadUsers();
				for(Player a : Bukkit.getOnlinePlayers())
				{
					Rank r = Skyrim.Users.getUserPlayer(a).getRank();
					if(r == Rank.USER)
					{
						user.addPlayer(a);
					}
					else if(r == Rank.MODERATOR)
					{
						moderator.addPlayer(a);
					}
					else if(r == Rank.ADMIN)
					{
						admin.addPlayer(a);
					}
					else if(r == Rank.OWNER)
					{
						owner.addPlayer(a);
					}
					for(Player e : Bukkit.getOnlinePlayers())
					{
						e.setScoreboard(board);
					}
				}
			}
		}, 2L);
		Shouts = new me.HugoDaBosss.skyrim.shouts.Shouts(this,this);
		Shops = new Shops(this,this);
		noshout = Items.createItem(Material.NETHER_STAR,"&7No shout selected",1);
		disableshout = Items.createItem(Material.NETHER_STAR,"&cSet to no shout",1);
		new File(this.getDataFolder(),"players").mkdirs();
		me.HugoDaBosss.skyrim.util.Locations.loadConfig();
		rl = new RandomLocation();
		WorldGuard = getWorldGuard();
		org.bukkit.plugin.PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(new InteractEvent(), this);
		pm.registerEvents(new InventoryEvent(), this);
		pm.registerEvents(new DropEvent(), this);
		pm.registerEvents(new BreakBlockEvent(), this);
		pm.registerEvents(new PlaceBlockEvent(), this);
		pm.registerEvents(new DamageEntityEvent(), this);
		//pm.registerEvents(new MoveEvent(), this);
		pm.registerEvents(new ChatEvent(), this);
		pm.registerEvents(new MiscEvent(), this);
		pm.registerEvents(new DeathEvent(), this);
		this.getCommand("money").setExecutor(new CommandMoney());
		this.getCommand("shouts").setExecutor(new CommandShouts());
		this.getCommand("shop").setExecutor(new CommandShop());
		this.getCommand("location").setExecutor(new CommandLocation());
		this.getCommand("spawn").setExecutor(new CommandSpawn());
		this.getCommand("gm").setExecutor(new CommandGm());
		this.getCommand("rename").setExecutor(new CommandRename());
		this.getCommand("user").setExecutor(new CommandUser());
		Calendar cal = Calendar.getInstance();
		long now = cal.getTimeInMillis();
		int time = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if(time >= 23)
		{
			cal.add(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);			
		}
		else
		{
			cal.add(Calendar.HOUR_OF_DAY, 1);
		}
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		long offset = cal.getTimeInMillis() - now;
		long ticks = offset / 50;
		this.getLogger().info(ticks + " " + cal.toString());
		new BukkitRunnable()
		{

			@Override
			public void run() {
				Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "The server is going to save data! Lagg expected!");
				Bukkit.getScheduler().cancelTasks(Skyrim.getPlugin());
				for(BukkitTask task : Bukkit.getScheduler().getPendingTasks())
				{
					if(task.getTaskId() != this.getTaskId() && task.getOwner().getName().equalsIgnoreCase(Skyrim.getPlugin().getName()))
					{
						task.cancel();
					}
				}
				new BukkitRunnable()
				{

					@Override
					public void run() {
						Skyrim.onReload();
						Bukkit.broadcastMessage(ChatColor.GREEN + "Saved all data!");
					}
					
				}.runTaskLater(Skyrim.getPlugin(), 100L);
			}
			
		}.runTaskTimer(this, ticks, 72000L);
	}
	
	public Skyrim()
	{
		super();
	}
	
	public static void onReload()
	{
		Skyrim.getPlugin().getLogger().info("Saving data...");
		for(World w : Bukkit.getWorlds())
			w.save();
		Users.saveUsers();
		Shops.saveShops();
		Locations.saveConfig();
		Users = new Users();
		Shops = new Shops(Skyrim.getPlugin(),instance);
		Users.loadUsers();
		for(Player a : Bukkit.getOnlinePlayers())
		{
			Rank r = Skyrim.Users.getUserPlayer(a).getRank();
			if(r == Rank.USER)
			{
				user.addPlayer(a);
			}
			else if(r == Rank.MODERATOR)
			{
				moderator.addPlayer(a);
			}
			else if(r == Rank.ADMIN)
			{
				admin.addPlayer(a);
			}
			else if(r == Rank.OWNER)
			{
				owner.addPlayer(a);
			}
			for(Player e : Bukkit.getOnlinePlayers())
			{
				e.setScoreboard(board);
			}
		}
		mobs = new Mobs();
		mobs.initializeMobs();
		Skyrim.getPlugin().getLogger().info("Data saved!");
	}
	
	@Override
	public void onDisable()
	{
		Users.saveUsers();
		Shops.saveShops();
		Locations.saveConfig();
	}
	
	public static Player getPlayer(String name)
	{
		return Users.getPlayer(name);
	}
	
	public static void info(String s)
	{
		Skyrim.getPlugin().getLogger().info(ChatColor.GOLD + s);
	}
	
	public static void warn(String s)
	{
		Skyrim.getPlugin().getLogger().warning(ChatColor.RED + s);
	}
	
	public static void randomLocation(Player p)
	{
		rl.teleportPlayer(p);
	}
	
	public static Location getRandomLocation()
	{
		return rl.getRandomLocation();
	}
	
	public Random getRandom()
	{
		return random;
	}
	
	public static String getRankName(Player p)
	{
		return board.getPlayerTeam(p).getPrefix();
	}
	
	@EventHandler
	public void join(org.bukkit.event.player.PlayerJoinEvent e)
	{
		e.setJoinMessage(null);
		final Player p = e.getPlayer();
		p.setPlayerTime(9000, false);
		Skyrim.Users.addUser(p);
		User u = Users.getUserPlayer(p);
		if(u.isNew())
		{
			p.getInventory().setItem(6, noshout);
			p.getInventory().setItem(7, noshout);
			p.getInventory().setItem(8, noshout);
		}
		final Rank r = u.getRank();
		//String prefix = r.getBukkitColor() + r.getName();
		//p.setPlayerListName(prefix + u.getRank().getBukkitColor() + ChatColor.BOLD + p.getName());
		new BukkitRunnable()
		{

			@Override
			public void run() {
				User u = Skyrim.Users.getUserPlayer(p);
				for(int i = 1; i < 4; i++)
				{
					if(!u.isUnlocked(u.getActiveShout(i)))
						u.setActiveShout(i, ShoutType.NONE);
				}
				if(r == Rank.USER)
				{
					user.addPlayer(p);
				}
				else if(r == Rank.MODERATOR)
				{
					moderator.addPlayer(p);
				}
				else if(r == Rank.ADMIN)
				{
					admin.addPlayer(p);
				}
				else if(r == Rank.OWNER)
				{
					owner.addPlayer(p);
				}
				if(p.getGameMode() == GameMode.CREATIVE)
				{
					if(!RankUtils.isHighEnough(r, Rank.ADMIN))
						p.setGameMode(GameMode.ADVENTURE);
				}
				if(p.getGameMode() != GameMode.CREATIVE)
				{
					p.teleport(Locations.getLocation(Names.SPAWN));
				}
				Bukkit.getScheduler().runTaskLater(Skyrim.getPlugin(), new Runnable()
				{

					@Override
					public void run() {
						for(Player a : Bukkit.getOnlinePlayers())
						{
							a.setScoreboard(board);
						}
					}
					
				}, 2L);
			}
			
		}.runTaskLater(Skyrim.getPlugin(),1L);
	}
	
	@EventHandler
	public void quit(org.bukkit.event.player.PlayerQuitEvent e)
	{
		e.setQuitMessage(null);
		//START BUG FIX
		//NPE when player quits during reload
		final Player player = e.getPlayer();
		try
		{
			Users.getUserPlayer(e.getPlayer()).saveConfig();
			Users.removeUserPlayer(e.getPlayer());
		}
		catch(NullPointerException e1)
		{
			Skyrim.getPlugin().getLogger().info(player.getName() + " quitted, but it triggered a NPE, retrying in 20 ticks");
			new BukkitRunnable()
			{

				@Override
				public void run() {
					try
					{
						Users.getUserPlayer(player).saveConfig();
						Users.removeUserPlayer(player);
					}
					catch(Exception e2)
					{
						Skyrim.getPlugin().getLogger().info("The quit of " + player.getName() + " still triggered a exception: " + e2.getMessage() + ", skipping the save...");
					}					
				}
				
			}.runTaskLater(Skyrim.getPlugin(), 20L);
		}
		//END BUG FIX
		//ISNT WORKING
		BossBar.hasHealthBar.put(player.getName(), false);
	}
	
	private WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");
	 
	    // WorldGuard may not be loaded
	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return null; // Maybe you want throw an exception instead
	    }
	 
	    return (WorldGuardPlugin) plugin;
	}
	
	public static RegionManager getRegionManager()
	{
		return WorldGuard.getRegionManager(Bukkit.getWorlds().get(0));
	}
	
	public ArrayList<User> getOnlineUsers()
	{
		return Skyrim.Users.getOnlineUsers();
	}
	
	public static Plugin getPlugin()
	{
		return instance;
	}
	
}