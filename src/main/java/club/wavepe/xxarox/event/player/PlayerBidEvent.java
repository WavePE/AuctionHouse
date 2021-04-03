package club.wavepe.xxarox.event.player;

import club.wavepe.xxarox.generic.Bid;
import cn.nukkit.Player;
import cn.nukkit.event.Cancellable;
import cn.nukkit.event.player.PlayerEvent;
import lombok.Getter;

@Getter
public class PlayerBidEvent extends PlayerEvent implements Cancellable {
    protected Bid bid;

    public PlayerBidEvent(Player player, Bid bid) {
        this.player = player;
        this.bid = bid;
    }
}
