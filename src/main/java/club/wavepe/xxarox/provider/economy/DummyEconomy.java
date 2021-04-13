package club.wavepe.xxarox.provider.economy;

import cn.nukkit.Player;

import java.util.UUID;

public class DummyEconomy implements EconomyInterface {
    @Override
    public String getName() {
        return "Dummy";
    }

    @Override
    public void addMoney(String name, int amount) {

    }

    @Override
    public void addMoney(Player player, int amount) {

    }

    @Override
    public void addMoney(UUID uuid, int amount) {

    }

    @Override
    public void removeMoney(String name, int amount) {

    }

    @Override
    public void removeMoney(Player player, int amount) {

    }

    @Override
    public void removeMoney(UUID uuid, int amount) {

    }
}
