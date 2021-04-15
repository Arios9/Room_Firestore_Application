package com.example.room_firestore_application.ui.athlete;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.room_firestore_application.Local_Tables.Athlete;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.myArrayAdapter.AthletesAdapter;

import java.util.ArrayList;
import java.util.List;

public class AthleteFragment extends Fragment {

    private ListView listView;
    private List<Athlete> list;

    //-----------tests---------
    private Button button,refresher;
    private ListView listViewTest;
    private EditText editText;
    private TextView textView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity.CurrentFragment = this;

        View root = inflater.inflate(R.layout.fragment_athlete, container, false);
        listView = (ListView) root.findViewById(R.id.athlete_list);


        createList(root );
        add_delete_listener(listView);


        //-----------tests-----------
        listViewTest = root.findViewById(R.id.athleteFootballList);
        refresher= root.findViewById(R.id.buttonRE);
        button = root.findViewById(R.id.buttonSportAthlete);
        editText = root.findViewById(R.id.editTextSportAthlete);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int sportId = Integer.parseInt(editText.getText().toString());

              // list = MainActivity.localDatabase.basicDao().getAthleteSport(sportId);
                ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
                listViewTest.setAdapter(arrayAdapter);
            }

        });
        refresher.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                if (fragment instanceof AthleteFragment) {
                    FragmentTransaction fragTransaction =   (getActivity()).getSupportFragmentManager().beginTransaction();
                    fragTransaction.detach(fragment);
                    fragTransaction.attach(fragment);
                    fragTransaction.commit();}

            }

        });
        //-----------------------------

        return root;
    }

    private void createList(View root) {


       // list.add(result);
        list = MainActivity.localDatabase.basicDao().getAthlete();

//
//
//        }
//        textView = root.findViewById(R.id.text);
//        textView.setText(result);

        AthletesAdapter athletesAdapter = new AthletesAdapter(getActivity(), list);
      //  ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, list);
        listView.setAdapter(athletesAdapter);
    }

    private void add_delete_listener(ListView listView) {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Athlete athlete = (Athlete) parent.getItemAtPosition(position);
                MainActivity.localDatabase.basicDao().delete(athlete);
                Toast.makeText(getActivity(),"Deleted",Toast.LENGTH_SHORT).show();
                Fragment fragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
                if (fragment instanceof AthleteFragment) {
                    FragmentTransaction fragTransaction =   (getActivity()).getSupportFragmentManager().beginTransaction();
                    fragTransaction.detach(fragment);
                    fragTransaction.attach(fragment);
                    fragTransaction.commit();}
                return true;

            }
        });
    }
}