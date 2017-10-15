package vlad.kucher.rv.util;

import vlad.kucher.rv.model.Menu;
import vlad.kucher.rv.to.RestaurantTo;

import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {
    private RestaurantUtil() {
    }

    public static List<RestaurantTo> getTo(List<Menu> menus){
        return menus.stream().map(RestaurantUtil::createTo).collect(Collectors.toList());
    }

    public static RestaurantTo createTo(Menu menu){
        return new RestaurantTo(menu.getRestaurant().getId(),
                menu.getRestaurant().getName(),
                menu.getDishes(),
                menu.getDate());
    }
}
