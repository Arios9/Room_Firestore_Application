package com.example.room_firestore_application.MyFragments.ListFragments;

import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.room_firestore_application.R;

public class ParentFragment extends Fragment {

    protected ListView listView;


    public ParentFragment() {
        Log.d("paren","ego eimai o parent");
    }



    protected void add_context() {
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

    //Helper methods , holding the selected ListView item's position.
    protected int _position;
    protected AdapterView _parent;
    protected void setItemPosition(int position){
        _position = position;
    }
    protected int getItemPosition(){
        return _position;
    }
    protected void setParent(AdapterView<?> parent){
        _parent = parent;
    }
    protected AdapterView get_parent(){
        return _parent;
    }

}
