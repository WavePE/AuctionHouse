package club.wavepe.xxarox.provider;

import club.wavepe.xxarox.AuctionHouse;
import club.wavepe.xxarox.generic.Bid;
import club.wavepe.xxarox.generic.EndedListing;
import club.wavepe.xxarox.generic.Listing;

public interface Provider {
    void init(AuctionHouse plugin);

    void prepare();

    void load(AuctionHouse plugin);

    void onCreate(Listing listing);

    void onBid(Bid bid);

    void onEnd(Listing listing);

    void onCollect(EndedListing listing);

    void collectItem(EndedListing listing);

    void collectMoney(EndedListing listing);

    void shutdown();
}
