package ozog.szumlanski.development.organizer;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import java.util.List;

/**
 * Created by Przemek on 29/03/2018.
 */

public class notifIntentService extends IntentService {


    private static Database db;
    private int notifId;
    private Task task;
    private static final int NOTIFICATION_ID = 3;

    public notifIntentService() {
        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Reminder:");
        builder.setContentText("Do something");
        builder.setSmallIcon(R.drawable.red_dot);
        Intent notifyIntent = new Intent(this, MainWindow.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }

    public static int newestId() {
        int lastId = 0;
        List<Task> allTasks = db.getAllTasks();
        for(Task task : allTasks) {
            if(task.getId() >= lastId)
                lastId = task.getId();
        }
        return lastId;
    }
}
