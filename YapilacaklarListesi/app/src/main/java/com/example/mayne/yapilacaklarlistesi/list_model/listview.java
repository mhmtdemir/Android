package com.example.mayne.yapilacaklarlistesi.list_model;


/**
 * Created by mayne on 3.04.2017.
 */

public class listview {
    private String baslik;
    private String aciklama;
    private String tarih;
    private String saat;
    private String kategori;
    private String id;
    private String hatirlatma;
    private boolean durum;

    public listview(String id,String baslik, String kategori, String aciklama, String tarih, String saat,boolean durum,String hatirlatma) {
        this.baslik = baslik;
        this.aciklama = aciklama;
        this.tarih = tarih;
        this.saat = saat;
        this.kategori = kategori;
        this.id = id;     //tıklanan itemin veritabanındaki id degerini almak icin
        this.durum=durum;
        this.hatirlatma=hatirlatma;
    }
    public String getBaslik(){return baslik;}
    public String getAciklama(){return aciklama;}
    public String getTarih(){return tarih;}
    public String getSaat(){return saat;}
    public String getKategori(){return kategori;}
    public String getId(){return id;} //VERITABANINDAN GELEN ID BILGISI ICIN
   // public void setDurum(boolean durum){this.durum=durum; }
    public String getHatirlatma(){return hatirlatma;} //HATIRLATMA BILGISINI ALMAK İÇİN
    public boolean isDurum(){return durum;} /// YAPILDI YAPILMADI BILGISI İÇİN


}