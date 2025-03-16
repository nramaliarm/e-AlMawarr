package com.example.e_almawar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SiswaHomeFragment extends Fragment {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private TextView tvGreeting;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate layout fragment_home.xml
        View view = inflater.inflate(R.layout.fragment_siswa_home, container, false);

        // Inisialisasi Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Mengambil reference TextView untuk menyapa pengguna
        tvGreeting = view.findViewById(R.id.tv_greeting); // Pastikan ID ini sesuai dengan layout

        // Cek apakah nama pengguna sudah ada di SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("user_name", null);

        if (userName == null) {
            // Jika belum ada, ambil data dari Firebase dan simpan di SharedPreferences
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                String uid = user.getUid();

                // Ambil data pengguna dari Firebase Realtime Database
                mDatabase.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Mendapatkan nama pengguna dari Firebase
                            String name = dataSnapshot.child("nama").getValue(String.class);
                            if (name != null) {
                                // Menampilkan nama pengguna di TextView greeting
                                tvGreeting.setText("Halo, " + name + "!");

                                // Simpan nama pengguna di SharedPreferences
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("user_name", name);
                                editor.apply();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Menangani error jika ada
                        tvGreeting.setText("Terjadi kesalahan, coba lagi nanti.");
                    }
                });
            }
        } else {
            // Jika nama pengguna sudah ada di SharedPreferences, langsung tampilkan
            tvGreeting.setText("Halo, " + userName + "!");
        }

        // Inisialisasi tombol daftar
        Button daftarButton = view.findViewById(R.id.button_daftar);
        daftarButton.setOnClickListener(v -> {
            SiswaHomeActivity siswaHomeActivityy = (SiswaHomeActivity) getActivity();
            if (siswaHomeActivityy != null) {
                // Pindah ke FormulirFragment
                siswaHomeActivityy.replaceFragment(new SiswaFormulirFragment());

                // Set icon Formulir menjadi aktif di Bottom Navigation
                BottomNavigationView bottomNavigationView = siswaHomeActivityy.findViewById(R.id.bottom_navigation);
                bottomNavigationView.setSelectedItemId(R.id.formulir);
            }
        });

        return view;
    }
}
