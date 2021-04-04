package club.wavepe.xxarox.generic;

import cn.nukkit.item.Item;

public class BrowserFilter {
    protected String query = "";

    public Item[] filter(Item[] array) {
        Item[] arr = new Item[0];
        return arr;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
