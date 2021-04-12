package club.wavepe.xxarox.provider.language;

import cn.nukkit.Player;

public interface LanguageInterface {
    void sendMessage(Player player, String key, String[] values);

    String translate(Player player, String key, String[] values);
}
