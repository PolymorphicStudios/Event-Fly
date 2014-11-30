package bth740.eventfly;

import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import bth740.eventfly.Create.EnterFieldsFragment;
import bth740.eventfly.Profile.HistoryFragment;
import bth740.eventfly.Profile.ProfileFragment;
import bth740.eventfly.View.EventlistFragment;
import bth740.eventfly.View.ViewEventFragment;

public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] navPages;

    private int thisPosition = 0;

    static Button loginBtn;
    TextView actionTitleText;
    boolean isDrawerOpen = false;
    static boolean isLoggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setCustomView(R.layout.action_buttons);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

        actionTitleText = (TextView) actionBar.getCustomView().findViewById(R.id.action_title_txt);
        actionTitleText.setText("Event-Fly");

        mTitle = mDrawerTitle = "Event-Fly";
        navPages = getResources().getStringArray(R.array.nav_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, navPages));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        actionTitleText = (TextView) actionBar.getCustomView().findViewById(R.id.action_title_txt);
        actionTitleText.setText(mDrawerTitle);
        actionTitleText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isDrawerOpen) {
                    mDrawerLayout.closeDrawer(mDrawerList);
                } else
                    mDrawerLayout.openDrawer(mDrawerList);
            }
        });

        // enable ActionBar app icon to behave as action to toggle nav drawer
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                actionTitleText.setText(mTitle);
                isDrawerOpen = false;
            }

            public void onDrawerOpened(View drawerView) {
                actionTitleText.setText(mDrawerTitle);
                isDrawerOpen = true;
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }

        //----------------
        loginBtn = (Button) actionBar.getCustomView().findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isLoggedIn){
                    isLoggedIn = false;
                    loginBtn.setText("Log in");
                    Toast.makeText(getBaseContext(), "Logging out...", Toast.LENGTH_SHORT).show();
                }
                else {
                    mDrawerLayout.closeDrawer(mDrawerList);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.content_frame, new LoginFragment());
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack("login");
                    ft.commit();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* The click listener for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment;
        Bundle args = new Bundle();
        boolean canChange = true;

        switch (position) {
            case 0: fragment = new EventlistFragment(); break;
            case 1: fragment = new EnterFieldsFragment(); break;
            case 2:
                if (isLoggedIn){ fragment = new HistoryFragment();}
                else {
                    Toast.makeText(getBaseContext(), "Must log in to view this page", Toast.LENGTH_SHORT).show();
                    fragment = new NavFragment();
                    canChange = false;
                }
                break;
            case 3:
                if (isLoggedIn){ fragment = new ProfileFragment();}
                else {
                    Toast.makeText(getBaseContext(), "Must log in to view this page", Toast.LENGTH_SHORT).show();
                    fragment = new NavFragment();
                    canChange = false;
                }
                break;
            default: fragment = new NavFragment(); break;
        }

        if (canChange) {
            args.putInt(NavFragment.ARG_NAV_NUMBER, position);
            fragment.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            setTitle(navPages[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            thisPosition = position;
        }
        else {
            mDrawerList.setItemChecked(thisPosition, true);
        }

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Fragment that appears in the "content_frame", shows a nav item
     */
    public static class NavFragment extends Fragment {
        public static final String ARG_NAV_NUMBER = "nav_number";

        public NavFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_nav_item, container, false);
            int i = getArguments().getInt(ARG_NAV_NUMBER);
            String nav = getResources().getStringArray(R.array.nav_array)[i];

            int imageId = getResources().getIdentifier(nav.toLowerCase(Locale.getDefault()),
                    "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(nav);
            return rootView;
        }
    }
}