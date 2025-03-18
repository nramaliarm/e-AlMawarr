package com.example.e_almawar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.e_almawar.viewmodel.FormViewModel;

public class BiodataFragment extends Fragment {

    private EditText etNamaLengkap, etNisn, etNik, etTempatTanggalLahir, etJenisKelamin, etAgama;
    private EditText etJumlahSaudara, etNomorHandphone, etEmail, etAsalSekolah, etNpsn, etAlamatSekolah;
    private Button btnNext;
    private FormViewModel formViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_biodata, container, false);

        // Inisialisasi ViewModel
        formViewModel = new ViewModelProvider(requireActivity()).get(FormViewModel.class);

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

        // Isi ulang data jika sudah ada di ViewModel
        etNamaLengkap.setText(formViewModel.getNamaLengkap());
        etNisn.setText(formViewModel.getNisn());
        etNik.setText(formViewModel.getNik());
        etTempatTanggalLahir.setText(formViewModel.getTempatTanggalLahir());
        etJenisKelamin.setText(formViewModel.getJenisKelamin());
        etAgama.setText(formViewModel.getAgama());
        etJumlahSaudara.setText(formViewModel.getJumlahSaudara());
        etNomorHandphone.setText(formViewModel.getNomorHandphone());
        etEmail.setText(formViewModel.getEmail());
        etAsalSekolah.setText(formViewModel.getAsalSekolah());
        etNpsn.setText(formViewModel.getNpsn());
        etAlamatSekolah.setText(formViewModel.getAlamatSekolah());

        // Simpan otomatis saat user mengetik
        addTextWatchers();

        // Inisialisasi Button Next
        btnNext = view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(v -> pindahKeHalamanSelanjutnya());

        return view;
    }

    private void addTextWatchers() {
        etNamaLengkap.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { formViewModel.setNamaLengkap(s.toString()); }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        etNisn.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { formViewModel.setNisn(s.toString()); }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        etNik.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { formViewModel.setNik(s.toString()); }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        etTempatTanggalLahir.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { formViewModel.setTempatTanggalLahir(s.toString()); }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        etJenisKelamin.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { formViewModel.setJenisKelamin(s.toString()); }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        etAgama.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) { formViewModel.setAgama(s.toString()); }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    private void pindahKeHalamanSelanjutnya() {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new BiodataOrangtuaFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}