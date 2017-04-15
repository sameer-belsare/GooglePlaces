package sameer.belsare.googleplaces.placedetails;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import sameer.belsare.googleplaces.BaseActivity;
import sameer.belsare.googleplaces.R;

/**
 * Created by sameerbelsare on 14/04/17.
 */

public class PlaceDetailsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_place_details);

        PlaceDetailsFragment addEditTaskFragment =
                (PlaceDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        String placeId = getIntent().getStringExtra("placeID");
        if (addEditTaskFragment == null) {
            PlaceDetailsFragment placeDetailsFragment = new PlaceDetailsFragment();
            placeDetailsFragment.setID(placeId);
            addFragmentSupport(placeDetailsFragment, false, R.id.contentFrame, PlaceDetailsFragment.class.getSimpleName(), false);
        }
    }
}
