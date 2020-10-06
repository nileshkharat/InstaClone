package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.R;


import Acitvity.MainActivity;


public class FeedAdaptor extends  RecyclerView.Adapter<FeedAdaptor.MyViewHolder>{
    String data1[],data2[];
    int image[];

    Context context;
    public FeedAdaptor(Context ct,String s1[],String s2[],int img[]){
        context=ct;
        data1=s1;
        data2=s2;
        image=img;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.feed,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
     holder.title.setText(data1[position]);
     holder.des.setText(data2[position]);
     holder.feed_image.setImageResource(image[position]);

    }

    @Override
    public int getItemCount() {
        return image.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView feed_image,like,comment,send,profile;
        TextView title,des;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                title=itemView.findViewById(R.id.title);
                des=itemView.findViewById(R.id.des);
                feed_image=itemView.findViewById(R.id.feed_image);
                profile=itemView.findViewById(R.id.profile);
                like=itemView.findViewById(R.id.like);
                comment=itemView.findViewById(R.id.comment);
                send=itemView.findViewById(R.id.send);
            }
        }
}
