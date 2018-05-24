package com.corific.p2_vaio.corific_ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.corific.p2_vaio.corific_ui.R;
import com.corific.p2_vaio.corific_ui.modals.Qualification_Model;

import java.util.ArrayList;

/**
 * Created by p2 on 25/5/18.
 */

public class QualificationAdapter extends RecyclerView.Adapter<QualificationAdapter.ViewHolder> {

    private Context context ;
    private Activity activity;
    private ArrayList<Qualification_Model> qualificationlist ;

    public QualificationAdapter(Activity activity, ArrayList<Qualification_Model> qualificationlist) {
        this.activity = activity;
        this.qualificationlist = qualificationlist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_qualification, parent, false);
        return new QualificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewCourse.setText(qualificationlist.get(position).getCourse());
        holder.textViewCollege.setText(qualificationlist.get(position).getCollege());
        holder.textViewDuration.setText(qualificationlist.get(position).getDuration());
    }

    @Override
    public int getItemCount() {
        return qualificationlist.size()==0 || qualificationlist == null?0:qualificationlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewCourse , textViewCollege , textViewDuration;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewCourse = (TextView)itemView.findViewById(R.id.course);
            textViewCollege = (TextView)itemView.findViewById(R.id.college);
            textViewDuration = (TextView)itemView.findViewById(R.id.duration);
        }
    }
}
