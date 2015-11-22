package dev.br.corinthians;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScareReceiver extends BroadcastReceiver {
    public ScareReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //start activity
        Intent i = new Intent();
        i.setClassName("dev.br.corinthians", "dev.br.corinthians.ScareActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(MainActivity.SOUND_EXTRA, intent.getIntExtra(MainActivity.SOUND_EXTRA, 0));
        i.putExtra(MainActivity.IMAGE_EXTRA, intent.getIntExtra(MainActivity.IMAGE_EXTRA, 0));
        context.startActivity(i);
    }
}
