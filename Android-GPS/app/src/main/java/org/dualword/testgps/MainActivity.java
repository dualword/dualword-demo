package org.dualword.testgps;

import android.app.Activity;
import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.util.Iterator;

public class MainActivity extends Activity implements GpsStatus.Listener, LocationListener {

    private LocationManager locationManager;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)(findViewById(R.id.info));
        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try{
            LocationProvider mProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
            if (mProvider == null) {
                Log.e("", "Unable to get GPS_PROVIDER");
                return;
            }
            locationManager.addGpsStatusListener(this);
            locationManager.requestLocationUpdates(mProvider.getName(), 10000, 0, this);
        }catch (SecurityException e){
            Log.e("SecurityException:", "Unable to get GPS_PROVIDER");
        }
    }

    @Override
    public void onGpsStatusChanged(int event) {
        GpsStatus status = locationManager.getGpsStatus(null);
        if(event == GpsStatus.GPS_EVENT_SATELLITE_STATUS){
            Log.d("onGpsStatusChanged",event+"");
            if(status != null) {
                tv.setText("");
                Iterator<GpsSatellite> sat = status.getSatellites().iterator();
                int i = 0;
                while (sat.hasNext()) {
                    GpsSatellite satellite = sat.next();
                    tv.append((i++) + ": " + satellite.getPrn() + ", " + satellite.usedInFix() + ", " + satellite.getSnr()
                            + ", " + satellite.getAzimuth() + ", " + satellite.getElevation()+ "\n\n");
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", location.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("onStatusChanged", provider + " status:" + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("onProviderEnabled", provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("onProviderDisabled", provider);
    }
}
