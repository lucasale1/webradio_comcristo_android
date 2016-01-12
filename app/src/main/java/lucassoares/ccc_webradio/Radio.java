package lucassoares.ccc_webradio;

import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wseemann.media.FFmpegMediaMetadataRetriever;

/**
 * Created by lucas on 07/01/16.
 */
public abstract class Radio {
    protected  static  String url = "http://up-gc2.webnow.com.br/radiorockweb128.mp3";
    protected static MediaPlayer player = new MediaPlayer();
    private ConnectionTest connectionTest;
    private IRadioView radioView;
    AudioManager audioManager;
    FFmpegMediaMetadataRetriever metadataRetriever;
    private MetadataTimer metadataTimer;

    public Radio(){
        metadataTimer = MetadataTimer.getInstance(this);
    }

    public abstract void behaviour();
    public void RestoreConnection(){
        if(metadataRetriever!=null) {
            try {
                metadataRetriever.setDataSource(url);
                if(metadataTimer.isAlive())metadataTimer.interrupt();
                metadataTimer.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    public void ConnectionLost(){
        if(metadataRetriever !=null){
            if(metadataTimer.isAlive()){
                metadataTimer.interrupt();
            }
        }
    }

    public void setRadioView(IRadioView radioView){
        this.radioView = radioView;
        this.connectionTest = new ConnectionTest(this);
        Context context = radioView.getApplicationContext();
        context.registerReceiver(connectionTest, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        audioManager = (AudioManager)radioView.getSystemService(Context.AUDIO_SERVICE);
        metadataRetriever = new FFmpegMediaMetadataRetriever();
    }

    public IRadioView getRadioView(){
        return this.radioView;
    }
    public int getVolume(){
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public void mute(){
        AudioManager audio  = (AudioManager)radioView.getSystemService(Context.AUDIO_SERVICE);
        audio.setStreamMute(AudioManager.STREAM_MUSIC,true);
        radioView.updateVolume(getVolume());
    }

    public void unMute() {
        AudioManager audio  = (AudioManager)radioView.getSystemService(Context.AUDIO_SERVICE);
        audio.setStreamMute(AudioManager.STREAM_MUSIC,false);
        radioView.updateVolume(getVolume());
    }

    public void volumeUp(){
        adjustVolume(AudioManager.ADJUST_RAISE);
    }
    public void volumeDown(){
        adjustVolume(AudioManager.ADJUST_LOWER);
    }

    private void adjustVolume(int vol){
        audioManager.adjustVolume(vol, 0);
        radioView.updateVolume(getVolume());
    }

    public void changeVolume(int vol){
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, vol, 0);
        radioView.updateVolume(getVolume());
    }

    public String getAlbum(){
        return metadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM);
    }

    public String getArtist() {
        return metadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ARTIST);
    }

    public String getTitle(){
        String title = metadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_TITLE);
        if(title==null){
            title = metadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ICY_METADATA);
            Pattern pattern = Pattern.compile("'(.*?)'");
            Matcher matcher = pattern.matcher(getICYMetadata());
            if(matcher.find()){
                return matcher.group(0).substring(1,matcher.group(0).length()-1);
            }
            title = null;

        }
        return title;
    }

    public String getTrack(){
        return metadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_TRACK);
    }

    public String getICYMetadata(){
        return metadataRetriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ICY_METADATA);
    }

}
