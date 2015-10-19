package tw.bxb.servicetest1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by tony on 2015/9/14.
 */
public class OnBoot extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent)
  {
    // Create Intent
    Intent serviceIntent = new Intent(context, MyService.class);
    // Start service
    context.startService(serviceIntent);

  }
}
