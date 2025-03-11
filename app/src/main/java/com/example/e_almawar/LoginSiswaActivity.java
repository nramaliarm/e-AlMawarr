package com.example.e_almawar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginSiswaActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_siswa);

        // Inisialisasi Firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();  // Referensi ke Firebase Realtime Database

        EditText inputEmail = findViewById(R.id.inputEmail);
        EditText inputPassword = findViewById(R.id.inputPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        ImageView btnBack = findViewById(R.id.btnBack);
        TextView btnDaftar = findViewById(R.id.btnDaftar);
        TextView txtLupaPassword = findViewById(R.id.txtLupaPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginSiswaActivity.this, "Harap isi email dan password!", Toast.LENGTH_SHORT).show();
                } else {
                    // Login dengan Firebase Authentication
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginSiswaActivity.this, task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    // Verifikasi apakah pengguna terdaftar di Realtime Database
                                    mDatabase.child("users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                // Pengguna terdaftar di database, lanjutkan ke halaman utama
                                                Intent intent = new Intent(LoginSiswaActivity.this, SiswaHomeActivity.class);
                                                startActivity(intent);
                                            } else {
                                                // Pengguna tidak terdaftar di database
                                                Toast.makeText(LoginSiswaActivity.this, "Pengguna tidak terdaftar di database", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Toast.makeText(LoginSiswaActivity.this, "Gagal memeriksa data pengguna", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    // Jika login gagal
                                    Toast.makeText(LoginSiswaActivity.this, "Login gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        // Navigasi ke Halaman Sign Up
        btnDaftar.setOnClickListener(v -> {
            Intent intent = new Intent(LoginSiswaActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // Navigasi ke Halaman Lupa Password
        txtLupaPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginSiswaActivity.this, LupaPasswordActivity.class);
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> finish());  // Kembali ke halaman sebelumnya
    }
}
