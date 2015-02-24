package com.kadiryaka.myenglishwords.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kadiryaka.myenglishwords.R;
import com.kadiryaka.myenglishwords.entity.Grup;
import com.kadiryaka.myenglishwords.service.Veritabani;
import com.kadiryaka.myenglishwords.service.VeritabaniIslemleri;

import java.util.ArrayList;
import java.util.List;


public class Main_Activity extends Activity {

    List<Grup> grupList;
    VeritabaniIslemleri veritabaniIslemleri;
    Veritabani veritabani;
    private RadioGroup radiogroup;
    final List<String> arrayList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);

        final Button grupEkleButon = (Button) findViewById(R.id.grupekle);
        final Button kelimeEkleButon = (Button) findViewById(R.id.kelimeekle);
        final Button antrenmanButon = (Button) findViewById(R.id.antrenman);
        final CheckBox rastgele = (CheckBox) findViewById(R.id.rastgele);
        final Spinner gruplar = (Spinner) findViewById(R.id.spinnerana);
        final Button sil = (Button) findViewById(R.id.delete);

        radiogroup = (RadioGroup) findViewById(R.id.valueRadioGroup);
        veritabaniIslemleri = new VeritabaniIslemleri();
        veritabani = new Veritabani(this);


        spinnerYukle(gruplar);

        antrenmanButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String grup = String.valueOf(gruplar.getSelectedItem());
                if (!grup.equals("GRUP SEÇ (ŞUAN TÜMÜ SEÇİLİ)")) {
                    if (veritabaniIslemleri.gruptaElemanVarMi(veritabani, grup)) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Seçilen grupta kelime yok",
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                        return;
                    }
                } else {
                    if (veritabaniIslemleri.veritabaniBosMu(veritabani)) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Veritabanı Boş. Önce Grup Ekle",
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                        return;
                    }
                }

                Intent intent = new Intent(Main_Activity.this, AntrenmanActivity.class);
                Boolean isCheck = rastgele.isChecked();
                Integer index = radiogroup.indexOfChild(findViewById(radiogroup.getCheckedRadioButtonId()));
                Bundle extras = new Bundle();
                extras.putString("isCheck", String.valueOf(isCheck));
                extras.putString("grup", grup);
                extras.putString("radioIndex", String.valueOf(index));
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        grupEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Main_Activity.this);
                dialog.setContentView(R.layout.grup_ekle_dialog);
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main_Activity.this);
                final EditText editText=(EditText)dialog.findViewById(R.id.grupedit);
                final Button grupKaydet = (Button)dialog.findViewById(R.id.grupsave);
                final Button grupCik = (Button)dialog.findViewById(R.id.grupcancel);
                final TextView grupmesaj = (TextView)dialog.findViewById(R.id.grupmessage);
                grupCik.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                grupKaydet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editText.getText().toString() == null || editText.getText().toString().trim().equals("")) {
                            grupmesaj.setText("Lütfen Boş girmeyiniz");
                        } else {
                            if (veritabaniIslemleri.grupKayitEkle(editText.getText().toString(), veritabani, Main_Activity.this)) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Grup eklendi",
                                        Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
                                toast.show();
                                spinnerYukle(gruplar);
                                dialog.dismiss();
                            } else {
                                grupmesaj.setText("Grup daha önce eklenmiş");
                            }

                        }
                    }
                });
                dialog.show();

                dialog.setTitle("Grup Ekle");

                dialog.show();

            }
        });



        kelimeEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (veritabaniIslemleri.grupBosMu(veritabani)) {
                    Toast.makeText(getApplicationContext(), "Hiç grup yok, önce grup ekle. Örn: Sıfatlar)",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                //diğer activity'e geçiş için gerekli kodlar
                Intent intent = new Intent(Main_Activity.this, KelimeEkleActivity.class);
                Bundle extras = new Bundle();
                intent.putExtras(extras);
                startActivity(intent);
            }
        });


        sil.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        Main_Activity.this);

                alertDialog2.setTitle("Hoop Tablo gidecek");
                alertDialog2.setMessage("Gerçekten tabloyu silmek istiyor musun");
                alertDialog2.setPositiveButton("HAYIR",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                Toast.makeText(getApplicationContext(),
                                        "SİLMEDİN", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });

                alertDialog2.setNegativeButton("EVET",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    veritabaniIslemleri.tabloyuKompleSil(getApplicationContext());
                                    finish();
                                    startActivity(getIntent());
                                    Toast.makeText(getApplicationContext(),
                                            "GİTTİ TABLO HERŞEY SIFIRLANDI", Toast.LENGTH_SHORT)
                                            .show();
                                } catch (Exception ex) {
                                    Toast.makeText(getApplicationContext(),
                                            "Hata Oluştu", Toast.LENGTH_SHORT)
                                            .show();
                                } finally {
                                    dialog.cancel();
                                }

                            }
                        });
                alertDialog2.show();
            }

        });
    }

    public void spinnerYukle (Spinner gruplar) {
        try {
            grupList = veritabaniIslemleri.gruplariGetir(veritabani);
        } finally {
            veritabani.close();
        }
        arrayList.clear();
        arrayList.add("GRUP SEÇ (ŞUAN TÜMÜ SEÇİLİ)");
        for (Grup item : grupList) {
            arrayList.add(item.getName());
        }
        ArrayAdapter adapter = new ArrayAdapter(this,
                R.layout.spinner_gruplar, arrayList);

        adapter.setDropDownViewResource(R.layout.spinner_grup_tek);
        gruplar.setAdapter(adapter);
        gruplar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
            return super.onOptionsItemSelected(item);
    }
}
