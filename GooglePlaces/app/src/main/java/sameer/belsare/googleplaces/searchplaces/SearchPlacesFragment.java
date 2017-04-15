package sameer.belsare.googleplaces.searchplaces;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import sameer.belsare.googleplaces.Constants;
import sameer.belsare.googleplaces.R;
import sameer.belsare.googleplaces.databinding.FragmentSearchPlacesBinding;

/**
 * Created by sameerbelsare on 14/04/17.
 */

public class SearchPlacesFragment extends Fragment implements SearchPlacesContract.SearchPlacesView {
    private SearchPlacesPresenter mPresenter;
    private SearchPlacesActivity mActivityReference;
    private FragmentSearchPlacesBinding mFragmentSearchPlacesBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentSearchPlacesBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_search_places, container, false);
        return mFragmentSearchPlacesBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivityReference = (SearchPlacesActivity) getActivity();
        mPresenter = new SearchPlacesPresenter();
        mPresenter.setView(this);
        mFragmentSearchPlacesBinding.search.setOnClickListener(mPresenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Constants.PERMISSION_REQUEST_ACCESS_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.startPlaceSelector();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        permissions[0])) {
                    Toast.makeText(getActivity(), getString(R.string.msg_permission_denied), Toast.LENGTH_SHORT).show();
                } else {
                    Snackbar snackBar = Snackbar.make(mFragmentSearchPlacesBinding.getRoot(), getString(R.string.enable_permission),
                            Snackbar.LENGTH_LONG);
                    snackBar.setAction(R.string.action_enable, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mActivityReference.launchApplicationSetting(mActivityReference);
                        }
                    });
                    snackBar.show();
                }
            }
        }
    }
}
