package sg.edu.nus.todo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowTask extends Activity {

    Button btnEditTask;
    TextView name, description, location, endTime, endDate;
    String ids, names, descriptions, endDates, endTimes, locations, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        status = getIntent().getStringExtra("status");
        if (status == null) {
            setContentView(R.layout.activity_show_task);
        }
        else {
            setContentView(R.layout.activity_show_completed_task);
        }
        name = (TextView) findViewById(R.id.editName);
        description = (TextView) findViewById(R.id.editDescription);
        endDate = (TextView) findViewById(R.id.editEndDate);
        endTime = (TextView) findViewById(R.id.editEndTime);
        location = (TextView) findViewById(R.id.editLocation);
        btnEditTask = (Button) findViewById(R.id.editTask);
        ids = getIntent().getStringExtra("id");
        names = getIntent().getStringExtra("name");
        descriptions = getIntent().getStringExtra("description");
        endDates = getIntent().getStringExtra("endDate");
        endTimes = getIntent().getStringExtra("endTime");
        locations = getIntent().getStringExtra("location");
        status = getIntent().getStringExtra("status");
        name.setText(names);
        description.setText(descriptions);
        endDate.setText(endDates);
        endTime.setText(endTimes);
        location.setText(locations);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up today_button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick_goToEditTask(View view) {
        Intent myIntent = new Intent(this, EditTask.class);
        myIntent.putExtra("id", ids);
        myIntent.putExtra("name", names);
        myIntent.putExtra("description", descriptions);
        myIntent.putExtra("endDate", endDates);
        myIntent.putExtra("endTime", endTimes);
        myIntent.putExtra("location", locations);
        startActivity(myIntent);
    }

    public void onClick_goToGetLocation(View view){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+locations);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
