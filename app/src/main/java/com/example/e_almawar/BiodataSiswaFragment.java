package com.example.e_almawar;

public class BiodataSiswaFragment {
    private String namaLengkap;
    private String nisn;
    private String nik;
    private String tempatTanggalLahir;
    private String jenisKelamin;
    private String agama;
    private String jumlahSaudara;
    private String nomorHandphone;
    private String email;
    private String asalSekolah;
    private String npsn;
    private String alamatSekolah;

    // Constructor default
    public BiodataSiswaFragment() {}

    // Constructor dengan parameter
    public BiodataSiswaFragment(String namaLengkap, String nisn, String nik, String tempatTanggalLahir,
                        String jenisKelamin, String agama, String jumlahSaudara, String nomorHandphone,
                        String email, String asalSekolah, String npsn, String alamatSekolah) {
        this.namaLengkap = namaLengkap;
        this.nisn = nisn;
        this.nik = nik;
        this.tempatTanggalLahir = tempatTanggalLahir;
        this.jenisKelamin = jenisKelamin;
        this.agama = agama;
        this.jumlahSaudara = jumlahSaudara;
        this.nomorHandphone = nomorHandphone;
        this.email = email;
        this.asalSekolah = asalSekolah;
        this.npsn = npsn;
        this.alamatSekolah = alamatSekolah;
    }

    // Getter dan Setter untuk setiap field
    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getTempatTanggalLahir() {
        return tempatTanggalLahir;
    }

    public void setTempatTanggalLahir(String tempatTanggalLahir) {
        this.tempatTanggalLahir = tempatTanggalLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getJumlahSaudara() {
        return jumlahSaudara;
    }

    public void setJumlahSaudara(String jumlahSaudara) {
        this.jumlahSaudara = jumlahSaudara;
    }

    public String getNomorHandphone() {
        return nomorHandphone;
    }

    public void setNomorHandphone(String nomorHandphone) {
        this.nomorHandphone = nomorHandphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAsalSekolah() {
        return asalSekolah;
    }

    public void setAsalSekolah(String asalSekolah) {
        this.asalSekolah = asalSekolah;
    }

    public String getNpsn() {
        return npsn;
    }

    public void setNpsn(String npsn) {
        this.npsn = npsn;
    }

    public String getAlamatSekolah() {
        return alamatSekolah;
    }

    public void setAlamatSekolah(String alamatSekolah) {
        this.alamatSekolah = alamatSekolah;
    }
}
