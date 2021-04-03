package club.wavepe.xxarox.generic;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class Listing {
    protected String id;
    protected String ownerUuid;
    protected String ownerDisplayName;
    protected Item item;
    protected Integer startingPrice;
    protected Integer currentPrice;
    protected long expiresAt;
    protected List<Bid> bids;

    public Listing(Player owner, Item item, Integer startingPrice, long expiresAt) {
        this.ownerUuid = owner.getUniqueId().toString();
        this.ownerDisplayName = owner.getDisplayName();
        this.item = item;
        this.startingPrice = this.currentPrice = startingPrice;
        this.expiresAt = expiresAt;
        bids = new ArrayList<>();
    }

    /*public Listing(String id, String ownerUuid, String ownerDisplayName, Item item, Integer startingPrice, Integer currentPrice, long expiresAt, List<Bid> bids){
    }*/

    public Bid getLastBid() {
        return bids.get(0);
    }

    public Bid onBid(Player bidder, Integer amount){
        Bid bid = new Bid(bidder, amount, id);
        bids.add(0, bid);
        return bid;
    }

    public void onEnd(){
        //TODO
    }
}
