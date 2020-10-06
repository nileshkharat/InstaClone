package Acitvity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instagramclone.R;

import java.io.FileNotFoundException;
import java.io.IOException;

import Models.StoriesModel;
import SQLiteDB.CreateStoriesDB;
import Services.ImageChooserService;
import Services.SharedPrferenceServiceForInsta;
import Services.Util;


public class CreateStory extends AppCompatActivity {
    EditText title,description,hashtags;
    ImageView photo_imageView2;
    Button photo_button,log_complain;
    SharedPrferenceServiceForInsta sharedPrferenceServiceForInsta;
    CreateStoriesDB createStoriesDB ;


    ImageChooserService imageChooserService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null)getSupportActionBar().hide();
        setContentView(R.layout.activity_create_story);
        title=(EditText)findViewById(R.id.title);
        description=(EditText)findViewById(R.id.description);
        hashtags=(EditText)findViewById(R.id.hashtags);
        photo_imageView2=(ImageView) findViewById(R.id.photo_imageView2);
        photo_button=(Button) findViewById(R.id.photo_button);
        log_complain=(Button)findViewById(R.id.log_complain);
        imageChooserService= new ImageChooserService(CreateStory.this);
        sharedPrferenceServiceForInsta= new SharedPrferenceServiceForInsta(this.getApplicationContext());
        createStoriesDB= new CreateStoriesDB(CreateStory.this);
        photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });
        log_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(createStoriesDB.insertStories(getDataFromUiAndConvertingItModel())>0){
                    Toast.makeText(getApplicationContext(),"Story SussesFully Added!!!!",Toast.LENGTH_LONG).show();


                }else{
                    Toast.makeText(getApplicationContext(),"Not able to Add Story!!!!",Toast.LENGTH_LONG).show();

                }
                ;
            }
        });

    }

    public void imageChooser(){

        imageChooserService.launchImageSelectionOption();
    }

    public StoriesModel getDataFromUiAndConvertingItModel(){
        StoriesModel storiesModel = new StoriesModel();
       storiesModel.setStory_title(title.getText().toString());
        storiesModel.setStories_dis(description.getText().toString());
        storiesModel.setStories_hastags(hashtags.getText().toString());
        Bitmap  bitmaps = ((BitmapDrawable)photo_imageView2.getDrawable()).getBitmap();
        storiesModel.setImage(Util.generatetByteArray(bitmaps));

        return storiesModel;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == 8) {

            try {


               String uri = sharedPrferenceServiceForInsta.getString("path","");

                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(uri));
                    photo_imageView2.setImageBitmap(Util.getScaledBitmap(bitmap));
                  /*  bitmap=Util.getScaledBitmap(bitmap);
                   // generateDigitalSignViewModel.onImageBitmap.postValue(Util.getScaledBitmap(bitmap));
                    Uri urls = Uri.parse(uri);
                    fireBaseCouldStorage.uploadImage(urls);*/


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }



            } catch (Exception e) {
                e.printStackTrace();
            }


        } else if (requestCode == 6) {

            try {

                photo_imageView2.setImageBitmap(Util.generateBitmap(this,data));


            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}