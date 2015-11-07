package sg.edu.nus.todo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Contacts.People;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddTask extends Activity {
    MyDBHelper myDb;
    Button btnAddTask, btnAddContact;
    EditText name, description, location, endTime, endDate, contactName, contactNumber;
    Calendar myCalendar = Calendar.getInstance();
    Spinner reminder;
    String reminder_period = "";
    public static final int PICK_CONTACT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        myDb = new MyDBHelper(this);
        name = (EditText) findViewById(R.id.editName);
        description = (EditText) findViewById(R.id.editDescription);
        endDate = (EditText) findViewById(R.id.editEndDate);
        endTime = (EditText) findViewById(R.id.editEndTime);
        location = (EditText) findViewById(R.id.editLocation);
        contactName = (EditText) findViewById(R.id.textContactName);
        contactNumber = (EditText) findViewById(R.id.textNumber);
        btnAddContact = (Button) findViewById(R.id.addContacts);
        reminder = (Spinner) findViewById(R.id.reminder_spinner);
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
            }
        });
        btnAddTask = (Button) findViewById(R.id.addTask);

        //Spinner spinner = (Spinner)findViewById(R.id.reminder_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.reminder_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        reminder.setAdapter(adapter);
        reminder.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reminder_period = parent.getItemAtPosition(position).toString();
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addTask();
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
        return true;
    }

    public void addTask() {
        btnAddTask.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (name.getText().toString().matches("")||description.getText().toString().matches("")||endDate.getText().toString().matches("")||endTime.getText().toString().matches("")) {
                            Toast.makeText(AddTask.this, "Ensure all required(*) fields is filled up", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Log.d("111111111111", reminder_period);
                            boolean isInserted = myDb.insertData(name.getText().toString(),
                                    description.getText().toString(), endDate.getText().toString(), endTime.getText().toString(), location.getText().toString(),
                                    null, contactName.getText().toString(), contactNumber.getText().toString(), reminder_period);
                            if (isInserted) {
                                Toast.makeText(AddTask.this, "Task Added", Toast.LENGTH_LONG).show();
                                Intent myIntent = new Intent(AddTask.this, MainActivity.class);
                                //scheduleNotification(getNotification("name.getText().toString()"), myCalendar);

                                startActivity(myIntent);
                            } else
                                Toast.makeText(AddTask.this, "Task not Added", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );}

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String name) {
        Notification.Builder builder = new Notification.Builder(this);

        CharSequence title = "Your task has expired!";
        CharSequence text = "'" + name + "' has expired! Extend the task?";


        builder.setTicker("Your task has expired!");
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentTitle(title);
        builder.setContentText(text);
        builder.setAutoCancel(true);
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setLights(Color.YELLOW, 1000, 1000);

/*        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        builder.setContentIntent(contentIntent);*/


        return builder.build();
    }


    public void addDate() {
        endDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddTask.this, date, myCalendar
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
                mTimePicker = new TimePickerDialog(AddTask.this, new TimePickerDialog.OnTimeSetListener() {
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
