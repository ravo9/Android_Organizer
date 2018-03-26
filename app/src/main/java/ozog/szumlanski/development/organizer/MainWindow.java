package ozog.szumlanski.development.organizer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends AppCompatActivity {

    //layouts
    public static RelativeLayout rlMain;
    public static Context c;

    //declaring database db
    public static Database db;

    public static ListView taskList;
    public static CustomArrayAdapter adapter;

    public static TextView titleTextView;
    public static TextView taskContentInput;

    public static List<Task> allTasks;
    public static List<String> display;

    public static void updateTasks() {

        try {
            allTasks = db.getAllTasks();
            display.clear();
            for(Task singleTask : allTasks)
                display.add(singleTask.getContent());
        }
        catch (Exception e) {}
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        rlMain = findViewById(R.id.RelativeLayoutMain);
        c = getApplicationContext();

        titleTextView = findViewById(R.id.title);
        taskContentInput = findViewById(R.id.taskContentInput);

        display = new ArrayList<>();

        adapter = new CustomArrayAdapter(display, this);
        taskList = findViewById(R.id.taskList);
        taskList.setAdapter(adapter);

        db = new Database(this);
        //db.dropTasksTable();
        //db.dropArchiveTable();
        db.createTable();
        updateTasks();

        Log.d("Tasks in Database", Integer.toString(db.getTaskCount()));
        Log.d("Tasks in Archive", Integer.toString(db.getArchivedTaskCount()));
    }

    public void addTask(View v) {

        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
        finish();
    }

    public void createTask(View v)
    {
        createTask();
    }

    public static void createTask()
    {
        db.addTask(new Task(newId(), taskContentInput.getText().toString(), "create", "status"));
        updateTasks();
        adapter.notifyDataSetChanged();
    }

    public void onTaskClick(View v) {}

    public static int newId() {
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
