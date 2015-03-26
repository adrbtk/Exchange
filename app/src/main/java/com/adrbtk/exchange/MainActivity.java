package com.adrbtk.exchange;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.adrbtk.exchange.model.Data;
import com.adrbtk.exchange.model.Organization;
import com.adrbtk.exchange.model.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.list);

        Data data = null;
        try {
            AsyncTask<String, Void, Data> task = new GetTask().execute("ru");
            data = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        final List<Organization> finalData = new ArrayList<>();

        for (Organization orrg : data.organizations) {
            if (orrg.currencies.containsKey("USD")) {
                finalData.add(orrg);
            }
        }


        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return finalData.size();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                Organization curOrg = finalData.get(i);
                View iv = getLayoutInflater().inflate(R.layout.list_item, viewGroup, false);
                TextView org = ((TextView) iv.findViewById(R.id.organization));
                org.setText(curOrg.title);
                Typeface tf = Typeface.createFromAsset(getAssets(), "digital-7 (mono).ttf");

                TextView ask = (TextView) iv.findViewById(R.id.ask);
                ask.setTypeface(tf);
                ask.setText(curOrg.currencies.get("USD").ask);

                TextView bid = (TextView) iv.findViewById(R.id.bid);
                bid.setTypeface(tf);
                bid.setText(curOrg.currencies.get("USD").bid);

                return iv;
            }
        });

        TextView dateView = (TextView) findViewById(R.id.date);
        dateView.setTypeface(Typeface.createFromAsset(getAssets(), "digital-7 (mono).ttf"));
        dateView.setText(data.date);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class GetTask extends AsyncTask<String, Void, Data> {

        @Override
        protected Data doInBackground(String... params) {
            return Request.get("ru");
        }
    }
}
