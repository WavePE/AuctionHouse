package club.wavepe.xxarox.provider;

import club.wavepe.xxarox.AuctionHouse;
import club.wavepe.xxarox.generic.Bid;
import club.wavepe.xxarox.generic.EndedListing;
import club.wavepe.xxarox.generic.Listing;
import club.wavepe.xxarox.utils.Utils;
import cn.nukkit.utils.Config;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonProvider implements Provider {
    protected Config auctions;
    protected Config bids;
    protected Config ended;

    @Override
    public void init(AuctionHouse plugin) {
        this.auctions = new Config(plugin.getDataFolder().toPath().resolve("db").resolve("auctions.json").toFile(), Config.JSON);
        this.bids = new Config(plugin.getDataFolder().toPath().resolve("db").resolve("bids.json").toFile(), Config.JSON);
        this.ended = new Config(plugin.getDataFolder().toPath().resolve("db").resolve("ended.json").toFile(), Config.JSON);
    }

    @Override
    public void prepare() {
    }

    @Override
    public void load(AuctionHouse plugin) {
        auctions.getAll().forEach((key, value) -> {
            if (value instanceof LinkedTreeMap) {
                LinkedTreeMap<String, Object> map = ((LinkedTreeMap<String, Object>) value);
                ArrayList<Bid> resultBids = new ArrayList<>();
                ArrayList<String> bidStr = (ArrayList<String>) map.get("bids");
                bidStr.forEach((id) -> {
                    LinkedTreeMap<String, Object> map2 = ((LinkedTreeMap<String, Object>) bids.get(id));
                    if (((String) map2.get("getParentId")).equals((String) map.get("getId"))) {
                        resultBids.add(new Bid((String) map2.get("getId"), (String) map2.get("getParentId"), (String) map2.get("bidderUuid"), (String) map2.get("bidderDisplayName"), (int) map2.get("amount"), (long) map2.get("timestamp")));
                    }
                });
                try {
                    Listing listing = new Listing((String) map.get("getId"), (String) map.get("getOwnerUuid"), (String) map.get("getOwnerDisplayName"), Utils.decodeItem((String) map.get("getItem")), (int) map.get("getStartingPrice"), (int) map.get("getCurrentPrice"), (long) map.get("getExpiresAt"), resultBids);
                    AuctionHouse.getInstance().cache.add(listing);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onCreate(Listing listing) {
        try {
            HashMap<String, Object> listingData = new HashMap<>();
            listingData.put("getId", listing.getId());
            listingData.put("getCurrentPrice", listing.getCurrentPrice());
            listingData.put("getStartingPrice", listing.getStartingPrice());
            listingData.put("getExpiresAt", listing.getExpiresAt());
            listingData.put("getItem", Utils.encodeItem(listing.getItem()));
            listingData.put("getOwnerDisplayName", listing.getOwnerDisplayName());
            listingData.put("getOwnerUuid", listing.getOwnerUuid());

            ArrayList<String> bids = new ArrayList<>();
            listing.getBids().forEach((Bid bid) -> bids.add(bid.getId()));
            listingData.put("bids", bids);

            auctions.set(listing.getId(), listingData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBid(Bid bid) {

    }

    @Override
    public void onEnd(Listing listing) {

    }

    @Override
    public void onCollect(EndedListing listing) {

    }

    @Override
    public void shutdown() {
        auctions.save();
        bids.save();
        ended.save();
    }
}
