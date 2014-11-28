package bth740.eventfly.Create;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import bth740.eventfly.MainActivity;
import bth740.eventfly.R;
import bth740.eventfly.ViewRecipeActivity;

public class EnterDescriptionActivity extends MainActivity {
    protected static Context THIS = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_description);

        THIS = this;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_description, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPreviousActivityClicked(View v) {
        finish();
    }

    public void onCreateEventClicked(View v) {
        Intent intent = new Intent(THIS, CreationConfirmationActivity.class);
        startActivity(intent);
    }
}
