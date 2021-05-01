package com.example.room_firestore_application.MyFragments.ListFragments;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.example.room_firestore_application.MainActivity;
import com.example.room_firestore_application.R;

public abstract class ParentFragment extends Fragment {

    ListView listView;


    public ParentFragment() {
        MainActivity.CurrentFragment = this;
    }

    abstract void EditAction();
    abstract void DeleteAction();

    public abstract Class getActivityClass();

     void add_context() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                setItemPosition(position);
                setParent(parent);
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
            case 1: EditAction(); break;
            case 2: DeleteAction(); break;
        }
        return true;
    }

    //Helper methods , holding the selected ListView item's position.
     int _position;
     AdapterView _parent;
     void setItemPosition(int position){
        _position = position;
     }
     int getItemPosition(){
        return _position;
     }
     void setParent(AdapterView<?> parent){
        _parent = parent;
     }
     AdapterView get_parent(){
        return _parent;
     }

}
