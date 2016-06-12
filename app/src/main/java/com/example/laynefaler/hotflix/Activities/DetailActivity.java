package com.example.laynefaler.hotflix.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.laynefaler.hotflix.Fragments.MovieDetailFragment;
import com.example.laynefaler.hotflix.R;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            String data[] = getIntent().getStringArrayExtra(Intent.EXTRA_TEXT);
            arguments.putStringArray(Intent.EXTRA_TEXT, data);

            // incorrect id
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frame_layout_detail, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(backIntent, 0);
    }

}
