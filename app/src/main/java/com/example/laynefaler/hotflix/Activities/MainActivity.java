package com.example.laynefaler.hotflix.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.laynefaler.hotflix.Fragments.MovieDetailFragment;
import com.example.laynefaler.hotflix.Fragments.MovieImageFragment;
import com.example.laynefaler.hotflix.R;
import com.example.laynefaler.hotflix.Sync.MovieSyncAdapter;
import com.example.laynefaler.hotflix.Utilities.Utility;

public class MainActivity extends AppCompatActivity implements MovieImageFragment.Callback {


    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String MOVIE_DETAIL_FRAGMENT_TAG = "MDFTAG";
    private static final String MOVIE_LIST_FRAGMENT_TAG = "MLFTAG";
    private String mSortOrder;
    private boolean mTwoPane;
    private MovieImageFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSortOrder = Utility.getSortOrder(this);

        if (findViewById(R.id.detail_fragment) != null) {

            mTwoPane = true;

            mFragment = new MovieImageFragment();
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setTwoPane(mTwoPane);
            if (savedInstanceState == null) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_fragment, fragment)
                        .commit();
            } else {
                mFragment = (MovieImageFragment) getSupportFragmentManager()
                        .findFragmentByTag(MOVIE_LIST_FRAGMENT_TAG);
            }
        } else {
            mTwoPane = false;
        }
        if (mFragment != null) {
            mFragment.setTwoPane(mTwoPane);
        }
        MovieSyncAdapter.initializeSyncAdapter(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(String[] data) {

        if (mTwoPane) {

            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setTwoPane(mTwoPane);
            Bundle args = new Bundle();
            args.putStringArray(Intent.EXTRA_TEXT, data);
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().
                    replace(R.id.detail_fragment, fragment, MOVIE_DETAIL_FRAGMENT_TAG)
                    .commit();

        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, data);
            startActivity(intent);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        String sortOrder = Utility.getSortOrder(this);

        if (sortOrder != null && !sortOrder.equals(mSortOrder)) {
            MovieImageFragment mf = (MovieImageFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.main_fragment);
            if (null != mf) {
                mf.dataAfterSortSelected();
            } else {
                mFragment = (MovieImageFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
                mFragment.setTwoPane(mTwoPane);
                mFragment.dataAfterSortSelected();
            }
            mSortOrder = sortOrder;
        }
    }

}
