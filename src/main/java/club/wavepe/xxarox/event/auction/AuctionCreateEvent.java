package club.wavepe.xxarox.event.auction;

import club.wavepe.xxarox.generic.Listing;
import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuctionCreateEvent extends Event implements Cancellable {
    protected Player player;
    protected Listing listing;
}
