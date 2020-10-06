package Acitvity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.instagramclone.R;

import Adapters.CenterZoomLayoutManager;
import Adapters.FeedAdaptor;
import Adapters.StoriesAdapter;

public class MainActivity extends AppCompatActivity {
    RecyclerView recently_added_stories;
public String s1[],s2[];
    RecyclerView instagram_feed;
    int images[]={R.drawable.microsoft,R.drawable.manali,R.drawable.cs,R.drawable.fantastic,R.drawable.harry,R.drawable.apple,R.drawable.google,R.drawable.navy};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        String s1[]  =getResources().getStringArray(R.array.Title);
        String s2[]=  getResources().getStringArray(R.array.Description);
        setContentView(R.layout.activity_main);
        CenterZoomLayoutManager centerZoomLayoutManager = new CenterZoomLayoutManager(MainActivity.this, 0, false);
        recently_added_stories = (RecyclerView) findViewById(R.id.recently_added_stories);
        recently_added_stories.setLayoutManager(centerZoomLayoutManager);
        LinearLayoutManager LayoutManager = new CenterZoomLayoutManager(MainActivity.this, 0, false);
        recently_added_stories = (RecyclerView) findViewById(R.id.recently_added_stories);
        /*LinearLayoutManager LinearLayout = new LinearLayoutManager(MainActivity.this, 1, false);
        instagram_feed = (RecyclerView) findViewById(R.id.instagram_feed);
        instagram_feed.setLayoutManager(LinearLayout);
*/
        instagram_feed=findViewById(R.id.instagram_feed);
        FeedAdaptor feedAdaptor=new FeedAdaptor(this,s1,s2,images);
        instagram_feed.setAdapter(feedAdaptor);
        instagram_feed.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onResume() {
        super.onResume();
        StoriesAdapter storiesAdapter = new StoriesAdapter(MainActivity.this);
        recently_added_stories.setAdapter(storiesAdapter);

    }
}