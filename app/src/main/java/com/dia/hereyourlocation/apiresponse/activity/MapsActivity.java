package com.dia.hereyourlocation.apiresponse.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.dia.hereyourlocation.R;
import com.dia.hereyourlocation.apiresponse.application.ParselApplication;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private final String _KEY_LOCALITY_ = "locality";
    private final String _KEY_ADDRESS_ = "address";

    private static final String TAG = MapsActivity.class.getName();
    private GoogleMap map;
    private Marker marker;

    private static final long DEFAULT_UPDATE_LOCATION_INTERVAL = 5 * 1000;
    private static final long DEFAULT_UPDATE_LOCATION_FAST_INTERVAL = 5 * 1000;
    private static final long DEFAULT_TERMINATE_SAT_FINDING = 1 * 60 * 60 * 1000;

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    Location mPreviousLocation;

    GetAddressTask getAddressTask;

    ParselApplication appObj;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(DEFAULT_UPDATE_LOCATION_INTERVAL);
        mLocationRequest.setFastestInterval(DEFAULT_UPDATE_LOCATION_FAST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        appObj = (ParselApplication) getApplicationContext();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        createLocationRequest();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

    }

    private void updateLocation(LatLng mapCenter, HashMap<String, String> addressMap) {


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)

            if (this.marker != null) {
                marker.remove();
            }
        map.setMyLocationEnabled(true);

        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));

        String title = addressMap != null ? addressMap.get(_KEY_LOCALITY_) : "";
        String snippet = addressMap != null ? addressMap.get(_KEY_ADDRESS_) : "";


        this.marker = map.addMarker(new MarkerOptions()
                .title(!TextUtils.isEmpty(title) ? title : "")
                .snippet(!TextUtils.isEmpty(snippet) ? snippet : "")
                .position(mapCenter)
                .flat(true)
                .rotation(90));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(mapCenter)
                .zoom(13)
                .bearing(90)
                .build();


        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);


        if (mPreviousLocation != null)
            options.add(new LatLng(mPreviousLocation.getLatitude(), mPreviousLocation.getLongitude()));

        options.add(mapCenter);

        map.addPolyline(options);

        // Animate the change in camera view over 2 seconds
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                2000, null);

        mPreviousLocation = mCurrentLocation;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
            Log.d(TAG, "Location update started ..............: ");
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");

        mCurrentLocation = location;


        if (mCurrentLocation != null) {

            appObj.setCurrentLocation(mCurrentLocation);

            if (isLocationChanged(mCurrentLocation)) {

                //first time task execution
                if (getAddressTask == null) {
                    getAddressTask = new GetAddressTask(mCurrentLocation);
                    getAddressTask.execute();
                } else {
                    if (getAddressTask.getStatus() == AsyncTask.Status.FINISHED || getAddressTask.isCancelled()) {
                        getAddressTask = new GetAddressTask(mCurrentLocation);
                        getAddressTask.execute();
                    }
                }
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }

        Location currentLocation = appObj.getCurrentLocation();
        HashMap<String, String> currentAddress = appObj.getCurrentAddress();

        if (currentLocation != null && currentAddress != null) {
            this.updateLocation(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), currentAddress);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart fired ..............");
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop fired ..............");
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

        mCurrentLocation = null;
        mPreviousLocation = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy fired ..............");
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

        mCurrentLocation = null;
        mPreviousLocation = null;

    }

    private HashMap<String, String> getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        HashMap<String, String> addressMap = new HashMap<>();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);

                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }

                addressMap.put(_KEY_LOCALITY_, !TextUtils.isEmpty(returnedAddress.getLocality()) ? returnedAddress.getLocality() : "");
                addressMap.put(_KEY_ADDRESS_, !TextUtils.isEmpty(strReturnedAddress.toString()) ? strReturnedAddress.toString() : "");

                Log.w("Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.w("Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Current Loc address", "Canont get Address!");
        }
        return addressMap;
    }

    private boolean isLocationChanged(Location mCurrentLocation) {
        if (mPreviousLocation == null) {
            return true;
        }

        return (mCurrentLocation.getLatitude() != mPreviousLocation.getLatitude()) && (mCurrentLocation.getLongitude() != mPreviousLocation.getLongitude());
    }

    private class GetAddressTask extends AsyncTask<Void, Void, HashMap<String, String>> {

        public boolean isTaskFinished;

        Location location;

        public GetAddressTask(Location location) {
            this.location = location;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isTaskFinished = false;
        }

        @Override
        protected HashMap<String, String> doInBackground(Void... voids) {
            isTaskFinished = false;
            return getCompleteAddressString(this.location.getLatitude(), this.location.getLongitude());
        }

        @Override
        protected void onPostExecute(final HashMap<String, String> address) {
            super.onPostExecute(address);

            LatLng mapCenter = new LatLng(location.getLatitude(), location.getLongitude());

            //LatLng mapCenter = ((ParselApplication) getApplicationContext()).getMapCenter();

            updateLocation(mapCenter, address);
            isTaskFinished = true;
        }
    }

    @Override
    public void onBackPressed() {
        //exit application
        this.finishAffinity();
    }


}

