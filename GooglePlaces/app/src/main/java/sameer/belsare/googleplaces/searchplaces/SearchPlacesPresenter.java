package sameer.belsare.googleplaces.searchplaces;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import sameer.belsare.googleplaces.Constants;
import sameer.belsare.googleplaces.R;
import sameer.belsare.googleplaces.placedetails.PlaceDetailsActivity;

/**
 * Created by sameerbelsare on 14/04/17.
 */

public class SearchPlacesPresenter implements SearchPlacesContract.SearchPlacesPresenter {
    private final String TAG = "SearchPlacesPresenter";
    private Fragment mView = null;
    private final int PLACES_AUTOCOMPLETE_REQUEST_CODE = 1;
    private FragmentActivity mActivity;

    @Override
    public void setView(SearchPlacesContract.SearchPlacesView view) {
        mView = (Fragment) view;
        mActivity = mView.getActivity();
    }

    @Override
    public void startPlaceSelector() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(mActivity);
            mView.startActivityForResult(intent, PLACES_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search:
                checkPermissions();
                break;
        }
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE})) {
                startPlaceSelector();
            }
        } else {
            startPlaceSelector();
        }
    }

    public boolean checkPermissions(String[] permissions) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(mActivity, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(mActivity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), Constants.PERMISSION_REQUEST);
            return false;
        }
        return true;
    }

    @Override
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACES_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(mActivity, data);
                Log.i(TAG, "Place: " + place.getName());
                Intent intent = new Intent(mView.getContext(), PlaceDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                LatLng latLng = place.getLatLng();
                intent.putExtra("latLng", latLng.latitude+","+latLng.longitude);
                mView.getContext().startActivity(intent);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(mActivity, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
