package ru.graduation_project_topjava;

import ru.graduation_project_topjava.MatcherFactory;
import ru.graduation_project_topjava.MealTestData;
import ru.graduation_project_topjava.model.Restaurant;
import ru.graduation_project_topjava.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);
         /*   MatcherFactory.usingAssertions(Restaurant.class,
//     No need use ignoringAllOverriddenEquals, see https://assertj.github.io/doc/#breaking-changes
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("meals", "votes").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });*/
    public static MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER =
            MatcherFactory.usingEqualsComparator(RestaurantTo.class);

    public static final long RESTAURANT1_ID = 1;
    public static final long RESTAURANT2_ID = 2;

    public static final Restaurant RESTAURANT1 = new Restaurant("NotActualRestaurant", MealTestData.actualRestaurant1Meals);
    public static final Restaurant RESTAURANT2 = new Restaurant("NotActualRestaurant", MealTestData.actualRestaurant2Meals);

    static {
        RESTAURANT1.setId(RESTAURANT1_ID);
        RESTAURANT2.setId(RESTAURANT2_ID);
        RESTAURANT2.setLastUpdateDate(LocalDate.now());
    }

    public static final List<Restaurant> notActualRestaurants =  List.of(RESTAURANT1);
    public static final List<Restaurant> actualRestaurants =  List.of(RESTAURANT2);

/*    public static Restaurant getNew() {
        return new Restaurant("new Restaurant", ru.graduation_project_topjava.MealTestData.mealsNewRestaurant);
    }*/
}
