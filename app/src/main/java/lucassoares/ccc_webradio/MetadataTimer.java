package lucassoares.ccc_webradio;

/**
 * Created by lucas on 12/01/16.
 */
public class MetadataTimer extends Thread {
    private Radio radio;
    private static MetadataTimer metadataTimer;
    private boolean stop=false;

    public void setStop(boolean stop){
        this.stop = stop;
    }

    private MetadataTimer(Radio radio) {
        this.radio = radio;
    }

    public static MetadataTimer getInstance(Radio radio){
        if(metadataTimer == null)metadataTimer = new MetadataTimer(radio);
        metadataTimer.radio = radio;
        return metadataTimer;
    }

    @Override
    public void run() {
        while(!stop) {
            try {
                IRadioView radioView = radio.getRadioView();
                String title = radio.getTitle();
                radioView.updateTitle(title);
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
