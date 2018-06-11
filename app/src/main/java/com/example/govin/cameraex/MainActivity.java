package com.example.govin.cameraex;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import static com.example.govin.cameraex.AppConstants.WEB_IMAGE;

public class MainActivity extends AppCompatActivity {

    private PickImageDialog pickImageDialog;
    private String selectedFilePath = "";
    ImageView imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageview=(ImageView)findViewById(R.id.imageview);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraToUpload();


            }
        });
    }

    private void openCameraToUpload() {
        PermissionUtils.getInstance().grantPermission(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, new PermissionUtils.Callback() {
                    @Override
                    public void onFinish(HashMap<String, Integer> permissionsStatusMap) {
                        int storageStatus = permissionsStatusMap.get(Manifest.permission.CAMERA);
                        switch (storageStatus) {
                            case PermissionUtils.PERMISSION_GRANTED:
                                showImageOptionsDialog();
                                Toast.makeText(getApplicationContext(),"location :" + "granted",Toast.LENGTH_LONG);
                                break;
                            case PermissionUtils.PERMISSION_DENIED:
                                Toast.makeText(getApplicationContext(),"location :" + "denied",Toast.LENGTH_LONG);
                                break;
                            case PermissionUtils.PERMISSION_DENIED_FOREVER:
                                Toast.makeText(getApplicationContext(),"location :" + "denied forever",Toast.LENGTH_LONG);
                                break;
                            default:
                                break;
                        }
                    }
                });


    }

    private void showImageOptionsDialog() {

        pickImageDialog = new PickImageDialog(this);
        pickImageDialog.mImageHandlingDelegate = pickImageDialogInterface;
        pickImageDialog.initDialogLayout();
    }

    private PickImageDialogInterface pickImageDialogInterface = new PickImageDialogInterface() {
        @Override
        public void handleIntent(Intent intent, int  requestCode) {
            if (requestCode == AppConstants.RequestCodes.SEND_IMAGE_PATH) { // loading image in full screen
                if (TextUtils.isEmpty(selectedFilePath)) {
                  //  showErrorMessage(getString(R.string.error_image_path_req));
                } else {
                    intent.putExtra(AppConstants.IntentConstants.IMAGE_PATH, selectedFilePath);
                    startActivity(intent);
                }
                return;
            }
            startActivityForResult(intent, requestCode);
        }

        @Override
        public void displayPickedImage(String uri, int requestCode) {
            selectedFilePath = uri;
            loadImageUsingGlide(selectedFilePath, imageview);


        }
    };
    public void loadImageUsingGlide(String imagePath, ImageView imageView) {

        if (imagePath.contains(WEB_IMAGE)) {
            Glide.with(this).load(imagePath).into(imageView);
            return;
        }
        Glide.with(this).load(new File(imagePath))
                .into(imageView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case AppConstants.RequestCodes.TERMS_AND_CONDITIONS:
                    break;
                case AppConstants.RequestCodes.ADDRESS_LOCATION:
                    break;
                default:
                    pickImageDialog.onActivityResult(requestCode, resultCode, data);
                    break;
            }
        }

    }
}
