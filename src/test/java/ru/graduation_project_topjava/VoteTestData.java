package ru.graduation_project_topjava;

import ru.graduation_project_topjava.model.Vote;

import java.time.LocalDate;
import java.util.List;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant");

    public static final long FIRST_VOTE_ID = 150;

    public static final Vote vote1UserNotActualRestaurant = new Vote(UserTestData.user, RestaurantTestData.notActualRestaurant);
    public static final Vote vote2UserNotActualRestaurant = new Vote(UserTestData.user, RestaurantTestData.notActualRestaurant);
    public static final Vote actualVote1UserActualRestaurant = new Vote(UserTestData.user, RestaurantTestData.actualRestaurant);
    public static final Vote vote2UserActualRestaurant = new Vote(UserTestData.user, RestaurantTestData.actualRestaurant);
    public static final Vote vote1AdminNotActualRestaurant = new Vote(UserTestData.admin, RestaurantTestData.notActualRestaurant);
    public static final Vote vote2AdminNotActualRestaurant = new Vote(UserTestData.admin, RestaurantTestData.notActualRestaurant);
    public static final Vote actualVote1AdminActualRestaurant = new Vote(UserTestData.admin, RestaurantTestData.actualRestaurant);
    public static final Vote vote2AdminActualRestaurant = new Vote(UserTestData.admin, RestaurantTestData.actualRestaurant);

    static {
        vote1UserNotActualRestaurant.setId(FIRST_VOTE_ID);
        vote1UserNotActualRestaurant.setVoteDate(LocalDate.of(1000,10,10));
        vote2UserNotActualRestaurant.setId(FIRST_VOTE_ID + 1);
        vote2UserNotActualRestaurant.setVoteDate(LocalDate.of(2000,10,10));
        actualVote1UserActualRestaurant.setId(FIRST_VOTE_ID + 2);
        vote2UserActualRestaurant.setId(FIRST_VOTE_ID + 3);
        vote2UserActualRestaurant.setVoteDate(LocalDate.of(2000,10,12));
        vote1AdminNotActualRestaurant.setId(FIRST_VOTE_ID + 4);
        vote1AdminNotActualRestaurant.setVoteDate(LocalDate.of(900,10,10));
        vote2AdminNotActualRestaurant.setId(FIRST_VOTE_ID + 5);
        vote2AdminNotActualRestaurant.setVoteDate(LocalDate.of(2000,10,10));
        actualVote1AdminActualRestaurant.setId(FIRST_VOTE_ID + 6);
        vote2AdminActualRestaurant.setId(FIRST_VOTE_ID + 7);
        vote2AdminActualRestaurant.setVoteDate(LocalDate.of(2000,10,15));
    }

    public static final List<Vote> actualRestaurant2Votes = List.of(actualVote1UserActualRestaurant, actualVote1AdminActualRestaurant);
}
