package com.nct.trenx.utils;

import com.nct.trenx.R;
import com.nct.trenx.model.FeedPost;

import java.util.ArrayList;
import java.util.List;

public class CommunityDataProvider {

    public static List<FeedPost> getCommunityFeed() {
        List<FeedPost> feed = new ArrayList<>();
        
        feed.add(new FeedPost(
                "c1",
                "Dan Citizen",
                R.drawable.avatar_dan,
                "Is feeling Pumped",
                "34 minutes ago",
                R.drawable.feed_workout_1, // chameleon
                "Completed a Workout",
                "100% Progress",
                "30:48 Time",
                "Intermediate",
                1,
                0,
                "one of the beautiful little friends from nearby zoo \uD83D\uDE04 \uD83D\uDC4F happy Sunday to you all \uD83E\uDD29"
        ));

        feed.add(new FeedPost(
                "c2",
                "Steffchen",
                R.drawable.avatar_steffchen,
                "Is feeling Motivated",
                "2 days ago",
                R.drawable.feed_workout_2, // yoga
                "Completed a Challenge",
                "100% Progress",
                "26:49 Time",
                "Advanced",
                52,
                24,
                "Coreeeee\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25\nHappy Friday happy weekend \uD83E\uDD73\uD83E\uDD73\uD83D\uDC83"
        ));

        // Add one more for scrolling
        feed.add(new FeedPost(
                "c3",
                "Chris Heria",
                R.mipmap.ic_launcher_round, // placeholder if we don't have enough avatars, actually we can just use avatar_dan
                "Is feeling Strong",
                "5 days ago",
                R.drawable.bg_premium_gradient, // just some placeholder image
                "Completed a Program",
                "100% Progress",
                "45:00 Time",
                "Expert",
                9000,
                1200,
                "Keep pushing your limits! \uD83D\uDCAA"
        ));

        return feed;
    }

    public static List<FeedPost> getPersonalFeed() {
        List<FeedPost> feed = new ArrayList<>();
        
        feed.add(new FeedPost(
                "p1",
                "Steffchen", // friend
                R.drawable.avatar_steffchen,
                "Is feeling Motivated",
                "2 days ago",
                R.drawable.feed_workout_2, // yoga
                "Completed a Challenge",
                "100% Progress",
                "26:49 Time",
                "Advanced",
                52,
                24,
                "Coreeeee\uD83D\uDD25\uD83D\uDD25\uD83D\uDD25\nHappy Friday happy weekend \uD83E\uDD73\uD83E\uDD73\uD83D\uDC83"
        ));
        
        feed.add(new FeedPost(
                "p2",
                "Dan Citizen",
                R.drawable.avatar_dan,
                "Is feeling Pumped",
                "34 minutes ago",
                R.drawable.feed_workout_1, // chameleon
                "Completed a Workout",
                "100% Progress",
                "30:48 Time",
                "Intermediate",
                1,
                0,
                "one of the beautiful little friends from nearby zoo \uD83D\uDE04 \uD83D\uDC4F happy Sunday to you all \uD83E\uDD29"
        ));

        return feed;
    }
}
