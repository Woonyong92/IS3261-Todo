package sg.edu.nus.todo;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class TodayFragment extends Fragment {
    MyDBHelper myDb;
    ListView viewAll;
    ArrayList<ArrayList<String>> placeList = new ArrayList<ArrayList<String>>();
    ArrayAdaptor adapter;

    public TodayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_today, container, false);
        viewAll = (ListView) v.findViewById(R.id.viewToday);
        myDb = new MyDBHelper(getActivity());
        placeList = viewToday();
        adapter = new ArrayAdaptor(placeList, getActivity());
        viewAll.setAdapter(adapter);
        return v;
    }

    public  ArrayList<ArrayList<String>> viewToday() {
        Cursor res = myDb.getToday();
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
            list.add(row);
        }
        return list;
    }

}
