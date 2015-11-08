package sg.edu.nus.todo;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment {
    MyDBHelper myDb;
    ListView viewAll;
    ArrayList<ArrayList<String>> placeList = new ArrayList<ArrayList<String>>();
    ArrayAdaptor adapter;

    public UpcomingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_upcoming, container, false);
        viewAll = (ListView) v.findViewById(R.id.viewUpcoming);
        myDb = new MyDBHelper(getActivity());
        placeList = viewUpcoming();
        adapter = new ArrayAdaptor(placeList, getActivity());
        viewAll.setAdapter(adapter);
        return v;
    }

    public  ArrayList<ArrayList<String>> viewUpcoming() {
        Cursor res = myDb.getUpcoming();
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        while (res.moveToNext()) {
            ArrayList<String> row = new ArrayList<String>();
            row.add(res.getString(0));
            row.add(res.getString(1));
            row.add(res.getString(2));
            row.add(res.getString(3));
            row.add(res.getString(4));
            row.add(res.getString(5));
            row.add(res.getString(6));
            row.add(res.getString(7));
            row.add(res.getString(8));
            row.add(res.getString(9));
            list.add(row);
        }
        return list;
    }
}
