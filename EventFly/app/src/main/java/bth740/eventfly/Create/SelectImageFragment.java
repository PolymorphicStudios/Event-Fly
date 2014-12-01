package bth740.eventfly.Create;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

import bth740.eventfly.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectImageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelectImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectImageFragment extends Fragment {
    public static final String ARG_NAV_NUMBER = "nav_number";
    GridView imageGrid;
    View oldImageView;
    ImageView back_iv, forward_iv;
    boolean imageSelected = false;

    public SelectImageFragment() {}

    public static SelectImageFragment newInstance() {
        SelectImageFragment fragment = new SelectImageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Required stuff for fragment creation
        View rootView = inflater.inflate(R.layout.fragment_select_image, container, false);
        String nav = getResources().getStringArray(R.array.nav_array)[2];

        int imageId = getResources().getIdentifier(nav.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());
        getActivity().setTitle(nav);

        //Our stuff to have done on creation (listeners and bindings)
        imageGrid = (GridView) rootView.findViewById(R.id.ec_image_grid_gv);
        back_iv = (ImageView) rootView.findViewById(R.id.ec_back_image_iv);
        forward_iv = (ImageView) rootView.findViewById(R.id.ec_forward_image_iv);
        imageGrid.setAdapter(new ImageAdapter(rootView.getContext()));

        imageGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Toast.makeText(THIS, "image: " + position, Toast.LENGTH_SHORT).show();
                if (v != oldImageView) {
                    if (oldImageView != null){
                        oldImageView.setBackgroundColor(0xFFFFFFFF);
                        oldImageView.invalidate();
                    }
                    v.setBackgroundColor(0x6633B5E5);
                    v.invalidate();
                }
                oldImageView = v;
                imageSelected = true;
            }
        });

        back_iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { back(); }
        });
        forward_iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (imageSelected)
                    forwards();
                else
                    Toast.makeText(getActivity(), "Please select an image", Toast.LENGTH_LONG).show();
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
    public void forwards(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, new MiscInfoFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack("selectImage");
        ft.commit();
    }

    //----------------------------------------------------------------------------------------------
    // Any required nested classes
    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mThumbIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(300,300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setPadding(8, 8, 8, 8);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.placeholder, R.drawable.beer,
                R.drawable.boardgames, R.drawable.food,
                R.drawable.movie, R.drawable.sports,
        };
    }
}
