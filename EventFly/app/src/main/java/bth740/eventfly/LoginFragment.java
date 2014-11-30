package bth740.eventfly;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    Button loginButton;
    EditText user_et, pass_et;
    String user, pass;

    public LoginFragment() {}

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Required stuff for fragment creation
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        String nav = "Log in";

        int imageId = getResources().getIdentifier(nav.toLowerCase(Locale.getDefault()),
                "drawable", getActivity().getPackageName());
        getActivity().setTitle(nav);

        //Our stuff to have done on creation (listeners and bindings)
        loginButton = (Button) rootView.findViewById(R.id.login_btn);
        user_et = (EditText) rootView.findViewById(R.id.login_uname_et);
        pass_et = (EditText) rootView.findViewById(R.id.login_pass_et);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                user = user_et.getText().toString();
                pass = pass_et.getText().toString();

                if((user.equals("Olivier") || user.equals("olivier")) &&  pass.equals("password")) {
                    Toast.makeText(getActivity(), "Logging in...", Toast.LENGTH_SHORT).show();
                    MainActivity.isLoggedIn = true;
                    MainActivity.loginBtn.setText("Logout");
                    back();
                }
                else
                {
                    Toast.makeText(getActivity(), "Incorrect credentials", Toast.LENGTH_SHORT).show();
                }

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(user_et.getWindowToken(), 0);
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
}
