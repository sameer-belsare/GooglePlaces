package sameer.belsare.googleplaces;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by sameerbelsare on 14/04/17.
 */

public class BaseActivity extends AppCompatActivity {

    public void addFragmentSupport(Fragment fragment, boolean addToBackStack, int containerId, String tag, boolean showTransition) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (showTransition) {
            transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        }

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(containerId);
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        transaction.add(containerId, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }

        transaction.commitAllowingStateLoss();
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

    public static void launchApplicationSetting(Activity activity) {
        Intent appIntent = new Intent();
        appIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        appIntent.addCategory(Intent.CATEGORY_DEFAULT);
        appIntent.setData(Uri.parse("package:" + activity.getPackageName()));
        appIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        activity.startActivity(appIntent);
    }
}
