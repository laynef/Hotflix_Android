package com.example.laynefaler.hotflix.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.laynefaler.hotflix.Activities.DetailActivity;
import com.example.laynefaler.hotflix.Adapters.ImageAdapter;
import com.example.laynefaler.hotflix.DataTypes.MovieData;
import com.example.laynefaler.hotflix_android.BuildConfig;
import com.example.laynefaler.hotflix_android.R;

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

public class  MovieImageFragment extends Fragment {

    private String LOG_TAG = MovieImageFragment.class.getSimpleName();

    ArrayList<MovieData> MovieDataArrayList = new ArrayList<MovieData>();
    ImageAdapter mMovieDataAdapter;

    public MovieImageFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            MovieDataArrayList = new ArrayList<MovieData>();
        } else {
            MovieDataArrayList = savedInstanceState.getParcelableArrayList("movies");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movies", MovieDataArrayList);
    }

    private void updateImages() {
        FetchImageTask imageTask = new FetchImageTask();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = prefs.getString(getString(R.string.movieKey), getString(R.string.arrayPopularValue));
        imageTask.execute(sortOrder);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateImages();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie_image, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.main_gridview);
        mMovieDataAdapter = new ImageAdapter(getContext(), MovieDataArrayList);
        gridView.setAdapter(mMovieDataAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieData movieData = (MovieData) mMovieDataAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                MovieData movieDataDetail = new MovieData(
                        movieData.getImagePath(),
                        movieData.getOverview(),
                        movieData.getTitle(),
                        movieData.getDate(),
                        movieData.getRating(),
                        movieData.getPop(),
                        movieData.getId());
                intent.putExtra("movies", movieDataDetail);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public class FetchImageTask extends AsyncTask<String, Void, ArrayList<MovieData>> {

        private String LOG_TAG = FetchImageTask.class.getSimpleName();

        private ArrayList<MovieData> getMovieImageFromJson(String movieImageJsonStr) throws JSONException {
            final String MDB_RESULTS = "results";
            final String MDB_IMAGE = "poster_path";
            final String MDB_OVERVIEW = "overview";
            final String MDB_TITLE = "original_title";
            final String MDB_DATE = "release_date";
            final String MDB_RATING = "vote_average";
            final String MDB_POP = "popularity";
            final String MDB_ID = "id";

            JSONObject movieJson = new JSONObject(movieImageJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(MDB_RESULTS);

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movieImagePathObject = movieArray.getJSONObject(i);

                String imagePath = "http://image.tmdb.org/t/p/w185/" + movieImagePathObject.getString(MDB_IMAGE);
                String overview = movieImagePathObject.getString(MDB_OVERVIEW);
                String title = movieImagePathObject.getString(MDB_TITLE);
                String date = movieImagePathObject.getString(MDB_DATE);
                String rating = movieImagePathObject.getString(MDB_RATING);
                String pop = movieImagePathObject.getString(MDB_POP);
                String id = movieImagePathObject.getString(MDB_ID);

                MovieDataArrayList.add(new MovieData(imagePath, overview, title, date, rating, pop, id));
            }

            return MovieDataArrayList;
        }

        @Override
        protected ArrayList<MovieData> doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String singleMovieJsonStr = null;

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            String sortOrder = prefs.getString(getString(R.string.movieKey), getString(R.string.arrayPopularValue));

            if (!sortOrder.equals(getString(R.string.arrayFavoriteValue))) {
                try {
                    final String MOVIE_URL_BASE = "http://api.themoviedb.org/3/movie/";
                    final String API_PARAM = "api_key";

                    Uri builtUri = Uri.parse(MOVIE_URL_BASE).buildUpon()
                            .appendPath(sortOrder)
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
                    singleMovieJsonStr = buffer.toString();

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
            }

            try {
                return getMovieImageFromJson(singleMovieJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> movieDatas) {
            mMovieDataAdapter.updateData(movieDatas);
        }

    } // end of task

} // end of class
