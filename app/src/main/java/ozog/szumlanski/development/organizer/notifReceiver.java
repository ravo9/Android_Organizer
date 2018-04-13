package ozog.szumlanski.development.organizer;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Przemek on 29/03/2018.
 */

public class notifReceiver extends BroadcastReceiver {
    public notifReceiver() {
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent(context, notifIntentService.class);
        context.startService(intent1);
    }
}
