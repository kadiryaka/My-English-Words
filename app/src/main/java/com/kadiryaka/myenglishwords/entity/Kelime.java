package com.kadiryaka.myenglishwords.entity;

/**
 * Created by kadiryaka on 11.01.15.
 */
public class Kelime {
    private Integer id;
    private String turkce;
    private String ingilizce;
    private String grupAdi;

    public Kelime (Integer id, String turkce, String ingilizce, String grupAdi) {
        super();
        this.id = id;
        this.turkce = turkce;
        this.ingilizce = ingilizce;
        this.grupAdi = grupAdi;
    }

    public Kelime () {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTurkce() {
        return turkce;
    }

    public void setTurkce(String turkce) {
        this.turkce = turkce;
    }

    public String getGrupAdi() {
        return grupAdi;
    }

    public void setGrupAdi(String grupAdi) {
        this.grupAdi = grupAdi;
    }

    public String getIngilizce() {
        return ingilizce;
    }

    public void setIngilizce(String ingilizce) {
        this.ingilizce = ingilizce;
    }
}
