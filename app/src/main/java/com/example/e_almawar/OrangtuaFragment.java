package com.example.e_almawar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.e_almawar.BiodataOrangtua;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrangtuaFragment extends Fragment {

    private EditText etNamaAyah, etNamaIbu, etPendidikanAyah, etPendidikanIbu, etPekerjaanAyah, etPekerjaanIbu,
            etPenghasilanAyah, etPenghasilanIbu, etNomorHandphoneOrangtua;
    private Button btnNext, btnPrev;

    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout untuk fragment_orangtua.xml
        View view = inflater.inflate(R.layout.fragment_orangtua, container, false);

        // Inisialisasi Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Inisialisasi EditText
        etNamaAyah = view.findViewById(R.id.etNamaAyah);
        etNamaIbu = view.findViewById(R.id.etNamaIbu);
        etPendidikanAyah = view.findViewById(R.id.etPendidikanAyah);
        etPendidikanIbu = view.findViewById(R.id.etPendidikanIbu);
        etPekerjaanAyah = view.findViewById(R.id.etPekerjaanAyah);
        etPekerjaanIbu = view.findViewById(R.id.etPekerjaanIbu);
        etPenghasilanAyah = view.findViewById(R.id.etPenghasilanAyah);
        etPenghasilanIbu = view.findViewById(R.id.etPenghasilanIbu);
        etNomorHandphoneOrangtua = view.findViewById(R.id.etNomorHandphoneOrangtua);

        // Tombol Next
        btnNext = view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(v -> {
            saveDataToFirebase();  // Menyimpan data ke Firebase
            // Navigasi ke halaman berikutnya
            ((SiswaFormulirFragment) getParentFragment()).nextPage();
        });

        // Tombol Previous
        btnPrev = view.findViewById(R.id.btn_prev);
        btnPrev.setOnClickListener(v -> {
            // Navigasi ke halaman sebelumnya
            ((SiswaFormulirFragment) getParentFragment()).previousPage();
        });

        return view;
    }

    // Fungsi untuk menyimpan data ke Firebase
    private void saveDataToFirebase() {
        String namaAyah = etNamaAyah.getText().toString().trim();
        String namaIbu = etNamaIbu.getText().toString().trim();
        String pendidikanAyah = etPendidikanAyah.getText().toString().trim();
        String pendidikanIbu = etPendidikanIbu.getText().toString().trim();
        String pekerjaanAyah = etPekerjaanAyah.getText().toString().trim();
        String pekerjaanIbu = etPekerjaanIbu.getText().toString().trim();
        String penghasilanAyah = etPenghasilanAyah.getText().toString().trim();
        String penghasilanIbu = etPenghasilanIbu.getText().toString().trim();
        String nomorHandphoneOrangtua = etNomorHandphoneOrangtua.getText().toString().trim();

        // Validasi input
        if (namaAyah.isEmpty() || namaIbu.isEmpty() || pendidikanAyah.isEmpty() || pendidikanIbu.isEmpty() ||
                pekerjaanAyah.isEmpty() || pekerjaanIbu.isEmpty() || penghasilanAyah.isEmpty() || penghasilanIbu.isEmpty() ||
                nomorHandphoneOrangtua.isEmpty()) {
            Toast.makeText(getContext(), "Harap isi semua kolom!", Toast.LENGTH_SHORT).show();
        } else {
            // Membuat objek BiodataOrangtua
            com.example.e_almawar.BiodataOrangtua biodataOrangtua = new com.example.e_almawar.BiodataOrangtua(namaAyah, namaIbu, pendidikanAyah, pendidikanIbu,
                    pekerjaanAyah, pekerjaanIbu, penghasilanAyah, penghasilanIbu, nomorHandphoneOrangtua);

            // Menyimpan data ke Firebase
            String userId = mDatabase.push().getKey(); // Mendapatkan ID unik
            if (userId != null) {
                mDatabase.child("orangtua").child(userId).setValue(biodataOrangtua)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(getContext(), "Data orang tua berhasil disimpan", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), "Gagal menyimpan data orang tua: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        }
    }
}
