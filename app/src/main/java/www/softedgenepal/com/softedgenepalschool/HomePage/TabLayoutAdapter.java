package www.softedgenepal.com.softedgenepalschool.HomePage;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import www.softedgenepal.com.softedgenepalschool.Adapters.FragmentAdapter;
import www.softedgenepal.com.softedgenepalschool.HomePage.Fragments.Calendar;
import www.softedgenepal.com.softedgenepalschool.HomePage.Fragments.Home;
import www.softedgenepal.com.softedgenepalschool.HomePage.Fragments.Notification;
import www.softedgenepal.com.softedgenepalschool.R;

public class TabLayoutAdapter {
    FragmentAdapter fragmentAdapter;

    //todo here fragments, titles and drawableImage should have equal no. of items
    private final Fragment[] fragments = new Fragment[]{new Home(), new Notification(), new Calendar()};
    private final String[] titles = new String[]{"Home", "Notification", "Calendar"};
    private final int[] drawableImage = new int[]{R.drawable.logo,R.drawable.logo,R.drawable.logo};

    public TabLayoutAdapter(Activity activity, TabLayout tabLayout, ViewPager viewPager, FragmentManager supportFragmentManager) {
        fragmentAdapter = new FragmentAdapter(activity,tabLayout,viewPager,supportFragmentManager, fragments, titles, drawableImage);
    }

    public void setTablayout(boolean toShowText) {
        fragmentAdapter.setTablayout(toShowText);
    }

    public void setIcons(){
        fragmentAdapter.setIcons();
    }

    public void disableSwipe() {
        fragmentAdapter.disableSwipe();
    }
}