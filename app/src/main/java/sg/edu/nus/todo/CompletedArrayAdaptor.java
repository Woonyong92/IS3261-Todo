package sg.edu.nus.todo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by woonyong123 on 5/10/2015.
 */
public class CompletedArrayAdaptor extends BaseAdapter implements ListAdapter {
    ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
    private Context context;
    MyDBHelper myDb;
    private CompletedArrayAdaptor adapter;

    public CompletedArrayAdaptor(ArrayList<ArrayList<String>> list, Context context) {
        this.list = list;
        this.context = context;
        this.adapter = this;
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
            view = inflater.inflate(R.layout.completed_task_view, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.taskTextView);
        String text = list.get(position).get(1);
        listItemText.setText(text);

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.deleteButton);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDb = new MyDBHelper(context);
                String index = list.get(position).get(0);
                myDb.delete(index);
                list.remove(position);
                adapter.notifyDataSetChanged();
                Intent myIntent = new Intent("SOC_Students");
                myIntent.putExtra("MSG", "Task Deleted");
                context.sendBroadcast(myIntent);
            }
        });

        TextView taskTextView = (TextView) view.findViewById(R.id.taskTextView);

        taskTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                myDb = new MyDBHelper(context);
                Intent myIntent = new Intent(context, ShowTask.class);
                myIntent.putExtra("id", list.get(position).get(0));
                myIntent.putExtra("name", list.get(position).get(1));
                myIntent.putExtra("description", list.get(position).get(2));
                myIntent.putExtra("endDate", list.get(position).get(3));
                myIntent.putExtra("endTime", list.get(position).get(4));
                myIntent.putExtra("location", list.get(position).get(5));
                myIntent.putExtra("status", list.get(position).get(6));
                myIntent.putExtra("contactName", list.get(position).get(7));
                myIntent.putExtra("contactNumber", list.get(position).get(8));
                myIntent.putExtra("reminder", list.get(position).get(9));
                v.getContext().startActivity(myIntent);
            }
        });

        return view;
    }
}
