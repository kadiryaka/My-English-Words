package com.kadiryaka.myenglishwords.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Random;

import com.kadiryaka.myenglishwords.R;
import com.kadiryaka.myenglishwords.entity.Kelime;
import com.kadiryaka.myenglishwords.service.Veritabani;
import com.kadiryaka.myenglishwords.service.VeritabaniIslemleri;


public class AntrenmanActivity extends Activity {

    VeritabaniIslemleri veritabaniIslemleri;
    Veritabani veritabani;
    List<Kelime> kelimeList;
    private int count;
    private int countRandom;
    String index = null;
    Boolean rastgele = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antrenman);

        Button ileri = (Button) findViewById(R.id.ileributton);
        Button sonuc = (Button) findViewById(R.id.sonucbutton);
        final TextView birinciKelime = (TextView) findViewById(R.id.ilkkelime);
        final TextView ikinciKelime = (TextView) findViewById(R.id.ikincikelime);

        String grup = null;

        count = 0;
        countRandom = 0;
        veritabaniIslemleri = new VeritabaniIslemleri();
        veritabani = new Veritabani(getApplicationContext());

        //Main Activity'den gelen veriler alınıyor
        Bundle bundle = new  Bundle();
        bundle = getIntent().getExtras();

        //random olup olmayacağının kontrolü yapılıyor
        if (bundle.getString("isCheck").equals("true")) {
            rastgele = true;
        } else {
            rastgele = false;
        }
        //grup bilgisi getiriliyor
        grup = bundle.getString("grup");
        if (grup.equals("GRUP SEÇ (ŞUAN TÜMÜ SEÇİLİ)")) {
            kelimeList = veritabaniIslemleri.kelimeGetirTumunu(veritabani);
        } else {
            kelimeList = veritabaniIslemleri.kelimeGetirGrubaGore(veritabani,grup);
        }
        //ingilizce-türkçe bilgisi alınıyor
        index = bundle.getString("radioIndex");
        if (rastgele)
            count = randomSayiUret(kelimeList.size());
        if (index.equals("0")) {
            birinciKelime.setText(kelimeList.get(count).getIngilizce());
        } else {
            birinciKelime.setText(kelimeList.get(count).getTurkce());
        }

        //çevir butonu
        sonuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (index.equals("0")) {
                        birinciKelime.setText(kelimeList.get(count).getIngilizce());
                        ikinciKelime.setText(kelimeList.get(count).getTurkce());
                    } else {
                        birinciKelime.setText(kelimeList.get(count).getTurkce());
                        ikinciKelime.setText(kelimeList.get(count).getIngilizce());
                    }
                } catch (Exception ex) {
                    birinciKelime.setText("");
                    Toast.makeText(getApplicationContext(), "Muhtemelen Kelime Bitti Hafız yada bi hata oluştu, yeniden aç",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        ileri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rastgele)
                    count = randomSayiUret(kelimeList.size());
                else
                    count++;
                ikinciKelime.setText("");
                try {
                    if (index.equals("0")) {
                        birinciKelime.setText(kelimeList.get(count).getIngilizce());
                    } else {
                        birinciKelime.setText(kelimeList.get(count).getTurkce());
                    }
                } catch (Exception ex) {
                    birinciKelime.setText("");
                    Toast.makeText(getApplicationContext(), "Muhtemelen Kelime Bitti Hafız yada bi hata oluştu, yeniden aç",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public Integer randomSayiUret (Integer diziElemanSayisi) {
        Random rnd = new Random();
        return rnd.nextInt(diziElemanSayisi);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_antrenman, menu);
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
