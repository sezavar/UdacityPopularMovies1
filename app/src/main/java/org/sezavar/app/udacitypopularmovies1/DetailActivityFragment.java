package org.sezavar.app.udacitypopularmovies1;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {


    private ImageView mThumbnail;
    private TextView mTitleView;
    private TextView mOverviewView;
    private TextView mRatingView;
    private TextView mReleaseDateView;


    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        String title = intent.getStringExtra(Constants.DETAIL_INTENT_EXTRA_TITLE_KEY);
        String posterPath = intent.getStringExtra(Constants.DETAIL_INTENT_EXTRA_POSTER_PATH_KEY);
        String overview = intent.getStringExtra(Constants.DETAIL_INTENT_EXTRA_OVERVIEW_KEY);
        String ratingStr = intent.getStringExtra(Constants.DETAIL_INTENT_EXTRA_USER_RATING_KEY);
        String releaseDateStr = intent.getStringExtra(Constants.DETAIL_INTENT_EXTRA_RELEASE_DATE_KEY);

        mThumbnail = (ImageView) rootView.findViewById(R.id.movie_thumbnail);
        mTitleView = (TextView) rootView.findViewById(R.id.detail_title_textview);
        mOverviewView = (TextView) rootView.findViewById(R.id.detail_overview_textview);
        mRatingView = (TextView) rootView.findViewById(R.id.detail_rating_textview);
        mReleaseDateView = (TextView) rootView.findViewById(R.id.detail_release_date_textview);

        mTitleView.setText(title);
        mOverviewView.setText(overview);
        mRatingView.setText(ratingStr);
        mReleaseDateView.setText(releaseDateStr);

        Transformation transformer = new RoundedTransformation(6, 0);
        Picasso.with(getContext()).
                load(Constants.MOVIE_POSTER_BASE_URL + posterPath).transform(transformer).
                placeholder(R.drawable.place_holder).
                fit().
                into(mThumbnail);

        return rootView;
    }
}
