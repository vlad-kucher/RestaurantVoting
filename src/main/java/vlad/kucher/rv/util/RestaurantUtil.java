package vlad.kucher.rv.util;

import vlad.kucher.rv.model.Menu;
import vlad.kucher.rv.to.RestaurantTo;

import java.util.ArrayList;
import java.util.List;

public class RestaurantUtil {
    private RestaurantUtil() {
    }

    public static List<RestaurantTo> getTo(List<Menu> menus){
        List<RestaurantTo> toList = new ArrayList<>();

        for (Menu m : menus) {
            toList.add(createTo(m));
        }

        return toList;
    }

    public static RestaurantTo createTo(Menu menu){
        return new RestaurantTo(menu.getRestaurant().getId(),
                menu.getRestaurant().getName(),
                menu.getDishes(),
                menu.getDate());
    }
}
