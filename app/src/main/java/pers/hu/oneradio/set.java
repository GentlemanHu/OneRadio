package pers.hu.oneradio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class set extends AppCompatActivity {

    private Button bt_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);

        bt_set = (Button) findViewById(R.id.bt_set);

    }

    public void sset(View v){
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }

    public void install(View view) {
    }

    public void about(View view) {
        Toast toast = Toast.makeText(this,"电台",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void content(View view) {
        Toast toast = Toast.makeText(this,"胡义林",Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
