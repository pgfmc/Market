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
		
		Listing.loadListings();
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
	
	public static List<String> createLore(String line1) { // Creates a List<String> that represents lore // I want to be organized and not have this code for every ItemStack I want to create with a lore  -.-
	
		List<String> lore = new ArrayList<String>();
		lore.add(line1);
		return lore;
	}
	
	public static ItemStack switchLore(ItemStack item, List<String> lore) { // changes the lore of an item
		
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setLore(lore);
		item.setItemMeta(itemMeta);
		return item;
	}
	
public static ItemStack switchLore(ItemStack item, String lore) { // changes the lore of an item using a string as input
		
		ItemMeta itemMeta = item.getItemMeta();
		
		List<String> list = new ArrayList<String>();
		list.add(lore);
		itemMeta.setLore(list);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public static String makePlural(ItemStack itemStack) { // takes an item, and then represents it in a string || as in: "1 diamond" or "4 Dark Prismarine Blocks" || automatically 
		
		String name = Main.getName(itemStack.getType()); // --------- plural stuff
		if (itemStack.getAmount() == 1) {
			return(String.valueOf(itemStack.getAmount()) + " " + name);
		} else {
			if (name.endsWith("s")) {
				
				return(String.valueOf(itemStack.getAmount()) + " " + name + "s");
			} else {
				return(String.valueOf(itemStack.getAmount()) + " " + name + "es");
			}
		}
	}
	
	public static String getName(Material material) {
		
		switch(material) {
		
		case CHEST_MINECART: return "Minecart with Chest";
		case CHIPPED_ANVIL: return "Slightly Damaged Anvil";
		case CLAY_BALL: return "Clay";
		case COMPARATOR: return "Redstone Comparator";
		case DAMAGED_ANVIL: return "Very Damaged Anvil";
		case EXPERIENCE_BOTTLE: return "Bottle O' Enchanting";
		case FILLED_MAP: return "Map";
		case FURNACE_MINECART: return "Minecart with Furnace";
		case GLISTERING_MELON_SLICE: return "Glistering Melon";
		case HONEYCOMB_BLOCK: return "Block of Honey";
		case MELON_SLICE: return "Melon";
		case RABBIT_FOOT: return "Rabbit's Foot";
		case REPEATER: return "Redstone Repeater";
		
		default: 	String name = material.name();
		
					if (name.contains("MUSIC_DISC_")) {
						name.replaceFirst("MUSIC_DISC_", "");
					}
		
					name.toLowerCase();
					name.replace("_", " ");
					String[] list = name.split(" ");
					
					name = "";
					for (String string : list) {
						
						Character letter =  string.charAt(0);
						Character gamer = Character.toUpperCase(letter);
						string.replaceFirst(letter.toString(), gamer.toString());
						name = name + string + " ";
					}
					name.stripTrailing();
					
					return name;
		}
	}
}
