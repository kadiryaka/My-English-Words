package com.kadiryaka.myenglishwords.entity;

/**
 * Created by kadiryaka on 10.01.15.
 */
public class Grup {

    private Integer id;
    private String name;
    private Integer kelimeSayisi;

    public Grup () {

    }

    public Grup (Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Grup (Integer id, String name, Integer kelimeSayisi) {
        this.id = id;
        this.name = name;
        this.kelimeSayisi = kelimeSayisi;
    }

    public Grup (String name, Integer kelimeSayisi) {
        this.name = name;
        this.kelimeSayisi = kelimeSayisi;
    }

    public Grup (String name) {
        this.name = name;
    }

    public Integer getKelimeSayisi() {
        return kelimeSayisi;
    }

    public void setKelimeSayisi(Integer kelimeSayisi) {
        this.kelimeSayisi = kelimeSayisi;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
