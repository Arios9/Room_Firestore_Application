package com.example.room_firestore_application.MyFragments.ListFragments;

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
import com.example.room_firestore_application.MyActivities.TeamActivity;
import com.example.room_firestore_application.R;
import com.example.room_firestore_application.helpClasses.MyNotification;
import com.example.room_firestore_application.MyFragments.SubcollectionFragment.IndiMatchFragment;
import com.example.room_firestore_application.MyFragments.SubcollectionFragment.TeamMatchFragment;
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

public class MatchFragment extends ParentFragment {

    public final Class ActivityClass = MatchActivity.class;

    FragmentTransaction ft;
    Fragment teamMatchFrag = new TeamMatchFragment();
    Fragment indiMatchFrag = new IndiMatchFragment();

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
    public static ArrayList<GeoPoint> GeoPointArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MainActivity.CurrentActivityClass = ActivityClass;
        View root = inflater.inflate(R.layout.fragment_match, container, false);

        listView = root.findViewById(R.id.match_list);
        collectionReference = db.collection("Matches");
        createList();
        add_context();

        addOnClickListener();

        TodayDate = getTodayFormatString();

        return root;
    }

    public void createList() {
        if(getContext()!=null) {
            collectionReference
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                createNewArrayLists();

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

                                    checkDateAndGeopoint(date, geoPoint);
                                }

                                ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list);
                                listView.setAdapter(adapter);

                                checkForNotification();
                            } else {
                                Toast.makeText(getActivity(), "Document doesnt Exist", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private void createNewArrayLists() {
        list = new ArrayList<>();
        myIds = new ArrayList<>();
        sportTypeAr = new ArrayList<>();
        sportCityAr = new ArrayList<>();
        sportCountryAr = new ArrayList<>();
        sportDateAr = new ArrayList<>();
        sportNameAr = new ArrayList<>();
        GeoPointArrayList = new ArrayList<>();
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
                bundle.putString("ID", myId);

                if(sportType.equals("Team")) {
                    bundle.putString("City",sportCity);
                    bundle.putString("Country", sportCountry);
                    bundle.putString("Date", sportDate);
                    bundle.putString("Name", sportName);

                    openFragment(teamMatchFrag, bundle);
                }
                else{
                    openFragment(indiMatchFrag, bundle);
                }
            }
            private void openFragment(Fragment fragment, Bundle bundle) {
                fragment.setArguments(bundle);
                ft = getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, null);
                ft.detach(fragment);
                ft.attach(fragment);
                ft.commit();
                ft.addToBackStack(null);
            }
        });
    }


    @Override
    void EditAction() {
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

    @Override
    void DeleteAction() {
        Toast.makeText(getActivity(), "Document Deleted", Toast.LENGTH_SHORT).show();
        int position = getItemPosition();
        String documentId = myIds.get(position);
        collectionReference.document(documentId).delete();
        createList();
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

    private void checkForNotification() {
        if(GeoPointArrayList.isEmpty())
            return;
        new MyNotification(getActivity().getApplicationContext());
    }


}

