package com.example.printer.onlinecurrencyexchangerate;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    String[] strings_spinner1 = {"EUR","HUF","INR","PLN","USD","AED","AUD","BGN","BRL","CAD","CHF","CLP","CZK","DKK","GEL","HKD",
            "IDR","MAD","MXN","MYR","NGN","NOK","NZD","PHP","PKR","RON","SEK","SGD","THB","TRY","UAH"};

    int arr_images_spinner1[] = {R.drawable.img_flag_eur,R.drawable.img_flag_huf, R.drawable.img_flag_inr,
            R.drawable.img_flag_pln, R.drawable.img_flag_usd, R.drawable.img_flag_aed,R.drawable.img_flag_aud,
            R.drawable.img_flag_bgn, R.drawable.img_flag_brl, R.drawable.img_flag_cad, R.drawable.img_flag_chf,
            R.drawable.img_flag_clp, R.drawable.img_flag_czk, R.drawable.img_flag_dkk, R.drawable.img_flag_gel,
            R.drawable.img_flag_hkd, R.drawable.img_flag_idr, R.drawable.img_flag_mad, R.drawable.img_flag_mxn,
            R.drawable.img_flag_myr, R.drawable.img_flag_ngn, R.drawable.img_flag_nok, R.drawable.img_flag_nzd,
            R.drawable.img_flag_php, R.drawable.img_flag_pkr, R.drawable.img_flag_ron, R.drawable.img_flag_sek,
            R.drawable.img_flag_sgd, R.drawable.img_flag_thb, R.drawable.img_flag_try, R.drawable.img_flag_uah};

    String[] strings_spinner2 = {"EUR","HUF","INR","PLN","USD","AED","AUD","BGN","BRL","CAD","CHF","CLP","CZK","DKK","GEL","HKD",
            "IDR","MAD","MXN","MYR","NGN","NOK","NZD","PHP","PKR","RON","SEK","SGD","THB","TRY","UAH"};

    int arr_images_spinner2[] = { R.drawable.img_flag_eur,R.drawable.img_flag_huf, R.drawable.img_flag_inr,
            R.drawable.img_flag_pln, R.drawable.img_flag_usd, R.drawable.img_flag_aed,R.drawable.img_flag_aud,
            R.drawable.img_flag_bgn, R.drawable.img_flag_brl, R.drawable.img_flag_cad, R.drawable.img_flag_chf,
            R.drawable.img_flag_clp, R.drawable.img_flag_czk, R.drawable.img_flag_dkk, R.drawable.img_flag_gel,
            R.drawable.img_flag_hkd, R.drawable.img_flag_idr, R.drawable.img_flag_mad, R.drawable.img_flag_mxn,
            R.drawable.img_flag_myr, R.drawable.img_flag_ngn, R.drawable.img_flag_nok, R.drawable.img_flag_nzd,
            R.drawable.img_flag_php, R.drawable.img_flag_pkr, R.drawable.img_flag_ron, R.drawable.img_flag_sek,
            R.drawable.img_flag_sgd, R.drawable.img_flag_thb, R.drawable.img_flag_try, R.drawable.img_flag_uah};

    Spinner spinner_send_money_from_country,spinner_receive_money_To_country;
    MyAdapter adapter;
    MyAdapter1 adapter1;
    TextView txt_current_currency_rate;

    String str_Currency1,str_Currency2,str_BothCurrency;
    String URL="http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20%28%22";
    String URL1="%22%29&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";

    NetworkConnection nw;
    ProgressDialog prgDialog;
    Boolean netConnection = false;
    Double Currency_Convert_Rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nw = new NetworkConnection(getApplicationContext());
        prgDialog = new ProgressDialog(this);
        prgDialog.setCancelable(false);

        txt_current_currency_rate= (TextView) findViewById(R.id.activity_money_transfer_txt_current_currency_rate);

        spinner_send_money_from_country= (Spinner) findViewById(R.id.spinner_send_money);
        spinner_receive_money_To_country= (Spinner) findViewById(R.id.spinner_receive_money);

        adapter=new MyAdapter(MainActivity.this, R.layout.spinner_layout, strings_spinner1);
        adapter1=new MyAdapter1(MainActivity.this, R.layout.spinner_layout, strings_spinner2);
        //adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner_send_money_from_country.setAdapter(adapter);
        spinner_receive_money_To_country.setAdapter(adapter1);

        spinner_send_money_from_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                str_Currency1=spinner_send_money_from_country.getSelectedItem().toString();
                str_Currency2=spinner_receive_money_To_country.getSelectedItem().toString();
                str_BothCurrency = str_Currency1+str_Currency2;
                new CurrencyExchangeRateOperation().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinner_receive_money_To_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                str_Currency1=spinner_send_money_from_country.getSelectedItem().toString();
                str_Currency2=spinner_receive_money_To_country.getSelectedItem().toString();
                str_BothCurrency = str_Currency1+str_Currency2;
                new CurrencyExchangeRateOperation().execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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

    public class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context, int textViewResourceId,   String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.spinner_layout, parent, false);

            ImageView img_country=(ImageView)row.findViewById(R.id.spinner_img_country);
            img_country.setImageResource(arr_images_spinner1[position]);

            TextView txt_country=(TextView)row.findViewById(R.id.spinner_txt_country);
            txt_country.setText(strings_spinner1[position]);

            return row;
        }
    }

    public class MyAdapter1 extends ArrayAdapter<String> {

        public MyAdapter1(Context context, int textViewResourceId,   String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.spinner_layout, parent, false);

            ImageView img_country=(ImageView)row.findViewById(R.id.spinner_img_country);
            img_country.setImageResource(arr_images_spinner2[position]);

            TextView txt_country=(TextView)row.findViewById(R.id.spinner_txt_country);
            txt_country.setText(strings_spinner2[position]);

            return row;
        }
    }

    private class CurrencyExchangeRateOperation extends AsyncTask<String, Void, Void> {

        String status, message;
        @Override
        protected void onPreExecute() {
            // Set Progress Dialog Text
        }

        @Override
        protected Void doInBackground(String... urls) {

            if(nw.isConnectingToInternet() == true)
            {
                try
                {
                    //List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    ServiceHandler sh  = new ServiceHandler();

                    String URL3= URL + str_BothCurrency + URL1;
                    String response = sh.makeServiceCall(URL3 , ServiceHandler.GET);

                    Log.e("response", response);

                    JSONObject js = new JSONObject(response);
                    JSONObject js1=js.getJSONObject("query");
                    JSONObject js2=js1.getJSONObject("results");
                    JSONObject js3=js2.getJSONObject("rate");
                    Currency_Convert_Rate=js3.getDouble("Rate");

                    Log.e("Jsonobject 3",js3.toString());

                    Log.e("Rate of Currency","" + Currency_Convert_Rate);

                }catch(Exception ex){

                }
                netConnection = true;
            }else
            {
                netConnection = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            txt_current_currency_rate.setText("Current " + str_Currency2 + " Rate: " + Double.toString(Currency_Convert_Rate));
        }
    }
}
