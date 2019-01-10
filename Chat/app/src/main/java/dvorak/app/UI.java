package dvorak.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class UI extends AppCompatActivity {

    private static final String TAG = "ConversationList";

    Storage s = new Storage();

    private ListView LV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        LV = findViewById(R.id.listView);

        populateListView();
    }

    private void populateListView(){

        ArrayList<String> USRS = s.usrs();
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, USRS);
        LV.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
            }
        });
    }
}