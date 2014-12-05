package bth740.eventfly.View;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;
import bth740.eventfly.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewEventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewEventFragment extends Fragment {
    Button reminderBtn, contactBtn;
    Spinner going;
    boolean isLoggedIn = true;

    public ViewEventFragment() {}

    public static ViewEventFragment newInstance(String param1, String param2) {
        ViewEventFragment fragment = new ViewEventFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Required stuff for fragment creation
        View rootView = inflater.inflate(R.layout.fragment_view_event, container, false);
        String nav = getResources().getStringArray(R.array.nav_array)[0];
        boolean isHistory = false;
        if(getArguments() != null) {
            isLoggedIn = getArguments().getBoolean("isLoggedIn");
            isHistory = getArguments().getBoolean("history") || false;
        }

        int imageId = getResources().getIdentifier(nav.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());
        getActivity().setTitle(nav);

        //Our stuff to have done on creation (listeners and bindings)

        reminderBtn = (Button) rootView.findViewById(R.id.reminder_btn);
        reminderBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Reminder added to Calendar", Toast.LENGTH_SHORT).show();
            }
        });

        contactBtn = (Button) rootView.findViewById(R.id.contact_btn);
        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContactClicked();
            }
        });

        going = (Spinner) rootView.findViewById(R.id.spinner);

        if(!isLoggedIn || isHistory) {
            reminderBtn.setVisibility(View.GONE);
            contactBtn.setVisibility(View.GONE);
            going.setVisibility(View.GONE);
        }

        //Return the view
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
    public void onContactClicked() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, new ContactHostFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack("viewEvent");
        ft.commit();
    }

    //----------------------------------------------------------------------------------------------
    // Any required nested classes
}
