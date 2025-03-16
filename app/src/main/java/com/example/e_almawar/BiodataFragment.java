package com.example.e_almawar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BiodataFragment extends Fragment {

    private EditText etNamaLengkap, etNisn, etNik, etTempatTanggalLahir, etJenisKelamin, etAgama,
            etJumlahSaudara, etNomorHandphone, etEmail, etAsalSekolah, etNpsn, etAlamatSekolah;
    private Button btnNext;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_biodata, container, false);

        // Inisialisasi Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Cek apakah user sudah login
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Log.d("FirebaseAuth", "User tidak login, kembali ke LoginActivity!");
            Intent intent = new Intent(getActivity(), LoginAdminActivity.class);
            startActivity(intent);
            getActivity().finish();
            return view;
        }

        // Inisialisasi EditText
        etNamaLengkap = view.findViewById(R.id.etNamaLengkap);
        etNisn = view.findViewById(R.id.etNisn);
        etNik = view.findViewById(R.id.etNik);
        etTempatTanggalLahir = view.findViewById(R.id.etTempatTanggalLahir);
        etJenisKelamin = view.findViewById(R.id.etJenisKelamin);
        etAgama = view.findViewById(R.id.etAgama);
        etJumlahSaudara = view.findViewById(R.id.etJumlahSaudara);
        etNomorHandphone = view.findViewById(R.id.etNomorHandphone);
        etEmail = view.findViewById(R.id.etEmail);
        etAsalSekolah = view.findViewById(R.id.etAsalSekolah);
        etNpsn = view.findViewById(R.id.etNpsn);
        etAlamatSekolah = view.findViewById(R.id.etAlamatSekolah);

        // Inisialisasi Button Next
        btnNext = view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(v -> {
            submitData();
            pindahKeHalamanSelanjutnya();
        });

        return view;
    }

    private void submitData() {
        String namaLengkap = etNamaLengkap.getText().toString().trim();
        String nisn = etNisn.getText().toString().trim();
        String nik = etNik.getText().toString().trim();
        String tempatTanggalLahir = etTempatTanggalLahir.getText().toString().trim();
        String jenisKelamin = etJenisKelamin.getText().toString().trim();
        String agama = etAgama.getText().toString().trim();
        String jumlahSaudara = etJumlahSaudara.getText().toString().trim();
        String nomorHandphone = etNomorHandphone.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String asalSekolah = etAsalSekolah.getText().toString().trim();
        String npsn = etNpsn.getText().toString().trim();
        String alamatSekolah = etAlamatSekolah.getText().toString().trim();

        if (namaLengkap.isEmpty() || nisn.isEmpty() || nik.isEmpty() || tempatTanggalLahir.isEmpty() || jenisKelamin.isEmpty() ||
                agama.isEmpty() || jumlahSaudara.isEmpty() || nomorHandphone.isEmpty() || email.isEmpty() || asalSekolah.isEmpty() ||
                npsn.isEmpty() || alamatSekolah.isEmpty()) {
            Toast.makeText(getActivity(), "Harap isi semua kolom!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Buat objek BiodataSiswa
        BiodataSiswa biodataSiswa = new BiodataSiswa(namaLengkap, nisn, nik, tempatTanggalLahir, jenisKelamin,
                agama, jumlahSaudara, nomorHandphone, email, asalSekolah, npsn, alamatSekolah);

        // Simpan ke Firebase
        String userId = mDatabase.child("pendaftar").push().getKey();
        if (userId != null) {
            mDatabase.child("pendaftar").child(userId).setValue(biodataSiswa)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getActivity(), "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show();
                        clearFields();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getActivity(), "Pendaftaran gagal: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void pindahKeHalamanSelanjutnya() {
        Intent intent = new Intent(getActivity(), OrangtuaFragment.class);
        startActivity(intent);
    }

    private void clearFields() {
        etNamaLengkap.setText("");
        etNisn.setText("");
        etNik.setText("");
        etTempatTanggalLahir.setText("");
        etJenisKelamin.setText("");
        etAgama.setText("");
        etJumlahSaudara.setText("");
        etNomorHandphone.setText("");
        etEmail.setText("");
        etAsalSekolah.setText("");
        etNpsn.setText("");
        etAlamatSekolah.setText("");
    }
}