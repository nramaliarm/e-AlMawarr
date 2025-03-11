package com.example.e_almawar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FormPagerAdapter extends FragmentStateAdapter {
    public FormPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new BiodataFragment(); // Fragment Biodata
            case 1: return new OrangtuaFragment(); // Fragment Orangtua
            case 2: return new WaliFragment(); // Fragment Wali
            case 3: return new TempatTinggalFragment(); // Fragment Tempat Tinggal
            default: return new BiodataFragment(); // Default Fragment
        }
    }

    @Override
    public int getItemCount() {
        return 4; // Pastikan jumlah item sesuai dengan fragment yang diinginkan
    }
