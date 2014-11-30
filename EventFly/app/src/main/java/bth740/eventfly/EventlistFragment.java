package bth740.eventfly;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.util.Locale;

import bth740.eventfly.View.ViewEventFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventlistFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventlistFragment extends Fragment {

    ImageView backButton, forwardButton, featureImage;
    ListView listview;
    int pos = 0;

    int[] featureImages =  {
            R.drawable.food, R.drawable.beer, R.drawable.basketball, R.drawable.boardgames
    };

    String[] events = {
      "Board game cafe meetup", "Pickup basketball meetup", "Comedy bar standup", "Night at the Drake", "Munchkins Card Game", "Fight Club"
    };

    public EventlistFragment() {}

    public static EventlistFragment newInstance(String param1, String param2) {
        EventlistFragment fragment = new EventlistFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        String nav = getResources().getStringArray(R.array.nav_array)[0];

        int imageId = getResources().getIdentifier(nav.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());
        getActivity().setTitle(nav);

        backButton = (ImageView) rootView.findViewById(R.id.eventlist_back_btn);
        forwardButton = (ImageView) rootView.findViewById(R.id.eventlist_forward_btn);
        listview = (ListView) rootView.findViewById(R.id.eventlist_event_list);
        featureImage = (ImageView) rootView.findViewById(R.id.eventlist_feature_img);

        //Feature image browser
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if( pos == 0) { pos = featureImages.length; }
                else          { pos--; }

                featureImage.setImageResource(featureImages[pos]);
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(pos == featureImages.length){ pos = 0; }
                else                           { pos++;}

                featureImage.setImageResource(featureImages[pos]);
            }
        });

        featureImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                goToEvent();
            }
        });

        listview.setAdapter(new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, events));

        //All event scroller
        listview.setOnItemClickListener(new EventItemClickListener());

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) { super.onAttach(activity); }

    @Override
    public void onDetach() { super.onDetach(); }

    public interface OnFragmentInteractionListener { public void onFragmentInteraction(Uri uri); }
    //----------------------------------------------------------------------------------------------
    //Methods
    public void goToEvent(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, new ViewEventFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack("confirmEvent");
        ft.commit();
    }

    //----------------------------------------------------------------------------------------------
    //Nested class
    private class EventItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            goToEvent();
        }
    }

}
