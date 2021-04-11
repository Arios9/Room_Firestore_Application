package com.example.room_firestore_application.ui.sport;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.ui.athlete.AthleteInsertFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

public class SportFragment extends Fragment {

    private static String myText;
    private SportViewModel sportViewModel;
    TextView textView1;

    public static void textSettingTest(String aText){
        myText = aText;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sportViewModel =
                new ViewModelProvider(this).get(SportViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sport, container, false);
        final TextView textView = root.findViewById(R.id.text_sport);
        sportViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        textView1 = root.findViewById(R.id.textView3);
        textView1.setText(myText);
        return root;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Sport Fragment Insert Button", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new SportInsertFragment()).addToBackStack(null).commit();
            }
        });
        FloatingActionButton fabEdit, fabDelete;
        fabEdit = (FloatingActionButton) view.findViewById(R.id.fabEdit);
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Sport Fragment Edit Button", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fabDelete = (FloatingActionButton) view.findViewById(R.id.fabDelete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Sport Fragment Delete Button", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

}