package sameer.belsare.googleplaces.searchplaces;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by sameerbelsare on 14/04/17.
 */

public interface SearchPlacesContract {
    interface SearchPlacesView{

    }

    interface SearchPlacesPresentor {

        void setView(SearchPlacesView view);

        void startPlaceSelector();

        void handleActivityResult(int requestCode, int resultCode, Intent data);

    }
}
