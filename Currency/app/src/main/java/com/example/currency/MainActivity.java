package com.example.currency;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    private Button convert;
    private ArrayAdapter<String> adapter;
    private AutoCompleteTextView autoFrom;
    private AutoCompleteTextView autoTo;
    private ImageView imgto, imgfrom;
    private EditText amount;
    private TextView result;
    private int positionfrom = 0, positionto = 0;


    private List<String> namecountry;

    private List<Curren> currenList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currenList = new ArrayList<>();
        namecountry = new ArrayList<>();
        Anhxa();
        String urlcountry = "https://usd.fxexchangerate.com/rss.xml";
        CurrencyAsyncTask currencyAsyncTask = new CurrencyAsyncTask(namecountry, currenList, this);
            currencyAsyncTask.execute(urlcountry);
       // new Async().execute(urlcountry);


        adapter = new ArrayAdapter<String>(this, R.layout.item_country, namecountry);
        autoFrom.setAdapter(adapter);
        autoTo.setAdapter(adapter);

        Onclick();
        autoFrom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                positionfrom = i;

            }
        });
        autoTo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                positionto = i;

            }
        });


        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (amount.getText() == null) {
                    Toast.makeText(MainActivity.this, "Please input amount", Toast.LENGTH_SHORT).show();
                } else {

                    Curren cufrom = currenList.get(positionfrom);
                    String codeFrom = cufrom.getCode();
                    Curren curto = currenList.get(positionto);


                    String codeTo = curto.getCode();

                    //String url = "https://" + codeFrom.toLowerCase() + ".fxexchangerate.com/" + codeTo.toLowerCase() + ".xml";
                    String url = "https://" + codeFrom.toLowerCase() + ".fxexchangerate.com/rss.xml";

                   // new Async().execute(url);
                   CurrencyAsyncTask currencyAsyncTask = new CurrencyAsyncTask(namecountry, currenList, MainActivity.this);
                    currencyAsyncTask.execute(url);

//                    Curren cur = currenList.get(0);
//                    Double kq = Double.valueOf(cur.getCurrency());
//                    result.setText(String.valueOf(kq));
                    String a = "";
                    for (Curren curren : currenList) {
                        if (curren.getCode().equals(codeTo)) {
                            Float kq = Float.valueOf(amount.getText().toString());
                            if (kq == null) {
                                kq = Float.valueOf(1);
                                amount.setText("1");
                            }
                            a = String.valueOf(kq * curren.getCurrency());
                            Log.d("a", a);

                           // Toast.makeText(MainActivity.this, curren.getCurrency().toString(), Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    result.setText(a);


                }
            }
        });

    }


    private void Onclick() {
        imgto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoTo.showDropDown();
            }
        });
        imgfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autoFrom.showDropDown();
            }
        });
    }


    private void Anhxa() {
        convert = findViewById(R.id.btnconvert);
        autoFrom = (AutoCompleteTextView) findViewById(R.id.autotvfrom);
        autoTo = (AutoCompleteTextView) findViewById(R.id.autotvto);
        imgto = (ImageView) findViewById(R.id.imageto);
        imgfrom = (ImageView) findViewById(R.id.imagefrom);
        amount = (EditText) findViewById(R.id.edtamount);
        convert = (Button) findViewById(R.id.btnconvert);
        result = (TextView) findViewById(R.id.tvresult);
    }



}