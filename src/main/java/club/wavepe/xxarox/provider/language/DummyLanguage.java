package club.wavepe.xxarox.provider.language;

import cn.nukkit.Player;

import java.util.Arrays;

public class DummyLanguage implements LanguageInterface {
    @Override
    public void sendMessage(Player player, String key, String[] values) {
        player.sendMessage(key + Arrays.toString(values));
    }

    @Override
    public String translate(Player player, String key, String[] values) {
        return key + Arrays.toString(values);
    }
}
