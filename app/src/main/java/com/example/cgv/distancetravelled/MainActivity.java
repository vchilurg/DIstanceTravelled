package com.example.cgv.distancetravelled;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button b, b1;
    double lat=0, lng=0, lt=0, lg=0, lt_new = 0,lg_new = 0;
    private LocationManager manager;
    private LocationListener listener;
    Location loc1 = new Location("");
    Location loc2 = new Location("");
    private Handler handler = new Handler();
    //private Handler handler2 = new Handler();
    float d = 0, Total = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler.removeCallbacks(r);
        handler.postDelayed(r, 15000);

        b = (Button) findViewById(R.id.button);
        b1 = (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Total = 0000;
            }
        });

        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                TextView T1 = (TextView) findViewById(R.id.textView2);


                T1.setText("loc1\n"+"lt" + lat + "lg:"+lng);
                lat = location.getLatitude();
                lng = location.getLongitude();
                loc2.setLatitude(lat);
                loc2.setLongitude(lng);
                Distance();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);

            }
        };




        configure_button();

    }


    final Runnable r = new Runnable()
    {   public void run()
    {
        location();
        handler.postDelayed(this, 15000);
    }

    };



    private void location() {
        lt = lat;
        lg = lng;
        TextView T = (TextView) findViewById(R.id.textView);
        T.setText("loc1\n"+"lt: " +lt+ "lg: "+lg);
        loc1.setLatitude(lt);
        loc1.setLongitude(lg);
        if(d > 500)
            Total = 0;
        else
            Total = Total + d;
        TextView TotalDist = (TextView) findViewById(R.id.textView4);
        TotalDist.setText("Total D: " + Total+" m");

    }


    private void Distance() {

        d = loc1.distanceTo(loc2);
        TextView Distance = (TextView) findViewById(R.id.textView3);
        Distance.setText("Dist every 15s: " + d+" m");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    1
            );
            //return;
        }
        //manager.requestLocationUpdates("gps", 0, 0, listener);
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission
                manager.requestLocationUpdates("gps", 0, 0, listener);
            }
        });

    }


}
