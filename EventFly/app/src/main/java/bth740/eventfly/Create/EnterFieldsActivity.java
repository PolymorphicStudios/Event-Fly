package bth740.eventfly.Create;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import bth740.eventfly.MainActivity;
import bth740.eventfly.R;
import bth740.eventfly.ViewRecipeActivity;

public class EnterFieldsActivity extends MainActivity {
    static TextView date_tv, time_tv;
    protected Context THIS = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_fields);

        THIS = this;

        date_tv = (TextView) findViewById(R.id.ec_date_field_tv);
        time_tv = (TextView) findViewById(R.id.ec_time_field_tv);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_enter_fields, menu);
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


    /*
        onClick actions
     */
    public void onDateFieldClicked(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
    public void onTimeFieldClicked(View v) {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }
    public void onNextActivityClicked(View v) {
        //Add validation for time+date?
        //Store values?
        Intent intent = new Intent(THIS,SelectImageActivity.class);
        startActivity(intent);
    }


    //----------------------------------------------------------------------------------------------
    //Nested fragments within the activity.. I am a monster
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        public EditText editText;
        DatePicker dpResult;

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            date_tv.setText(String.valueOf(day) + "/" +
                            String.valueOf(month + 1) + "/" +
                            String.valueOf(year));
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        public static final String ARG_HOUR = "hour";
        public static final String ARG_MINUTE = "minute";

        private TimePickerDialog.OnTimeSetListener mListener;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            int hour, minute;
            if (getArguments() != null) {
                hour = getArguments().getInt(ARG_HOUR);
                minute = getArguments().getInt(ARG_MINUTE);
            } else {
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
            }

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }

        public void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener listener) {

            mListener = listener;
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String am_pm = "am";
            if(hourOfDay > 12){
                hourOfDay -=12;
                am_pm = "pm";
            }

            if (mListener != null) {
                mListener.onTimeSet(view, hourOfDay, minute);
            }
            time_tv.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute) + " " + am_pm);
        }
    }
}
