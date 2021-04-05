package net.pgfmc.shop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

/*

Modified by CrimsonDart

saves and loads Listings to database.yml
includes serializer and deserializer for Listing()

 */

public class Database {
	
	static File file = new File(Main.plugin.getDataFolder() + File.separator + "database.yml"); // Creates a File object
	static FileConfiguration database = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
	
	public static void save(List<Listing> listings) { // saves all instances of Listing
		
		int i = 0;
		
		for (Listing listing : listings) {
			
			List<Object> list = new ArrayList<Object>();
			
			list.add(listing.itemBeingSold);
			list.add(listing.playerUuid.toString());
			list.add(listing.tradeItem);
			list.add(listing.type.toString());
			
			database.set(String.valueOf(i), list);
			i++;
		}
		
		try {
			database.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<Listing> load() { // ------------- loads all listings
		
		List<Listing> listings = new ArrayList<Listing>();
		
		for (String key : database.getKeys(false)) { // gets all keys in databse.yml, then then gets each listing induvidually, while putting them into a Set
			List<?> memSec = database.getList(key);
			
			listings.add(new Listing(Bukkit.getOfflinePlayer(UUID.fromString((String) memSec.get(1))), (ItemStack) memSec.get(0), (ItemStack) memSec.get(2), Listing.listingType.valueOf((String) memSec.get(3))));
			// i spent 3 actual hours trying to get Serialization to work normally
			// i just decided to settle on this monstrosity lol
		}
		return listings;
	}
}