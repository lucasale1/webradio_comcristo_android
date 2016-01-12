package lucassoares.ccc_webradio;

import android.media.MediaPlayer;

/**
 * Created by lucas on 07/01/16.
 */
public class RadioPause extends Radio {

    private static RadioPause radioPause;
    private RadioPause(){}
    public static RadioPause getInstance(){
        if(radioPause==null)radioPause = new RadioPause();
        return radioPause;
    }
    @Override
    public void behaviour() {
        player.stop();
    }

}
