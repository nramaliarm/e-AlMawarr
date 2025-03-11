package com.example.e_almawar;

import android.content.Intent; // Import Intent
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView; // Import ImageView

import androidx.annotation.NonNull; // Import NonNull
import androidx.annotation.Nullable; // Import Nullable
import androidx.fragment.app.Fragment;

public class SiswaSchoolFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_siswa_school, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView iconSejarah = view.findViewById(R.id.icon_sejarah);
        ImageView iconTujuan = view.findViewById(R.id.icon_tujuan);
        ImageView iconVisimisi = view.findViewById(R.id.icon_visimisi);
        ImageView iconSarpras = view.findViewById(R.id.icon_sarpras);
        ImageView iconEkstrakulikuler = view.findViewById(R.id.icon_ekstrakulikuler);

        iconSejarah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SiswaSejarahFragment siswaSejarahFragment = new SiswaSejarahFragment();
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, siswaSejarahFragment) // ID container layout
                        .addToBackStack(null) // biar bisa di-back
                        .commit();
            }
        });

        iconTujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SiswaTujuanFragment siswaTujuanFragment = new SiswaTujuanFragment();
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, siswaTujuanFragment) // ID container layout
                        .addToBackStack(null) // biar bisa di-back
                        .commit();
            }
        });

        iconVisimisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SiswaVisimisiFragment siswaVisimisiFragment = new SiswaVisimisiFragment();
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, siswaVisimisiFragment) // ID container layout
                        .addToBackStack(null) // biar bisa di-back
                        .commit();
            }
        });

        iconSarpras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SiswaSarprasFragment siswaSarprasFragmentt = new SiswaSarprasFragment();
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, siswaSarprasFragmentt) // ID container layout
                        .addToBackStack(null) // biar bisa di-back
                        .commit();
            }
        });

        iconEkstrakulikuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SiswaEkstrakulikulerFragment siswaEkstrakulikulerFragment = new SiswaEkstrakulikulerFragment();
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, siswaEkstrakulikulerFragment) // ID container layout
                        .addToBackStack(null) // biar bisa di-back
                        .commit();
            }
        });

    }
}
