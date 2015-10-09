package org.sezavar.app.udacitypopularmovies1;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by amir on 10/7/15.
 */
public class GridViewAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = GridViewAdapter.class.getSimpleName();
    private int mResourceId;

    public GridViewAdapter(Context context, int resource, List<Movie> objects) {
        super(context, resource, objects);
        this.mResourceId = resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) this.getContext()).getLayoutInflater();
            row = inflater.inflate(this.mResourceId, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        Movie item = this.getItem(position);

        Transformation transformer = new RoundedTransformation(6, 0);
        Picasso.with(getContext()).
                load(Constants.MOVIE_POSTER_BASE_URL + item.getPath()).transform(transformer).
                placeholder(R.drawable.place_holder).
                fit().
                into(holder.getMovieThumbnail());

        return row;
    }

    public static class ViewHolder {
        private ImageView movieThumbnail;

        public ImageView getMovieThumbnail() {
            return movieThumbnail;
        }

        public ViewHolder(View view) {
            movieThumbnail = (ImageView) view.findViewById(R.id.movie_thumbnail);
        }
    }

    public void setGridData(List<Movie> mGridData) {
        this.clear();
        this.addAll(mGridData);
        Log.i(LOG_TAG, "all movies are added to adapter");
        notifyDataSetChanged();
    }
}
