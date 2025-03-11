package com.example.e_almawar;

public class BiodataOrangtua {
    private String namaAyah;
    private String namaIbu;
    private String pendidikanAyah;
    private String pendidikanIbu;
    private String pekerjaanAyah;
    private String pekerjaanIbu;
    private String penghasilanAyah;
    private String penghasilanIbu;
    private String nomorHandphoneOrangtua;

    // Constructor default
    public BiodataOrangtua() {}

    // Constructor dengan parameter
    public BiodataOrangtua(String namaAyah, String namaIbu, String pendidikanAyah, String pendidikanIbu,
                           String pekerjaanAyah, String pekerjaanIbu, String penghasilanAyah, String penghasilanIbu,
                           String nomorHandphoneOrangtua) {
        this.namaAyah = namaAyah;
        this.namaIbu = namaIbu;
        this.pendidikanAyah = pendidikanAyah;
        this.pendidikanIbu = pendidikanIbu;
        this.pekerjaanAyah = pekerjaanAyah;
        this.pekerjaanIbu = pekerjaanIbu;
        this.penghasilanAyah = penghasilanAyah;
        this.penghasilanIbu = penghasilanIbu;
        this.nomorHandphoneOrangtua = nomorHandphoneOrangtua;
    }

    // Getter dan Setter untuk setiap field
    public String getNamaAyah() {
        return namaAyah;
    }

    public void setNamaAyah(String namaAyah) {
        this.namaAyah = namaAyah;
    }

    public String getNamaIbu() {
        return namaIbu;
    }

    public void setNamaIbu(String namaIbu) {
        this.namaIbu = namaIbu;
    }

    public String getPendidikanAyah() {
        return pendidikanAyah;
    }

    public void setPendidikanAyah(String pendidikanAyah) {
        this.pendidikanAyah = pendidikanAyah;
    }

    public String getPendidikanIbu() {
        return pendidikanIbu;
    }

    public void setPendidikanIbu(String pendidikanIbu) {
        this.pendidikanIbu = pendidikanIbu;
    }

    public String getPekerjaanAyah() {
        return pekerjaanAyah;
    }

    public void setPekerjaanAyah(String pekerjaanAyah) {
        this.pekerjaanAyah = pekerjaanAyah;
    }

    public String getPekerjaanIbu() {
        return pekerjaanIbu;
    }

    public void setPekerjaanIbu(String pekerjaanIbu) {
        this.pekerjaanIbu = pekerjaanIbu;
    }

    public String getPenghasilanAyah() {
        return penghasilanAyah;
    }

    public void setPenghasilanAyah(String penghasilanAyah) {
        this.penghasilanAyah = penghasilanAyah;
    }

    public String getPenghasilanIbu() {
        return penghasilanIbu;
    }

    public void setPenghasilanIbu(String penghasilanIbu) {
        this.penghasilanIbu = penghasilanIbu;
    }

    public String getNomorHandphoneOrangtua() {
        return nomorHandphoneOrangtua;
    }

    public void setNomorHandphoneOrangtua(String nomorHandphoneOrangtua) {
        this.nomorHandphoneOrangtua = nomorHandphoneOrangtua;
    }
}
