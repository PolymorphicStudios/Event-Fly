package bth740.eventfly.Create;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import bth740.eventfly.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MiscInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MiscInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MiscInfoFragment extends Fragment {
    protected static Context THIS = null;
    static ListView items_lv;
    NumberPicker np;
    ImageView addItem;
    CheckBox cb;
    TextView difficulty,novice,expert;
    SeekBar sb;
    ImageView back_iv, forward_iv;
    static ArrayAdapter<String> adapter;

    static ArrayList<String> items;

    public MiscInfoFragment() {}

    public static MiscInfoFragment newInstance() {
        MiscInfoFragment fragment = new MiscInfoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Required stuff for fragment creation
        View rootView = inflater.inflate(R.layout.fragment_misc_info, container, false);
        String nav = getResources().getStringArray(R.array.nav_array)[1];

        int imageId = getResources().getIdentifier(nav.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());
        getActivity().setTitle(nav);

        //Our stuff to have done on creation (listeners and bindings)
        THIS = rootView.getContext();

        np = (NumberPicker) rootView.findViewById(R.id.ec_max_guests_np);
        addItem = (ImageView) rootView.findViewById(R.id.ec_add_iv);
        cb = (CheckBox) rootView.findViewById(R.id.ec_difficulty_show_cb);
        difficulty = (TextView) rootView.findViewById(R.id.ec_difficulty_title_tv);
        novice = (TextView) rootView.findViewById(R.id.ec_difficulty_novice_tv);
        expert = (TextView) rootView.findViewById(R.id.ec_difficulty_expert_tv);
        sb = (SeekBar) rootView.findViewById(R.id.ec_difficulty_bar_sb);
        items_lv = (ListView) rootView.findViewById(R.id.ec_items_lv);
        back_iv = (ImageView) rootView.findViewById(R.id.ec_back_image_iv);
        forward_iv = (ImageView) rootView.findViewById(R.id.ec_forward_image_iv);

        np.setMinValue(1);
        np.setMaxValue(50);
        np.setWrapSelectorWheel(true);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        items = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_dropdown_item_1line, items);
        adapter.setNotifyOnChange(true);

        addItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onAddItemClicked();
            }
        });

        items_lv.setAdapter((ListAdapter) adapter);

        items_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                AlertDialog.Builder adb=new AlertDialog.Builder(THIS);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete?");
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        items.remove(positionToRemove);
                        adapter.notifyDataSetChanged();
                    }
                });
                adb.show();
            }
        });

        cb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { onDifficultyShowClicked(); }
        });
        back_iv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { back(); }
        });
        forward_iv.setOnClickListener(new View.OnClickListener() {
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
    public void onAddItemClicked() {
        DialogFragment newFragment = new ItemDialogFragment();
        newFragment.show(getFragmentManager(), "addItem");
    }

    public void onDifficultyShowClicked() {
        boolean isChecked = cb.isChecked();
        novice.setEnabled(isChecked);
        expert.setEnabled(isChecked);
        sb.setEnabled(isChecked);
    }

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
        ft.replace(R.id.content_frame, new EnterDescriptionFragment());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.addToBackStack("miscInfo");
        ft.commit();
    }

    //----------------------------------------------------------------------------------------------
    // Any required nested classes
    public static class ItemDialogFragment extends DialogFragment {
        final EditText input = new EditText(THIS);

        static ItemDialogFragment newInstance(String title) {
            ItemDialogFragment fragment = new ItemDialogFragment();
            Bundle args = new Bundle();
            args.putString("New Item", title);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            input.setSingleLine();
            input.setImeOptions(EditorInfo.IME_ACTION_DONE);
            return new AlertDialog.Builder(getActivity())
                    .setTitle("Enter item:")
                    .setView(input)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    String newItem = input.getText().toString().trim();
                                    if (newItem != "") {
                                        adapter.add(newItem);
                                    }
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            }).create();
        }
    }
}
