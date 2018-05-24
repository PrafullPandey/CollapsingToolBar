package com.corific.p2_vaio.corific_ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.corific.p2_vaio.corific_ui.R;

import java.util.ArrayList;

/**
 * Created by p2 on 25/5/18.
 */

public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.ViewHolder>  {

    private Context context ;
    private Activity activity;
    private ArrayList<String> morelist ;


    public MoreAdapter(Activity activity, ArrayList<String> morelist) {
        this.activity = activity;
        this.morelist = morelist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_more, parent, false);
        return new MoreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewMore.setText(morelist.get(position));
    }

    @Override
    public int getItemCount() {
        return morelist.size()==0 || morelist == null?0:morelist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewMore;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewMore = (TextView)itemView.findViewById(R.id.moreText);
        }
    }
}
