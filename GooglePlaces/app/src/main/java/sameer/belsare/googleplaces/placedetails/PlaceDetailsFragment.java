package sameer.belsare.googleplaces.placedetails;

import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import sameer.belsare.googleplaces.R;
import sameer.belsare.googleplaces.databinding.FragmentPlaceDetailsBinding;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by sameer.belsare on 15/4/17.
 */

public class PlaceDetailsFragment extends Fragment implements PlaceDetailsContract.PlaceDetailsView, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {
    private FragmentPlaceDetailsBinding mFragmentPlaceDetailsBinding;
    private String mLatLng;
    private PlaceDetailsActivity mActivityReference;
    private PlaceDetailsPresenter mPresenter;
    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentPlaceDetailsBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_place_details, container, false);
        init();
        return mFragmentPlaceDetailsBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivityReference = (PlaceDetailsActivity) getActivity();
    }

    private void init() {
        mPresenter = new PlaceDetailsPresenter();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 2);
        gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFragmentPlaceDetailsBinding.rvPhotos.setLayoutManager(gridLayoutManager);

        mPresenter.setViewAndData(this, mLatLng);
        mFragmentPlaceDetailsBinding.rvPhotos.setAdapter(mPresenter.getAdaptor());
        mPresenter.loadPlacePhotos();
    }

    public void setLatLng(String latLng) {
        mLatLng = latLng;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);

        Location location = getCurrentLocation();
        if(location != null) {
            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(currentLocation).title("My Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    private Location getCurrentLocation() {
        long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
        long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
        Location location = null;
        LocationManager locationManager = (LocationManager) mActivityReference
                .getSystemService(LOCATION_SERVICE);
        boolean checkGPS = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean checkNetwork = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!checkGPS && !checkNetwork) {
            Toast.makeText(mActivityReference, getString(R.string.no_service_available), Toast.LENGTH_SHORT).show();
        } else {
            if (checkNetwork) {
                try {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, mPresenter);
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    }

                    if (location != null) {
                        return location;
                    }
                } catch (SecurityException e) {

                }
            }
        }
        if (checkGPS) {
            if (location == null) {
                try {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, mPresenter);
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            return location;
                        }
                    }
                } catch (SecurityException e) {

                }
            }
        }
        return location;
    }
}
