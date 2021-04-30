package com.example.room_firestore_application.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.MyActivities.MatchActivity;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.helpClasses.GeoPointArrayList;
import com.example.room_firestore_application.helpClasses.MyNotification;
import com.example.room_firestore_application.ui.SubcollectionFragment.IndiMatchFragment;
import com.example.room_firestore_application.ui.SubcollectionFragment.TeamMatchFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MatchFragment extends Fragment {

    FragmentTransaction ft;
    Fragment teamMatchFrag = new TeamMatchFragment();
    Fragment indiMatchFrag = new IndiMatchFragment();

    ListView listView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    CollectionReference collectionReference;


    List<String> list ;
    ArrayList<String> myIds ;
    ArrayList<String> sportTypeAr ;
    ArrayList<String> sportCityAr ;
    ArrayList<String> sportCountryAr ;
    ArrayList<String> sportDateAr ;
    ArrayList<String> sportNameAr ;

    private String TodayDate;
    // saves today's matches that have geopoint in firebase
    public static GeoPointArrayList GeoPointArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity.CurrentFragment = this;

        View root = inflater.inflate(R.layout.fragment_match, container, false);

        listView = root.findViewById(R.id.match_list);
        collectionReference = db.collection("Matches");
        createList();
        addOnClickListener();
        add_edit_listener();

        TodayDate = getTodayFormatString();

        return root;
    }

    public void createList() {
        collectionReference
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            list = new ArrayList<>();
                            myIds = new ArrayList<>();
                            sportTypeAr = new ArrayList<>();
                            sportCityAr = new ArrayList<>();
                            sportCountryAr = new ArrayList<>();
                            sportDateAr = new ArrayList<>();
                            sportNameAr = new ArrayList<>();

                            GeoPointArrayList = new GeoPointArrayList();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String city = document.getString("City");
                                String country = document.getString("Country");
                                String date = document.getString("Date");
                                String sport = document.getString("Sport");
                                String match_id = document.getString("ID");
                                String match_sportType = document.getString("SportType");

                                GeoPoint geoPoint = document.getGeoPoint("location");

                                list.add("Match ID : " + match_id + "\n" + " City : " + city + "\n Country : " + country + "\n Date : " + date + "\n Sport : " + sport);
                                myIds.add(match_id);
                                sportCityAr.add(city);
                                sportCountryAr.add(country);
                                sportNameAr.add(sport);
                                sportDateAr.add(date);
                                sportTypeAr.add(match_sportType);

                                if(getActivity()!=null)
                                checkDateAndGeopoint(date,geoPoint);
                            }
                            if (getContext() != null) {
                                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
                                listView.setAdapter(adapter);
                            }

                            createNotification();
                        } else {
                            Toast.makeText(getActivity(), "Document doesnt Exist", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void createNotification() {
        if(GeoPointArrayList.isEmpty())
            return;
        new MyNotification(getActivity().getApplicationContext());
    }


    private void add_edit_listener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                setItemPosition(position);

                registerForContextMenu(listView);
                getActivity().openContextMenu(listView);

                return true;
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select Option ").setHeaderIcon(R.drawable.ic_baseline_help_24);
        menu.add(Menu.NONE, 1 , Menu.NONE, "Edit");
        menu.add(Menu.NONE, 2,Menu.NONE,"Delete");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch(item.getItemId()){
            case 1:{
                int position = getItemPosition();
                String sid = myIds.get(position);
                String city = sportCityAr.get(position);
                String date = sportDateAr.get(position);

                Intent intent = new Intent(getActivity(), MatchActivity.class);

                intent.putExtra("id",sid);
                intent.putExtra("date",date);
                intent.putExtra("city",city);

                startActivity(intent);
            }
            break;
            case 2:{
                Toast.makeText(getActivity(), "Document Deleted", Toast.LENGTH_SHORT).show();
                int position = getItemPosition();
                String documentId = myIds.get(position);
                collectionReference.document(documentId).delete();
            }
            break;
        }
        return true;
    }


    private void addOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                String myId = myIds.get(position);
                String sportType = sportTypeAr.get(position);
                String sportName = sportNameAr.get(position);
                String sportCity = sportCityAr.get(position);
                String sportCountry = sportCountryAr.get(position);
                String sportDate = sportDateAr.get(position);

                Bundle bundle = new Bundle();


                if(sportType.equals("Team")) {
                    // String tag = String.valueOf(view.getTag());

                    bundle.putString("ID", myId);
                    // bundle.putString("sportype", sportType);

                    bundle.putString("City",sportCity);
                    bundle.putString("Country", sportCountry);
                    bundle.putString("Date", sportDate);
                    bundle.putString("Name", sportName);
                    teamMatchFrag.setArguments(bundle);

                    ft = getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, teamMatchFrag, null);
                    ft.detach(teamMatchFrag);
                    ft.attach(teamMatchFrag);
                    ft.commit();
                    ft.addToBackStack(null);

                }
                else{

                    bundle.putString("ID", myId);

                    indiMatchFrag.setArguments(bundle);

                    ft = getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, indiMatchFrag, null);
                    ft.detach(indiMatchFrag);
                    ft.attach(indiMatchFrag);
                    ft.commit();
                    ft.addToBackStack(null);
                }
            }
        });
    }

    //Helper methods , holding the selected ListView item's position.
    int _position;
    public void setItemPosition(int position){
        _position = position;
    }
    public int getItemPosition(){
        return _position;
    }


    private String getTodayFormatString() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String string = simpleDateFormat.format(calendar.getTime());
        return string;
    }

    // date must be today and geopoint must exist to add it in the list
    private void checkDateAndGeopoint(String date, GeoPoint geoPoint) {
        if(TodayDate.equals(date)&&geoPoint!=null)
            GeoPointArrayList.add(geoPoint);
    }

}

