package club.wavepe.xxarox.provider.economy;

import cn.nukkit.Player;

import java.util.UUID;

public interface EconomyInterface {
    String getName();

    void addMoney(String name, int amount);

    void addMoney(Player player, int amount);

    void addMoney(UUID uuid, int amount);

    void removeMoney(String name, int amount);

    void removeMoney(Player player, int amount);

    void removeMoney(UUID uuid, int amount);
}
