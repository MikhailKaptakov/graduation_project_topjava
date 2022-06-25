package ru.graduation_project_topjava;

import ru.graduation_project_topjava.model.Vote;

import java.time.LocalDate;
import java.util.List;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");

    public static final long FIRST_VOTE_ID = 150;

    public static final Vote vote1UserRestaurant1 = new Vote(UserTestData.user, RestaurantTestData.notActualRestaurant);
    public static final Vote vote2UserRestaurant1 = new Vote(UserTestData.user, RestaurantTestData.notActualRestaurant);
    public static final Vote vote1UserRestaurant2 = new Vote(UserTestData.user, RestaurantTestData.actualRestaurant);
    public static final Vote vote2UserRestaurant2 = new Vote(UserTestData.user, RestaurantTestData.actualRestaurant);
    public static final Vote vote1AdminRestaurant1 = new Vote(UserTestData.admin, RestaurantTestData.notActualRestaurant);
    public static final Vote vote2AdminRestaurant1 = new Vote(UserTestData.admin, RestaurantTestData.notActualRestaurant);
    public static final Vote vote1AdminRestaurant2 = new Vote(UserTestData.admin, RestaurantTestData.actualRestaurant);
    public static final Vote vote2AdminRestaurant2 = new Vote(UserTestData.admin, RestaurantTestData.actualRestaurant);

    static {
        vote1UserRestaurant1.setId(FIRST_VOTE_ID);
        vote1UserRestaurant1.setVoteDate(LocalDate.of(1000,10,10));
        vote2UserRestaurant1.setId(FIRST_VOTE_ID + 1);
        vote2UserRestaurant1.setVoteDate(LocalDate.of(2000,10,10));
        vote1UserRestaurant2.setId(FIRST_VOTE_ID + 2);
        vote2UserRestaurant2.setId(FIRST_VOTE_ID + 3);
        vote2UserRestaurant2.setVoteDate(LocalDate.of(2000,10,12));
        vote1AdminRestaurant1.setId(FIRST_VOTE_ID + 4);
        vote1AdminRestaurant1.setVoteDate(LocalDate.of(900,10,10));
        vote2AdminRestaurant1.setId(FIRST_VOTE_ID + 5);
        vote2AdminRestaurant1.setVoteDate(LocalDate.of(2000,10,10));
        vote1AdminRestaurant2.setId(FIRST_VOTE_ID + 6);
        vote2AdminRestaurant2.setId(FIRST_VOTE_ID + 7);
        vote2AdminRestaurant2.setVoteDate(LocalDate.of(2000,10,15));
    }

    public static final List<Vote> actualRestaurant2Votes = List.of(vote1UserRestaurant2, vote1AdminRestaurant2);
}
