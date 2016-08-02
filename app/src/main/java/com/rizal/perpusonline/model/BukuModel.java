package com.rizal.perpusonline.model;

/**
 * Created by Rizal Y on 7/27/2016.
 */
public class BukuModel {
    private String kdBuku;
    private String judulBuku;
    private String pengarang;
    private String penerbit;
    private int harga;

    public BukuModel() {

    }

    public BukuModel(String kdBuku, String judulBuku, String pengarang, String penerbit, int harga) {
        this.kdBuku = kdBuku;
        this.judulBuku = judulBuku;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.harga = harga;
    }

    public String getKdBuku() {
        return kdBuku;
    }

    public void setKdBuku(String kdBuku) {
        this.kdBuku = kdBuku;
    }

    public String getJudulBuku() {
        return judulBuku;
    }

    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
