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
    public static ArrayAdapter<String> listToListViewAdapter;

    public static TextView titleTextView;
    public static TextView contentTextView;

    public static List<Task> allTasks;
    public static List<String> display;
    public static List<String> contents;
    public static List<Date> notifDates;
    public static List<String> status;

    public void updateTasks() {
        allTasks = db.getAllTasks();

       display = new ArrayList<>();
        contents = new ArrayList<>();
        notifDates = new ArrayList<>();
        status = new ArrayList<>();


        for(Task singleTask : allTasks) {
            display.add(singleTask.getTitle() + ": " + singleTask.getContent());
        }


        listToListViewAdapter = new ArrayAdapter<String> (c, android.R.layout.simple_list_item_1, display);

        taskList = findViewById(R.id.taskList);
        taskList.setAdapter(listToListViewAdapter);
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

        db = new Database(this);
        db.createTable();
        updateTasks();

        rlAddTask.setVisibility(View.INVISIBLE);
    }


    public void addTask(View v) {
        //taskListString.add("First task!");
        //listToListViewAdapter.notifyDataSetChanged();

        rlAddTask.setVisibility(View.VISIBLE);



        /*
        //Dimming the background
        ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();
        applyDim(root, 0.5f); */
    }

    public void createTask(View v)
    {
        db.addTask(new Task(titleTextView.getText().toString(), (db.getTaskCount() + 1),
                contentTextView.getText().toString(), "date", "status"));
        rlAddTask.setVisibility(View.INVISIBLE);

        updateTasks();
        listToListViewAdapter.notifyDataSetChanged();
    }

    public void onTaskClick(View v)
    {

    }

    //Methods for dimming background layouts
    public static void applyDim(@NonNull ViewGroup parent, float dimAmount){
        Drawable dim = new ColorDrawable(Color.BLACK);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * dimAmount));

        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }
    public static void clearDim(@NonNull ViewGroup parent) {
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }
}
