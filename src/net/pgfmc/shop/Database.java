package net.pgfmc.shop;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Database {
	
	static File file = new File(Main.plugin.getDataFolder() + File.separator + "database.yml"); // Creates a File object
	static FileConfiguration database = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
	
	public static void save(List<Listing> listings) { // saves all instances of Listing
		
		int i = 0;
		
		for (Listing listing : listings) {
			database.set(String.valueOf(i), listing);
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
			listings.add((Listing) database.get(key));
		}
		return listings;
	}
}