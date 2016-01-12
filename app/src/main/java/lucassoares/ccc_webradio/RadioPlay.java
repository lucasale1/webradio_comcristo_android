package lucassoares.ccc_webradio;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;


import java.io.IOException;

/**
 * Created by lucas on 07/01/16.
 */
public class RadioPlay extends Radio implements OnPreparedListener {

    private static boolean prepared = false;
    private static RadioPlay radioPlay;

    private RadioPlay(){
        super();
    }

    public static RadioPlay getInstance(){
        if(radioPlay==null)radioPlay = new RadioPlay();
        return radioPlay;
    }

    @Override
    public void behaviour() {
        if(audioManager.isStreamMute(AudioManager.STREAM_MUSIC)){
            changeVolume(7);
        }
        if(!prepared) {
            prepare();
        }else{
            player.start();
        }
    }

    @Override
    public void RestoreConnection() {
        super.RestoreConnection();
        prepare();
    }


    public void onPrepared(MediaPlayer player){
        prepared = true;
        player.start();
    }


    private void prepare() {
        player.stop();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.reset();

        try {
            player.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.prepareAsync();
    }

}
