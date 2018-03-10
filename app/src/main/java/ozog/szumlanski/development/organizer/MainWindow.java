package ozog.szumlanski.development.organizer;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainWindow extends AppCompatActivity {

    public static RelativeLayout rl;
    public static Context c;

    public static ListView taskList;
    public static List<String> taskListString;
    public static ArrayAdapter<String> listToListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        rl = findViewById(R.id.RelativeLayoutMain);
        c = getApplicationContext();

        taskListString = new ArrayList<>();
        listToListViewAdapter = new ArrayAdapter<String> (c, android.R.layout.simple_list_item_1, taskListString);
        taskList = findViewById(R.id.taskList);
        taskList.setAdapter(listToListViewAdapter);
    }


    public void addTask(View v) {
        Log.e("ssd", "SSDS");
        taskListString.add("First task!");
        listToListViewAdapter.notifyDataSetChanged();
    }
}
