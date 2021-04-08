package club.wavepe.xxarox;

import club.wavepe.xxarox.generic.Bid;
import club.wavepe.xxarox.generic.EndedListing;
import club.wavepe.xxarox.generic.Listing;
import club.wavepe.xxarox.provider.JsonProvider;
import club.wavepe.xxarox.provider.Provider;
import club.wavepe.xxarox.provider.economy.EconomyInterface;
import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.plugin.PluginBase;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class AuctionHouse extends PluginBase {
    protected static AuctionHouse instance;
    protected Provider provider;
    protected EconomyInterface economyProvider;

    public ArrayList<Listing> cache = new ArrayList<>();
    public ArrayList<EndedListing> waitForCollect = new ArrayList<>();

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();

        provider = getProviderFromString(getConfig().getString("provider", "json"));
        provider.init(this);
        economyProvider = getEconomyProviderFromString(getConfig().getString("economy-provider", "json"));
    }

    @Override
    public void onEnable() {
        getLogger().info("enabled");
        provider.prepare();
        provider.load(this);
    }

    @Override
    public void onDisable() {
        getLogger().info("disabled");
        provider.shutdown();
    }

    public static AuctionHouse getInstance() {
        return instance;
    }

    public Listing getListing(String marketId) { // FIXME: 04.04.2021 @FHexix maybe take a look over that function.
        AtomicReference<Listing> listing = new AtomicReference<>();
        cache.forEach((list -> {
            if (marketId.equals(list.getId())) {
                listing.set(list);
            }
        }));
        return listing.get();
    }

    private Provider getProviderFromString(String providerType) {
        switch (providerType.toLowerCase()) {
            case "json":
            case "js":
                return new JsonProvider();
        }
        throw new RuntimeException("Provider '" + providerType + "' not found!");
    }

    private EconomyInterface getEconomyProviderFromString(String economyPluginName) {
        switch (economyPluginName.toLowerCase()) {
            //TODO
        }
        throw new RuntimeException("Economy-Interface '" + economyPluginName + "' not found!");
    }

    public Listing create(Player player, Item item, int price, long expireDate) {
        //TODO: remove auction fee
        Listing listing = new Listing(player, item, price, expireDate);
        provider.onCreate(listing);
        cache.add(0, listing);
        return listing;
    }

    public Bid bid(Player player, Listing listing, int amount) {
        Bid bid = listing.onBid(player, amount);
        provider.onBid(bid);
        economyProvider.removeMoney(player, amount);
        return bid;
    }

    public void end(Listing listing) {
        cache.remove(listing);
        provider.onEnd(listing);
        EndedListing endedListing = listing.onEnd();
        waitForCollect.add(0, endedListing);
    }

    public boolean claimListing(Player player, EndedListing endedListing) {
        if (endedListing == null) {
            return false;
        }
        if (endedListing.getBoughtUuid() != null) {
            if ((endedListing.getOwnerUuid().equals(player.getUniqueId().toString())) && player.getInventory().canAddItem(endedListing.getItem())) {
                endedListing.setItemCollected(true);
                endedListing.setCoinsCollected(true);
                player.getInventory().addItem(endedListing.getItem());

                if (endedListing.isCoinsCollected() && endedListing.isItemCollected()) {
                    provider.onCollect(endedListing);
                }
                return true;
            }
            return false;
        } else {
            if ((player.getUniqueId().toString().equals(endedListing.getBoughtUuid())) && !endedListing.isItemCollected() && player.getInventory().canAddItem(endedListing.getItem())) {
                endedListing.setItemCollected(true);
                provider.collectItem(endedListing);
                player.getInventory().addItem(endedListing.getItem());

                if (endedListing.isCoinsCollected() && endedListing.isItemCollected()) {
                    provider.onCollect(endedListing);
                }
                return true;
            } else {
                if (endedListing.getOwnerUuid().equals(player.getUniqueId().toString()) && !endedListing.isCoinsCollected()) {
                    endedListing.setCoinsCollected(true);
                    provider.collectMoney(endedListing);
                    economyProvider.addMoney(player, endedListing.getTotalPrice());

                    if (endedListing.isCoinsCollected() && endedListing.isItemCollected()) {
                        provider.onCollect(endedListing);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void claimAllListings(Player player) {
        claimAllListings(player, Collect.ALL);
    }

    public void claimAllListings(Player player, Collect type) {
        ArrayList<EndedListing> arr = new ArrayList<>();
        if (type == Collect.ALL) {
            arr.addAll(getBoughtEndedListings(player));
            arr.addAll(getSoldEndedListings(player));
        } else if (type == Collect.BOUGHT) {
            arr = getBoughtEndedListings(player);
        } else if (type == Collect.SOLD) {
            arr = getSoldEndedListings(player);
        }
        arr.forEach(endedListing -> claimListing(player, endedListing));
    }

    public ArrayList<EndedListing> getBoughtEndedListings(Player player) {
        ArrayList<EndedListing> arr = new ArrayList<>();
        waitForCollect.forEach(endedListing -> {
            if (player.getUniqueId().toString().equals(endedListing.getBoughtUuid()) && endedListing.isItemCollected()) {
                arr.add(endedListing);
            }
        });
        return arr;
    }

    public ArrayList<EndedListing> getSoldEndedListings(Player player) {
        ArrayList<EndedListing> arr = new ArrayList<>();
        waitForCollect.forEach(endedListing -> {
            if (endedListing.getOwnerUuid().equals(player.getUniqueId().toString()) && !endedListing.isCoinsCollected()) {
                arr.add(endedListing);
            }
        });
        return arr;
    }

    public ArrayList<Listing> getBiddedListings(Player player) {
        ArrayList<Listing> arr = new ArrayList<>();
        cache.forEach(listing -> {
            for (Bid bid : listing.getBids()) {
                if (bid.getBidderUuid().equals(player.getUniqueId().toString())) {
                    arr.add(listing);
                    break;
                }
            }
        });
        return arr;
    }

    public enum Collect {
        ALL,
        BOUGHT,
        SOLD
    }
}
