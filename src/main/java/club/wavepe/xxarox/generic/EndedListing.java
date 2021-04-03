package club.wavepe.xxarox.generic;

import cn.nukkit.item.Item;
import lombok.Getter;

@Getter
public class EndedListing {
    private String id;
    private String ownerDisplayName;
    private String ownerUuid;
    private String boughtDisplayName;
    private String boughtUuid;
    private int totalPrice;
    private boolean coinsCollected;
    private boolean itemCollected;
    private Item item;

    public EndedListing(Listing listing){
        this(listing, false);
    }

    public EndedListing(Listing listing, boolean coinsCollected){
        this(listing, coinsCollected, false);
    }

    public EndedListing(Listing listing, boolean coinsCollected, boolean itemCollected){
        id = listing.getId();
        ownerDisplayName = listing.getOwnerDisplayName();
        ownerUuid = listing.getOwnerUuid();
        this.coinsCollected = coinsCollected;
        this.itemCollected = itemCollected;
        boughtDisplayName = null;
        boughtUuid = null;

        if (listing.getLastBid() != null) {
            Bid lastBid = listing.getLastBid();
            this.boughtDisplayName = lastBid.getBidderDisplayName();
            this.boughtUuid = lastBid.getBidderUuid();
        }
        totalPrice = listing.getCurrentPrice();
        item = listing.getItem().clone();
    }

    public void setCoinsCollected(boolean coinsCollected) {
        this.coinsCollected = coinsCollected;
    }

    public void setItemCollected(boolean itemCollected) {
        this.itemCollected = itemCollected;
    }
}
