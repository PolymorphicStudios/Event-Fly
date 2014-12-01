package bth740.eventfly.Create;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

import bth740.eventfly.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EnterFieldsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EnterFieldsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnterFieldsFragment extends Fragment {
    public static final String ARG_NAV_NUMBER = "nav_number";
    static TextView date_tv, time_tv;
    static EditText title_et, location_et, date_et, time_et;
    String title, location, date, time;
    ImageView forward_iv;

    public EnterFieldsFragment() {}

    public static EnterFieldsFragment newInstance() {
        EnterFieldsFragment fragment = new EnterFieldsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_enter_fields, container, false);
        int i = getArguments().getInt(ARG_NAV_NUMBER);
        String nav = getResources().getStringArray(R.array.nav_array)[i];

        int imageId = getResources().getIdentifier(nav.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());
        getActivity().setTitle(nav);

        title_et = (EditText) rootView.findViewById(R.id.ec_title_field_et);
        location_et = (EditText) rootView.findViewById(R.id.ec_location_field_et);
        date_et  = (EditText) rootView.findViewById(R.id.ec_date_field_et);
        date_tv = (TextView) rootView.findViewById((R.id.ec_date_field_tv));
        time_et  = (EditText) rootView.findViewById(R.id.ec_time_field_et);
        time_tv = (TextView) rootView.findViewById(R.id.ec_time_field_tv);
        forward_iv = (ImageView) rootView.findViewById(R.id.ec_forward_image_iv);

        date_et.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onDateFieldClicked();
            }
        });
        date_tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { onDateFieldClicked(); }
        });

        time_et.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { onTimeFieldClicked(); }
        });
        time_tv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { onTimeFieldClicked(); }
        });
        forward_iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (validFields()){
                    onNextClicked();
                }
                else {
                    Toast.makeText(getActivity(), "Missing fields, please fill all fields marked with *", Toast.LENGTH_LONG).show();
                }
            }
        });


        return rootView;
    }

    //----------------------------------------------------------------------------------------------
    // Generated methods

    @Override
    public void onAttach(Activity activity) { super.onAttach(activity); }
    @Override
    public void onDetach() { super.onDetach(); }
    public interface OnFragmentInteractionListener { public void onFragmentInteraction(Uri uri); }

    //----------------------------------------------------------------------------------------------
    // My methods for the fragment
    private boolean validFields() {
        boolean isValid = true;
        title = title_et.getText().toString().trim();
        location = location_et.getText().toString().trim();
        date = date_tv.getText().toString().trim();
        time = time_tv.getText().toString().trim();
        if (title.equals("") || location.equals("") || date.equals("") || time.equals(""))
            isValid = false;
        return isValid;
    }

    public void onDateFieldClicked() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void onTimeFieldClicked() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void onNextClicked() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, new SelectImageFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack("enterFields");
        ft.commit();
    }

    //----------------------------------------------------------------------------------------------
    //Nested fragments within the fragment.. I am a monster
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

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
            String min = String.valueOf(minute);
            if(hourOfDay > 12){
                hourOfDay -=12;
                am_pm = "pm";
            }
            if (minute < 10) {
                min = "0"+min;
            }

            if (mListener != null) {
                mListener.onTimeSet(view, hourOfDay, minute);
            }
            time_tv.setText(String.valueOf(hourOfDay) + ":" + min + " " + am_pm);
        }
    }

}
