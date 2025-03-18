package com.example.e_almawar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SiswaAkunFragment extends Fragment {

    Button btnLogout, btnEditProfile;
    TextView tvUserName, tvEmail;  // TextViews for displaying user name and email
    ImageView ivProfile;  // ImageView to display profile image

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_siswa_akun, container, false);

        // Link views to their XML counterparts
        btnLogout = view.findViewById(R.id.btnLogout);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);  // Edit Profile button
        tvUserName = view.findViewById(R.id.tvName);  // TextView for displaying the username
        tvEmail = view.findViewById(R.id.tvEmail);  // TextView for displaying the email
        ivProfile = view.findViewById(R.id.ivProfile); // Profile image view

        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("user_name", "Not Available");
        String userEmail = sharedPreferences.getString("user_email", "Not Available");

        // Display the username and email in the TextViews
        tvUserName.setText(userName);
        tvEmail.setText(userEmail);

        // Retrieve user data from Firebase to get the profile image URL
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

            // Get profile image URL from Firebase
            mDatabase.child("users").child(userId).child("profileImage").get().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult().exists()) {
                    String profileImageUrl = task.getResult().getValue(String.class);
                    Log.d("ProfileImage", "Profile image URL: " + profileImageUrl);  // Log the URL

                    // Check if the URL is valid
                    if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                        // Load profile image using Glide
                        Glide.with(getContext())
                                .load(profileImageUrl)  // This URL comes from Firebase
                                .circleCrop()  // This will crop the image into a circle shape
                                .into(ivProfile);  // Set the image in the ImageView

                    } else {
                        // If the URL is empty or invalid, show default profile image
                        ivProfile.setImageResource(R.drawable.ic_profile);
                        Toast.makeText(getContext(), "No profile image available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // If no profile image URL is found, use a default profile image
                    ivProfile.setImageResource(R.drawable.ic_profile);
                    Toast.makeText(getContext(), "Profile image not found in Firebase", Toast.LENGTH_SHORT).show();
                }
            });
        }

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
            // Navigate to EditProfileSiswaFragment when "Edit Profile" button is clicked
            EditProfileSiswaFragment editProfileFragment = new EditProfileSiswaFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, editProfileFragment)  // Replace the current fragment
                    .addToBackStack(null)  // Add to back stack to enable going back
                    .commit();
        });

        return view;  // Return the view to be displayed
    }
}
