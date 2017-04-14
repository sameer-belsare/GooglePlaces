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

    interface SearchPlacesPresentor extends View.OnClickListener {

        void setView(SearchPlacesView view);

        void invokePlaceSelector();

        void handleactivityResult(int requestCode, int resultCode, Intent data);

    }
}
