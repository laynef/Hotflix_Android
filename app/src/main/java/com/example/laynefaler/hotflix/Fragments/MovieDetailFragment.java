package com.example.laynefaler.hotflix.Fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laynefaler.hotflix.Activities.DetailActivity;
import com.example.laynefaler.hotflix.Adapters.ReviewAdapter;
import com.example.laynefaler.hotflix.Adapters.TrailerAdapter;
import com.example.laynefaler.hotflix.Data.MovieContract.MovieEntry;
import com.example.laynefaler.hotflix.DataTypes.MovieData;
import com.example.laynefaler.hotflix.DataTypes.ReviewData;
import com.example.laynefaler.hotflix.DataTypes.TrailerData;
import com.example.laynefaler.hotflix_android.BuildConfig;
import com.example.laynefaler.hotflix_android.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

public class MovieDetailFragment extends Fragment  {

    public MovieDetailFragment() {

    }

    public ArrayList<ReviewData> reviewDataArrayList = new ArrayList<ReviewData>();
    public ArrayList<TrailerData> trailerDataArrayList = new ArrayList<TrailerData>();
    public ArrayList<MovieData> FavoritesArrayList;

    TrailerAdapter mTrailerAdapter;
    ReviewAdapter mReviewAdapter;
    MovieData currentMovie;

    private static final String[] MOVIE_COLUMNS = {
        MovieEntry.COLUMN_MOVIE_ID,
        MovieEntry.COLUMN_ORIGINAL_TITLE,
        MovieEntry.COLUMN_OVERVIEW,
        MovieEntry.COLUMN_RELEASE_DATE,
        MovieEntry.COLUMN_POSTER_PATH,
        MovieEntry.COLUMN_POPULARITY,
        MovieEntry.COLUMN_VOTE_AVERAGE
    };

