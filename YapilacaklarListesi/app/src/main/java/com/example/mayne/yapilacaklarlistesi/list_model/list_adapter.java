package com.example.mayne.yapilacaklarlistesi.list_model;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayne.yapilacaklarlistesi.EkleActivity;
import com.example.mayne.yapilacaklarlistesi.MainActivity;
import com.example.mayne.yapilacaklarlistesi.R;
import com.example.mayne.yapilacaklarlistesi.veritabani.VeritabaniHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by mayne on 3.04.2017.
 */

public class list_adapter extends BaseAdapter implements AdapterView.OnItemSelectedListener,TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {
public ArrayList<listview> yapilacakListe;
    Activity activity;
    public EditText etKategori2;
    public String secilenKategori="";
    public String baslamaZamani="";
    public String baslamaSaati="";
    public Button btnSearch, btnCancel;
    MainActivity mainActivity;
    public VeritabaniHelper veritabani;

    public list_adapter(Activity activity,ArrayList<listview> yapilacakListe){
        super();
        veritabani = new VeritabaniHelper(activity);
        this.activity=activity;
        this.yapilacakListe=yapilacakListe;

    }

    @Override
    public int getCount() {
        return yapilacakListe.size();
    }

    @Override
    public Object getItem(int position) {
        return yapilacakListe.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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

    public class Listeİcerik  {
        TextView baslik;
        TextView kategori;
        TextView tarih;
        TextView saat;
        TextView aciklama;
        TextView id;
        CheckBox durum;
        ImageView hatirlatma;
    }






    @Override
    public View getView(final int position, View view, final ViewGroup viewGroup) {

          final list_adapter.Listeİcerik liste;
        LayoutInflater inf=activity.getLayoutInflater();
        if(view==null)
        {
            view=inf.inflate(R.layout.list_satir,null);
            liste=new list_adapter.Listeİcerik();
            liste.aciklama=(TextView)view.findViewById(R.id.aciklamatext);
            liste.baslik=(TextView)view.findViewById(R.id.tvBaslik);
            liste.kategori=(TextView)view.findViewById(R.id.kategori);
            liste.saat=(TextView)view.findViewById(R.id.tvTime);
            liste.tarih=(TextView)view.findViewById(R.id.tvTarih);
            liste.durum=(CheckBox)view.findViewById(R.id.durum);
            liste.id=(TextView)view.findViewById(R.id.iddeger);
            liste.hatirlatma=(ImageView)view.findViewById(R.id.alarm);
            //alarm=(Button)view.findViewById(R.id.alarm);
            view.setTag(liste);
        }
        else {
            liste = (Listeİcerik) view.getTag();
        }

       final listview liste2=yapilacakListe.get(position);
        liste.aciklama.setText(liste2.getAciklama());
        liste.kategori.setText(liste2.getKategori());
        liste.baslik.setText(liste2.getBaslik());
        liste.tarih.setText(liste2.getTarih());
        liste.saat.setText(liste2.getSaat());
        liste.id.setText(liste2.getId());

       // liste.durum.isChecked();
/** HATIRLATMA BILGISININ VARLIGINA GORE LISTVIEW DEKI HATIRLATMA RESMİNİN DEĞİŞİKLİĞİ**/
        if (liste2.getHatirlatma().isEmpty())
        {
            liste.hatirlatma.setImageResource(R.drawable.alarm1);

        }
        else
        {
            liste.hatirlatma.setImageResource(R.drawable.alarmok);
        }


        /****VERİTABANINDAN GELEN YAPILDI BILGISINE GORE CHECKBOX BUTONUN GORUNUMU VE BAZI BILGILERIN UZERININ CIZILMESI s*/////
        if(liste2.isDurum())
        {
            liste.durum.setChecked(true);
            liste.durum.setClickable(false);
           // liste.durum.setPressed(true);
            liste.baslik.setPaintFlags(liste.baslik.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            liste.kategori.setPaintFlags(liste.baslik.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            liste.tarih.setPaintFlags(liste.baslik.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            liste.saat.setPaintFlags(liste.baslik.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);


        }
        else {
              liste.durum.setChecked(false);
              liste.durum.setClickable(true);

        }

                        // CHECKBOX KONTROLÜ  CHECKBOX TIKLANDIGINDA AKTİFFF//////////
        liste.durum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!liste2.isDurum()){

                    Button ok,buton2;
                    final Dialog dialog = new Dialog(activity); // Context, this, etc.
                    dialog.setContentView(R.layout.dialogdemo);
                    ok=(Button)dialog.findViewById(R.id.dialog_ok);
                    buton2=(Button)dialog.findViewById(R.id.dialog_cancel);
                    dialog.setTitle(R.string.dialog_title);
                    dialog.show();
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                             yapilacakListe.remove(position);
                             notifyDataSetChanged();
                             veritabani.durum_guncelle(String.valueOf(liste.id.getText()));
                             liste.durum.setClickable(false);
                             liste.durum.setChecked(true);
                             dialog.dismiss();

                        }
                    });

                    buton2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            liste.durum.setChecked(false);
                            dialog.dismiss();
                        }
                    });

                }
                else{
                   // liste.durum.setClickable(false);

                }


            }

        });

              /***********LİSTEYE  TIKLAMA OLAYI --->>>>POPUP MENU İŞLEMLERİ */
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(view.getContext(),view,R.style.PopupMenu );
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        {
                            switch (item.getItemId()){
                                case R.id.düzenle:


                                    dialog_getir(liste.id.getText().toString(),liste.baslik.getText().toString(),liste.kategori.getText().toString(),liste.aciklama.getText().toString(),liste.tarih.getText().toString(),liste.saat.getText().toString(),liste2.getHatirlatma());

                                     //   Intent intent=new Intent(activity,EkleActivity.class);
                                     //  intent.putExtra("baslik",liste.baslik.getText());
                                     //  intent .putExtra("aciklama",liste.aciklama.getText());
                                     // activity.startActivity(intent);

                                    return true;
                                case R.id.sil:
                                    yapilacakListe.remove(position);
                                    notifyDataSetChanged();
                                    veritabani.sil(String.valueOf(liste.id.getText()));
                                   // Toast.makeText(activity, "Message ", Toast.LENGTH_SHORT).show();
                                    return true;
                            }
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });



        return view;
    }

   public void dialog_getir(final String alinan_id,String baslik, final String kategori,final String aciklama, String tarih, String saat, String hatirlatma)
   {
       final Spinner kategori2;
       final EditText baslik2;
      final EditText aciklama2;
       final Button tarih2;
       final  Button saat2;
       final Button hatirlatma2;
       Button onay2,iptal2;
       Button ekle2;
       Toolbar tool;
      // int mScreenWidth = activity.getWindowManager().getDefaultDisplay().getWidth();
       //int mScreenHeight = activity.getWindowManager().getDefaultDisplay().getHeight();
       secilenKategori=kategori;
       final List<String> kategoriliste2=new ArrayList<String>();
       final Dialog dialog=new Dialog(activity,R.style.AppTheme);
       //dialog.setTitle("Görev Düzenle");
       View view = activity.getLayoutInflater().inflate(R.layout.fragment1, null);
       dialog.setContentView(view);
       dialog.show();
       tool=(Toolbar)dialog.findViewById(R.id.edittool);
       tool.setTitle("\tGörev Düzenle");
       tool.setLogo(R.drawable.edit);
       kategori2=(Spinner)dialog.findViewById(R.id.spinkategori);
       baslik2=(EditText)dialog.findViewById(R.id.basliktext);
       aciklama2=(EditText)dialog.findViewById(R.id.aciklama);
       tarih2=(Button)dialog.findViewById(R.id.tarih);
       saat2=(Button)dialog.findViewById(R.id.saat);
       onay2=(Button)dialog.findViewById(R.id.onayla);
       ekle2=(Button)dialog.findViewById(R.id.eklebuton);
       hatirlatma2=(Button)dialog.findViewById(R.id.alarmekle);
       iptal2=(Button)dialog.findViewById(R.id.iptalet);

       /***** LİSTVİEW ITEMLERİ ALIYORUZ *******/
       baslik2.setText(baslik);
       aciklama2.setText(aciklama);
       tarih2.setText(tarih);
       saat2.setText(saat);
       hatirlatma2.setText(hatirlatma);

       /****kategori için spinner işlemleri*///
       kategoriliste2.add(kategori);
       kategori2.setOnItemSelectedListener(this);
       veritabani.kategori_getir(kategoriliste2);
       final ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(activity,android.R.layout.simple_spinner_dropdown_item,kategoriliste2);
       kategori2.setAdapter(dataAdapter);
       /*****************************************************/

       ekle2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               final Dialog dialog2 = new Dialog(activity, android.R.style.Theme_Holo_InputMethod);
               dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);

               dialog2.setCancelable(true);
               dialog2.setContentView(R.layout.dialog);

               etKategori2 = (EditText) dialog2.findViewById(R.id.etsearch);
               btnSearch = (Button) dialog2.findViewById(R.id.btnsearch);
               btnCancel = (Button) dialog2.findViewById(R.id.btncancel);

               dialog2.show();

               btnCancel.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       dialog2.dismiss();
                   }
               });
               btnSearch.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       //Kategoriyi listeye önce ekliyorum Daha sonra veritabanına eklıyorum
                       kategoriliste2.add(etKategori2.getText().toString());
                       dialog2.dismiss();

                   }
               });

           }


       });



       tarih2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Calendar now=Calendar.getInstance();
               final DatePickerDialog datePickerDialog=DatePickerDialog.newInstance(mainActivity,
                       now.get(Calendar.YEAR),
                       now.get(Calendar.MONTH),
                       now.get(Calendar.DAY_OF_MONTH)
               );

             //  datePickerDialog.setMinDate(Calendar.getInstance());
               datePickerDialog.setThemeDark(false); //set dark them for dialog?
               datePickerDialog.vibrate(true); //vibrate on choosing date?
               datePickerDialog.dismissOnPause(true); //dismiss dialog when onPause() called?
               datePickerDialog.showYearPickerFirst(false); //choose year first?
               datePickerDialog.setAccentColor(Color.parseColor("#2E8B57"));
               datePickerDialog.setTitle("Tarih Seç"); //dialog title
               datePickerDialog.show(activity.getFragmentManager(), "Tarih Seç "); //show dialog
               datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                       baslamaZamani = dayOfMonth + "/" + (++monthOfYear) + "/" + year;

                       tarih2.setText(baslamaZamani);
                   }
               });


           }
       });

       /******************************SAAT AYARLAMASI İÇİN *////////////////////////
       saat2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Calendar now=Calendar.getInstance();
               TimePickerDialog timePickerDialog=TimePickerDialog.newInstance(mainActivity,
                       now.get(Calendar.HOUR_OF_DAY),
                       now.get(Calendar.MINUTE),
                       true
               );

               timePickerDialog.setThemeDark(false); //Dark Theme?
               timePickerDialog.vibrate(true); //vibrate on choosing time?
               timePickerDialog.dismissOnPause(false); //dismiss the dialog onPause() called?
               timePickerDialog.enableSeconds(false); //show seconds?
               timePickerDialog.setTitle("Başlangıç Zamanı");
               timePickerDialog.setAccentColor(Color.parseColor("#2E8B57"));
               timePickerDialog.show(activity.getFragmentManager(), "Saat Sec "); //show dialog
               timePickerDialog.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                   @Override
                   public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                       String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                       //Saat dakika 10 dan kucukse basına 0 ekliyorum.
                       String minuteString = minute < 10 ? "0" + minute : "" + minute;
                       baslamaSaati = hourString + ":" + minuteString ;
                       // timetext2.setText("\n"+time);
                       saat2.setText(baslamaSaati);
                   }
               });

           }
       });

       /////*****************HATIRLATMA AYARLARINI DUZELETMEK İÇİN ************////////////////////
       hatirlatma2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               AlertDialog.Builder builder = new AlertDialog.Builder(activity);
               builder.setTitle("Alarm Kur");
               builder.setMessage("Alarm Kurmak İstiyormusunuz?!!!");
               builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener(){
                   public void onClick(DialogInterface dialog, int id) {

                       hatirlatma2.setText("");

                   }
               });


               builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                                  Calendar now=Calendar.getInstance();
                       TimePickerDialog timePickerDialog=TimePickerDialog.newInstance(mainActivity,
                               now.get(Calendar.HOUR_OF_DAY),
                               now.get(Calendar.MINUTE),
                               true
                       );

                       timePickerDialog.setThemeDark(false); //Dark Theme?
                       timePickerDialog.vibrate(true); //vibrate on choosing time?
                       timePickerDialog.dismissOnPause(false); //dismiss the dialog onPause() called?
                       timePickerDialog.enableSeconds(false); //show seconds?
                       timePickerDialog.setTitle("Hatirlatma Zamanı");
                       timePickerDialog.setAccentColor(Color.parseColor("#2E8B57"));
                       timePickerDialog.show(activity.getFragmentManager(), "Saat Sec "); //show dialog
                       timePickerDialog.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                           @Override
                           public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                               String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                               //Saat dakika 10 dan kucukse basına 0 ekliyorum.
                               String minuteString = minute < 10 ? "0" + minute : "" + minute;
                               baslamaSaati = hourString + ":" + minuteString ;
                               // timetext2.setText("\n"+time);
                               hatirlatma2.setText(baslamaSaati);
                           }

                       });

                   }
               });


               builder.show();

           }
       });



       ///////***********ONAYLA BUTONU İŞLEMLERİ ********/////////////////////////////
       onay2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               /*************TUM HERSEY TAMAMSA EKLEME GERCEKLESTIRLECEK**********///

                   veritabani.liste_guncelle(alinan_id,baslik2.getText().toString(),secilenKategori, aciklama2.getText().toString(), tarih2.getText().toString(), saat2.getText().toString(),hatirlatma2.getText().toString());
                   Toast.makeText(activity, "Kategori:" + secilenKategori
                                   + "\nBaslik  " + baslik2.getText()
                                   + "\naciklama  " + aciklama2.getText()
                                   + "\ntarih  " + tarih2.getText()
                                   + "\nsaat  :" + saat2.getText()
                                   + "\nHatirlatma  :" + hatirlatma2.getText()
                           , Toast.LENGTH_LONG).show();

                      //dialog.onBackPressed();
                   Intent intent = new Intent(activity, MainActivity.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   activity.startActivity(intent);
                   dialog.dismiss();

           }
       });

       iptal2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               dialog.dismiss();

           }
       });

   }
}
