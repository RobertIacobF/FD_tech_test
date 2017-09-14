package com.example.robertiacob.fd_tech_test;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {
    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<>();
    private ListView myList;

    private static final String ID = "Id";
    private static final String BRAND = "Brandr";
    private static final String MODEL = "Model";
    private static final String IMG = "Img";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DoTheTask(MainActivity.this).execute();
    }

    private class DoTheTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;

        public DoTheTask(MainActivity activity) {
            context = activity;
            dialog = new ProgressDialog(context);
        }

        private Context context;

        @Override
        protected Boolean doInBackground(String... params) {
            JSONParser jParser = new JSONParser();
            JSONArray json = jParser.getJSONFromUrl("C:\\Users\\Robert Iacob\\Desktop\\Files Origin\\4.Android Work\\FD_tech_test\\cars.json");

            for (int i = 0; i < json.length(); i++) {
                try {
                    JSONObject obj = json.getJSONObject(i);
                    String id = obj.getString(ID);
                    String brand = obj.getString(BRAND);
                    String model = obj.getString(MODEL);
                    String img = obj.getString(IMG);

                    HashMap<String, String> map = new HashMap<>();

                    map.put(ID, id);
                    map.put(BRAND, brand);
                    map.put(MODEL, model);
                    map.put(IMG, img);
                    jsonlist.add(map);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            ListAdapter adapter = new SimpleAdapter(context, jsonlist, R.layout.list_item, new String[]{ID, BRAND, MODEL, IMG}, new int[]{
                    R.id.vehicleId, R.id.vehicleBrand, R.id.vehicleModel, R.id.vehicleImg});
            setListAdapter(adapter);
            myList = getListView();
        }
    }
}
