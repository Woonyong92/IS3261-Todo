package sg.edu.nus.todo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ShowTask extends Activity {

    Button btnEditTask,getLocation;
    TextView name, description, location, endTime, endDate, contactName, contactNumber;
    TextView reminder;
    String ids, names, descriptions, endDates, endTimes, locations, status, contactNames, contactNumbers, reminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ids = getIntent().getStringExtra("id");
        names = getIntent().getStringExtra("name");
        descriptions = getIntent().getStringExtra("description");
        endDates = getIntent().getStringExtra("endDate");
        endTimes = getIntent().getStringExtra("endTime");
        locations = getIntent().getStringExtra("location");
        status = getIntent().getStringExtra("status");
        contactNames = getIntent().getStringExtra("contactName");
        contactNumbers = getIntent().getStringExtra("contactNumber");
        reminders = getIntent().getStringExtra("reminder");
        setContentView(R.layout.activity_show_task);
        name = (TextView) findViewById(R.id.editName);
        description = (TextView) findViewById(R.id.editDescription);
        endDate = (TextView) findViewById(R.id.editEndDate);
        endTime = (TextView) findViewById(R.id.editEndTime);
        location = (TextView) findViewById(R.id.editLocation);
        contactName = (TextView) findViewById(R.id.editContactName);
        contactNumber = (TextView) findViewById(R.id.editContactNumber);
        btnEditTask = (Button) findViewById(R.id.editTask);
        getLocation = (Button) findViewById(R.id.getLocation);
        reminder = (TextView) findViewById(R.id.editReminder);
        LinearLayout linearLocation = (LinearLayout) findViewById(R.id.location);
        LinearLayout linearContact = (LinearLayout) findViewById(R.id.contact);
        LinearLayout linearContactBtn = (LinearLayout) findViewById(R.id.contactBtn);
        if (status == null) {
            if (locations.equals("")) {
                getLocation.setVisibility(View.GONE);
                linearLocation.setVisibility(View.GONE);
                if (contactNames.equals("")) {
                    linearContact.setVisibility(View.GONE);
                    linearContactBtn.setVisibility(View.GONE);
                }
                else if (contactNumbers.equals("")){
                    contactNumber.setVisibility(View.GONE);
                    linearContactBtn.setVisibility(View.GONE);
                }
            }
            else if (contactNames.equals("")) {
                linearContact.setVisibility(View.GONE);
                linearContactBtn.setVisibility(View.GONE);
            }
            else if (contactNumbers.equals("")){
                contactNumber.setVisibility(View.GONE);
                linearContactBtn.setVisibility(View.GONE);
            }
        }
        else {
            getLocation.setVisibility(View.GONE);
            linearContactBtn.setVisibility(View.GONE);
            btnEditTask.setVisibility(View.GONE);
            if (locations.equals("")) {
                getLocation.setVisibility(View.GONE);
                linearLocation.setVisibility(View.GONE);
                if (contactNames.equals("")) {
                    linearContact.setVisibility(View.GONE);
                    linearContactBtn.setVisibility(View.GONE);
                }
                else if (contactNumbers.equals("")){
                    contactNumber.setVisibility(View.GONE);
                    linearContactBtn.setVisibility(View.GONE);
                }
            }
            else if (contactNames.equals("")) {
                linearContact.setVisibility(View.GONE);
                linearContactBtn.setVisibility(View.GONE);
            }
            else if (contactNumbers.equals("")){
                contactNumber.setVisibility(View.GONE);
                linearContactBtn.setVisibility(View.GONE);
            }
        }

        name.setText(names);
        description.setText(descriptions);
        endDate.setText(endDates);
        endTime.setText(endTimes);
        location.setText(locations);
        contactName.setText(contactNames);
        contactNumber.setText(contactNumbers);
        reminder.setText(reminders);
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
        myIntent.putExtra("contactName", contactNames);
        myIntent.putExtra("contactNumber", contactNumbers);
        myIntent.putExtra("reminder", reminders);
        startActivity(myIntent);
    }

    public void onClick_goToGetLocation(View view) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + locations);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void onClick_goToSMS(View view) {
        Intent myIntent = new Intent(android.content.Intent.ACTION_VIEW);
        myIntent.putExtra("address", contactNumbers);
        myIntent.putExtra("sms_body", "");
        myIntent.setType("vnd.android-dir/mms-sms");
        startActivity(myIntent);
    }

    public void onClick_Call(View view) {
        Intent i;

        i = new Intent(android.content.Intent.ACTION_CALL, Uri.parse("tel:" + contactNumbers));
        startActivity(i);
    }
}
