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
import java.util.Random;

/**
 * Created by woonyong123 on 5/10/2015.
 */
public class ArrayAdaptor extends BaseAdapter implements ListAdapter {
    ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
    private Context context;
    MyDBHelper myDb;
    private ArrayAdaptor adapter;

    public ArrayAdaptor(ArrayList<ArrayList<String>> list, Context context) {
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
            view = inflater.inflate(R.layout.task_view, null);
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

        Button doneBtn = (Button)view.findViewById(R.id.doneButton);

        doneBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                myDb = new MyDBHelper(context);
                String index = list.get(position).get(0);
                myDb.done(index);
                list.remove(position);
                ArrayList<String> messages = new ArrayList<String>();
                messages.add("Congratulations for completing a task!");
                messages.add("One job well done can be redemption for many a mistake of the past. Keep it up.");
                messages.add("Less problems, more solutions – keep working like this and nothing will be able to stop you from reaching the top. Good job.");
                messages.add("Less problems, more solutions – keep working like this and nothing will be able to stop you from reaching the top. Good job.");
                messages.add("One less task for the day! Good Job.");
                messages.add("One more task down for the day! Keep up the good work!");
                Random rand = new Random();
                int id = rand.nextInt((5) + 1);
                adapter.notifyDataSetChanged();
                Intent myIntent = new Intent("SOC_Students");
                myIntent.putExtra("MSG", messages.get(id));
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
                v.getContext().startActivity(myIntent);
            }
        });

        return view;
    }
}
