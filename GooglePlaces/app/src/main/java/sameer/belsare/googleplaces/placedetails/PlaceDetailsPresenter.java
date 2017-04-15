package sameer.belsare.googleplaces.placedetails;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by sameer.belsare on 15/4/17.
 */

public class PlaceDetailsPresenter implements PlaceDetailsContract.PlaceDetailsPresenter {

    private Fragment mView;
    private String mPlaceID;
    private PhotoViewAdaptor mAdaptor;

    @Override
    public void setViewAndData(PlaceDetailsContract.PlaceDetailsView view, String id) {
        mView = (Fragment) view;
        mPlaceID = id;
    }

    @Override
    public void loadPlacePhotos() {

    }

    public PhotoViewAdaptor getAdaptor() {
        return mAdaptor;
    }

    @Override
    public void handleActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
