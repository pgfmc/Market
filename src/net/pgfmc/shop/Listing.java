package net.pgfmc.shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

public class Listing implements Serializable {
	
	private static final long serialVersionUID = -8515261291824360001L;
	private static List<Listing> instances = new ArrayList<Listing>();

	public enum listingType {
		LISTING,
		OFFER,
		TRADEOFFER
	}
	
	listingType type;
	OfflinePlayer seller;
	ItemStack itemBeingSold;
	int price;
	ItemStack tradeItem;
	
	// ------------------------------------------------------------------------ Constructors
	
	public Listing(OfflinePlayer Seller, ItemStack Item, int priceBit) { // new "direct purchase" / "Listing"
		
		type = listingType.LISTING;
		seller = Seller;
		itemBeingSold = Item;
		price = priceBit;
		tradeItem = null;

		instances.add(this);
	}
	
	public Listing(OfflinePlayer Seller, int priceBit, ItemStack icon) { // new offer / Service / Bulk Purchase
		
		type = listingType.OFFER;
		seller = Seller;
		itemBeingSold = icon;
		price = priceBit;
		tradeItem = null;

		instances.add(this);
	}
	
	public Listing(OfflinePlayer Seller, ItemStack trade, ItemStack icon) { // new trade offer
		
		type = listingType.TRADEOFFER;
		seller = Seller;
		itemBeingSold = icon;
		price = 0;
		tradeItem = trade;
		
		instances.add(this);
	}
	
	// ------------------------------------------------------------------------ Save and Load
	
	public static void saveListings() {
		Database.save(instances);
	}
	
	public static void loadListings() {
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
		return seller;
	}
	
	// ------------------------------------------------------------------------ Get Price
	
	public Integer getPrice() {
		return price;
	}
	
	// ------------------------------------------------------------------------ Get TradeItem
	
	public ItemStack getTrade() {
		return tradeItem;
	}
}
