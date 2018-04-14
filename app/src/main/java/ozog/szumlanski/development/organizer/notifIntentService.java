package ozog.szumlanski.development.organizer;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationManagerCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

/**
 * Created by Przemek on 29/03/2018.
 */

public class notifIntentService extends IntentService {

    private int notifId = AddTaskActivity.notifId;
    private int priority = AddTaskActivity.notifPriority;
    long time = System.currentTimeMillis();

    public notifIntentService() {
        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(notifPriority());
        builder.setContentText(AddTaskActivity.notifContent);
        builder.setSmallIcon(R.drawable.reminder);
        //builder.addAction(R.drawable.snooze, "+30 Minutes", snoozeIntent());
        Intent notifyIntent = new Intent(this, MainWindow.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, notifId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(notifId, notificationCompat);
    }

    public String notifPriority() {
        if(priority == 1) {
            return "High priority";
        } else if(priority == 2) {
            return "Medium priority";
        } else {
            return "Low priority";
        }
    }
    public PendingIntent snoozeIntent() {
        Intent notifyIntent = new Intent(this,notifReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (getApplicationContext(), notifId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,  time + 10000, pendingIntent);
        return pendingIntent;
    }
}
