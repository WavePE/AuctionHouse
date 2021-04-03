package club.wavepe.xxarox.generic;

import cn.nukkit.Player;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Bid {
    private String id;
    private String parentId;
    private String bidderUuid;
    private String bidderDisplayName;
    private Integer amount;
    private long timestamp;

    public Bid(Player bidder, Integer amount, String parentId){
        id = UUID.randomUUID().toString();
        this.parentId = parentId;
        this.bidderUuid = bidder.getUniqueId().toString();
        this.bidderDisplayName = bidder.getDisplayName();
        this.amount = amount;
        this.timestamp = Instant.now().getEpochSecond();
    }

    public Bid(String id, String parentId, String bidderUuid, String bidderDisplayName, Integer amount, long timestamp){
        this.id = id;
        this.parentId = parentId;
        this.bidderUuid = bidderUuid;
        this.bidderDisplayName = bidderDisplayName;
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
