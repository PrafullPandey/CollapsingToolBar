package com.corific.p2_vaio.corific_ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.corific.p2_vaio.corific_ui.R;
import com.corific.p2_vaio.corific_ui.modals.Qualification_Model;
import com.corific.p2_vaio.corific_ui.modals.Review_Model;

import java.util.ArrayList;

/**
 * Created by p2 on 25/5/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private Context context ;
    private Activity activity;
    private ArrayList<Review_Model> reviewlist ;

    public ReviewAdapter(Activity activity, ArrayList<Review_Model> reviewlist) {
        this.activity = activity;
        this.reviewlist = reviewlist;
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_review, parent, false);
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {
        holder.textViewName.setText(reviewlist.get(position).getName());
        holder.textViewMessage.setText(reviewlist.get(position).getMessage());
        holder.ratingBar.setRating(reviewlist.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return reviewlist.size()==0 || reviewlist == null?0:reviewlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewName, textViewMessage ;
        private RatingBar ratingBar ;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = (TextView)itemView.findViewById(R.id.name);
            textViewMessage = (TextView)itemView.findViewById(R.id.message);
            ratingBar = (RatingBar)itemView.findViewById(R.id.ratingBar);
        }
    }
}
