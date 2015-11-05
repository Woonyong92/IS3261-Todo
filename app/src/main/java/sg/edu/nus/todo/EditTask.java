package sg.edu.nus.todo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditTask extends Activity {
    MyDBHelper myDb;
    Button btnEditTask, btnAddContact;
    EditText name, description, location, endTime, endDate, contactNumber, contactName;
    String ids, names, descriptions, endDates, endTimes, locations, contactNames, contactNumbers;
    Calendar myCalendar = Calendar.getInstance();
    public static final int PICK_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        myDb = new MyDBHelper(this);
        name = (EditText) findViewById(R.id.editName);
        description = (EditText) findViewById(R.id.editDescription);
        endDate = (EditText) findViewById(R.id.editEndDate);
        endTime = (EditText) findViewById(R.id.editEndTime);
        location = (EditText) findViewById(R.id.editLocation);
        contactName = (EditText) findViewById(R.id.textContactName);
        contactNumber = (EditText) findViewById(R.id.textNumber);
        btnEditTask = (Button) findViewById(R.id.editTask);
        ids = getIntent().getStringExtra("id");
        names = getIntent().getStringExtra("name");
        descriptions = getIntent().getStringExtra("description");
        endDates = getIntent().getStringExtra("endDate");
        endTimes = getIntent().getStringExtra("endTime");
        locations = getIntent().getStringExtra("location");
        contactNames = getIntent().getStringExtra("contactName");
        contactNumbers = getIntent().getStringExtra("contactNumber");
        name.setText(names);
        description.setText(descriptions);
        endDate.setText(endDates);
        endTime.setText(endTimes);
        location.setText(locations);
        contactName.setText(contactNames);
        contactNumber.setText(contactNumbers);
        btnAddContact = (Button) findViewById(R.id.addContacts);
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });
        editTask();
        addTime();
        addDate();
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        switch (reqCode) {
            case (PICK_CONTACT) :
                ContentResolver cr = getContentResolver();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                cur.moveToFirst();
                try {
                    Uri uri = data.getData();
                    cur = getContentResolver().query(uri, null, null, null, null);
                    cur.moveToFirst();
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    contactName.setText(name);
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactNumber.setText(phone);
                    }
                    pCur.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
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

    public void editTask() {
        btnEditTask.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (name.getText().toString().matches("")||description.getText().toString().matches("")||endDate.getText().toString().matches("")||endTime.getText().toString().matches("")) {
                                Toast.makeText(EditTask.this, "Ensure all required(*) fields is filled up", Toast.LENGTH_LONG).show();
                        }
                        else {
                            boolean isInserted = myDb.editData(ids, name.getText().toString(),
                                    description.getText().toString(), endDate.getText().toString(), endTime.getText().toString(), location.getText().toString(),
                                    null, contactName.getText().toString(), contactNumber.getText().toString());
                            if (isInserted) {
                                Toast.makeText(EditTask.this, "Task Updated", Toast.LENGTH_LONG).show();
                                Intent myIntent = new Intent(EditTask.this, MainActivity.class);
                                startActivity(myIntent);
                            } else
                                Toast.makeText(EditTask.this, "Task not Updated", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );}

    public void addDate() {
        endDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditTask.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    public void addTime() {
        endTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String output = String.format("%02d:%02d", selectedHour, selectedMinute);
                        endTime.setText(output);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        endDate.setText(sdf.format(myCalendar.getTime()));
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };
}
