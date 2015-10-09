package org.sezavar.app.udacitypopularmovies1;

import java.util.Date;

/**
 * Created by amir on 10/6/15.
 */
public class Movie {
    private String title;
    private String path;
    private String overview;
    private double userRating;
    private Date dateTime;
    private double popularity;

    public Movie(String title, String path, String overview, double userRating, Date dateTime, double popularity) {
        this.title = title;
        this.path = path;
        this.overview = overview;
        this.userRating = userRating;
        this.dateTime = dateTime;
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public String getOverview() {
        return overview;
    }

    public double getUserRating() {
        return userRating;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public double getPopularity() {
        return popularity;
    }
}
