package bth740.eventfly.Profile;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Locale;

import bth740.eventfly.R;
import bth740.eventfly.View.ViewEventFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    ListView listview;
    String[] events = {
            "Board game cafe meetup", "Pickup basketball meetup", "Comedy bar standup", "Night at the Drake", "Munchkins Card Game", "Fight Club"
    };

    public HistoryFragment() {}

    private OnFragmentInteractionListener mListener;


    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_history, container, false);
        String nav = getResources().getStringArray(R.array.nav_array)[3];

        int imageId = getResources().getIdentifier(nav.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());
        getActivity().setTitle(nav);

        listview = (ListView) rootView.findViewById(R.id.history_event_list_view);

        listview.setAdapter(new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, events));

        //Our stuff to have done on creation (listeners and bindings)
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
        args.putBoolean("history", true);
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
