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

    public static Context c;
    public static Database db;

    public static ListView taskList;
    public static CustomArrayAdapter adapter;
    public static Score s;
    public static TextView scoreTextView;

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
        db.createTable();
        updateAllTasks();
        scoreTextView = findViewById(R.id.score_text);
        s = new Score();
        scoreTextView.setText(Integer.toString(s.getScore()));
        updateScore();
        adapterUpdate();
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateAllTasks();
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
    }

    public void openDoneTasks(View v) {

        Intent intent = new Intent(this, DoneTasks.class);
        startActivity(intent);
    }

    public void updateScore() {

        scoreTextView.setText(Integer.toString(s.getScore()));

        if(s.ratingColor() == 1) {
            scoreTextView.setBackgroundColor(getResources().getColor(R.color.green));
        } else if (s.ratingColor() == 2) {
            scoreTextView.setBackgroundColor(getResources().getColor(R.color.yellow));
        } else  if (s.ratingColor() == 3){
            scoreTextView.setBackgroundColor(getResources().getColor(R.color.red));
        } else if(s.ratingColor() == 0) {
            scoreTextView.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }
    public void adapterUpdate() {
        adapter.setOnDataChangeListener(new CustomArrayAdapter.OnDataChangeListener(){
            public void onDataChanged(int size){
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
    }
}
