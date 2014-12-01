package bth740.eventfly.Profile;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Locale;

import bth740.eventfly.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    public static final String ARG_NAV_NUMBER = "nav_number";

    public ProfileFragment() {}

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        //Required stuff for fragment creation
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        int i = getArguments().getInt(ARG_NAV_NUMBER);
        String nav = getResources().getStringArray(R.array.nav_array)[i];

        int imageId = getResources().getIdentifier(nav.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());
        getActivity().setTitle(nav);

        //Our stuff to have done on creation (listeners and bindings)


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

}
