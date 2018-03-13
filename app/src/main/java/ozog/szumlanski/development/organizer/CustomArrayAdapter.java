package ozog.szumlanski.development.organizer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Przemek on 12/03/2018.
 */

public class CustomArrayAdapter extends BaseAdapter implements ListAdapter {
    private List<String> list;
    private Context context;
    Database db = new Database(MainWindow.c);



    public CustomArrayAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.listview_item_layout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button notDoneBtn = (Button)view.findViewById(R.id.not_done_btn);
        Button doneBtn = (Button)view.findViewById(R.id.done_btn);

        notDoneBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Task clickedTask = focusedTask(position);
                setArchiveTaskId(clickedTask);
                db.archiveTask(clickedTask);
                db.removeTask(focusedTask(position));
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Task clickedTask = focusedTask(position);
                setArchiveTaskId(clickedTask);
                clickedTask.setStatus("done");
                db.archiveTask(clickedTask);
                db.removeTask(focusedTask(position));
                list.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }
    public Task focusedTask(int position) {
        List<Task> tasks;
        tasks = db.getAllTasks();
        return tasks.get(position);
    }
    public void setArchiveTaskId(Task task) {
        int newId;
        newId = db.getArchivedTaskCount() + 1;
        task.setId(newId);
    }
}
