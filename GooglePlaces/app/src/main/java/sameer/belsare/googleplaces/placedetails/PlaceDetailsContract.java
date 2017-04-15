package sameer.belsare.googleplaces.placedetails;

import android.content.Intent;
import android.location.LocationListener;

/**
 * Created by sameer.belsare on 15/4/17.
 */

public class PlaceDetailsContract {
    interface PlaceDetailsView {

    }

    interface PlaceDetailsPresenter extends LocationListener {

        void setViewAndData(PlaceDetailsView view, String id);

        void loadPlacePhotos();

        void handleActivityResult(int requestCode, int resultCode, Intent data);

    }
}
