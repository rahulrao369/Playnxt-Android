package com.cw.playnxt.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cw.playnxt.R;
import com.cw.playnxt.databinding.ActivityEditProfileBinding;
import com.cw.playnxt.databinding.HeaderLayoutBinding;
import com.cw.playnxt.model.ChangePassword.ChangePasswordParaRes;
import com.cw.playnxt.model.EditWishlist.EditWishlistParaRes;
import com.cw.playnxt.model.GetMyProfile.GetMyProfileResponse;
import com.cw.playnxt.model.GetMyProfile.Profile;
import com.cw.playnxt.model.ResponseSatusMessage;
import com.cw.playnxt.server.Allurls;
import com.cw.playnxt.server.ApiUtils;
import com.cw.playnxt.server.JsonPlaceHolderApi;
import com.cw.playnxt.server.MySharedPref;
import com.cw.playnxt.utils.Constants;
import com.cw.playnxt.utils.Customprogress;
import com.cw.playnxt.utils.Filepath;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Context context;
    private ActivityEditProfileBinding binding;
    private HeaderLayoutBinding headerBinding;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MySharedPref mySharedPref;
    private boolean isShowPassword = false;
    LinearLayout btnChangePassword;
    EditText etOldPassword,etNewPassword,etConfirmPassword;
    ImageView old_password_toggle_imageView,new_password_toggle_imageView,confirmPasswordToggleImageView;
    private String userChoosenTask;
    String selectedPath = "";
    String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        headerBinding = binding.bindingHeader;
        setContentView(binding.getRoot());
        init();
        onclicks();

    }
    public void init() {
        context = EditProfileActivity.this;
        jsonPlaceHolderApi = ApiUtils.getAPIService();
        mySharedPref = new MySharedPref(context);
        headerBinding.tvHeading.setText(R.string.EditAccount);
        headerBinding.btnFilter.setVisibility(View.GONE);
        headerBinding.btnAdd.setVisibility(View.GONE);
        headerBinding.btnShare.setVisibility(View.GONE);
        headerBinding.btnEdit.setVisibility(View.GONE);

        if (Constants.isInternetConnected(context)) {
            GetMyProfileAPI();
        } else {
            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    public void onclicks() {
        headerBinding.btnBack.setOnClickListener(this);
        binding.llDeleteAccount.setOnClickListener(this);
        binding.llChangePassword.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);
        binding.llSelectImage.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;

            case R.id.llSelectImage:
              /*  CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(this);*/
                selectImage();
                break;

            case R.id.btnSave:
                if (Constants.isInternetConnected(context)) {
                    EditProfileAPI(selectedPath);
                } else {
                    Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.llDeleteAccount:
                openDeleteAccountDialog();
                break;

            case R.id.llChangePassword:
                openDialogChangePassword();
                break;
        }
    }
    public void openDialogChangePassword() {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_change_password);
         btnChangePassword = dialog.findViewById(R.id.btnChangePassword);
        etOldPassword = dialog.findViewById(R.id.etOldPassword);
        etNewPassword = dialog.findViewById(R.id.etNewPassword);
        etConfirmPassword = dialog.findViewById(R.id.etConfirmPassword);
        old_password_toggle_imageView = dialog.findViewById(R.id.old_password_toggle_imageView);
        new_password_toggle_imageView = dialog.findViewById(R.id.new_password_toggle_imageView);
        confirmPasswordToggleImageView = dialog.findViewById(R.id.confirm_password_toggle_imageView);

      old_password_toggle_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowPassword = !isShowPassword;
                showOldPassword(isShowPassword);
            }
        });
        showOldPassword(isShowPassword);

        new_password_toggle_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowPassword = !isShowPassword;
                showNewPassword(isShowPassword);
            }
        });
        showNewPassword(isShowPassword);

        confirmPasswordToggleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowPassword = !isShowPassword;
                showConfirmPassword(isShowPassword);
            }
        });
        showConfirmPassword(isShowPassword);

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (Constants.isInternetConnected(context)) {
                    ChangePasswordAPI();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }

    public void showOldPassword(Boolean isShow) {
        if (isShow) {
            etOldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            old_password_toggle_imageView.setImageResource(R.drawable.ic_hide_password);
        } else {
            etOldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            old_password_toggle_imageView.setImageResource(R.drawable.ic_show_password);
        }
    }

    public void showNewPassword(Boolean isShow) {
        if (isShow) {
            etNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
          new_password_toggle_imageView.setImageResource(R.drawable.ic_hide_password);
        } else {
            etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            new_password_toggle_imageView.setImageResource(R.drawable.ic_show_password);
        }
    }

    public void showConfirmPassword(Boolean isShow) {
        if (isShow) {
            etConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            confirmPasswordToggleImageView.setImageResource(R.drawable.ic_hide_password);
        } else {
            etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            confirmPasswordToggleImageView.setImageResource(R.drawable.ic_show_password);
        }
    }

    private void openDeleteAccountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getResources().getString(R.string.are_you_sure_you_want_to_delete_account));
        builder.setCancelable(true);
        builder.setNegativeButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Constants.isInternetConnected(context)) {
                    DeleteAccountAPI();
                } else {
                    Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                }
                dialog.cancel();
            }
        });
        builder.setPositiveButton(getResources().getString(R.string.Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void ChangePasswordAPI() {
        Customprogress.showPopupProgressSpinner(context, true);
        ChangePasswordParaRes changePasswordParaRes = new ChangePasswordParaRes();
        changePasswordParaRes.setOldpass(etOldPassword.getText().toString().trim());
        changePasswordParaRes.setNewpass(etNewPassword.getText().toString().trim());

        jsonPlaceHolderApi.ChangePasswordAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken(),changePasswordParaRes).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status)
                    {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseSatusMessage> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    public void GetMyProfileAPI() {
        Customprogress.showPopupProgressSpinner(context, true);

        jsonPlaceHolderApi.GetMyProfileAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<GetMyProfileResponse>() {
            @Override
            public void onResponse(Call<GetMyProfileResponse> call, Response<GetMyProfileResponse> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status)
                    {
                        if(response.body().getData() != null){
                            setMyProfileData(response.body().getData().getProfile());
                        }
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetMyProfileResponse> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    private void setMyProfileData(Profile profileData) {
        if(!profileData.getImage().equals("")){
            Picasso.get().load(Allurls.IMAGEURL+profileData.getImage()).error(R.drawable.default_user).placeholder(R.drawable.default_user).into(binding.cvUserProfile);
            Log.d("TAG","Image>>>>"+Allurls.IMAGEURL+profileData.getImage());
        }else{

        }
        binding.etName.setText(profileData.getName());
        binding.etEmail.setText(profileData.getEmail());
    }

    public void EditProfileAPI(String pathMain) {
        Customprogress.showPopupProgressSpinner(context, true);
        HashMap<String, RequestBody> data = new HashMap<>();
        data.put("name", createRequestBody(binding.etName.getText().toString().trim()));
        data.put("email", createRequestBody(binding.etEmail.getText().toString().trim()));

        MultipartBody.Part image = null;
        if (pathMain != null && !pathMain.equals("")) {
            File file = new File(pathMain);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            image = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        }
        jsonPlaceHolderApi.EditProfileAPI("Bearer " + mySharedPref.getSavedAccessToken(),data, image).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                Customprogress.showPopupProgressSpinner(context, false);
                if (response.isSuccessful()) {
                    boolean status = response.body().getStatus();
                    String msg = response.body().getMessage();
                    if (status == true) {
                      Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                      finish();
                    } else {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseSatusMessage> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
                Toast.makeText(context, "" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public RequestBody createRequestBody(@NonNull String s) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), s);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                try {
                    onSelectFromGalleryResultProfile(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResultProfile(data);
            }
        }
      /*  if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                binding.cvUserProfile.setImageURI(resultUri);
                selectedPath = Filepath.getPathFromUri(context, resultUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }*/
    }

    //************************************SELECT IMAGE*********************************************
    private void selectImage() {
        final CharSequence[] items = {"Take Photo","Choose from Library", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Upload Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA);
                    } else {
                        cameraIntent();
                    }
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (ContextCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SELECT_FILE);
                    } else {
                        galleryIntent();
                    }
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        System.out.println("GALLERY OPEN 22");
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        try {
            System.out.println("CAMERA OPEN 22");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void onSelectFromGalleryResultProfile(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BitmapDrawable d = new BitmapDrawable(bm);
        int left = 0;
        int top = 0;
        int right = 40;
        int bottom = 40;
        Rect r = new Rect(left, top, right, bottom);
        d.setBounds(r);
        Uri tempUri = getImageUri(context, bm);
        System.out.println("data.getData() " + data.getData());
        selectedPath = getRealPathFromURI(tempUri);
        System.out.println("ProfilePicPath  " + selectedPath);
        binding.cvUserProfile.setImageURI(tempUri);

    }

    private void onCaptureImageResultProfile(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), "" + System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri tempUri = getImageUri(context, thumbnail);
        selectedPath = getRealPathFromURI(tempUri);
        binding.cvUserProfile.setImageURI(tempUri);
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
            path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "" + System.currentTimeMillis(), null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentURI, projection, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = 0;
            idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }

        return result;

    }

    //***************************************DeleteAccountAPI**************************************
    public void DeleteAccountAPI() {
        Customprogress.showPopupProgressSpinner(context, true);

        jsonPlaceHolderApi.DeleteAccountAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status)
                    {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        if (Constants.isInternetConnected(context)) {
                            LogoutAPI();
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseSatusMessage> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

    //***************************************LOGOUT API**************************************
    public void LogoutAPI() {
        Customprogress.showPopupProgressSpinner(context, true);

        jsonPlaceHolderApi.LogoutAPI(Constants.CONTENT_TYPE,"Bearer " + mySharedPref.getSavedAccessToken()).enqueue(new Callback<ResponseSatusMessage>() {
            @Override
            public void onResponse(Call<ResponseSatusMessage> call, Response<ResponseSatusMessage> response) {
                if (response.isSuccessful()) {
                    Boolean status = response.body().getStatus();
                    Customprogress.showPopupProgressSpinner(context, false);
                    if (status)
                    {
                        Intent i = new Intent(getBaseContext(), LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseSatusMessage> call, Throwable t) {
                Customprogress.showPopupProgressSpinner(context, false);
                Log.e("TAG", "" + t.getMessage());
            }
        });
    }

}