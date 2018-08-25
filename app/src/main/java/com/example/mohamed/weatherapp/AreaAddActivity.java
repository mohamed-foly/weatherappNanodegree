package com.example.mohamed.weatherapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.weatherapp.DB.AppDatabase;
import com.example.mohamed.weatherapp.Interfaces.AddNewAreaInterface;
import com.example.mohamed.weatherapp.Models.AreaModel;
import com.example.mohamed.weatherapp.Tasks.AddNewArea;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AreaAddActivity extends AppCompatActivity implements
        OnMapReadyCallback,AddNewAreaInterface {

    private GoogleMap mMap;
    private static final int PermissionRequest = 1;
    private FusedLocationProviderClient mFusedLocationClient;
    private double markerLat , markerLong ;
    private String markerAreaName, locationName ;
    private TextView locationNameTv ,areaNameTV;
    GoogleMap.OnMarkerDragListener onMarkerDragListener;
    MarkerOptions markerOptions;
    private AppDatabase appDatabase;
    //check for internet status
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_add);
        appDatabase = AppDatabase.getDatabase(this.getApplication());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (!isGooglePlayServicesAvailable(AreaAddActivity.this)){
                    finish();
        }


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        mFusedLocationClient = new FusedLocationProviderClient(this );

        locationNameTv = findViewById(R.id.current_location_name);
        areaNameTV = findViewById(R.id.current_area_name);

        markerOptions =new MarkerOptions().position(new LatLng(0, 0))
                .title("Chosen Area")
                .draggable(true);




onMarkerDragListener = new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Log.e("WeatherApp" , "MarkerDragStarted");
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                Log.e("WeatherApp" , "MarkerDrag");
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Log.e("WeatherApp" , "MarkerDragEnd");

                updateMarkerAreaName(marker.getPosition().latitude,marker.getPosition().longitude);

            }
        };
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.area_add_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_save:
  //              Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
//                Log.e("WeatherApp","Lat: "+String.valueOf(markerLat)+" Long:"+String.valueOf(markerLong));
                AreaModel areaModel = new AreaModel(markerAreaName,markerLat,markerLong,new Date());
                AddNewArea addNewArea = new AddNewArea(appDatabase,this);
                addNewArea.execute(areaModel);
                finish();
                break;
            default:
                break;
        }
        return true;
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMyLocationButtonClickListener(onMyLocationButtonClickListener);
        mMap.addMarker(markerOptions);
        mMap.setOnMarkerDragListener(onMarkerDragListener);

        enableMyLocationIfPermitted();

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(11);
        getLastLocation();
    }

    private void enableMyLocationIfPermitted() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    PermissionRequest);
        } else if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    private GoogleMap.OnMyLocationButtonClickListener onMyLocationButtonClickListener =
            new GoogleMap.OnMyLocationButtonClickListener() {
                @Override
                public boolean onMyLocationButtonClick() {

                    getLastLocation();
                    mMap.setMinZoomPreference(15);

                    return false;
                }
            };


private void getLastLocation(){

    if (ActivityCompat.checkSelfPermission(AreaAddActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        //return;
    }

    mFusedLocationClient.getLastLocation()
            .addOnSuccessListener(AreaAddActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object

                        locationName =  AreaName(location.getLatitude(),location.getLongitude());
                        locationNameTv.setText(locationName);
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 10);
                        mMap.animateCamera(cameraUpdate);
                        updateMarker(location);

                    }
                }
            });
}


private void updateMarker (Location location){
    mMap.clear();
    markerLat = location.getLatitude();
    markerLong = location.getLongitude();

    markerOptions.position(new LatLng(location.getLatitude(), location.getLongitude()));
    updateMarkerAreaName(location.getLatitude(),location.getLongitude());
    mMap.addMarker(markerOptions);

}

private void   updateMarkerAreaName(double lat, double lng){
    markerAreaName =  AreaName(lat,lng);
    areaNameTV.setText(markerAreaName);

}

    public String AreaName(double lat, double lng) {
        Geocoder geocoder = new Geocoder(AreaAddActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            String countryName = addresses.get(0).getCountryName();
            String cityName = addresses.get(0).getLocality();
            if (countryName != null){
                Log.e("WeatherApp", countryName);
            }
            if (cityName != null){
                Log.e("WeatherApp", cityName);
            }


            return countryName+", "+cityName;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if(status != ConnectionResult.SUCCESS) {
            if(googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void OnAreaAdded(Long Result) {
    if (Result > 0){
        Toast.makeText(this, "Area Added Successfully", Toast.LENGTH_SHORT).show();
    }else {
        Toast.makeText(this, "Failed To Add Area", Toast.LENGTH_SHORT).show();
    }


    }
}
