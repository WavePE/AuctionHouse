package club.wavepe.xxarox;

import club.wavepe.xxarox.generic.EndedListing;
import club.wavepe.xxarox.generic.Listing;
import club.wavepe.xxarox.provider.JsonProvider;
import club.wavepe.xxarox.provider.Provider;
import cn.nukkit.plugin.PluginBase;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class AuctionHouse extends PluginBase {
    protected static AuctionHouse instance;
    protected Provider provider;

    public ArrayList<Listing> cache = new ArrayList<>();
    public ArrayList<EndedListing> waitForCollect = new ArrayList<>();

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();

        provider = getProviderFromString(getConfig().getString("provider", "json"));

        provider.init(this);
    }

    @Override
    public void onEnable() {
        provider.prepare();
        provider.load(this);
    }

    @Override
    public void onDisable() {
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
            case "js": {
                return new JsonProvider();
            }
        }
        throw new RuntimeException("Provider '" + providerType + "' not found!");
    }
}
