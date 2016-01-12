package lucassoares.ccc_webradio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class RadioActivity extends AppCompatActivity implements IRadioView,OnSeekBarChangeListener{

    private static RadioStatus radioStatus;
    private static Radio radio;
    private ConnectionTest connectionTest;
    private ImageButton play;
    private ImageButton pause;
    private ImageButton muteBtn;
    private ImageButton muteOn;
    private SeekBar barVolume;
    private boolean muteStatus = false;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        play = (ImageButton)findViewById(R.id.btnPlay);
        pause = (ImageButton)findViewById(R.id.btnPause);
        title = (TextView) findViewById(R.id.lblTitleInf);
        pause();
        muteBtn = (ImageButton)findViewById(R.id.btnMute);
        muteOn = (ImageButton)findViewById(R.id.btnMuteOn);
        barVolume = (SeekBar)findViewById(R.id.barVolume);
        barVolume.setProgress(radio.getVolume());
        if(radio.getVolume()<=0)mute();
        barVolume.setOnSeekBarChangeListener(this);
    }


    @Override
    public void updateTitle(String title) {
        if(title != null)
            this.title.setText(title);
        else
            this.title.setText("-");
    }

    @Override
    public void updateVolume(int volume) {
        barVolume.setProgress(volume);
    }

    public void play(View view) {
        play();
    }

    private void play() {
        radio = RadioStatus.PLAYING.getRadio();
        radio.setRadioView(this);
        radio.behaviour();
        play.setVisibility(View.INVISIBLE);
        pause.setVisibility(View.VISIBLE);
    }

    public void pause(View view) {
        pause();
    }

    private void pause() {
        radio = RadioStatus.PAUSED.getRadio();
        radio.setRadioView(this);
        radio.behaviour();
        play.setVisibility(View.VISIBLE);
        pause.setVisibility(View.INVISIBLE);
    }

    public void mute(View view) {
        mute();
    }

    private void mute() {
        if(!muteStatus){
            radio.mute();
            muteBtn.setVisibility(View.INVISIBLE);
            muteOn.setVisibility(View.VISIBLE);
        }else{
            radio.unMute();
            muteBtn.setVisibility(View.VISIBLE);
            muteOn.setVisibility(View.INVISIBLE);
        }
        muteStatus = !muteStatus;
    }

    public void volumeUp(View view) {
        radio.volumeUp();
        muteStatus=false;
        muteBtn.setVisibility(View.VISIBLE);
        muteOn.setVisibility(View.INVISIBLE);
    }

    public void volumeDown(View view) {
        radio.volumeDown();
        muteStatus=false;
        muteBtn.setVisibility(View.VISIBLE);
        muteOn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        muteStatus = false;
        muteBtn.setVisibility(View.VISIBLE);
        muteOn.setVisibility(View.INVISIBLE);
        radio.changeVolume(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
