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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import bth740.eventfly.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeaturedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeaturedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeaturedFragment extends Fragment {
    public static final String ARG_NAV_NUMBER = "nav_number";
    int[] featureImages =  {
            R.drawable.boardgames, R.drawable.basketball, R.drawable.food, R.drawable.beer, R.drawable.placeholder, R.drawable.sports, R.drawable.movie
    };

    String[] events = {
            "Board game cafe meetup", "Pickup basketball meetup", "Comedy bar standup", "Night at the Drake", "Munchkins Card Game", "Fight Club", "Movie night"
    };
    int imageIndex = 0;

    TextView title_tv;
    ImageView featured_iv, left_iv, right_iv;

    public FeaturedFragment() {}
    public static FeaturedFragment newInstance(String param1, String param2) {
        FeaturedFragment fragment = new FeaturedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Required stuff for fragment creation
        final View rootView = inflater.inflate(R.layout.fragment_featured, container, false);
        int i = getArguments().getInt(ARG_NAV_NUMBER);
        String nav = getResources().getStringArray(R.array.nav_array)[i];
        int imageId = getResources().getIdentifier(nav.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());
        getActivity().setTitle(nav);

        //Our stuff to have done on creation (listeners and bindings)
        title_tv = (TextView) rootView.findViewById(R.id.featured_title_tv);
        featured_iv = (ImageView) rootView.findViewById((R.id.featured_image_iv));
        left_iv = (ImageView) rootView.findViewById((R.id.left_iv));
        right_iv = (ImageView) rootView.findViewById((R.id.right_iv));

        featured_iv.setImageResource(featureImages[0]);
        title_tv.setText(events[0]);

        left_iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cycleLeft();
            }
        });
        right_iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cycleRight();
            }
        });
        featured_iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                goToEvent();
            }
        });

        //Return the view
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) { super.onAttach(activity); }
    @Override
    public void onDetach() { super.onDetach(); }
    public interface OnFragmentInteractionListener { public void onFragmentInteraction(Uri uri); }
    //----------------------------------------------------------------------------------------------
    //Methods

    public void cycleLeft() {
        if (imageIndex == 0)
            imageIndex = featureImages.length-1;
        else
            imageIndex--;

        featured_iv.setImageResource(featureImages[imageIndex]);
        title_tv.setText(events[imageIndex]);
    }
    public void cycleRight() {
        if (imageIndex == featureImages.length-1)
            imageIndex = 0;
        else
            imageIndex++;

        featured_iv.setImageResource(featureImages[imageIndex]);
        title_tv.setText(events[imageIndex]);
    }
    public void goToEvent(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, new ViewEventFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack("confirmEvent");
        ft.commit();
    }
}
