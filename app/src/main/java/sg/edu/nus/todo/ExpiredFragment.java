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
public class ExpiredFragment extends Fragment {
    MyDBHelper myDb;
    ListView viewAll;
    ArrayList<ArrayList<String>> placeList = new ArrayList<ArrayList<String>>();
    ArrayAdaptor adapter;


    public ExpiredFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_expired, container, false);
        viewAll = (ListView) v.findViewById(R.id.viewExpired);
        myDb = new MyDBHelper(getActivity());
        placeList = viewExpired();
        adapter = new ArrayAdaptor(placeList, getActivity());
        viewAll.setAdapter(adapter);
        return v;
    }

    public  ArrayList<ArrayList<String>> viewExpired() {
        Cursor res = myDb.getExpired();
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        while (res.moveToNext()) {
            ArrayList<String> row = new ArrayList<String>();
            row.add(res.getString(0));
            row.add(res.getString(1));
            row.add(res.getString(2));
            row.add(res.getString(3));
            row.add(res.getString(4));
            row.add(res.getString(5));
            list.add(row);
        }
        return list;
    }

}
