package ozog.szumlanski.development.organizer;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainWindow extends AppCompatActivity {

    public static RelativeLayout rlMain;
    public static RelativeLayout rlAddTask;
    public static Context c;

    public static Database db;

    public static ListView taskList;
    public static List<Task> taskArrayList;
    public static ArrayAdapter<String> listToListViewAdapter;

    public static TextView title;
    public static TextView content;

    public static List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        rlMain = findViewById(R.id.RelativeLayoutMain);
        rlAddTask = findViewById(R.id.RelativeLayoutTaskCreation);
        c = getApplicationContext();

        title = findViewById(R.id.title);
        content = findViewById(R.id.content);

        db = new Database(this);
        db.createTable();
        final List<Task> alltasks = db.getAllTasks();



        //get all titles from all tasks into a list of strings called titles, feed to arrayadapter below

        taskArrayList = new ArrayList<>();
        listToListViewAdapter = new ArrayAdapter<String> (c, android.R.layout.simple_list_item_1, titles);
        taskList = findViewById(R.id.taskList);
        taskList.setAdapter(listToListViewAdapter);

        rlAddTask.setVisibility(View.INVISIBLE);

        db.createTable();
    }


    public void addTask(View v) {
        //taskListString.add("First task!");
        //listToListViewAdapter.notifyDataSetChanged();

        rlAddTask.setVisibility(View.VISIBLE);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.dimAmount = 2.75f;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(layoutParams);
    }

    public void createTask(View v) {
        db.addTask(new Task(title.getText().toString(), 0));
    }
}
