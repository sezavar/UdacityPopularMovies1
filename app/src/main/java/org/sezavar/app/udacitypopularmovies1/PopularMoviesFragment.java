package org.sezavar.app.udacitypopularmovies1;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PopularMoviesFragment extends Fragment {
    private GridViewAdapter mMoviesAdapter;

    public PopularMoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.popular_movies_fragment, container, false);

        List<Movie> listOfMovies = new ArrayList<>();
        this.mMoviesAdapter = new GridViewAdapter(getContext(), R.layout.grid_item_popular_movies, listOfMovies);

        GridView gridOfMovies = (GridView) rootView.findViewById(R.id.gridview_popular_movies);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridOfMovies.setNumColumns(4);
        } else {
            gridOfMovies.setNumColumns(3);
        }
        gridOfMovies.setAdapter(this.mMoviesAdapter);
        gridOfMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l) {
                Movie movie = mMoviesAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);

                intent.putExtra(Constants.DETAIL_INTENT_EXTRA_TITLE_KEY, movie.getTitle());
                intent.putExtra(Constants.DETAIL_INTENT_EXTRA_OVERVIEW_KEY, movie.getOverview());
                intent.putExtra(Constants.DETAIL_INTENT_EXTRA_POSTER_PATH_KEY, movie.getPath());
                intent.putExtra(Constants.DETAIL_INTENT_EXTRA_RELEASE_DATE_KEY, Constants.MOVIE_RELEASE_DATE_FORMAT_TO_WRITE.format(movie.getDateTime()));
                intent.putExtra(Constants.DETAIL_INTENT_EXTRA_USER_RATING_KEY, movie.getUserRating() + "/10");

                startActivity(intent);
            }
        });
        updateMovieList();
        return rootView;
    }

    private void updateMovieList() {
        FetchMoviesListTask fetchMoviesListTask = new FetchMoviesListTask(this.mMoviesAdapter, getContext());
        fetchMoviesListTask.execute(Utility.getPreferredSorting(getContext()));
    }

    void onSortingChanged() {
        updateMovieList();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
