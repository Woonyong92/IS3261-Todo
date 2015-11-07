package sg.edu.nus.todo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by SM on 3/11/2015.
 */
public class NotifyService extends Service {
        MyDBHelper myDb = new MyDBHelper(this);
        /**
         * Class for clients to access
         */
        public class ServiceBinder extends Binder {
            NotifyService getService() {
                return NotifyService.this;
            }
        }

        // Unique id to identify the notification.
        private static final int NOTIFICATION = 123;
        // Name of an intent extra we can use to identify if this service was started to create a notification
        public static final String INTENT_NOTIFY = "com.blundell.tut.service.INTENT_NOTIFY";
        // The system notification manager
        private NotificationManager mNM;
        private static int task_id;

        @Override
        public void onCreate() {
            Log.i("NotifyService", "onCreate()");
            mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.i("LocalService", "Received start id " + startId + ": " + intent);


            // If this service was started by out AlarmTask intent then we want to show our notification
            if(intent.getBooleanExtra(INTENT_NOTIFY, false)) {
                task_id = intent.getIntExtra("task id", 0);
                Log.d("before show notif", "id is " + task_id);
                showNotification(task_id);
            }
            // We don't care if this service is stopped as we have already delivered our notification
            return START_STICKY;
        }

/*    @Override
    public IBinder onBind(Intent intent) {
        Log.d("on bind", "yes");
        return null;
    }*/


        @Override
        public IBinder onBind(Intent intent) {
            return mBinder;
        }

        // This is the object that receives interactions from clients
        private final IBinder mBinder = new ServiceBinder();


        /**
         * Creates a notification and shows it in the OS drag-down status bar
         */
        private void showNotification(int task_id) {
            // This is the 'title' of the notification
            Log.d("notify service", "id is " + task_id);
            String task_name = myDb.getName(task_id);

            CharSequence title = "Your task has expired!";
            int icon = R.drawable.add_button;
            CharSequence text = "'" + task_name + "' has expired! Extend the task?";
            long time = System.currentTimeMillis();

            /*Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            builder.setLargeIcon(bm);   */
            /*Notification notification = new Notification(icon, text, time);*/


            // The PendingIntent to launch our activity if the user selects this notification
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class),
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

            Notification notification = new NotificationCompat.Builder(this)
                    .setTicker("Your task has expired!")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setLights(Color.YELLOW, 1000, 1000)
                    .setContentIntent(contentIntent)
                    .build();




            // Clear the notification when it is pressed
            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            // Send the notification to the system.
            mNM.notify(NOTIFICATION, notification);

            // Stop the service when we are finished
            stopSelf();
        }
}
