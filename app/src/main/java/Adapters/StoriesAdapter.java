package Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;

import java.util.ArrayList;

import Acitvity.CreateStory;
import Models.StoriesModel;
import SQLiteDB.CreateStoriesDB;
import Services.Util;
import de.hdodenhof.circleimageview.CircleImageView;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.MyHolder>{
    Context context;
    ArrayList<StoriesModel> storiesModels;
    CreateStoriesDB createStoriesDB;

    public StoriesAdapter(Context context) {
        this.context = context;
        createStoriesDB = new CreateStoriesDB(context);
        storiesModels=createStoriesDB.getAllStories();
    }


    @NonNull
    @Override
    public StoriesAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_stories, parent, false);
        return new StoriesAdapter.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StoriesAdapter.MyHolder holder, final int position) {
        if(position==0){
            holder.article_title.setVisibility(View.INVISIBLE);
        }
        else if(position>0){
            holder.article_title.setVisibility(View.VISIBLE);
            holder.article_title.setText(storiesModels.get(position-1).getStory_title());
            holder.article_image.setImageBitmap(Util.getBitmap(storiesModels.get(position-1).getImage()));

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(position==0){
                    Intent intent = new Intent(context, CreateStory.class);
                    context.startActivity(intent);
                }
                else{
                 /*   Intent intent = new Intent(context, CrimeDetails.class);
                    intent.putExtra("report",reports.get(position-1));
                    context.startActivity(intent);*/
                }

            }
        });

    }

    @Override
    public int getItemCount() {

        if(storiesModels.size()!=0 || storiesModels !=null){
            return storiesModels.size()+1;
        }


        else
        {
            return 1;
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        CircleImageView article_image;
        TextView article_title;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            article_image=(CircleImageView)itemView.findViewById(R.id.article_image);
            article_title=(TextView)itemView.findViewById(R.id.article_title);
        }
    }
}
