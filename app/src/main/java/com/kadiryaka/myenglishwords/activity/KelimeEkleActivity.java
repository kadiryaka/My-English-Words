package com.kadiryaka.myenglishwords.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kadiryaka.myenglishwords.R;
import com.kadiryaka.myenglishwords.entity.Grup;
import com.kadiryaka.myenglishwords.service.Veritabani;
import com.kadiryaka.myenglishwords.service.VeritabaniIslemleri;

import java.util.List;
import java.util.ArrayList;


public class KelimeEkleActivity extends Activity {

    VeritabaniIslemleri veritabaniIslemleri;
    Veritabani veritabani;
    List<Grup> grupList;
    private EditText turkce;
    private EditText ingilizce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelime_ekle);
        veritabaniIslemleri = new VeritabaniIslemleri();
        veritabani = new Veritabani(this);
        List<String> arrayList = new ArrayList<String>();
        Button btnEkle = (Button) findViewById(R.id.ekleButon);
        final Spinner sampleSpinner = (Spinner) findViewById(R.id.spinner);
        turkce    = (EditText) findViewById(R.id.turkce);
        ingilizce = (EditText) findViewById(R.id.ingilizce);

        grupList = veritabaniIslemleri.gruplariGetir(veritabani);
        for (Grup item:grupList) {
            arrayList.add(item.getName());
        }
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, arrayList);

        adapter.setDropDownViewResource(R.layout.spinner_layout);
        sampleSpinner.setAdapter(adapter);
        sampleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // TODO Auto-generated method stub
                String selectedItem = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String turkceKelime = turkce.getText().toString();
                String ingilizceKelime = ingilizce.getText().toString();
                if (kelimeInputuBosMu(turkceKelime, ingilizceKelime)) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.bos,
                            Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();
                } else {
                    String grupIsmi = sampleSpinner.getSelectedItem().toString();
                    if ( veritabaniIslemleri.kelimeEkle(turkceKelime,ingilizceKelime,grupIsmi,veritabani,getApplicationContext()) ) {
                        turkce.setText("");
                        ingilizce.setText("");
                    }
                }


            }
        });

    }

    public Boolean kelimeInputuBosMu (String turkce, String ingilizce) {
        if (turkce == null || ingilizce == null || turkce.trim().equals("") || ingilizce.trim().equals(""))
            return true;
        else
            return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kelime_ekle, menu);
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
}
