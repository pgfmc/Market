package net.pgfmc.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Listing {
	
	private transient static List<Listing> instances = new ArrayList<Listing>();

	public enum listingType {
		LISTING,
		OFFER,
		TRADEOFFER
	}
	
	listingType type;
	ItemStack itemBeingSold;
	int price;
	ItemStack tradeItem;
	UUID playerUuid;
	
	
	// ------------------------------------------------------------------------ Constructors
	
	public Listing(OfflinePlayer seller, ItemStack itemBeingSold, int price, ItemStack tradeItem, listingType type) {
		this.type = type;
		this.itemBeingSold = itemBeingSold;
		this.price = price;
		this.tradeItem = tradeItem;
		this.playerUuid = seller.getUniqueId();
		
		instances.add(this);
		Listing.saveListings();
	}
	
	public static Listing createListing(OfflinePlayer Seller, ItemStack Item, int priceBit) { // new "direct purchase" / "Listing"
		return new Listing(Seller, Item, priceBit, null, listingType.LISTING);
	}
	
	public static Listing offerListing(OfflinePlayer Seller, int priceBit, ItemStack icon) { // new offer / Service / Bulk Purchase
		return new Listing(Seller, icon, priceBit, null, listingType.OFFER);
	}
	
	public static Listing tradeListing(OfflinePlayer Seller, ItemStack trade, ItemStack icon) { // new trade offer
		return new Listing(Seller, trade, 0, icon, listingType.TRADEOFFER);
	}
	
	// ------------------------------------------------------------------------ Save and Load
	
	public static void saveListings() {
		Database.save(instances);
	}
	
	public static void loadListings() {
		instances.clear();
		Database.load();
	}
	
	// ------------------------------------------------------------------------ Get Listings (instances)
	
	public static List<Listing> getListings() {
		return instances;
	}
	
	// ------------------------------------------------------------------------ Get Listing Type
	
	public listingType getType() {
		return type;
	}
	
	// ------------------------------------------------------------------------ Get itemBeingSold / icon
	
	public ItemStack getItem() {
		return itemBeingSold;
	}
	
	// ------------------------------------------------------------------------ Get Seller
	
	public OfflinePlayer getPlayer() {
		return Bukkit.getOfflinePlayer(playerUuid);
	}
	
	// ------------------------------------------------------------------------ Get Price
	
	public Integer getPrice() {
		return price;
	}
	
	// ------------------------------------------------------------------------ Get TradeItem
	
	public ItemStack getTrade() {
		return tradeItem;
	}
	
	// ------------------------------------------------------------------------ Confirm / Buy
	
	public void purchase(Player player) {
		
	}
}
