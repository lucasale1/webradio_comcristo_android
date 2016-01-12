package lucassoares.ccc_webradio;

import android.content.Context;


/**
 * Created by lucas on 09/01/16.
 */
public interface IRadioView {
    void updateTitle(String album);
    void updateVolume(int volume);
    Context getApplicationContext();
    Object getSystemService(String name);
}
