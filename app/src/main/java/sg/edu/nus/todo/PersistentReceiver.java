package sg.edu.nus.todo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by woonyong123 on 26/10/2015.
 */
public class PersistentReceiver extends BroadcastReceiver {
    public void onReceive(Context ctx, Intent i) {
        String msg = i.getStringExtra("MSG");
        Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
    }
}
