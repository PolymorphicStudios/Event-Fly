package bth740.eventfly.Create;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Locale;

import bth740.eventfly.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EnterDescriptionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EnterDescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnterDescriptionFragment extends Fragment {
    Button createButton;
    ImageView back_iv;

    public EnterDescriptionFragment() {}

    public static EnterDescriptionFragment newInstance() {
        EnterDescriptionFragment fragment = new EnterDescriptionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Required stuff for fragment creation
        View rootView = inflater.inflate(R.layout.fragment_enter_description, container, false);
        String nav = getResources().getStringArray(R.array.nav_array)[1];

        int imageId = getResources().getIdentifier(nav.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());
        getActivity().setTitle(nav);

        //Our stuff to have done on creation (listeners and bindings)
        back_iv = (ImageView) rootView.findViewById(R.id.ec_back_image_iv);
        createButton = (Button) rootView.findViewById(R.id.ec_create_btn);
        back_iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { back(); }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { forwards(); }
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
    public void forwards() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, new CreationConfirmationFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack("enterDesc");
        ft.commit();
    }
    //----------------------------------------------------------------------------------------------
    // Any required nested classes
}
