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
                if (!isNOtAdded(finalData, orrg)) {
                    finalData.add(orrg);
                }
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

                String bid = curOrg.currencies.get("USD").bid;
                String ask = curOrg.currencies.get("USD").ask;

                TextView askView = (TextView) iv.findViewById(R.id.ask);
                askView.setTypeface(tf);
                askView.setText(ask.substring(0, ask.length() - 2));

                TextView bidView = (TextView) iv.findViewById(R.id.bid);
                bidView.setTypeface(tf);
                bidView.setText(bid.substring(0, bid.length() - 2));

                return iv;
            }
        });

        TextView dateView = (TextView) findViewById(R.id.date);
        dateView.setTypeface(Typeface.createFromAsset(getAssets(), "digital-7 (mono).ttf"));
        dateView.setText(data.date);
    }

    private boolean isNOtAdded(List<Organization> list, Organization org2) {
        for (Organization org1 : list) {
            String[] split1 = org1.title.split(" ");
            String[] split2 = org2.title.split(" ");

            if (split1[0].equals(split2[0])) {
                org1.title = split1[0];
                return true;
            }
        }
        return false;
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
