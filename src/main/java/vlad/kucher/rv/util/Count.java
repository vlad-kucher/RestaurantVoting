package vlad.kucher.rv.util;

//https://stackoverflow.com/a/36329166
public class Count {
    private int menuId;
    private long count;

    public Count(int menuId, long count) {
        this.menuId = menuId;
        this.count = count;
    }

    public int getMenuId() {
        return menuId;
    }

    public long getCount() {
        return count;
    }
}
