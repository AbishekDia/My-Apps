package com.dia.hereyourlocation.apiresponse.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dia.hereyourlocation.R;
import com.dia.hereyourlocation.apiresponse.application.ParselApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.os.Build.VERSION.SDK;

/**
 * Created by abishek on 2/14/2017.
 */

public class SplashScreenActivity extends AppCompatActivity {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    final String _PERMISSION_FINE_LOCATION_STR_ = "permission_fine_location";
    final String _PERMISSION_COARSE_LOCATION_STR_ = "permission_coarse_location";
    final String _PERMISSION_WRITE_EXTERNAL_STORAGE_STR_ = "permission_external_storage";
    final String _PERMISSION_GET_ACCOUNTS_STR_ = "permission_get_accounts";
    final String _PERMISSION_READ_CONTACTS_STR_ = "permission_read_contacts";
    final String _PERMISSION_WRITE_CONTACTS_STR_ = "permission_write_contacts";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkAppPermissions(getAppPermisssionsMap());
    }

    @Override
    protected void onResume() {
        super.onResume();
        //checkAppPermissions(getAppPermisssionsMap());
    }

    private void init(boolean isLoggedIn) {

        Class<?> activityClass;
        if (isLoggedIn) {
            activityClass = MapsActivity.class;
        } else {
            activityClass = LoginActivity.class;
        }

        startActivity(new Intent(this, activityClass));
    }

    private void checkAppPermissions(HashMap<String, String> permissionsMap) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> listPermissionsNeeded = new ArrayList<>();
            final int _permission_granted_ = PackageManager.PERMISSION_GRANTED;

            List<String> keys = new ArrayList<>(permissionsMap.keySet());

            for (String key : keys) {
                if (ContextCompat.checkSelfPermission(this, permissionsMap.get(key)) != _permission_granted_) {
                    listPermissionsNeeded.add(permissionsMap.get(key));
                }
            }

            if (listPermissionsNeeded.size() > 0) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            } else {
                init(((ParselApplication) getApplicationContext()).isLoggedIn());
            }
        } else {
            init(((ParselApplication) getApplicationContext()).isLoggedIn());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        int permissionGranted = PackageManager.PERMISSION_GRANTED;
        HashMap<String, String> pendingPermissions = new HashMap<>();
        List<String> permissionKeys = new ArrayList<>(getAppPermisssionsMap().keySet());

        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS:

                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != permissionGranted) {
                        String key = permissionKeys.get(i);
                        pendingPermissions.put(key, getAppPermisssionsMap().get(key));
                    }
                }

                break;
        }

        if (pendingPermissions.size() > 0) {
            Toast.makeText(this, getString(R.string.accept_missing_permissions), Toast.LENGTH_LONG).show();
            checkAppPermissions(pendingPermissions);
        } else {
            init(((ParselApplication) getApplicationContext()).isLoggedIn());
        }

    }

    private HashMap<String, String> getAppPermisssionsMap() {
        HashMap<String, String> appPermissionsMap = new HashMap<>();

        appPermissionsMap.put(_PERMISSION_FINE_LOCATION_STR_, Manifest.permission.ACCESS_FINE_LOCATION);
        appPermissionsMap.put(_PERMISSION_COARSE_LOCATION_STR_, Manifest.permission.ACCESS_COARSE_LOCATION);
        appPermissionsMap.put(_PERMISSION_WRITE_EXTERNAL_STORAGE_STR_, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        appPermissionsMap.put(_PERMISSION_GET_ACCOUNTS_STR_, Manifest.permission.GET_ACCOUNTS);
        appPermissionsMap.put(_PERMISSION_READ_CONTACTS_STR_, Manifest.permission.READ_CONTACTS);
        appPermissionsMap.put(_PERMISSION_WRITE_CONTACTS_STR_, Manifest.permission.WRITE_CONTACTS);

        return appPermissionsMap;
    }
}
