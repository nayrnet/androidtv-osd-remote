package io.github.nayrnet.androidtv.osd.remote;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.os.AsyncTask;
import android.util.Base64;
import java.net.HttpURLConnection;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.os.Handler;

public class OSDRemote extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_osdremote);
  }


  public void execAction(View selectedView) {
    Handler goBack = new Handler();

    switch (selectedView.getId()) {
      case R.id.main_actionItem_ipcameras:
        new AsyncHttpTask().execute("http://192.168.254.33:8080/json.htm?type=command&param=switchlight&idx=145&switchcmd=Set%20Level&level=40");
        goBack.postDelayed(dialog, 3000);
        break;
      case R.id.main_actionItem_ps3:
        new AsyncHttpTask().execute("http://192.168.254.33:8080/json.htm?type=command&param=switchlight&idx=145&switchcmd=Set%20Level&level=20");
        goBack.postDelayed(dialog, 3000);
        break;
      case R.id.main_actionItem_ps4:
        new AsyncHttpTask().execute("http://192.168.254.33:8080/json.htm?type=command&param=switchlight&idx=145&switchcmd=Set%20Level&level=30");
        goBack.postDelayed(dialog, 3000);
        break;
      case R.id.main_actionItem_poweroff:
        new AsyncHttpTask().execute("http://192.168.254.33:8080/json.htm?type=command&param=switchlight&idx=145&switchcmd=Off");
        goBack.postDelayed(dialog, 3000
        );
        break;
      case R.id.main_actionItem_mute:
        new AsyncHttpTask().execute("http://192.168.254.33:8080/json.htm?type=command&param=switchlight&idx=105&switchcmd=Toggle");
        this.finish();
        break;
      case R.id.main_actionItem_dim:
        new AsyncHttpTask().execute("http://192.168.254.33:8080/json.htm?type=command&param=switchscene&idx=5&switchcmd=On");
        this.finish();
        break;
      case R.id.main_actionItem_lightsout:
        new AsyncHttpTask().execute("http://192.168.254.33:8080/json.htm?type=command&param=switchscene&idx=6&switchcmd=Off");
        this.finish();
        break;
      default:
        this.finish();
        break;
    }
  }

  public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {
    // Auth-Basic Login Credentials to Domoticz
    final String basicAuth = "Basic " + Base64.encodeToString("dev:dev".getBytes(), Base64.NO_WRAP);

    @Override
    protected Integer doInBackground(String... params) {
      InputStream inputStream = null;

      HttpURLConnection urlConnection = null;

      Integer result = 0;
      try {
                /* forming th java.net.URL object */
        URL url = new URL(params[0]);

        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty ("Authorization", basicAuth);

                /* for Get request */
        urlConnection.setRequestMethod("GET");

        int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
        if (statusCode == 200) {

          inputStream = new BufferedInputStream(urlConnection.getInputStream());

          result = 1; // Successful

        } else {
          result = 0; //"Failed to fetch data!";
        }

      } catch (Exception e) {
        //Log.d(TAG, e.getLocalizedMessage());
      }

      return result; //"Failed to fetch data!";
    }

  }

  private Runnable dialog = new Runnable() {
    @Override
    public void run() {
      new AlertDialog.Builder(OSDRemote.this)
          .setTitle("Please Note")
          .setMessage("You can return to the Nexus Player after changing inputs/powering off by simply pressing ENTER/OK on the Nexus remote.\n\nIf you can see this message simply press continue.")
          .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              new AsyncHttpTask().execute("http://192.168.254.33:8080/json.htm?type=command&param=switchlight&idx=145&switchcmd=Set%20Level&level=10");
              OSDRemote.this.finish();
            }
          })
          .create()
          .show();
    }
  };
}
