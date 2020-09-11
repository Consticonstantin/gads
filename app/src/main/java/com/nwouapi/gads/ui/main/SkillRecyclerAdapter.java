package com.nwouapi.gads.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nwouapi.gads.R;
import com.nwouapi.gads.models.LearnerDataModel;
import com.nwouapi.gads.models.SkillDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SkillRecyclerAdapter extends RecyclerView.Adapter<SkillRecyclerAdapter.MyViewHolder> {
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public  TextView tittle;
        public ImageView badge;
        public  TextView desc;
        public MyViewHolder(CardView linearLayout) {
            super(linearLayout);
           tittle = linearLayout.findViewById(R.id.learner_title);
           desc =  linearLayout.findViewById(R.id.leaner_desc);
           badge = linearLayout.findViewById(R.id.learner_badge);
        }
        public Context getContext(){
            return  tittle.getContext();
        }
    }
     ArrayList<SkillDataModel> list ;
     public SkillRecyclerAdapter(ArrayList<SkillDataModel> list) {
        super();
        this.list = list;
    }
    public void update(ArrayList<SkillDataModel> list ){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        CardView view = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.learning_leaders_item,parent,false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         final SkillDataModel skillDataModel = list.get(position);
        holder.tittle.setText(skillDataModel.name);
        holder.desc.setText(String.format(new Locale("en"), "%d  %s %s", skillDataModel.score, holder.getContext().getText(R.string.skill_iq), skillDataModel.country));
        Glide
                .with(holder.getContext())
                .load(skillDataModel.badgeUrl)
                .centerCrop()
                //.placeholder(R.drawable.loading_spinner)
                .into(holder.badge);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
