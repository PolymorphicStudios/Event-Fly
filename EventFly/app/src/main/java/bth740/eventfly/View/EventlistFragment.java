package bth740.eventfly.View;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Locale;

import bth740.eventfly.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventlistFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventlistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventlistFragment extends Fragment {
    public static final String ARG_NAV_NUMBER = "nav_number";
    ListView listview;
    CustomListViewAdapter adapter;
    ArrayList<RowItem> rowItems;
    Bitmap[] a;
    boolean isLoggedIn;


    int[] featureImages =  {
            R.drawable.boardgames, R.drawable.basketball, R.drawable.food, R.drawable.beer, R.drawable.placeholder, R.drawable.sports, R.drawable.movie
    };

    String[] events = {
        "Board game cafe meetup", "Pickup basketball meetup", "Comedy bar standup", "Night at the Drake", "Munchkins Card Game", "Fight Club", "Movie night"
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
        //Required stuff for fragment creation
        final View rootView = inflater.inflate(R.layout.fragment_eventlist, container, false);
        int i = getArguments().getInt(ARG_NAV_NUMBER);
        isLoggedIn = getArguments().getBoolean("isLoggedIn");
        String nav = getResources().getStringArray(R.array.nav_array)[i];
        int imageId = getResources().getIdentifier(nav.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());
        getActivity().setTitle(nav);

        //Our stuff to have done on creation (listeners and bindings)

        rowItems = new ArrayList<RowItem>();
        a = new Bitmap[7];
        for (int j = 0; j<7; j++){
            a[j] = BitmapFactory.decodeResource(this.getResources(), featureImages[j]);
            rowItems.add(new RowItem(events[j],a[j]));
        }

        listview = (ListView) rootView.findViewById(R.id.eventlist_event_list);

        listview.setAdapter(new CustomListViewAdapter(rootView.getContext(), android.R.layout.simple_list_item_1, rowItems));

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
        Fragment f = new ViewEventFragment();
        Bundle args = new Bundle();
        args.putBoolean("isLoggedIn", isLoggedIn);
        f.setArguments(args);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, f);
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
