package com.example.laynefaler.hotflix.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.laynefaler.hotflix.DataTypes.ReviewData;
import com.example.laynefaler.hotflix_android.R;

import java.util.ArrayList;

/**
 * Created by laynefaler on 5/26/16.
 */
public class ReviewAdapter extends ArrayAdapter<ReviewData> {


    public ArrayList<ReviewData> reviewDataArrayList;

    public void updateReviewData(ArrayList<ReviewData> newData) {
        this.reviewDataArrayList = newData;
        notifyDataSetChanged();
    }

    public ReviewAdapter(Context context, ArrayList<ReviewData> reviews) {
        super(context, 0, reviews);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReviewData reviewData = getItem(position);

        if (convertView == null) {
             convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_list_review, parent, false);
        }

        TextView authorTextView = (TextView) convertView.findViewById(R.id.review_author);
        authorTextView.setText(reviewData.getAuthor());

        TextView contentTextView = (TextView) convertView.findViewById(R.id.review_content);
        contentTextView.setText(reviewData.getContent());

        return convertView;
    }
}
