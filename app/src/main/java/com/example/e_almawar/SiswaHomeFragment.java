package com.example.e_almawar;

import android.content.Intent;
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

        // Ambil informasi pengguna yang sedang login dari Firebase Authentication
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();

            // Ambil data pengguna dari Firebase Realtime Database
            mDatabase.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Mendapatkan nama pengguna dari Firebase
                        String name = dataSnapshot.child("nama").getValue(String.class); // Pastikan ada field "nama"
                        if (name != null) {
                            // Menampilkan nama pengguna di TextView greeting
                            tvGreeting.setText("Halo, " + name + "!");
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Menangani error jika ada
                }
            });
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