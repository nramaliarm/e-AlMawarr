package com.example.e_almawar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class SiswaAkunFragment extends Fragment {

    Button btnLogout;
    TextView tvUserName, tvEmail;  // TextViews untuk menampilkan nama dan email pengguna

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_siswa_akun, container, false);

        // Menghubungkan tombol dan TextView dengan layout XML
        btnLogout = view.findViewById(R.id.btnLogout);
        tvUserName = view.findViewById(R.id.tvName); // ID untuk TextView nama pengguna
        tvEmail = view.findViewById(R.id.tvEmailLabel); // ID untuk TextView email pengguna

        // Ambil data pengguna dari SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("user_name", "Tidak Diketahui");
        String userEmail = sharedPreferences.getString("user_email", "Tidak Diketahui");

        // Menampilkan data nama dan email pengguna di TextView
        tvUserName.setText(userName);
        tvEmail.setText(userEmail);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tampilkan dialog konfirmasi logout
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Keluar");
                builder.setMessage("Apakah Anda yakin ingin keluar?");
                builder.setPositiveButton("Ya", (dialog, which) -> {
                    // Hapus data login yang tersimpan di SharedPreferences
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear(); // Menghapus semua data
                    editor.apply(); // Simpan perubahan

                    // Logout dari Firebase
                    FirebaseAuth.getInstance().signOut();

                    // Arahkan ke LoginSiswaActivity setelah logout
                    Intent intent = new Intent(getActivity(), LoginSiswaActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear stack activity
                    startActivity(intent);
                    getActivity().finish(); // Tutup semua activity sebelumnya
                });
                builder.setNegativeButton("Tidak", null);
                builder.show();
            }
        });

        return view;
    }
}
