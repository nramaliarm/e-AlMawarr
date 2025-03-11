package com.example.e_almawar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.e_almawar.BiodataSiswaFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BiodataFragment extends Fragment {

    private EditText etNamaLengkap, etNisn, etNik, etTempatTanggalLahir, etJenisKelamin, etAgama,
            etJumlahSaudara, etNomorHandphone, etEmail, etAsalSekolah, etNpsn, etAlamatSekolah;
    private Button btnSubmit;

    private DatabaseReference mDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout untuk fragment_biodata.xml
        View view = inflater.inflate(R.layout.fragment_biodata, container, false);

        // Inisialisasi Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference();

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

        btnSubmit = view.findViewById(R.id.btnSubmit);  // Pastikan ID tombol submit

        // OnClickListener untuk tombol submit
        btnSubmit.setOnClickListener(v -> {
            // Ambil data dari EditText
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

            // Validasi input
            if (namaLengkap.isEmpty() || nisn.isEmpty() || nik.isEmpty() || tempatTanggalLahir.isEmpty() || jenisKelamin.isEmpty() ||
                    agama.isEmpty() || jumlahSaudara.isEmpty() || nomorHandphone.isEmpty() || email.isEmpty() || asalSekolah.isEmpty() ||
                    npsn.isEmpty() || alamatSekolah.isEmpty()) {
                Toast.makeText(getActivity(), "Harap isi semua kolom!", Toast.LENGTH_SHORT).show();
            } else {
                // Membuat objek BiodataSiswa
                BiodataSiswaFragment biodataSiswa = new BiodataSiswaFragment(namaLengkap, nisn, nik, tempatTanggalLahir, jenisKelamin,
                        agama, jumlahSaudara, nomorHandphone, email, asalSekolah, npsn, alamatSekolah);

                // Menyimpan data ke Firebase Realtime Database
                String userId = mDatabase.push().getKey();
                if (userId != null) {
                    mDatabase.child("pendaftar").child(userId).setValue(biodataSiswa)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getActivity(), "Pendaftaran berhasil!", Toast.LENGTH_SHORT).show();
                                // Lakukan navigasi atau reset form jika perlu
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getActivity(), "Pendaftaran gagal: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }
            }
        });

        return view;
    }
}
