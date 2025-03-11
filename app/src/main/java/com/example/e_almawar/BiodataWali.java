package com.example.e_almawar;

public class BiodataWali {
    private String namaWali;
    private String pekerjaanWali;
    private String penghasilanWali;
    private String nomorHandphoneWali;

    // Constructor default
    public BiodataWali() {}

    // Constructor dengan parameter
    public BiodataWali(String namaWali, String pekerjaanWali, String penghasilanWali, String nomorHandphoneWali) {
        this.namaWali = namaWali;
        this.pekerjaanWali = pekerjaanWali;
        this.penghasilanWali = penghasilanWali;
        this.nomorHandphoneWali = nomorHandphoneWali;
    }

    // Getter dan Setter untuk semua field
    public String getNamaWali() {
        return namaWali;
    }

    public void setNamaWali(String namaWali) {
        this.namaWali = namaWali;
    }

    public String getPekerjaanWali() {
        return pekerjaanWali;
    }

    public void setPekerjaanWali(String pekerjaanWali) {
        this.pekerjaanWali = pekerjaanWali;
    }

    public String getPenghasilanWali() {
        return penghasilanWali;
    }

    public void setPenghasilanWali(String penghasilanWali) {
        this.penghasilanWali = penghasilanWali;
    }

    public String getNomorHandphoneWali() {
        return nomorHandphoneWali;
    }

    public void setNomorHandphoneWali(String nomorHandphoneWali) {
        this.nomorHandphoneWali = nomorHandphoneWali;
    }
}
