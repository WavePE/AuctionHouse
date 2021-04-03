package club.wavepe.xxarox.utils;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;

import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Base64;

public class Utils {
    private static ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;


    public static String encodeItem(Item item) throws IOException {
        return Base64.getEncoder().encodeToString(NBTIO.write(NBTIO.putItemHelper(item), byteOrder));
    }

    public static Item decodeItem(String buffer) throws IOException {
        return NBTIO.getItemHelper(NBTIO.read(Base64.getDecoder().decode(buffer), byteOrder));
    }
}
