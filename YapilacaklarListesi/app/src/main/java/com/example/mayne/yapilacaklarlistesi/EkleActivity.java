package com.example.mayne.yapilacaklarlistesi;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mayne.yapilacaklarlistesi.list_model.list_adapter;
import com.example.mayne.yapilacaklarlistesi.veritabani.VeritabaniHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EkleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {
 Spinner kategori;
    EditText baslik;
    EditText aciklama;
    Button tarih;
    Button saat;
    Button onay;
    Button ekle;
    Dialog dialog;
    Button iptalet;
    Button hatirlatma;
    EditText etKategori;
    Button btnSearch, btnCancel;
    public String secilenKategori="";
    public String baslamaZamani="";
    public String baslamaSaati="";
    VeritabaniHelper veritabani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekle);
        veritabani = new VeritabaniHelper(this);

        ////****Material Tanımlamaları/************///////////
        kategori=(Spinner)findViewById(R.id.spinkategori);
        baslik=(EditText)findViewById(R.id.basliktext);
        aciklama=(EditText)findViewById(R.id.aciklama);
        tarih=(Button)findViewById(R.id.tarih);
        saat=(Button)findViewById(R.id.saat);
        onay=(Button)findViewById(R.id.onayla);
        ekle=(Button)findViewById(R.id.eklebuton);
        hatirlatma=(Button)findViewById(R.id.alarmekle);
        iptalet=(Button)findViewById(R.id.iptalet);

//         Toast.makeText(getApplicationContext(),getIntent().getExtras().getString("baslik"),Toast.LENGTH_SHORT).show();







        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_actionbar);
      //  toolbar.setLogo();
        toolbar.setTitle("Görev Ekle");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        ///*******SPİNNER İŞLEMLERİ*****///////
        kategori.setOnItemSelectedListener(this);
        final List<String> kategoriliste=new ArrayList<String>();
        kategoriliste.add("Kategoriler");
        veritabani.kategori_getir(kategoriliste);

        final ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,kategoriliste);
        kategori.setAdapter(dataAdapter);
        ///**************************************//////


        ////*********DATE PİCKER DİALOG***********//////////

        tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now=Calendar.getInstance();
                final DatePickerDialog datePickerDialog=DatePickerDialog.newInstance(EkleActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                        );

                datePickerDialog.setMinDate(Calendar.getInstance());
                datePickerDialog.setAccentColor(Color.parseColor("#2E8B57"));
                datePickerDialog.setThemeDark(false); //set dark them for dialog?
                datePickerDialog.vibrate(true); //vibrate on choosing date?
                datePickerDialog.dismissOnPause(true); //dismiss dialog when onPause() called?
                datePickerDialog.showYearPickerFirst(false); //choose year first?
              //  datePickerDialog.setAccentColor(Color.parseColor("#9C27A0")); // custom accent color
                datePickerDialog.setTitle("Tarih Seç"); //dialog title
                datePickerDialog.show(getFragmentManager(), "Tarih Seç "); //show dialog
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        baslamaZamani = dayOfMonth + "/" + (++monthOfYear) + "/" + year;

                        tarih.setText(baslamaZamani);
                    }
                });


            }
        });

