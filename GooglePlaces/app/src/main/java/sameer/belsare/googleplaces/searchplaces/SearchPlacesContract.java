package sameer.belsare.googleplaces.searchplaces;

import android.content.Intent;
import android.view.View;

/**
 * Created by sameerbelsare on 14/04/17.
 */

public interface SearchPlacesContract {
    interface SearchPlacesView {

    }

    interface SearchPlacesPresenter extends View.OnClickListener {

        void setView(SearchPlacesView view);

        void startPlaceSelector();

        void handleActivityResult(int requestCode, int resultCode, Intent data);
    }
}
