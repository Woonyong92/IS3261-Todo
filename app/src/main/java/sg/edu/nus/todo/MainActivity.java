package sg.edu.nus.todo;

import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.BatteryManager;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction;
    IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    MyDBHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new MyDBHelper(this);
        Cursor res = myDb.getToday();
        if(res!=null && res.getCount()>0) {
            Fragment fragment = new TodayFragment();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentFragment, fragment);
            fragmentTransaction.commit();
        }
        else{
            Fragment fragment = new TodayCompletedFragment();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentFragment, fragment);
            fragmentTransaction.commit();
        }

/*        Intent batteryStatus = this.registerReceiver(null, ifilter);

        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPct = level / (float)scale;
        if (isCharging)
            Toast.makeText(this, "Battery is charging at " + batteryPct , Toast.LENGTH_LONG).show() ;
        else
            Toast.makeText(this, "Battery is not charging", Toast.LENGTH_LONG).show() ;*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onClick_goToAddTask(View view) {
        Intent myIntent = new Intent(this, AddTask.class);
        startActivity(myIntent);
    }

    public void onClick_changeToToday(View view) {
        Cursor res = myDb.getToday();
        if(res!=null && res.getCount()>0) {
            Fragment fragment = new TodayFragment();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentFragment, fragment);
            fragmentTransaction.commit();
        }
        else{
            Fragment fragment = new TodayCompletedFragment();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentFragment, fragment);
            fragmentTransaction.commit();
        }
    }

    public void onClick_changeToUpcoming(View view) {
        Fragment fragment = new UpcomingFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFragment, fragment);
        fragmentTransaction.commit();
    }

    public void onClick_changeToExpired(View view) {
        Fragment fragment = new ExpiredFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFragment, fragment);
        fragmentTransaction.commit();
    }

    public void onClick_changeToCompleted(View view) {
        Fragment fragment = new CompletedFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentFragment, fragment);
        fragmentTransaction.commit();
    }

}
