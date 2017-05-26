package com.example.mayne.yapilacaklarlistesi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;


import com.example.mayne.yapilacaklarlistesi.list_model.list_adapter;
import com.example.mayne.yapilacaklarlistesi.list_model.listview;
import com.example.mayne.yapilacaklarlistesi.veritabani.VeritabaniHelper;
import com.shamanland.fab.ShowHideOnScroll;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {
    public ArrayList<listview> yapilacakliste;
    FloatingActionButton fab;
    public VeritabaniHelper veritabani;
    public static list_adapter mylistadapter;
    public ListView Liste;
    String aranan_tarih="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        veritabani = new VeritabaniHelper(this);
        yapilacakliste= new ArrayList<listview>();
        yapilacakliste=veritabani.verilistele();


         Liste=(ListView)findViewById(R.id.yapilacaklist);
         final list_adapter adapter=new list_adapter(this,yapilacakliste);
         Liste.setAdapter(adapter);
         adapter.notifyDataSetChanged();

        //registerForContextMenu(Liste);

         fab=(FloatingActionButton)findViewById(R.id.eklefab);
         Liste.setOnTouchListener(new ShowHideOnScroll(fab, R.anim.push_left_in, R.anim.push_left_out));

         fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(MainActivity.this,EkleActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
      //  finish();

    }
});


        final android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
       // toolbar.setLogo(R.drawable.ic_launcher);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    // action with ID action_refresh was selected
                    case R.id.item1:
                        Calendar now=Calendar.getInstance();
                        final DatePickerDialog datePickerDialog=DatePickerDialog.newInstance(MainActivity.this,
                                now.get(Calendar.YEAR),
                                now.get(Calendar.MONTH),
                                now.get(Calendar.DAY_OF_MONTH)
                        );

                        //datePickerDialog.setMinDate(Calendar.getInstance());
                        datePickerDialog.setThemeDark(false); //set dark them for dialog?
                        datePickerDialog.vibrate(true); //vibrate on choosing date?
                        datePickerDialog.dismissOnPause(true); //dismiss dialog when onPause() called?
                        datePickerDialog.showYearPickerFirst(false); //choose year first?
                        datePickerDialog.setAccentColor(Color.parseColor("#6A5ACD")); // custom accent color
                        datePickerDialog.setTitle("Tarih Seç"); //dialog title
                        datePickerDialog.show(getFragmentManager(), "Tarih Seç "); //show dialog
                        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                                aranan_tarih = dayOfMonth + "/" + (++monthOfYear) + "/" + year;
                                toolbar.setTitle(aranan_tarih);
                                yapilacakliste=new ArrayList<listview>();
                                yapilacakliste=veritabani.tarih_getir(aranan_tarih);
                                mylistadapter=new list_adapter(MainActivity.this,yapilacakliste);
                                Liste.setAdapter(mylistadapter);
                                mylistadapter.notifyDataSetChanged();

                            }
                        });

                        /*Toast.makeText(getApplicationContext(), "asdsadasd", Toast.LENGTH_SHORT)
                                .show();*/
                        break;
                    // action with ID action_settings was selected
                    case R.id.item2:
                        //yapilanlar butonu tıklanınca bu liste yüklenecek
                        yapilacakliste=new ArrayList<listview>();
                        yapilacakliste=veritabani.yapildi_listele();
                        mylistadapter=new list_adapter(MainActivity.this,yapilacakliste);
                        Liste.setAdapter(mylistadapter);
                        mylistadapter.notifyDataSetChanged();
                        toolbar.setTitle("Yapilanlar");
                        toolbar.setTitleMargin(5,0,0,0);
                    //    mylistadapter.durum_degistir(MainActivity.this);
                        break;
                    case R.id.item3:
                        yapilacakliste= new ArrayList<listview>();
                        yapilacakliste=veritabani.verilistele();
                        Liste=(ListView)findViewById(R.id.yapilacaklist);
                        final list_adapter adapter=new list_adapter(MainActivity.this,yapilacakliste);
                        Liste.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        toolbar.setTitle("Yapilacaklar");
                        break;

                    default:
                        break;
                }

                return true;
            }       });



    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

    }
}
