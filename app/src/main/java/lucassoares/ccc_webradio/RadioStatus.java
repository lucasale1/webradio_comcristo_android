package lucassoares.ccc_webradio;

import android.media.MediaPlayer;

/**
 * Created by lucas on 07/01/16.
 */
public enum RadioStatus {
    PLAYING(RadioPlay.getInstance()),
    PAUSED (RadioPause.getInstance());

    private Radio radio;

    RadioStatus(Radio radio){
        this.radio = radio;
    }

    public Radio getRadio(){
        return this.radio;
    }
}
