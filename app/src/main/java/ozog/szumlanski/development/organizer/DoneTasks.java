package ozog.szumlanski.development.organizer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DoneTasks extends AppCompatActivity {

    private static ListView doneTasksListView;
    private static CustomArrayAdapter adapter;
    private static Database db = new Database(MainWindow.c);

    private static List<Task> doneTasks;
    private static List<String> display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_tasks);

        display = new ArrayList<>();

        doneTasksListView = findViewById(R.id.doneTasksListView);
        doneTasksListView.setAdapter(adapter);

        updateDoneTasks();
        adapter = new CustomArrayAdapter(display, this);
    }

    public static void updateDoneTasks() {

        doneTasks = db.getDoneTasks();
        display.clear();
        for(Task singleTask : doneTasks)
            display.add(singleTask.getContent());
        adapter.notifyDataSetChanged();
    }
}
