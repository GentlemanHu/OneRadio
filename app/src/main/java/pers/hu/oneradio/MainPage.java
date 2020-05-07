package pers.hu.oneradio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hrb.library.MiniMusicView;

public class MainPage extends AppCompatActivity {

    private MiniMusicView miniMusicView;
    private Button bt_set;
    private Button music_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        bt_set = (Button) findViewById(R.id.bt_set);
        music_play = (Button) findViewById(R.id.bt_detail) ;

        miniMusicView = (MiniMusicView) findViewById(R.id.mmv_music);
        miniMusicView.setTitleText("music name");
        miniMusicView.setAuthor("singer name");
        miniMusicView.startPlayMusic("music url");

    }
    public void set(View v){
        Intent intent = new Intent(this, set.class);
        startActivity(intent);
    }
    public void play(View v){
        Intent intent2 = new Intent(this, music_play.class);
        startActivity(intent2);
    }
}
