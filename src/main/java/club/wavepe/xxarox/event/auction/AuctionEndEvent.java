package club.wavepe.xxarox.event.auction;

import club.wavepe.xxarox.generic.EndedListing;
import cn.nukkit.Player;
import cn.nukkit.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuctionEndEvent extends Event {
    protected Player player;
    protected EndedListing endedListing;
}
