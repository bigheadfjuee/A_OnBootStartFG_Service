package tw.bxb.servicetest1;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private Button btnStart, btnStop, btnBind, btnUnBind;
  private MyService.MyBinder myBinder;
  public static final String TAG = "MainActivity";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Log.d(TAG, "onCreate");
    Log.d(TAG, "MainActivity thread id is " + Thread.currentThread().getId());

    btnStart = (Button) findViewById(R.id.btnStart);
    btnStop = (Button) findViewById(R.id.btnStop);
    btnBind = (Button) findViewById(R.id.btnBind);
    btnUnBind = (Button) findViewById(R.id.btnUnBind);
    btnStart.setOnClickListener(this);
    btnStop.setOnClickListener(this);
    btnBind.setOnClickListener(this);
    btnUnBind.setOnClickListener(this);
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
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onDestroy() {
    Log.d(TAG, "onDestroy");
    super.onDestroy();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.btnStart:
        Intent startIntent = new Intent(this, MyService.class);
        startService(startIntent);
        break;
      case R.id.btnStop:
        Intent stopIntent = new Intent(this, MyService.class);
        stopService(stopIntent);
        break;

      case R.id.btnBind:
        Intent bindIntent = new Intent(this, MyService.class);
        bindService(bindIntent, MyConnection, BIND_AUTO_CREATE);
        break;
      case R.id.btnUnBind:
        unbindService(MyConnection);
        break;

      default:
        break;
    }
  }

  private ServiceConnection MyConnection = new ServiceConnection() {

    @Override
    public void onServiceDisconnected(ComponentName name) {
      Log.d(TAG, "onServiceDisconnected");
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      Log.d(TAG, "onServiceConnected");
      myBinder.startDownload();
    }
  };
}
