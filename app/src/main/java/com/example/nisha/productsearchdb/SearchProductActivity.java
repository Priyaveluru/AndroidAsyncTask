package com.example.nisha.productsearchdb;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by nisha on 3/31/2017.
 */

public class SearchProductActivity extends Activity {
    EditText search;
    TextView searchBox;
    String searchText;
    String result="";
    String[] item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        Button btn_cancel = (Button) findViewById (R.id.button6);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void dbSearch(View view){
        search = (EditText) findViewById(R.id.editText5);
        searchText = search.getText().toString();

        Context context = getApplicationContext();
        DataBaseHelper controller = new DataBaseHelper(context);
        Toast saveToast = Toast.makeText(context, "Searching..", Toast.LENGTH_LONG);
        Toast emptyToast = Toast.makeText(context, "Search Item is empty!!", Toast.LENGTH_LONG);
        Toast notToast = Toast.makeText(context, "Item not found in DB!!", Toast.LENGTH_LONG);
        saveToast.show();
        if(!searchText.isEmpty()){
            String s=controller.search(searchText);
           if(s!=null && !s.isEmpty()) {

               item = s.split(Pattern.quote(","));
               searchBox = (TextView) findViewById(R.id.textView14);


               result = String.valueOf("Search Results: \n\nItem name:" + String.valueOf(item[0]) + "\n" +
                       "Item Description:" + String.valueOf(item[1]) + "\n" + "Price:" + String.valueOf(item[2]) + "\n" + "Review:"
                       + String.valueOf(item[3]) + "\n");
               searchBox.setText(result);
               controller.close();
           }
            else{
               notToast.show();
            //   searchBox.setText(" ");
           }
         }
        else{
            emptyToast.show();
        }

    }
}
