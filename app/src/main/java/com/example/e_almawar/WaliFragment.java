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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WaliFragment extends Fragment {

    private EditText etNamaWali, etPekerjaanWali, etPenghasilanWali, etNomorHandphoneWali;
    private Button btnNext, btnPrev;

    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wali, container, false);

        // Inisialisasi Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Inisialisasi EditText
        etNamaWali = view.findViewById(R.id.etNamaWali);
        etPekerjaanWali = view.findViewById(R.id.etPekerjaanWali);
        etPenghasilanWali = view.findViewById(R.id.etPenghasilanWali);
        etNomorHandphoneWali = view.findViewById(R.id.etNomorHandphoneWali);

        // Tombol Next
        btnNext = view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(v -> {
            saveDataToFirebase();  // Menyimpan data ke Firebase
            // Navigasi ke halaman berikutnya jika perlu
        });

        // Tombol Previous
        btnPrev = view.findViewById(R.id.btn_prev);
        btnPrev.setOnClickListener(v -> {
            // Navigasi ke halaman sebelumnya jika perlu
        });

        return view;
    }

    private void saveDataToFirebase() {
        // Ambil data dari EditText
        String namaWali = etNamaWali.getText().toString().trim();
        String pekerjaanWali = etPekerjaanWali.getText().toString().trim();
        String penghasilanWali = etPenghasilanWali.getText().toString().trim();
        String nomorHandphoneWali = etNomorHandphoneWali.getText().toString().trim();

        // Validasi input
        if (namaWali.isEmpty() || pekerjaanWali.isEmpty() || penghasilanWali.isEmpty() || nomorHandphoneWali.isEmpty()) {
            Toast.makeText(getContext(), "Harap isi semua kolom!", Toast.LENGTH_SHORT).show();
        } else {
            // Membuat objek BiodataWali dengan data yang diambil
            BiodataWali biodataWali = new BiodataWali(namaWali, pekerjaanWali, penghasilanWali, nomorHandphoneWali);

            // Menyimpan data ke Firebase Realtime Database
            String userId = mDatabase.push().getKey(); // Mendapatkan ID unik untuk setiap entri data
            if (userId != null) {
                mDatabase.child("wali").child(userId).setValue(biodataWali)
                        .addOnSuccessListener(aVoid -> {
                            // Berhasil menyimpan data
                            Toast.makeText(getContext(), "Data wali berhasil disimpan", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            // Gagal menyimpan data
                            Toast.makeText(getContext(), "Gagal menyimpan data wali: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        }
    }
}
