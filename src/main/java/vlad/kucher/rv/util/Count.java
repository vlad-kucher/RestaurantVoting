package vlad.kucher.rv.util;

//https://stackoverflow.com/a/36329166
public class Count {
    private int restaurantId;
    private long count;

    public Count(int restaurantId, long count) {
        this.restaurantId = restaurantId;
        this.count = count;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public long getCount() {
        return count;
    }
}
