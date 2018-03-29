package ozog.szumlanski.development.organizer;

import android.app.AlarmManager;
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
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
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
    public static Switch notifSwitch;
    Calendar myCalendar = Calendar.getInstance();
    public static Context c;


    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taskContentInput = findViewById(R.id.taskContentInput);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        notifSwitch = findViewById(R.id.notifSwitch);
        date.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);

        notifSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                date.setVisibility(View.VISIBLE);
                time.setVisibility(View.VISIBLE);
            }
        });
        notifSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                date.setVisibility(View.VISIBLE);
                time.setVisibility(View.VISIBLE);
            }
        });

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

    private void sendNotification(String content, long notifTimeInMillis) {
        Intent intent = new Intent(this, MainWindow.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        initChannels(this);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.drawable.btn_add)
                .setContentTitle("Reminder")
                .setContentText(content)
                .setContentIntent(pendingIntent)
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

    public long readDateTimeInMillis() {
        long notifdateinMilisec;
        String notifDate = date.getText().toString() + " " + time.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.UK);

        try {
            myCalendar.setTime(sdf.parse(notifDate));// all done
        } catch (java.text.ParseException e) {}

        notifdateinMilisec = myCalendar.getTimeInMillis();
        return notifdateinMilisec;
    }

    private void scheduleNotification2(Notification notification, long notifTimeInMillis) {

        Intent notificationIntent = new Intent(this, AddTaskActivity.class);
        notificationIntent.putExtra(notifReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(notifReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, notifTimeInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.btn_add);
        return builder.build();
    }

    public void alarmHandler() {
        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AddTaskActivity.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, readDateTimeInMillis(),
                1000 * 60 * 20, alarmIntent);
    }
}