/////////////****************TİMEPİCKER İŞLEMLERİ************/////////////////////////
        saat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now=Calendar.getInstance();
                TimePickerDialog timePickerDialog=TimePickerDialog.newInstance(EkleActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true
                );
                timePickerDialog.setAccentColor(Color.parseColor("#2E8B57"));
                timePickerDialog.setThemeDark(false); //Dark Theme?
                timePickerDialog.vibrate(true); //vibrate on choosing time?
                timePickerDialog.dismissOnPause(false); //dismiss the dialog onPause() called?
                timePickerDialog.enableSeconds(false); //show seconds?
                timePickerDialog.setTitle("Başlangıç Zamanı");
                timePickerDialog.show(getFragmentManager(), "Saat Sec "); //show dialog
                timePickerDialog.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                        //Saat dakika 10 dan kucukse basına 0 ekliyorum.
                        String minuteString = minute < 10 ? "0" + minute : "" + minute;
                        baslamaSaati = hourString + ":" + minuteString ;
                        // timetext2.setText("\n"+time);
                        saat.setText(baslamaSaati);
                    }
                });

            }
        });

        ///////***********ONAYLA BUTONU İŞLEMLERİ ********/////////////////////////////
        onay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /***BOS OLUP OLMADIKLARINN KONTROLU **/

                if(TextUtils.isEmpty(baslik.getText())&&TextUtils.isEmpty(aciklama.getText())){
                    // void sonuc;
                   // sonuc=(TextUtils.isEmpty(baslik.getText())) ? baslik.setError("alanı doldur"): baslik.setError("alanı doldur");
                    baslik.setError("baslik girin");
                    aciklama.setError("aciklama girin");
                    return;
                }
                else if(secilenKategori.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Geçerli Bir Kategori Giriniz",Toast.LENGTH_SHORT).show();

                }
                else if(TextUtils.isEmpty(baslik.getText())){
                    baslik.setError("baslik girin");

                }
                else if(TextUtils.isEmpty(aciklama.getText())){
                    aciklama.setError("alan boş olamaz");
                }
                else if(TextUtils.isEmpty(tarih.getText())&&TextUtils.isEmpty(saat.getText())){
                    tarih.setError("Tarih Seçin");
                    saat.setError("Saat seçin");
                }
                else if(TextUtils.isEmpty(tarih.getText())){
                    tarih.setError("Tarih Seçin");
                }
                else if(TextUtils.isEmpty(saat.getText())){
                    saat.setError("Saat seçin");
                }
                            /*************TUM HERSEY TAMAMSA EKLEME GERCEKLESTIRLECEK**********///
                else {
                    veritabani.veriekle(baslik.getText().toString(), secilenKategori, aciklama.getText().toString(), tarih.getText().toString(), saat.getText().toString(),hatirlatma.getText().toString());
                    Toast.makeText(getApplicationContext(), "Kategori:" + secilenKategori
                                    + "\nBaslik  " + baslik.getText()
                                    + "\naciklama  " + aciklama.getText()
                                    + "\ntarih  " + tarih.getText()
                                    + "\nsaat  :" + saat.getText()
                                    + "\nHatirlatma  :" + hatirlatma.getText()
                            , Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EkleActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }

            }
        });

        ///////*************KATEGORİ EKLE BUTONU*//////////////////////////////////
    ekle.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        dialog = new Dialog(EkleActivity.this, android.R.style.Theme_Holo_InputMethod);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);

        etKategori = (EditText) dialog.findViewById(R.id.etsearch);
        btnSearch = (Button) dialog.findViewById(R.id.btnsearch);
        btnCancel = (Button) dialog.findViewById(R.id.btncancel);

        dialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        dialog.dismiss();
    }
});
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //Kategoriyi listeye önce ekliyorum Daha sonra veritabanına eklıyorum
                kategoriliste.add(etKategori.getText().toString());
                dialog.dismiss();

            }
        });

    }


});


hatirlatma.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Calendar now=Calendar.getInstance();
        TimePickerDialog timePickerDialog=TimePickerDialog.newInstance(EkleActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );

        timePickerDialog.setThemeDark(false); //Dark Theme?
        timePickerDialog.vibrate(true); //vibrate on choosing time?
        timePickerDialog.dismissOnPause(false); //dismiss the dialog onPause() called?
        timePickerDialog.enableSeconds(false); //show seconds?
        timePickerDialog.setTitle("Hatirlatma Zamanı");
        timePickerDialog.show(getFragmentManager(), "Saat Sec "); //show dialog
        timePickerDialog.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                //Saat dakika 10 dan kucukse basına 0 ekliyorum.
                String minuteString = minute < 10 ? "0" + minute : "" + minute;
                baslamaSaati = hourString + ":" + minuteString ;
                // timetext2.setText("\n"+time);
                hatirlatma.setText(baslamaSaati);
            }

        });


    }
});

        iptalet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                /*Intent intent = new Intent(EkleActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();*/
            }
        });

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        if(position>0) {
            secilenKategori = adapterView.getItemAtPosition(position).toString();
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
