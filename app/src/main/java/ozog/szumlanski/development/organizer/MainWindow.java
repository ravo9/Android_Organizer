package ozog.szumlanski.development.organizer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends AppCompatActivity {

    public static Context c;
    public static Database db;

    public static ListView taskList;
    public static CustomArrayAdapter adapter;

    public static List<Task> allTasks;
    public static List<String> display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        c = getApplicationContext();

        display = new ArrayList<>();
        adapter = new CustomArrayAdapter(display, this);

        taskList = findViewById(R.id.taskList);
        taskList.setAdapter(adapter);

        db = new Database(this);
        //db.dropTasksTable();
        //db.dropArchiveTable();
        db.createTable();
        updateAllTasks();

        Log.d("Tasks in Database", Integer.toString(db.getTaskCount()));
        Log.d("Tasks in Archive", Integer.toString(db.getArchivedTaskCount()));
    }

    public static void updateAllTasks() {

        allTasks = db.getAllTasks();
        display.clear();
        for(Task singleTask : allTasks)
            display.add(singleTask.getContent());
        adapter.notifyDataSetChanged();
    }

    public void addTask(View v) {

        Intent intent = new Intent(this, AddTaskActivity.class);
        startActivity(intent);
        finish();
    }

    public void openDoneTasks(View v) {

        Intent intent = new Intent(this, DoneTasks.class);
        startActivity(intent);
        finish();
    }
}
