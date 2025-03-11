package com.example.e_almawar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Inisialisasi Firebase
        // Inisialisasi Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();  // Referensi ke Firebase Realtime Database

        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputNama = findViewById(R.id.inputNama);
        EditText inputPassword = findViewById(R.id.inputPassword);
        ImageView btnBack = findViewById(R.id.btnBack);
        Button btnSignUp = findViewById(R.id.btnSignUp);
        TextView btnLogin = findViewById(R.id.btnLogin);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String nama = inputNama.getText().toString();
                String password = inputPassword.getText().toString();

                if (email.isEmpty() || nama.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Harap isi semua kolom!", Toast.LENGTH_SHORT).show();
                } else {
                    // Mendaftar dengan Firebase Authentication
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUpActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    // Menyimpan data pengguna di Firebase Realtime Database
                                    User newUser = new User(email, nama);  // User adalah model data pengguna
                                    mDatabase.child("users").child(user.getUid()).setValue(newUser)
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    Toast.makeText(SignUpActivity.this, "Pendaftaran Berhasil!", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(SignUpActivity.this, LoginSiswaActivity.class);
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(SignUpActivity.this, "Gagal menyimpan data pengguna!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Toast.makeText(SignUpActivity.this, "Pendaftaran gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d("fbgagal", task.getException().getMessage());
                                }
                            });
                }
            }
        });

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginSiswaActivity.class);
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> finish());  // Kembali ke halaman sebelumnya
    }

    // Model untuk data pengguna
    public static class User {
        private String email;
        private String nama;

        public User(String email, String nama) {
            this.email = email;
            this.nama = nama;
        }

        // Getter dan Setter
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }
    }
}
