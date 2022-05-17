package com.example.currency;

import android.app.Activity;
import android.os.AsyncTask;
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
import java.util.Currency;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class CurrencyAsyncTask extends AsyncTask<String,Void, String> {
    private List<String> namecountry;
    private List<Curren> currenList;

    private Activity activity;
    public CurrencyAsyncTask(List<String> namecountry, List<Curren> currenList, Activity activity) {
        this.namecountry = namecountry;
        this.currenList = currenList;
        this.activity = activity;
    }



    @Override
    protected String doInBackground(String... strings) {
        URL url = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            url = new URL(strings[0]);
            InputStreamReader inputStreamReader = new InputStreamReader(getInputStream(url));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }


            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    @Override
    protected void onPostExecute(String s) {
            super.onPostExecute(s);

        NodeList nodeList = null;
        XMLParser xmlParser = new XMLParser();
        Document document = null;
        try {
            document = xmlParser.getDocument(s);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        nodeList = document.getElementsByTagName("item");


        for (int i = 0; i < nodeList.getLength(); i++) {

            Element title = (Element) nodeList.item(i);
            //Name country
            String name = xmlParser.getValue(title, "title");
            String arrayname[] = name.split("/", 2);
            name = arrayname[1];

            int index = name.indexOf('(');
            String code = "";
            for (int j = 3; j > 0; j--, index++) {

                code = code + name.charAt(index + 1);
            }


            String temp3 = xmlParser.getValue(title, "description");
            // Log.d("BBB","tem3: "+ temp3);
            String temp4[] = temp3.split("= ", 2);


            String a = temp4[1].trim().substring(0, temp4[1].indexOf(" ", 1));
            Log.d("AAA", a);
            //    Log.d("AAA",""+temp4[1].substring(0,temp4[1].indexOf(" ")));
            Log.d("BBB", "" + temp4[1].trim().substring(0, temp4[1].indexOf(" ", 1)));
            Double currency = Double.valueOf(a);

            Curren cur = new Curren(name, code, currency);

            currenList.add(cur);

            namecountry.add(name);
            Log.d("BBB", "cur:" + currenList.get(i) + "\n");
        }


    }
    public InputStream getInputStream(URL url) throws IOException {
        return url.openConnection().getInputStream();
    }




}
