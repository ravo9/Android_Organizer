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
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static android.app.Notification.VISIBILITY_PUBLIC;

public class AddTaskActivity extends AppCompatActivity {

    public TextView taskContentInput;
    public RelativeLayout textLayout;
    public TextView reminderLbl;
    public TextView date;
    public TextView time;
    public ImageButton bell;
    public ImageButton redBtn;
    public ImageButton yellowBtn;
    public ImageButton greenBtn;
    Calendar myCalendar = Calendar.getInstance();
    public Context c;
    public static Database db;
    String currentDate;
    String currentTime;
    String currentDateTime = currentDate + " " + currentTime;
    int  priority = 1;
    int notificationOn = 0;
    public static String notifContent;
    public static int notifId;
    public static int notifPriority;


    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        taskContentInput = findViewById(R.id.taskContentInput);
        textLayout = findViewById(R.id.textLayout);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        reminderLbl = findViewById(R.id.reminderLbl);
        bell = findViewById(R.id.bell);

        date.setVisibility(View.INVISIBLE);
        time.setVisibility(View.INVISIBLE);
        redBtn = findViewById(R.id.red_priority);
        yellowBtn = findViewById(R.id.yellow_priority);
        greenBtn = findViewById(R.id.green_priority);


        currentDateTime();
        date.setText(currentDate);
        time.setText(currentTime);
    }

    public void createTask(View v)
    {
        currentDateTime();
        db = new Database(MainWindow.c);
        notifContent = taskContentInput.getText().toString();
        notifId = newId();
        notifPriority = priority;
        db.addTask(new Task(newId(), taskContentInput.getText().toString(), currentDateTime, priority));
        if(notificationOn == 1) {
            notification(newId());
        }

        finish();
    }

    public void addReminder(View v) {

        if(notificationOn == 0) {
            notificationOn = 1;
            bell.setBackground(getResources().getDrawable(
                    R.drawable.green_bell));
            date.setVisibility(View.VISIBLE);
            time.setVisibility(View.VISIBLE);
            reminderLbl.setVisibility(View.INVISIBLE);
        } else {
            notificationOn = 0;
            bell.setBackground(getResources().getDrawable(
                    R.drawable.gray_bell));
            date.setVisibility(View.INVISIBLE);
            time.setVisibility(View.INVISIBLE);
            reminderLbl.setVisibility(View.VISIBLE);
        }
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

    public void redTask(View v) {
        priority = 3;
        showKeyboard();
        redBtn.setBackground(getResources().getDrawable(
                R.drawable.red_check));
        yellowBtn.setBackground(getResources().getDrawable(
                R.drawable.yellow_dot));
        greenBtn.setBackground(getResources().getDrawable(
                R.drawable.green_dot));
        textLayout.setBackground(getResources().getDrawable(
                R.drawable.red_task2));
        textLayout.setVisibility(View.VISIBLE);
    }
    public void yellowTask(View v) {
        priority = 2;
        showKeyboard();
        yellowBtn.setBackground(getResources().getDrawable(
                R.drawable.yellow_check));
        redBtn.setBackground(getResources().getDrawable(
                R.drawable.red_dot));
        greenBtn.setBackground(getResources().getDrawable(
                R.drawable.green_dot));
        textLayout.setBackground(getResources().getDrawable(
                R.drawable.yellow_task2));
        textLayout.setVisibility(View.VISIBLE);
    }
    public void greenTask(View v) {
        priority = 1;
        showKeyboard();
        greenBtn.setBackground(getResources().getDrawable(
                R.drawable.green_check));
        yellowBtn.setBackground(getResources().getDrawable(
                R.drawable.yellow_dot));
        redBtn.setBackground(getResources().getDrawable(
                R.drawable.red_dot));
        textLayout.setBackground(getResources().getDrawable(
                R.drawable.green_task2));
        textLayout.setVisibility(View.VISIBLE);
    }

    public void currentDateTime() {
        Calendar cal = new GregorianCalendar();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setTimeZone(cal.getTimeZone());
        timeFormat.setTimeZone(cal.getTimeZone());
        currentDate = dateFormat.format(cal.getTime());
        currentTime = timeFormat.format(cal.getTime());
    }

    private void updateDate() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        date.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTime() {
        String myFormat = "HH:mm"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        time.setText(sdf.format(myCalendar.getTime()));
    }
    private void showKeyboard() {
        taskContentInput.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(taskContentInput, InputMethodManager.SHOW_IMPLICIT);
    }

    public static int newId() {
        int lastId = 0;
        List<Task> allTasks = db.getAllTasks();
        for(Task task : allTasks) {
            if(task.getId() >= lastId)
                lastId = task.getId() + 1;
        }
        return lastId;
    }

    //NOTIFICATIONS
    private void notification(int notifId) {
        Intent notifyIntent = new Intent(this,notifReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast
                (getApplicationContext(), notifId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,  readDateTimeInMillis(), pendingIntent);
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



}
