package org.sezavar.app.udacitypopularmovies1;

import java.text.SimpleDateFormat;

/**
 * Created by amir on 10/9/15.
 */
public class Constants {
    public static final String DISCOVERY_BASE_URL = "http://api.themoviedb.org/3/discover/movie";
    public static final SimpleDateFormat MOVIE_RELEASE_DATE_FORMAT_TO_READ = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat MOVIE_RELEASE_DATE_FORMAT_TO_WRITE = new SimpleDateFormat("yyyy");

    public final static String DETAIL_INTENT_EXTRA_TITLE_KEY = "title";
    public final static String DETAIL_INTENT_EXTRA_POSTER_PATH_KEY = "poster";
    public final static String DETAIL_INTENT_EXTRA_OVERVIEW_KEY = "overview";
    public final static String DETAIL_INTENT_EXTRA_USER_RATING_KEY = "rating";
    public final static String DETAIL_INTENT_EXTRA_RELEASE_DATE_KEY = "release";

    public final static String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";
}
