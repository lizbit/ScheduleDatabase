package com.example.robertcockrell.scheduledatabase;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.List;


public class ScheduleList extends ListActivity{

    private EventDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list);
        datasource = new EventDataSource(this);
        try{
            datasource.open();
        } catch(SQLException ex){}

        List<EventItem> items = datasource.getAllItems();
        ArrayAdapter<EventItem> adapter = new ArrayAdapter<EventItem>(this, android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);

        ListView list = (ListView) findViewById(android.R.id.list);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                ListView lv = (ListView) findViewById(android.R.id.list);
                EventItem item = (EventItem) lv.getItemAtPosition(position);
                boolean success = datasource.deleteItem(item);
                ArrayAdapter<EventItem> adapter = (ArrayAdapter) lv.getAdapter();
                if(success)
                    adapter.remove(item);
                adapter.notifyDataSetChanged();
                return success;
            }
        });
    }


    public void OnClick(View view){
        @SuppressWarnings("unchecked")
        ArrayAdapter<EventItem> adapter = (ArrayAdapter<EventItem>) getListAdapter();
        EventItem item = null;
        EditText eventname = (EditText) findViewById(R.id.eventname);
        EditText eventtime = (EditText) findViewById(R.id.eventtime);

        switch(view.getId()){
            case R.id.button:
                String itemName = eventname.getText().toString();
                String itemTime = eventtime.getText().toString();
                item = datasource.createEventItem(itemName, itemTime);
                adapter.add(item);
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume(){
        try{
            datasource.open();
        } catch(SQLException ex){};
        super.onResume();
    }

    @Override
    protected void onPause(){
        datasource.close();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_schedule_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
