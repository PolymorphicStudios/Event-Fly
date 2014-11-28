package bth740.eventfly;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import bth740.eventfly.Create.EnterFieldsActivity;


public class MainActivity extends Activity {
    Button logoutBtn;
    TextView actionTitleText;
    Button viewrepBtn;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setCustomView(R.layout.action_buttons);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText("Create").setTabListener(new ActionBar.TabListener() {
                    @Override
                    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                        Toast.makeText(getBaseContext(), "CREATE Clicked", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, EnterFieldsActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

                    }

                    @Override
                    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

                    }
                })
        );

        actionBar.addTab(actionBar.newTab().setText("History").setTabListener(new ActionBar.TabListener() {
                    @Override
                    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                        Toast.makeText(getBaseContext(), "HISTORY Clicked", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

                    }

                    @Override
                    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

                    }
                })
        );

        actionBar.addTab(actionBar.newTab().setText("Near Me").setTabListener(new ActionBar.TabListener() {
                    @Override
                    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                        Toast.makeText(getBaseContext(), "NEARME Clicked", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

                    }

                    @Override
                    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

                    }
                })
        );

        /*actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);*/

        actionTitleText = (TextView) actionBar.getCustomView().findViewById(R.id.action_title_txt);
        actionTitleText.setText("Event Fly Main");

        logoutBtn = (Button) actionBar.getCustomView().findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Logout Clicked", Toast.LENGTH_LONG).show();
            }
        });

        //end of actionbar stuff

        viewrepBtn = (Button) findViewById(R.id.view_rep_btn);
        viewrepBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "View Rep Clicked", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, ViewRecipeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Toast.makeText(getBaseContext(), "Action Logout Clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

}
