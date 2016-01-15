package io.github.nayrnet.androidtv.osd.remote;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.os.AsyncTask;
import java.net.HttpURLConnection;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;


public class OSDRemote extends Activity {

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.activity_osdremote);
  }


  public void execAction(View selectedView) {
    switch (selectedView.getId()) {
      case R.id.main_actionItem_bedroom:
        new AsyncHttpTask().execute("http://dev:dev@192.168.254.33:8080/json.htm?type=command&param=switchlight&idx=121&switchcmd=Toggle");
        this.finish();
        break;
      case R.id.main_actionItem_livingpower:
        new AsyncHttpTask().execute("http://dev:dev@192.168.254.33:8080/json.htm?type=command&param=switchlight&idx=104&switchcmd=Toggle");
        this.finish();
        break;
      case R.id.main_actionItem_charlie:
        new AsyncHttpTask().execute("http://dev:dev@192.168.254.33:8080/json.htm?type=command&param=switchlight&idx=118&switchcmd=Toggle");
        this.finish();
        break;
      case R.id.main_actionItem_poweroff:
        new AsyncHttpTask().execute("http://dev:dev@192.168.254.33:8080/json.htm?type=command&param=switchlight&idx=119&switchcmd=Off");
        this.finish();
        break;
      case R.id.main_actionItem_mute:
        new AsyncHttpTask().execute("http://dev:dev@192.168.254.33:8080/json.htm?type=command&param=switchlight&idx=105&switchcmd=Toggle");
        this.finish();
        break;
      case R.id.main_actionItem_dim:
        new AsyncHttpTask().execute("http://dev:dev@192.168.254.33:8080/json.htm?type=command&param=switchscene&idx=8&switchcmd=Toggle");
        this.finish();
        break;
      case R.id.main_actionItem_lightsout:
        new AsyncHttpTask().execute("http://dev:dev@192.168.254.33:8080/json.htm?type=command&param=switchscene&idx=2&switchcmd=Off");
        this.finish();
        break;
      default:
        this.finish();
        break;
    }
  }

  public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

    @Override
    protected Integer doInBackground(String... params) {
      InputStream inputStream = null;

      HttpURLConnection urlConnection = null;

      Integer result = 0;
      try {
                /* forming th java.net.URL object */
        URL url = new URL(params[0]);

        urlConnection = (HttpURLConnection) url.openConnection();

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
}
