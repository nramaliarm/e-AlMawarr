package com.example.e_almawar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditProfileSiswaFragment extends Fragment {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText etName, etEmail, etOldPassword, etNewPassword;
    private Button btnSave, btnChooseImage;
    private ImageView ivProfile;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;

    public EditProfileSiswaFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_siswa_akun, container, false);

        // Initialize Firebase Auth, Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize views (EditText, Button, ImageView)
        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etOldPassword = view.findViewById(R.id.etOldPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        btnSave = view.findViewById(R.id.btnSave);
        btnChooseImage = view.findViewById(R.id.btnChooseImage);
        ivProfile = view.findViewById(R.id.ivProfile);

        // Retrieve currently logged-in user
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            // Fetch and display current user data if available
            mDatabase.child("users").child(userId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        String currentName = task.getResult().child("nama").getValue(String.class);
                        String currentEmail = task.getResult().child("email").getValue(String.class);
                        String currentProfileImage = task.getResult().child("profileImage").getValue(String.class);

                        etName.setText(currentName);
                        etEmail.setText(currentEmail);

                        // Set profile image if available
                        if (currentProfileImage != null && !currentProfileImage.isEmpty()) {
                            Glide.with(getContext()).load(currentProfileImage).into(ivProfile);
                        }
                    }
                }
            });
        }

        // Open file chooser to select image
        btnChooseImage.setOnClickListener(v -> openFileChooser());

        // Save changes to Firebase
        btnSave.setOnClickListener(v -> {
            String newName = etName.getText().toString();
            String newEmail = etEmail.getText().toString();
            String oldPassword = etOldPassword.getText().toString();
            String newPassword = etNewPassword.getText().toString();

            if (TextUtils.isEmpty(newName) || TextUtils.isEmpty(newEmail) || TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword)) {
                Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                FirebaseUser users = mAuth.getCurrentUser();
                if (users != null) {
                    String userId = users.getUid();

                    // Update name and email in Firebase Realtime Database
                    mDatabase.child("users").child(userId).child("nama").setValue(newName);
                    mDatabase.child("users").child(userId).child("email").setValue(newEmail);

                    // Change password if necessary
                    users.updatePassword(newPassword).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Failed to update password", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // If there's an image, upload it to ImgBB
                    if (imageUri != null) {
                        uploadImageToImgBB(imageUri);
                    } else {
                        Toast.makeText(getActivity(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                }
            }
        });

        return view;
    }

    // Open file chooser to pick an image
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // Upload the image to ImgBB
    private void uploadImageToImgBB(Uri uri) {
        File file = new File(getRealPathFromURI(uri)); // Convert URI to file
        OkHttpClient client = new OkHttpClient();

        // Create request body with the image file
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("image/*"), file))
                .addFormDataPart("key", "8fcb52ab93dc9230c2551417e398f787")  // ImgBB API key
                .build();

        // Create request to ImgBB API
        Request request = new Request.Builder()
                .url("https://api.imgbb.com/1/upload")
                .post(requestBody)
                .build();

        // Run the request asynchronously
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(() -> {
                    Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    try {
                        // Parse the JSON response from ImgBB
                        JSONObject jsonObject = new JSONObject(responseBody);
                        String imageUrl = jsonObject.getJSONObject("data").getString("url");

                        // Save the image URL to Firebase Realtime Database
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();
                            mDatabase.child("users").child(userId).child("profileImage").setValue(imageUrl);

                            getActivity().runOnUiThread(() -> {
                                Toast.makeText(getActivity(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                getActivity().onBackPressed();
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    // Get real path from URI
    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        return cursor.getString(columnIndex);
    }

    // Handle the result from the file chooser (for image selection)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            ivProfile.setImageURI(imageUri); // Display the selected image in the ImageView
        }
    }
}