    private static final int COLUMN_MOVIE_ID = 0;
    private static final int COLUMN_ORIGINAL_TITLE = 1;
    private static final int COLUMN_OVERVIEW = 2;
    private static final int COLUMN_RELEASE_DATE = 3;
    private static final int COLUMN_POSTER_PATH = 4;
    private static final int COLUMN_POPULARITY = 5;
    private static final int COLUMN_VOTE_AVERAGE = 6;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState == null || !savedInstanceState.containsKey("reviews") || !savedInstanceState.containsKey("movies")) {
            reviewDataArrayList = new ArrayList<ReviewData>();
            trailerDataArrayList = new ArrayList<TrailerData>();
        } else {
            reviewDataArrayList = savedInstanceState.getParcelableArrayList("reviews");
            trailerDataArrayList = savedInstanceState.getParcelableArrayList("trailers");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("reviews", reviewDataArrayList);
        outState.putParcelableArrayList("trailers", trailerDataArrayList);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        Intent movieIntent = getActivity().getIntent();
        currentMovie = movieIntent.getExtras().getParcelable("movies");

        final Button favorites = (Button) rootView.findViewById(R.id.favorite_button);

        favorites.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vector<ContentValues> cVVector = new Vector<ContentValues>(MOVIE_COLUMNS.length);

                ContentValues favoriteValues = new ContentValues();
                favoriteValues.put(MovieEntry.COLUMN_MOVIE_ID, currentMovie.getId());
                favoriteValues.put(MovieEntry.COLUMN_ORIGINAL_TITLE, currentMovie.getTitle());
                favoriteValues.put(MovieEntry.COLUMN_POSTER_PATH, currentMovie.getImagePath());
                favoriteValues.put(MovieEntry.COLUMN_RELEASE_DATE, currentMovie.getDate());
                favoriteValues.put(MovieEntry.COLUMN_OVERVIEW, currentMovie.getOverview());
                favoriteValues.put(MovieEntry.COLUMN_POPULARITY, currentMovie.getPop());
                favoriteValues.put(MovieEntry.COLUMN_VOTE_AVERAGE, currentMovie.getRating());

                cVVector.add(favoriteValues);

                if (cVVector.size() > 0) {
                    ContentValues[] cvArray = new ContentValues[cVVector.size()];
                    cVVector.toArray(cvArray);
                    getContext().getContentResolver().bulkInsert(MovieEntry.CONTENT_URI, cvArray);
                }
            }
        });

        ListView trailerView = (ListView) rootView.findViewById(R.id.listview_trailers);
        mTrailerAdapter = new TrailerAdapter(getContext(), trailerDataArrayList);
        trailerView.setAdapter(mTrailerAdapter);
        trailerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TrailerData trailerData = (TrailerData) mTrailerAdapter.getItem(position);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerData.getTrailerURL()));
                intent.resolveActivity(getActivity().getPackageManager());
                TrailerData trailerDataDetail = new TrailerData(
                        trailerData.getTrailerTitle(),
                        trailerData.getTrailerURL(),
                        trailerData.getTrailerThumbnail());
                intent.setData(Uri.parse(trailerData.getTrailerURL()));
                startActivity(intent);
            }
        });

        ListView reviewView = (ListView) rootView.findViewById(R.id.listview_reviews);
        mReviewAdapter = new ReviewAdapter(getContext(), reviewDataArrayList);
        reviewView.setAdapter(mReviewAdapter);
        reviewView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReviewData reviewData = (ReviewData) mReviewAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                ReviewData reviewDataDetail = new ReviewData(
                        reviewData.getAuthor(),
                        reviewData.getContent());
                intent.putExtra("reviews", reviewDataDetail);
                startActivity(intent);
            }
        });

        ImageView imageView = (ImageView) rootView.findViewById(R.id.detailView);
        imageView.setVisibility(View.VISIBLE);

        TextView titleView = (TextView) rootView.findViewById(R.id.title_textview);
        TextView rating_date_view = (TextView) rootView.findViewById(R.id.date_rating_textView);
        TextView descriptionView = (TextView) rootView.findViewById(R.id.summary_textView);
        TextView trailerNameView = (TextView) rootView.findViewById(R.id.trailer_name_textView);
        TextView reviewNameView = (TextView) rootView.findViewById(R.id.review_name_textview);

        Picasso.with(getContext()).load(currentMovie.getImagePath()).into(imageView);

        String description = "Summary: " + currentMovie.getOverview();
        String date_rating = "Released: " + currentMovie.getDate() + "\nRating: " + currentMovie.getRating() + "/10";

        titleView.setText(currentMovie.getTitle());
        rating_date_view.setText(date_rating);
        descriptionView.setText(description);
        trailerNameView.setText("Trailers:");
        reviewNameView.setText("Reviews:");

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateReviews();
        updateTrailers();
    }

    public void updateReviews() {
        FetchReviewsTask fetchReviewsTask = new FetchReviewsTask();
        fetchReviewsTask.execute(currentMovie);
    }

    public void updateTrailers() {
        FetchTrailersTask fetchTrailersTask = new FetchTrailersTask();
        fetchTrailersTask.execute(currentMovie);
    }

    public class FetchReviewsTask extends AsyncTask<MovieData, Void, ArrayList<ReviewData>> {

        private String LOG_TAG = FetchReviewsTask.class.getSimpleName();

        private ArrayList<ReviewData> getMovieReviewsFromJson(String reviewsJsonStr) throws JSONException {
            final String MDB_RESULTS = "results";

            JSONObject movieJson = new JSONObject(reviewsJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(MDB_RESULTS);

            final String MDB_AUTHOR = "author";
            final String MDB_CONTENT =  "content";

            for (int i = 0; i < movieArray.length(); i++) {

                JSONObject movieReviewsPathObject = movieArray.getJSONObject(i);

                String author = movieReviewsPathObject.getString(MDB_AUTHOR);
                String content = movieReviewsPathObject.getString(MDB_CONTENT);

                ReviewData reviewData = new ReviewData(author, content);

                reviewDataArrayList.add(reviewData);
            }
            return reviewDataArrayList;
        }

        @Override
        protected ArrayList<ReviewData> doInBackground(MovieData... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String reviewsJsonStr = null;
            MovieData movieData = currentMovie;

            final String movieId = movieData.getId();

            try {
                final String MOVIE_URL_BASE = "http://api.themoviedb.org/3/movie/" + movieId + "/reviews?";
                final String API_PARAM = "api_key";

                Uri builtUri = Uri.parse(MOVIE_URL_BASE).buildUpon()
                        .appendQueryParameter(API_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
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
                reviewsJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "IOException", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Final IOException", e);
                    }
                }
            }

            try {
                return getMovieReviewsFromJson(reviewsJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<ReviewData> movieDatas) {
            mReviewAdapter.updateReviewData(movieDatas);
        }
    }

    public class FetchTrailersTask extends AsyncTask<MovieData, Void, ArrayList<TrailerData>> {

        private String LOG_TAG = FetchTrailersTask.class.getSimpleName();

        private ArrayList<TrailerData> getTrailerDataFromJson(String trailerJsonStr) throws JSONException {

            final String MDB_RESULTS = "results";
            final String MDB_NAME  = "name";
            final String MDB_KEY = "key";

            JSONObject trailersJson = new JSONObject(trailerJsonStr);
            JSONArray trailersArray = trailersJson.getJSONArray(MDB_RESULTS);

            for (int i = 0; i < trailersArray.length(); i++) {

                JSONObject trailer = trailersArray.getJSONObject(i);

                String trailerTitle = trailer.getString(MDB_NAME);
                String trailerUrl = "http://www.youtube.com/watch?v=" + trailer.getString(MDB_KEY);
                String trailerThumbnail = "http://img.youtube.com/vi/" + trailer.getString(MDB_KEY) + "/0.jpg";

                TrailerData trailersInfo = new TrailerData(trailerTitle, trailerUrl, trailerThumbnail);

                trailerDataArrayList.add(trailersInfo);
            }
            return trailerDataArrayList;
        }

        @Override
        protected ArrayList<TrailerData> doInBackground(MovieData... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String trailersJsonStr = null;
            MovieData curMovie = currentMovie;

            final String movieId = curMovie.getId();

            try {

                final String TRAILERS_BASE_URL = "http://api.themoviedb.org/3/movie/" + movieId + "/videos?";
                final String API_KEY = "api_key";

                Uri builtUri = Uri.parse(TRAILERS_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
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
                trailersJsonStr = buffer.toString();

                Log.v(LOG_TAG, "Trailer JSON String: " + trailersJsonStr);

            } catch (IOException e) {
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {

                    }
                }

            }

            try {
                return getTrailerDataFromJson(trailersJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<TrailerData> trailerDatas) {
            mTrailerAdapter.updateTrailerData(trailerDatas);
        }
    }
}
