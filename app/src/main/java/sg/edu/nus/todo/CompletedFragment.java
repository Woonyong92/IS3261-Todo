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
public class CompletedFragment extends Fragment {
    MyDBHelper myDb;
    ListView viewAll;
    ArrayList<ArrayList<String>> placeList = new ArrayList<ArrayList<String>>();
    CompletedArrayAdaptor adapter;

    public CompletedFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_completed, container, false);
        viewAll = (ListView) v.findViewById(R.id.viewCompleted);
        myDb = new MyDBHelper(getActivity());
        placeList = viewCompleted();
        adapter = new CompletedArrayAdaptor(placeList, getActivity());
        viewAll.setAdapter(adapter);
        return v;
    }

    public  ArrayList<ArrayList<String>> viewCompleted() {
        Cursor res = myDb.getCompleted();
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
