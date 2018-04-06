package com.example.pc.componentsdemo.Fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.componentsdemo.Others.CurrentLocation;
import com.example.pc.componentsdemo.Others.DirectionsJSONParser;
import com.example.pc.componentsdemo.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.example.pc.componentsdemo.Others.CurrentLocation.getmCurrentLocation;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    private MapView mMapView;
    private GoogleMap googleMap;
    private ImageView ivLocation;
    private TextView txtAddress;
    private Geocoder geocoder;
    private PlaceAutocompleteFragment autocompleteFragment;

    private List<Address> addresses;
    private ArrayList markerPoints = new ArrayList();
    private Marker mMarker;


    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String knownName;

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) view.findViewById(R.id.mapView);
        txtAddress = (TextView) view.findViewById(R.id.txtaddress);
        ivLocation = (ImageView) view.findViewById(R.id.ivlocation);
        autocompleteFragment = (PlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.autoCompleteFrag);

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately

        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        // alLatLng = new ArrayList<>();

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {

                googleMap = mMap;

                googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

                    @Override
                    public void onMarkerDragStart(Marker marker) {
                        // marker.setDraggable(true);
                        Log.d("System out", "onMarkerDragStart..." + marker.getPosition().latitude + "..." + marker.getPosition().longitude);
                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {
                        //marker.setDraggable(true);
                        Log.d("System out", "onMarkerDrag..." + marker.getPosition().latitude + "..." + marker.getPosition().longitude);
                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {

                        double lat = marker.getPosition().latitude;
                        double lng = marker.getPosition().longitude;
                        LatLng latLng = new LatLng(lat, lng);

                        if (markerPoints.size() >= 2) {
                            //  markerPoints.clear();
                        }

                        markerPoints.add(latLng);

                        Log.d("System out", "onMarkerDragEnd..." + lat + "..." + lng);
                        try {
                            addresses = geocoder.getFromLocation(lat, lng, 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        autocompleteFragment.setText("");

                        getAddress();
                        mMarker.remove();
                        
                        mMarker = googleMap.addMarker(new MarkerOptions().position(latLng).title("Your are here").draggable(true));

                        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(14).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        txtAddress.setText("Lat : " + lat + "\nLng : " + lng + " \nAddress : " + address +
                                " City : " + city + " State : " + state + " Country : " + country + " Postalcode : " + postalCode);
                    }
                });
            }
        });


        ivLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                markerPoints.clear();
                googleMap.clear();
                autocompleteFragment.setText("");

                CurrentLocation location = new CurrentLocation(getActivity());
                location.checkPermission();

                final Location loc = getmCurrentLocation();

                if (loc != null) {

                    double lat = getmCurrentLocation().getLatitude();
                    double lng = getmCurrentLocation().getLongitude();

                    LatLng latLng = new LatLng(lat, lng);
                    markerPoints.add(latLng);

                    try {
                        addresses = geocoder.getFromLocation(lat, lng, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    getAddress();

                    mMarker = googleMap.addMarker(new MarkerOptions().position(latLng).title("Your are here").draggable(true));

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(14).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    txtAddress.setText("Lat : " + lat + "\nLng : " + lng + " \nAddress : " + address +
                            " City : " + city + " State : " + state + " Country : " + country + " Postalcode : " + postalCode);

                } else {
                    Toast.makeText(getActivity(), "Current Location not found", Toast.LENGTH_SHORT).show();
                }

            }
        });


        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();

        autocompleteFragment.setFilter(typeFilter);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                Log.d("TAG", "Place: " + place.getName() + "Latlng" + place.getLatLng());

                if (markerPoints.size() > 2) {
                    markerPoints.clear();
                }

                LatLng latLng = place.getLatLng();
                markerPoints.add(latLng);

                txtAddress.setText(place.getAddress());

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(latLng);
                options.title("" + place.getAddress());
                options.snippet("");

               /* if (markerPoints.size() == 1) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else if (markerPoints.size() == 2) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }*/

                // Add new marker to the Google Map Android API V2
                googleMap.addMarker(options);

                // Checks, whether start and end locations are captured
                if (markerPoints.size() == 2) {
                    LatLng origin = (LatLng) markerPoints.get(0);
                    LatLng dest = (LatLng) markerPoints.get(1);

                    // Getting URL to the Google Directions API
                    String url = getDirectionsUrl(origin, dest);

                    DownloadTask downloadTask = new DownloadTask();

                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                } else {
                    markerPoints.clear();
                }

                /*//to set marker after place selected and zoom it
                googleMap.clear();
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Your selected place").snippet("\t\t\t" + country).draggable(true));*/

                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(14).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(getActivity(), "Place not found", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "An error occurred: " + status);

            }
        });

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void getAddress() {


        address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
        city = addresses.get(0).getLocality();
        state = addresses.get(0).getAdminArea();
        country = addresses.get(0).getCountryName();
        postalCode = addresses.get(0).getPostalCode();
        knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);

        }
    }


    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);

            }

            if (lineOptions != null) {
                // Drawing polyline in the Google Map for the i-th route
                googleMap.addPolyline(lineOptions);
            }
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }


}
