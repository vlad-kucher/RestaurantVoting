package vlad.kucher.rv;

import vlad.kucher.rv.model.*;
import vlad.kucher.rv.util.Count;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class TestData {

    private TestData() {
    }

    public static final int USER_ID = 0;
    public static final int ADMIN_ID = 1;

    public static final LocalDate OLD_DATE = LocalDate.of(2017, 9, 29);

    public static final User USER = new User(USER_ID, "User", "user@gmail.com", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);

    public static final int KFC_ID = 0;
    public static final int BURGER_KING_ID = 1;
    public static final int PUZATA_HATA_ID = 2;

    public static final Restaurant KFC = new Restaurant(KFC_ID, "KFC");
    public static final Restaurant BURGER_KING = new Restaurant(BURGER_KING_ID, "Burger King");
    public static final Restaurant PUZATA_HATA = new Restaurant(PUZATA_HATA_ID, "Пузата Хата");

    public static final Dish KFC_TODAY_DISH_1 = new Dish(0, "Острые крылышки", null, 4000);
    public static final Dish KFC_TODAY_DISH_2 = new Dish(1, "Сочная ножка", null, 3000);
    public static final Dish KFC_OLD_DISH_1 = new Dish(2, "Крылышки", null, 2000);
    public static final Dish KFC_OLD_DISH_2 = new Dish(3, "Ножка", null, 1000);
    public static final Dish BURGER_KING_TODAY_DISH_1 = new Dish(4, "Двойной чизбургер", null, 2500);
    public static final Dish BURGER_KING_TODAY_DISH_2 = new Dish(5, "Вкусный гамбургер", null, 1500);
    public static final Dish BURGER_KING_OLD_DISH_1 = new Dish(6, "Чизбургер", null, 2000);
    public static final Dish BURGER_KING_OLD_DISH_2 = new Dish(7, "Гамбургер", null, 1000);
    public static final Dish PUZATA_HATA_TODAY_DISH_1 = new Dish(8, "Красный борщ", null, 9999);
    public static final Dish PUZATA_HATA_TODAY_DISH_2 = new Dish(9, "Домашнаяя колбаска", null, 5555);
    public static final Dish PUZATA_HATA_OLD_DISH_1 = new Dish(10, "Борщ", null, 9000);
    public static final Dish PUZATA_HATA_OLD_DISH_2 = new Dish(11, "Колбаска", null, 5000);

    public static final Menu KFC_TODAY_MENU = new Menu(0, KFC, Arrays.asList(KFC_TODAY_DISH_1, KFC_TODAY_DISH_2), LocalDate.now());
    public static final Menu KFC_OLD_MENU = new Menu(1, KFC, Arrays.asList(KFC_OLD_DISH_1, KFC_OLD_DISH_2), OLD_DATE);
    public static final Menu BURGER_KING_TODAY_MENU = new Menu(2, BURGER_KING, Arrays.asList(BURGER_KING_TODAY_DISH_1, BURGER_KING_TODAY_DISH_2), LocalDate.now());
    public static final Menu BURGER_KING_OLD_MENU = new Menu(3, BURGER_KING, Arrays.asList(BURGER_KING_OLD_DISH_1, BURGER_KING_OLD_DISH_2), OLD_DATE);
    public static final Menu PUZATA_HATA_TODAY_MENU = new Menu(4, PUZATA_HATA, Arrays.asList(PUZATA_HATA_TODAY_DISH_1, PUZATA_HATA_TODAY_DISH_2), LocalDate.now());
    public static final Menu PUZATA_HATA_OLD_MENU = new Menu(5, PUZATA_HATA, Arrays.asList(PUZATA_HATA_OLD_DISH_1, PUZATA_HATA_OLD_DISH_2), OLD_DATE);

    public static final Vote USER_TODAY_VOTE = new Vote(0, USER, KFC_TODAY_MENU, LocalDate.now());
    public static final Vote USER_OLD_VOTE = new Vote(1, USER, KFC_OLD_MENU, OLD_DATE);
    public static final Vote ADMIN_TODAY_VOTE = new Vote(2, ADMIN, KFC_TODAY_MENU, LocalDate.now());
    public static final Vote ADMIN_OLD_VOTE = new Vote(3, ADMIN, KFC_OLD_MENU, OLD_DATE);

    public static final List<Count> COUNTS = Arrays.asList(new Count(0, 2), new Count(1, 2));

    public static final List<Menu> TODAY_MENUS = Arrays.asList(KFC_TODAY_MENU, BURGER_KING_TODAY_MENU, PUZATA_HATA_TODAY_MENU);
    public static final List<Menu> OLD_MENUS = Arrays.asList(KFC_OLD_MENU, BURGER_KING_OLD_MENU, PUZATA_HATA_OLD_MENU);
    public static final List<Menu> KFC_MENUS = Arrays.asList(KFC_TODAY_MENU, KFC_OLD_MENU);
}
