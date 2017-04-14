package sameer.belsare.googleplaces.searchplaces;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import sameer.belsare.googleplaces.placedetails.PlaceDetailsActivity;

/**
 * Created by sameerbelsare on 14/04/17.
 */

public class SearchPlacesPresenter implements SearchPlacesContract.SearchPlacesPresentor {
    private final String TAG = "SearchPlacesPresenter";
    private Fragment mView = null;
    private final int PLACES_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    public void setView(SearchPlacesContract.SearchPlacesView view) {
        mView = (Fragment) view;
    }

    @Override
    public void startPlaceSelector() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(mView.getActivity());
            mView.startActivityForResult(intent, PLACES_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACES_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(mView.getActivity(), data);
                Log.i(TAG, "Place: " + place.getName());
                Intent intent = new Intent(mView.getContext(), PlaceDetailsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("placeID", place.getId());
                mView.getContext().startActivity(intent);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(mView.getActivity(), data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
