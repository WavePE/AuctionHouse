package club.wavepe.xxarox;

import club.wavepe.xxarox.generic.EndedListing;
import club.wavepe.xxarox.generic.Listing;
import club.wavepe.xxarox.provider.JsonProvider;
import club.wavepe.xxarox.provider.Provider;
import cn.nukkit.plugin.PluginBase;

public class AuctionHouse extends PluginBase {
    protected static AuctionHouse instance;
    protected Provider provider;

    private Listing[] cache = new Listing[0];
    private EndedListing[] waitForCollect = new EndedListing[0];

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
