package sameer.belsare.googleplaces;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by sameerbelsare on 14/04/17.
 */

public class BaseActivity extends AppCompatActivity {

    protected void loadFragment(Fragment frg){
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFrame, frg).addToBackStack(frg.getClass().getName()).commit();
    }

    public Fragment getVisibleFragment() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
