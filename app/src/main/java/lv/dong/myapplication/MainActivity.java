package lv.dong.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity
        implements OnSeekBarChangeListener
{
    private MediaPlayer mediaPlayer;
    public TextView songName, songDuration, remainingTime;
    private Handler updateHandler = new Handler();
    private SeekBar seekBar;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songName = (TextView) findViewById(R.id.songName);
        songName.setText("Em Gai Mua - Huong Tram.mp3");

        mediaPlayer = MediaPlayer.create(this, R.raw.em_gai_mua_huong_tram);

        songDuration = (TextView)findViewById(R.id.songDuration);
        int minutes = (int)TimeUnit.MILLISECONDS.toMinutes(mediaPlayer.getDuration());
        int seconds = (int)TimeUnit.MILLISECONDS.toSeconds(mediaPlayer.getDuration()) - minutes * 60;
        songDuration.setText(String.format("%02d:%02d",minutes, seconds));

        remainingTime = (TextView)findViewById(R.id.remainingTime);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setMax((int) mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(this);

        updateHandler = new Handler();
        updateHandler.postDelayed(update, 100);
    }

    public void play(View view)
    {
        mediaPlayer.start();
    }

    public void pause(View view)
    {
        mediaPlayer.pause();
    }

    public void stop(View view)
    {
        mediaPlayer.stop();
        mediaPlayer = mediaPlayer.create(this, R.raw.em_gai_mua_huong_tram);
    }

    private Runnable update = new Runnable()
    {
        public void run()
        {
            long currentTime = mediaPlayer.getCurrentPosition();
            seekBar.setProgress((int)currentTime);
            int minutes = (int)TimeUnit.MILLISECONDS.toMinutes(currentTime);
            int seconds = (int)TimeUnit.MILLISECONDS.toSeconds(currentTime) - minutes * 60;
            remainingTime.setText(String.format("%02d:%02d",minutes, seconds));
            updateHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        if(fromUser)
            mediaPlayer.seekTo(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}
