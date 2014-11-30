package bth740.eventfly.View;

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
import android.widget.Toast;

import java.util.Locale;

import bth740.eventfly.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactHostFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactHostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactHostFragment extends Fragment {
    Button send;

    public ContactHostFragment() {}

    public static ContactHostFragment newInstance(String param1, String param2) {
        ContactHostFragment fragment = new ContactHostFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Required stuff for fragment creation
        View rootView = inflater.inflate(R.layout.fragment_contact_host, container, false);
        String nav = getResources().getStringArray(R.array.nav_array)[0];

        int imageId = getResources().getIdentifier(nav.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());
        getActivity().setTitle(nav);

        //Our stuff to have done on creation (listeners and bindings)
        send = (Button) rootView.findViewById(R.id.send_btn);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Message Sent", Toast.LENGTH_SHORT).show();
                back();
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
    public void back(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        fragmentManager.popBackStack();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    //----------------------------------------------------------------------------------------------
    // Any required nested classes
}
