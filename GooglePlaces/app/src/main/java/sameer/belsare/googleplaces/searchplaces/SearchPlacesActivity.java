package sameer.belsare.googleplaces.searchplaces;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sameer.belsare.googleplaces.BaseActivity;
import sameer.belsare.googleplaces.R;

public class SearchPlacesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_places);
        SearchPlacesFragment searchPlacesFragment =
                (SearchPlacesFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (searchPlacesFragment == null) {
            loadFragment(SearchPlacesFragment.newInstance());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        SearchPlacesFragment.newInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
