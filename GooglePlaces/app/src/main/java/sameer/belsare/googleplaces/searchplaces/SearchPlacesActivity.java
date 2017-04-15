package sameer.belsare.googleplaces.searchplaces;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import sameer.belsare.googleplaces.BaseActivity;
import sameer.belsare.googleplaces.R;

public class SearchPlacesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_search_places);
        SearchPlacesFragment searchPlacesFragment =
                (SearchPlacesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (searchPlacesFragment == null) {
            addFragmentSupport(new SearchPlacesFragment(), false, R.id.contentFrame, SearchPlacesFragment.class.getSimpleName(), false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Fragment visibleFragment = getVisibleFragment();
        if (visibleFragment != null && visibleFragment instanceof SearchPlacesFragment) {
            visibleFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
