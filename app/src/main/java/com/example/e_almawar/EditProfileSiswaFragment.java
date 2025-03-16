package com.example.e_almawar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileSiswaFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText etName, etEmail;
    private Button btnSave;

    public EditProfileSiswaFragment() {
        // Konstruktor kosong, diperlukan oleh Fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout untuk fragment ini
        View view = inflater.inflate(R.layout.fragment_edit_siswa_akun, container, false);

        // Inisialisasi Firebase Auth dan Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Inisialisasi view (EditText dan Button)
        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        btnSave = view.findViewById(R.id.btnSave);

        // Mengambil data pengguna yang sedang login dari Firebase
        String userId = mAuth.getCurrentUser().getUid();

        // Mengambil dan menampilkan data pengguna jika ada
        mDatabase.child("users").child(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().exists()) {
                    String currentName = task.getResult().child("nama").getValue(String.class);
                    String currentEmail = task.getResult().child("email").getValue(String.class);

                    etName.setText(currentName);
                    etEmail.setText(currentEmail);
                }
            }
        });

        // Menyimpan perubahan data pengguna ke Firebase ketika tombol save diklik
        btnSave.setOnClickListener(v -> {
            String newName = etName.getText().toString();
            String newEmail = etEmail.getText().toString();

            // Memastikan bahwa field tidak kosong
            if (newName.isEmpty() || newEmail.isEmpty()) {
                Toast.makeText(getActivity(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
            } else {
                // Update data di Firebase Realtime Database
                mDatabase.child("users").child(userId).child("nama").setValue(newName);
                mDatabase.child("users").child(userId).child("email").setValue(newEmail);

                // Tampilkan pesan sukses dan kembali ke fragment sebelumnya
                Toast.makeText(getActivity(), "Profile berhasil diperbarui", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();  // Kembali ke fragment sebelumnya
            }
        });

        return view;
    }
}
