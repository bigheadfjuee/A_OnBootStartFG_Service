package tw.bxb.servicetest1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by tony on 2015/9/14.
 */
public class MyService extends Service {
  public static final String TAG = "MyService";
  private MyBinder mBinder = new MyBinder();

  @Override
  public void onCreate() {
    super.onCreate();

    Log.d(TAG, "onCreate() executed");
    Log.d(TAG, "MyService thread id is " + Thread.currentThread().getId());
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d(TAG, "onStartCommand() executed");

    new Thread(new Runnable() {
      @Override
      public void run() {
        // 开始执行后台任务
        Log.d(TAG, "onStartCommand - Thread : " + Thread.currentThread().getId());
      }
    }).start();

    Intent notificationIntent = new Intent(this,  MainActivity.class);
    PendingIntent pendingIntent=PendingIntent.getActivity(this, 0,
      notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);


    Notification notification=new NotificationCompat.Builder(this)
      .setSmallIcon(R.drawable.ic_stat_ic_notification)
      .setContentInfo("ContentInfo")
      .setContentTitle("ContentTitile")
      .setContentText("MyService")
      .setContentIntent(pendingIntent).build();

    startForeground(1, notification);

    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onDestroy() {
    Log.d(TAG, "onDestroy() executed");

    super.onDestroy();

  }

  @Override
  public IBinder onBind(Intent intent) {
    Log.d("TAG", "onBind() executed");
    return mBinder;
  }

  class MyBinder extends Binder {

    public void startDownload() {
      Log.d("TAG", "startDownload() executed");

      new Thread(new Runnable() {
        @Override
        public void run() {
          // 执行具体的下载任务
          Log.d(TAG, "startDownload - Thread : " + Thread.currentThread().getId());
        }
      }).start();
    }
  }

  private static final Class<?>[] mSetForegroundSignature = new Class[] {
    boolean.class};
  private static final Class<?>[] mStartForegroundSignature = new Class[] {
    int.class, Notification.class};
  private static final Class<?>[] mStopForegroundSignature = new Class[] {
    boolean.class};

  private NotificationManager mNM;
  private Method mSetForeground;
  private Method mStartForeground;
  private Method mStopForeground;
  private Object[] mSetForegroundArgs = new Object[1];
  private Object[] mStartForegroundArgs = new Object[2];
  private Object[] mStopForegroundArgs = new Object[1];

  void invokeMethod(Method method, Object[] args) {
    try {
      method.invoke(this, args);
    } catch (InvocationTargetException e) {
      // Should not happen.
      Log.w("ApiDemos", "Unable to invoke method", e);
    } catch (IllegalAccessException e) {
      // Should not happen.
      Log.w("ApiDemos", "Unable to invoke method", e);
    }
  }
}