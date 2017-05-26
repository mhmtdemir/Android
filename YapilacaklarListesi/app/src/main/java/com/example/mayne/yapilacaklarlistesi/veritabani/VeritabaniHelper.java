package com.example.mayne.yapilacaklarlistesi.veritabani;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.BaseAdapter;

import com.example.mayne.yapilacaklarlistesi.MainActivity;
import com.example.mayne.yapilacaklarlistesi.list_model.list_adapter;
import com.example.mayne.yapilacaklarlistesi.list_model.listview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mayne on 4.04.2017.
 */

public class VeritabaniHelper extends SQLiteOpenHelper {


    private static final String dbname = "yapilacaklar";
    public String DB_PATH = "/data/data/com.example.mayne.yapilacaklarlistesi/databases/yapilacaklar";
    private static String table_name = "yapilacak";
    private static final int dbversion = 1;
    private static final String row_id = "id";
    private static final String row_baslik = "baslik";
    private static final String row_kategori = "kategori";
    private static final String row_aciklama = "aciklama";
    private static final String row_tarih = "tarih";
    private static final String row_saat = "saat";
    private static final String row_durum = "durum";
    private Context mContext;
    public list_adapter adapter2;
    private SQLiteDatabase sqLiteDatabase;

    public VeritabaniHelper(Context contex) {
        super(contex, dbname, null, dbversion);
        mContext = contex;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table yapilacak (id integer primary key,baslik text,aciklama text,kategori text,tarih text,saat text,durum integer default 0,hatirlatma text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists yapilacak");
        onCreate(db);
    }

    public SQLiteDatabase getDatabase() {

        DB_PATH = mContext.getDatabasePath(VeritabaniHelper.dbname).getPath();
        String myPath = DB_PATH + dbname;

        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        return sqLiteDatabase;
    }

                        /***GÖREV EKLEME İŞLEMİ ****/
    public void veriekle(String baslik, String kategori, String aciklama, String tarih, String saat, String hatirlatma) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("baslik", baslik.trim());
        cv.put("kategori", kategori.trim());
        cv.put("aciklama", aciklama.trim());
        cv.put("tarih", tarih.trim());
        cv.put("saat", saat.trim());
        cv.put("hatirlatma", hatirlatma.trim());
        db.insert("yapilacak", null, cv);
        db.close();


    }

    /********YAPILACAK OLAN GÖREVLER LİSTELENIYOR******/
    public ArrayList<listview> verilistele() {

        ArrayList<listview> veriler = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String aranan = "durum";
        String[] durum_bilgisi = {"0"};
        String[] sutunlar = {"id", "baslik", "kategori", "aciklama", "tarih", "saat", "durum", "hatirlatma"};
        Cursor cursor = db.query("yapilacak", sutunlar, aranan + "=?", durum_bilgisi, null, null, null);
        while (cursor.moveToNext()) {
            veriler.add(new listview(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), false, cursor.getString(7)));
        }
        db.close();
        return veriler;
    }

            /************YAPILAN GÖREVLER LİSTELENECEK *///////////////////////
    public ArrayList<listview> yapildi_listele() {
        ArrayList<listview> yapilanlar = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String aranan = "durum";
        String[] durum_bilgisi = {"1"};
        String[] sutunlar = {"id", "baslik", "kategori", "aciklama", "tarih", "saat", "durum", "hatirlatma"};
        Cursor cursor = db.query("yapilacak", sutunlar, aranan + "=?", durum_bilgisi, null, null, null);
        while (cursor.moveToNext()) {

            yapilanlar.add(new listview(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), true, cursor.getString(7)));

        }
        db.close();
        return yapilanlar;
    }

    //************ CHECKBOX TIKLANDIGINDA İŞLEMİN YAPILDIGINI VERITABANINA KAYDEDIYORUZ */////////////
    public void durum_guncelle(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put("durum", "1");
        db.update("yapilacak", values, row_id + "=?", new String[]{String.valueOf(id)});
        db.close();

    }

    ///*******POPUP MENU --->DUZENLE *//////////
    public void liste_guncelle(String id,String baslik,String kategori,String aciklama,String tarih,String saat,String hatirlatma){

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("baslik",baslik.trim());
        values.put("kategori",kategori.trim());
        values.put("aciklama",aciklama.trim());
        values.put("tarih",tarih.trim());
        values.put("saat",saat.trim());
        values.put("hatirlatma",hatirlatma.trim());
        values.put("durum","0");
        db.update("yapilacak",values,row_id + "=?",new String[]{String.valueOf(id)});
        db.close();


    }

    ////**********KATEGORİ SPİNNERİ İÇİN YAZILDI******************///////////
    public void kategori_getir(List<String> kategoriList) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] sutun = {"kategori"};
        Cursor cursor = db.query(true,"yapilacak", sutun, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                kategoriList.add(cursor.getString(0));

            }
        } else {

        }

        db.close();

    }

            /*********TOOLBAR ---->>>>  TARİHE GORE LİSTELE ----->>>>>*/////////////////////
    public ArrayList<listview> tarih_getir(String aranan_tarih) {



        ArrayList<listview> yapilanlar = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] ara = {aranan_tarih};

        String[] sutunlar = {"id", "baslik", "kategori", "aciklama", "tarih", "saat", "durum", "hatirlatma"};
        Cursor cursor = db.query("yapilacak", sutunlar, "tarih=?", ara, null, null, null,null);



        if (cursor.getCount() > 0) {   ///Yapılan gorevler ıcın true yapılmayanlar ıcın false gonderıyoruz
            while (cursor.moveToNext()) {

                if (cursor.getString(6).matches("1")) {

                    yapilanlar.add(new listview(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), true, cursor.getString(7)));

                } else
                    yapilanlar.add(new listview(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), false, cursor.getString(7)));

            }
        }
            else { }

        db.close();

            return yapilanlar;
    }

        /**********VERİ SİL ---->>>>POPUP SİL*/

public void sil(String id){
    SQLiteDatabase db=getReadableDatabase();
    String[] silinecek={id};
    db.delete("yapilacak","id=?",silinecek);
    db.close();
}

}
