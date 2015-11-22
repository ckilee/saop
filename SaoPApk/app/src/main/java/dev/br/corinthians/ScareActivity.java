package dev.br.corinthians;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class ScareActivity extends Activity {
    private ImageView scareImageView;
    private int currentImageID = 0;
    private int currentSoundID = 0;
    private InterstitialAd mInterstitialAd;
    private boolean intertistialLoadedLocally = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scare);

        loadInterstistialAD();

        AudioManager am =
                (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        am.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0);

        Intent i = getIntent();

        currentImageID = i.getIntExtra(MainActivity.IMAGE_EXTRA,0);
        currentSoundID = i.getIntExtra(MainActivity.SOUND_EXTRA,0);
        scareImageView = (ImageView)findViewById(R.id.imageViewScare);

        MediaPlayer mp = MediaPlayer.create(this,currentSoundID);
        mp.start();

        Vibrator v = (Vibrator) this.getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(1000);

        Drawable image = getResources().getDrawable(currentImageID);
        if(scareImageView!=null)  //imageView is a ImageView
            scareImageView.setImageDrawable(image);

        int delay = 2500;
        if(intertistialLoadedLocally) {
            //If intertistial loading in this class, We will give 1 more second to load banner
            delay = 4000;
        }
        new ShowInterstitialTask().execute(delay);

    }

    private void loadInterstistialAD(){
        try {
            mInterstitialAd = MainActivity.getInstance().getInterstitialAd();
        } catch(NullPointerException ne){
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(this.getString(R.string.banner_ad_unit_id));
            /*AdRequest adRequestInterstitialAd = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("E112885C2D32D31690C7B60F25C89356")
                    .addTestDevice("13E7A5DDF2981F979D554ED02BC571B3")
                    .build();*/
            AdRequest adRequestInterstitialAd = new AdRequest.Builder().build();

            mInterstitialAd.loadAd(adRequestInterstitialAd);
            intertistialLoadedLocally = true;
        }
    }

    private void displayInterstitial(){

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scare, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ShowInterstitialTask extends AsyncTask<Integer,Void,Void>{

        @Override
        protected Void doInBackground(Integer... params){
            try {
                Thread.sleep(params[0]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            //displayInterstitial();
        }
    }




}
