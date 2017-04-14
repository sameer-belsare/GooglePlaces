package sameer.belsare.googleplaces.searchplaces;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import sameer.belsare.googleplaces.Constants;
import sameer.belsare.googleplaces.R;

/**
 * Created by sameerbelsare on 14/04/17.
 */

public class SearchPlacesFragment extends Fragment implements SearchPlacesContract.SearchPlacesView, View.OnClickListener {
    private SearchPlacesPresenter mPresentor;

    public SearchPlacesFragment() {
    }

    public static SearchPlacesFragment newInstance() {
        return new SearchPlacesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_places, container, false);
        mPresentor = new SearchPlacesPresenter();
        mPresentor.setView(this);
        Button btnSearch = (Button) root.findViewById(R.id.search);
        btnSearch.setOnClickListener(this);
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresentor.handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search:
                checkLocationPermission();
                break;
        }
    }

    private void checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION);
            if (result == PackageManager.PERMISSION_GRANTED) {
                mPresentor.startPlaceSelector();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Constants.PERMISSION_REQUEST_ACCESS_LOCATION);
            }
        } else {
            mPresentor.startPlaceSelector();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.PERMISSION_REQUEST_ACCESS_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresentor.startPlaceSelector();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        permissions[0])) {
                    Toast.makeText(getActivity(), getString(R.string.msg_permission_denied), Toast.LENGTH_SHORT).show();
                } else {
/*
                    Snackbar mSnackBar = Snackbar.make(sosSettingsFragmentBinding.getRoot(),  getString(R.string.enable_permission),
                            Snackbar.LENGTH_LONG);
                    snackBar.setAction(R.string.action_enable, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Utilities.launchApplicationSetting(mHomeActivityReference);
                        }
                    });
                    snackBar.show();*/
                }
            }
        }
    }
}
