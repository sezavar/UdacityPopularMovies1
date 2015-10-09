package org.sezavar.app.udacitypopularmovies1;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 10/5/15.
 */
public class FetchMoviesListTask extends AsyncTask<String, Void, List<Movie>> {


    private static final String LOG_TAG = FetchMoviesListTask.class.getSimpleName();
    private GridViewAdapter mAdapter;


    private Context mContext;
    private final String apiKey;

    public FetchMoviesListTask(GridViewAdapter adapter, Context context) {
        this.mAdapter = adapter;
        this.mContext = context;
        this.apiKey = mContext.getResources().getString(R.string.the_movie_db_api_key);
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String sortBy = params[0];


        final String SORT_PARAM = "sort_by";
        final String API_KEY_PARAM = "api_key";

        Uri builtUri = Uri.parse(Constants.DISCOVERY_BASE_URL).buildUpon().
                appendQueryParameter(SORT_PARAM, sortBy).
                appendQueryParameter(API_KEY_PARAM, this.apiKey).
                build();

        try {
            String urlStr = builtUri.toString();
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(("GET"));
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }

            return getMoviesDataFromJason(buffer.toString());

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error", e);
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error", e);
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Unable to parse the response", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }


        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies != null) {
            mAdapter.setGridData(movies);
        }
    }

    private List<Movie> getMoviesDataFromJason(String jsonStr) throws JSONException {
        List<Movie> movies = new ArrayList<>();

        final String MDB_ORIGINAL_TITLE = "original_title";
        final String MDB_POSTER_PATH = "poster_path";
        final String MDB_OVERVIEW = "overview";
        final String MDB_VOTE_AVERAGE = "vote_average";
        final String MDB_RELEASE_DATE = "release_date";
        final String MDB_POPULARITY = "popularity";

        JSONObject moviesJson = new JSONObject(jsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray("results");

        for (int i = 0; i < moviesArray.length(); i++) {
            String originalTitle;
            String moviePosterPath;
            String overview;
            double voteAverage;
            Date releaseDate;
            double popularity;

            JSONObject movieJson = moviesArray.getJSONObject(i);
            originalTitle = movieJson.getString(MDB_ORIGINAL_TITLE);
            moviePosterPath = movieJson.getString(MDB_POSTER_PATH);
            overview = movieJson.getString(MDB_OVERVIEW);
            if(overview==null || overview.equals("null")){
                overview="";
            }
            voteAverage = movieJson.getDouble(MDB_VOTE_AVERAGE);
            popularity = movieJson.getDouble(MDB_POPULARITY);

            try {
                releaseDate = Constants.MOVIE_RELEASE_DATE_FORMAT_TO_READ.parse(movieJson.getString(MDB_RELEASE_DATE));
            } catch (ParseException e) {
                Log.w(LOG_TAG, "The release date format is wrong.", e);
                releaseDate = new Date(System.currentTimeMillis());
            }
            movies.add(new Movie(originalTitle, moviePosterPath, overview, voteAverage, releaseDate,popularity));
        }
        Log.i(LOG_TAG, "Fetching movies from json res is done, and num of movies is " + movies.size());
        return movies;
    }
}
