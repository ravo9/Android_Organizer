package ozog.szumlanski.development.organizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.List;

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
        TextView listItemText = (TextView)view.findViewById(R.id.task_text);
        listItemText.setText(list.get(position));
        if(focusedTask(position).getPriority() == 1) {
            listItemText.setBackground(MainWindow.c.getResources().getDrawable(
                    R.drawable.green_task2));
        } else if(focusedTask(position).getPriority() == 2) {
            listItemText.setBackground(MainWindow.c.getResources().getDrawable(
                    R.drawable.yellow_task2));
        } else if (focusedTask(position).getPriority() == 3) {
            listItemText.setBackground(MainWindow.c.getResources().getDrawable(
                    R.drawable.red_task2));
        }


        //Handle buttons and add onClickListeners
        ImageButton notDoneBtn = (ImageButton)view.findViewById(R.id.not_done_btn);
        ImageButton doneBtn = (ImageButton)view.findViewById(R.id.done_btn);

        notDoneBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Task clickedTask = focusedTask(position);
                setArchiveTaskId(clickedTask);
                clickedTask.setStatus("notdone");
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
