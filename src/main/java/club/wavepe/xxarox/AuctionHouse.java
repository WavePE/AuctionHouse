package club.wavepe.xxarox;

import club.wavepe.xxarox.generic.EndedListing;
import club.wavepe.xxarox.generic.Listing;
import cn.nukkit.plugin.PluginBase;

public class AuctionHouse extends PluginBase {
    protected static AuctionHouse instance;

    private Listing[] cache = new Listing[0];
    private EndedListing[] waitForCollect = new EndedListing[0];

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    public static AuctionHouse getInstance() {
        return instance;
    }
}
