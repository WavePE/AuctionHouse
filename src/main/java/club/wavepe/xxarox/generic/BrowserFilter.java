package club.wavepe.xxarox.generic;

import club.wavepe.xxarox.AuctionHouse;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;

import java.util.HashMap;

public class BrowserFilter {
    protected String query = "";

    public Item[] filter(Item[] array) {
        Item[] arr = new Item[0];
        HashMap<Long, Listing> map = new HashMap<>();
        String marketId;

        for (Item item : array) {
            CompoundTag nbt = item.getNamedTag();
            marketId = nbt.getString("marketId");

            if (!("".equals(marketId))) {
                Listing listing = AuctionHouse.getInstance().getListing(marketId);
                map.put(listing.getExpiresAt(), listing);
            }
        }
        /* TODO: sort by keys */

        for (Item item : array) {
            CompoundTag nbt = item.getNamedTag();
            marketId = nbt.getString("marketId");

            if (!("".equals(marketId))) {
                Listing listing = AuctionHouse.getInstance().getListing(marketId);
            }
        }
        return arr;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public enum FilterType{
        TYPE_NONE,
        TYPE_EXPIRE_SOON;
    }
}
