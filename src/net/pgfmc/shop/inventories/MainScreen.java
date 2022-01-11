package net.pgfmc.shop.Inventories;

import java.util.List;
import java.util.stream.Collectors;

import net.pgfmc.core.inventoryAPI.ListInventory;
import net.pgfmc.core.inventoryAPI.extra.Button;
import net.pgfmc.core.inventoryAPI.extra.SizeData;
import net.pgfmc.shop.Listing;
import net.pgfmc.shop.Main;

public class MainScreen extends ListInventory {



    public MainScreen() {
        super(SizeData.BIG , "Market");

        






    }

    @Override
    public List<Button> load() {
        List<Button> list;

        list = Listing.getListings().stream().map(x -> {
            return new Button(x.getItem().getType(), (e, d) -> {
                // XXX add open listener button
            });
        }).collect(Collectors.toList());




        return list;
    }






    
}
