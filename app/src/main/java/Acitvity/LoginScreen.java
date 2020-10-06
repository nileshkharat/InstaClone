package Acitvity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instagramclone.R;

public class LoginScreen extends AppCompatActivity {
    EditText username,password;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_login_screen);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        submit=(Button) findViewById(R.id.btn_login);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(authentication(username.getText().toString(),password.getText().toString())){
                    Intent intent = new Intent(LoginScreen.this,MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Sucessfully Logged In!!!",Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(),"Username & password does not match!!!",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    boolean authentication(String username,String password){
        if(username.equals("admin")&&password.equals("123")){
            return true;
        }
        else{
            return false;
        }


    }

}