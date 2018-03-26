package ozog.szumlanski.development.organizer;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import static android.app.Notification.VISIBILITY_PUBLIC;

public class AddTaskActivity extends AppCompatActivity {

    public static TextView taskContentInput;
    public static TextView date;
    public static TextView time;
    Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taskContentInput = findViewById(R.id.taskContentInput);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);



        Calendar cal = new GregorianCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setTimeZone(cal.getTimeZone());
        timeFormat.setTimeZone(cal.getTimeZone());
        date.setText(dateFormat.format(cal.getTime()));
        time.setText(timeFormat.format(cal.getTime()));
    }

    public void createTask(View v)
    {
        MainWindow.db.addTask(new Task(Database.newId(), taskContentInput.getText().toString(), "create", "status"));
        sendNotification(taskContentInput.getText().toString());
        MainWindow.updateAllTasks();
        MainWindow.adapter.notifyDataSetChanged();

        Intent intent = new Intent(this, MainWindow.class);
        startActivity(intent);
        finish();
    }


    public void datePicker(View v) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };
        new DatePickerDialog(AddTaskActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    private void updateDate() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        date.setText(sdf.format(myCalendar.getTime()));
    }
    public void timePicker(View v) {

        int hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = myCalendar.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(AddTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                myCalendar.set(Calendar.MINUTE, selectedMinute);
                updateTime();
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.show();
    }
    private void updateTime() {
        String myFormat = "HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        time.setText(sdf.format(myCalendar.getTime()));
    }
    private void sendNotif(String content) {
        NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notify=new Notification.Builder
                (getApplicationContext())
                .setContentTitle("Do:")
                .setContentText(content)
                .setSmallIcon(R.drawable.btn_add).build();

        notify.flags |= Notification.FLAG_AUTO_CANCEL;
        notif.notify(0, notify);
    }
    private void sendNotification(String content) {
        Intent intent = new Intent(this, MainWindow.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        long notifdateinMilisec;
        String notifDate = date.getText().toString() + " " + time.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.UK);



        try {
            myCalendar.setTime(sdf.parse(notifDate));// all done
        } catch (java.text.ParseException e) {}

        notifdateinMilisec = myCalendar.getTimeInMillis();

        Log.d("I'M HERE!!!!!!!!!!!!", notifDate);

        initChannels(this);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.drawable.btn_add)
                .setContentTitle("Reminder")
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setWhen(notifdateinMilisec)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, mBuilder.build());
    }
    public void initChannels(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("default",
                "Channel name",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Channel description");
        notificationManager.createNotificationChannel(channel);
    }
}
