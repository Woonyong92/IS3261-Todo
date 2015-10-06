package sg.edu.nus.todo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
    Button btnEditTask;
    EditText name, description, location, endTime, endDate;
    String ids, names, descriptions, endDates, endTimes, locations;
    Calendar myCalendar = Calendar.getInstance();

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
        btnEditTask = (Button) findViewById(R.id.editTask);
        ids = getIntent().getStringExtra("id");
        names = getIntent().getStringExtra("name");
        descriptions = getIntent().getStringExtra("description");
        endDates = getIntent().getStringExtra("endDate");
        endTimes = getIntent().getStringExtra("endTime");
        locations = getIntent().getStringExtra("location");
        name.setText(names);
        description.setText(descriptions);
        endDate.setText(endDates);
        endTime.setText(endTimes);
        location.setText(locations);
        editTask();
        addTime();
        addDate();
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
        // automatically handle clicks on the Home/Up button, so long
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
                        if (name.getText().toString().matches(""))
                            Toast.makeText(EditTask.this, "Ensure name is filled up", Toast.LENGTH_SHORT).show();
                        else {
                            boolean isInserted = myDb.editData(ids, name.getText().toString(),
                                    description.getText().toString(), endDate.getText().toString(), endTime.getText().toString(), location.getText().toString(), null);
                            if (isInserted) {
                                Toast.makeText(EditTask.this, "Data Updated", Toast.LENGTH_LONG).show();
                                Intent myIntent = new Intent(EditTask.this, MainActivity.class);
                                startActivity(myIntent);
                            } else
                                Toast.makeText(EditTask.this, "Data not Updated", Toast.LENGTH_LONG).show();
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
                        endTime.setText( selectedHour + ":" + selectedMinute);
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
