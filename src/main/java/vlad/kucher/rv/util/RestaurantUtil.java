package vlad.kucher.rv.util;

import vlad.kucher.rv.model.Menu;
import vlad.kucher.rv.to.RestaurantTo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantUtil {
    private RestaurantUtil() {
    }

    public static List<RestaurantTo> getTo(List<Menu> menus, List<Count> counts){
        Map<Integer, Integer> rating = new HashMap<>();
        for (Count c : counts) {
            rating.put(c.getMenuId(), (int)c.getCount());
        }

        List<RestaurantTo> toList = new ArrayList<>();
        for (Menu m : menus) {
            toList.add(createTo(m, rating.getOrDefault(m.getId(), 0)));
        }

        return toList;
    }

    public static RestaurantTo createTo(Menu menu, int rating){
        return new RestaurantTo(menu.getRestaurant().getId(),
                menu.getRestaurant().getName(),
                menu.getDishes(),
                menu.getDate(),
                rating);
    }
}
