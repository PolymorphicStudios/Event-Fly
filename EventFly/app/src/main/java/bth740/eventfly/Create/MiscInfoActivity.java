package bth740.eventfly.Create;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;

import java.util.ArrayList;

import bth740.eventfly.MainActivity;
import bth740.eventfly.R;

public class MiscInfoActivity extends Activity {
    protected static Context THIS = null;
    static ListView items_lv;
    NumberPicker np;
    static ArrayAdapter<String> adapter;

    static ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misc_info);

        THIS = this;

        np = (NumberPicker) findViewById(R.id.ec_max_guests_np);
        items_lv = (ListView) findViewById(R.id.ec_items_lv);

        np.setMinValue(1);
        np.setMaxValue(50);
        np.setWrapSelectorWheel(true);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        items = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items);
        adapter.setNotifyOnChange(true);

        items_lv.setAdapter((ListAdapter) adapter);

        items_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(THIS);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        items.remove(positionToRemove);
                        adapter.notifyDataSetChanged();
                    }
                });
                adb.show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_misc_info, menu);
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

    public void onAddItemClicked(View v) {
        DialogFragment newFragment = new ItemDialogFragment();
        newFragment.show(getFragmentManager(), "addItem");
    }

    public void onPreviousActivityClicked(View v) {
        finish();
    }

    public void onNextActivityClicked(View v) {
        //Store image name?
        Intent intent = new Intent(THIS, MiscInfoActivity.class);
        startActivity(intent);
    }

    //What are with all the nested classes seriously
    public static class ItemDialogFragment extends DialogFragment {
        final EditText input = new EditText(THIS);

        static ItemDialogFragment newInstance(String title) {
            ItemDialogFragment fragment = new ItemDialogFragment();
            Bundle args = new Bundle();
            args.putString("New Item", title);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            input.setSingleLine();
            input.setImeOptions(EditorInfo.IME_ACTION_DONE);
            return new AlertDialog.Builder(getActivity())
                    .setTitle("Enter item:")
                    .setView(input)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    String newItem = input.getText().toString().trim();
                                    if (newItem != "") {
                                        adapter.add(newItem);
                                    }
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            }).create();
        }
    }
}
