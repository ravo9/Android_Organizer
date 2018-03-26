package ozog.szumlanski.development.organizer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AddTaskActivity extends AppCompatActivity {

    public static TextView taskContentInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskContentInput = findViewById(R.id.taskContentInput);
    }

    public void createTask(View v)
    {
        MainWindow.db.addTask(new Task(Database.newId(), taskContentInput.getText().toString(), "create", "status"));
        MainWindow.updateAllTasks();
        MainWindow.adapter.notifyDataSetChanged();

        Intent intent = new Intent(this, MainWindow.class);
        startActivity(intent);
        finish();
    }
}
