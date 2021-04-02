package net.pgfmc.shop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import net.pgfmc.shop.commands.Shop;
import net.pgfmc.shop.events.InventoryEvents;
import net.pgfmc.shop.events.PlayerEvents;

public class Main extends JavaPlugin {
	
	public static Main plugin;
	public static List<List<Object>> listings = new ArrayList<>();
	
	File file = new File(getDataFolder() + File.separator + "database.yml"); // Creates a File object
	FileConfiguration database = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
	
	@Override
	public void onEnable()
	{
		plugin = this;
		
		if (!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.getCommand("shop").setExecutor(new Shop());
		getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
		getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
	}
	
	// functions used all around the place in this pluign :)
	
	public static ItemStack createItem(String name, Material mat) // function for creating an item with a custom name
	{
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	
	public static ItemStack createItemWithLore(String name, Material mat, List<String> lore) // function for creating a new item with 
	{
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static List<String> createLore(String line1) // Creates a List<String> that represents lore // I want to be organized and not have this code for every ItemStack I want to create with a lore  -.-
	{
		List<String> lore = new ArrayList<String>();
		lore.add(line1);
		return lore;
	}
	
	
}
