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

    Button btnLogout, btnEditProfile;
    TextView tvUserName, tvEmail;  // TextViews for displaying user name and email

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_siswa_akun, container, false);

        // Link views to their XML counterparts
        btnLogout = view.findViewById(R.id.btnLogout);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);  // Edit Profile button
        tvUserName = view.findViewById(R.id.tvName);  // TextView for displaying the username
        tvEmail = view.findViewById(R.id.tvEmail);  // TextView for displaying the email

        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("user_name", "Not Available");
        String userEmail = sharedPreferences.getString("user_email", "Not Available");

        // Display the username and email in the TextViews
        tvUserName.setText(userName);
        tvEmail.setText(userEmail);

        // Handle Logout button click
        btnLogout.setOnClickListener(v -> {
            // Show confirmation dialog for logging out
            new AlertDialog.Builder(getContext())
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to log out?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // Clear the user data stored in SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();  // Clear all data
                        editor.apply();   // Save the changes

                        // Log out from Firebase
                        FirebaseAuth.getInstance().signOut();

                        // Redirect to Login page after logout
                        Intent intent = new Intent(getActivity(), LoginSiswaActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);  // Clear activity stack
                        startActivity(intent);
                        getActivity().finish();  // Close all previous activities
                    })
                    .setNegativeButton("No", null)  // Cancel logout action
                    .show();
        });

        // Handle Edit Profile button click
        btnEditProfile.setOnClickListener(v -> {
            // Open the Edit Profile activity
            Intent intent = new Intent(getActivity(), EditProfileSiswaFragment.class);
            startActivity(intent);
        });

        return view;  // Return the view to be displayed
    }
}
