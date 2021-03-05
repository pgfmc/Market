package net.pgfmc.shop;

import org.bukkit.plugin.java.JavaPlugin;

import net.pgfmc.shop.commands.Shop;
import net.pgfmc.shop.events.InventoryEvents;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable()
	{
		this.getCommand("shop").setExecutor(new Shop());
		getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
	}
	
	@Override
	public void onDisable()
	{
		
	}

}
