package ozog.szumlanski.development.organizer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainWindow extends AppCompatActivity {


    //layouts
    public static RelativeLayout rlMain;
    public static RelativeLayout rlAddTask;
    public static Context c;

    //declaring database db
    public static Database db;

    public static ListView taskList;
    CustomArrayAdapter adapter;

    public static TextView titleTextView;
    public static TextView contentTextView;

    public static List<Task> allTasks;
    public static List<String> display;

    public void updateTasks() {
        allTasks = db.getAllTasks();

        display.clear();

        for(Task singleTask : allTasks) {
            display.add(singleTask.getContent());
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        rlMain = findViewById(R.id.RelativeLayoutMain);
        rlAddTask = findViewById(R.id.RelativeLayoutTaskCreation);
        c = getApplicationContext();

        titleTextView = findViewById(R.id.title);
        contentTextView = findViewById(R.id.content);

        display = new ArrayList<>();

        adapter = new CustomArrayAdapter(display, this);
        taskList = findViewById(R.id.taskList);
        taskList.setAdapter(adapter);

        db = new Database(this);
        //db.dropTasksTable();
        //db.dropArchiveTable();
        db.createTable();
        updateTasks();

        rlAddTask.setVisibility(View.INVISIBLE);
        Log.d("Tasks in Database", Integer.toString(db.getTaskCount()));
        Log.d("Tasks in Archive", Integer.toString(db.getArchivedTaskCount()));
    }


    public void addTask(View v) {

        rlAddTask.setVisibility(View.VISIBLE);
    }

    public void createTask(View v)
    {
        db.addTask(new Task(newId(), contentTextView.getText().toString(), "create", "notif", "status"));
        rlAddTask.setVisibility(View.INVISIBLE);

        updateTasks();
        adapter.notifyDataSetChanged();
    }

    public void onTaskClick(View v)
    {

    }
    public int newId() {
        int lastId = 0;
        allTasks = db.getAllTasks();
        for(Task task : allTasks) {
            if(task.getId() >= lastId) {
                lastId = task.getId() + 1;
            }
        }
        return lastId;
    }
}
