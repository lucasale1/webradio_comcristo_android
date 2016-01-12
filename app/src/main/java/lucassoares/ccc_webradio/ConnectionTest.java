package lucassoares.ccc_webradio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

/**
 * Created by lucas on 10/01/16.
 */
public class ConnectionTest extends BroadcastReceiver{
    private Radio radio;
    public ConnectionTest(Radio radio){
        this.radio = radio;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean notConnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false);
        if(notConnected){
            radio.ConnectionLost();
        }else{
            radio.RestoreConnection();
        }
    }
}
