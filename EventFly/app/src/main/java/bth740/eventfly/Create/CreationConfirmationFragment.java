package bth740.eventfly.Create;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Locale;

import bth740.eventfly.R;
import bth740.eventfly.View.ViewEventFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreationConfirmationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreationConfirmationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreationConfirmationFragment extends Fragment {
    Button goToEventBtn;
    public CreationConfirmationFragment() {}

    public static CreationConfirmationFragment newInstance() {
        CreationConfirmationFragment fragment = new CreationConfirmationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Required stuff for fragment creation
        View rootView = inflater.inflate(R.layout.fragment_creation_confirmation, container, false);
        String nav = getResources().getStringArray(R.array.nav_array)[1];

        int imageId = getResources().getIdentifier(nav.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());
        getActivity().setTitle(nav);

        //Our stuff to have done on creation (listeners and bindings)
        goToEventBtn = (Button) rootView.findViewById(R.id.ec_goToEvent_b);
        goToEventBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                goToEvent();
            }
        });
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
    public void goToEvent(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, new ViewEventFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack("confirmEvent");
        ft.commit();
    }

    //----------------------------------------------------------------------------------------------
    // Any required nested classes

}