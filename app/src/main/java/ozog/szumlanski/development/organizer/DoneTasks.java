package ozog.szumlanski.development.organizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DoneTasks extends AppCompatActivity {

    public static ListView doneTasksListView;
    public static CustomArrayAdapter adapter;

    public static List<Task> doneTasks;
    public static List<String> display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_tasks);

        display = new ArrayList<>();
        adapter = new CustomArrayAdapter(display, this);

        doneTasksListView = findViewById(R.id.doneTasksListView);
        doneTasksListView.setAdapter(adapter);

        updateDoneTasks();
    }

    public static void updateDoneTasks() {

        doneTasks = MainWindow.db.getDoneTasks();
        display.clear();
        for(Task singleTask : doneTasks)
            display.add(singleTask.getContent());
        adapter.notifyDataSetChanged();
    }
}
