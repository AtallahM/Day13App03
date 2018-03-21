package com.matallahnh.nhadmin.day13app03;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//some changes
public class MainActivity extends AppCompatActivity {
    private final String SERVIVCE_URL = "http://apilayer.net/api/live?access_key=089debb1ad610a91fa2c760b5b4d004a&source=USD&currencies=USD,QAR,EGP,GBP&format=1";
    private TextView txt ;
    private ProgressDialog dialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void convert_click(View view) {

        txt = findViewById(R.id.txt);
        new MyTask().execute(SERVIVCE_URL);

    }

    private class MyTask extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            //txt.setText("Loading*****");
            Log.i("** MA **" , "onPreExecute");
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Please Wait");
            dialog.setMessage("Loading currency data ...");
            dialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            Log.i("** MA **" , "doInBackground");
            URL url = null;
            HttpURLConnection connection = null;
            InputStream in = null;
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;
            try {
                url =new URL(params[0]);
                connection =(HttpURLConnection) url.openConnection();//open stream and being ready to read
                in = connection.getInputStream();
                reader = new InputStreamReader(in);
                bufferedReader = new BufferedReader(reader);
                String rslt = "";
                String val = null; // ;//read json object
                while ((val =bufferedReader.readLine()) != null)
                {
                    rslt += val;
                }
                return rslt;//call postExecute(val)
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Log.i("**MA**",e.getMessage());
            }
            finally {
                try {
                    if (bufferedReader != null && reader!= null && in!= null) {
                        bufferedReader.close();
                        reader.close();
                        in.close();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.i("**MA**",e.getMessage());
                }
            }


            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            Log.i("** MA **" , "onPostExecute");
            Log.i("** MA **",s);

            try {
                JSONObject obj = new JSONObject(s);
                JSONObject child = obj.getJSONObject("quotes");
                txt.setText("USD:USD "+ child.getString("USDUSD"));
                txt.append("\nUSD:QAR "+ child.getString("USDQAR"));
                txt.append("\nUSD:EGP "+ child.getString("USDEGP"));
                txt.append("\nUSD:GBP "+ child.getString("USDGBP"));
                dialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}
