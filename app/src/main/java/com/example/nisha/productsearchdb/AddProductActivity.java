package com.example.nisha.productsearchdb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by nisha on 3/31/2017.
 */

public class AddProductActivity extends Activity {

    EditText edtTextName;
    String itemName="";
    EditText edtItemDesc;
    String itemDesc="";
    EditText edtItemPrice;
    String itemPrice="";
    EditText edtItemReview;
    String itemReview="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        Button btn_cancel = (Button) findViewById (R.id.button4);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
     }

    public void dbSave(View v){
        edtTextName = (EditText) findViewById(R.id.editText2);
        itemName = edtTextName.getText().toString();
        edtItemDesc = (EditText) findViewById(R.id.editText);
        itemDesc = edtItemDesc.getText().toString();
        edtItemPrice = (EditText) findViewById(R.id.editText3);
        itemPrice = edtItemPrice.getText().toString();
        edtItemReview = (EditText) findViewById(R.id.editText4);
        itemReview = edtItemReview.getText().toString();

        Context context = getApplicationContext();
        DataBaseHelper controller = new DataBaseHelper(context);
        /*Toast values = Toast.makeText(context, itemName+itemDesc+itemPrice+itemReview, Toast.LENGTH_LONG);
        values.show();*/
        Toast saveToast = Toast.makeText(context, "Item Added!", Toast.LENGTH_LONG);
        Toast emptyToast = Toast.makeText(context, "Item name is empty!!", Toast.LENGTH_LONG);

        if(!itemName.isEmpty()) {
            //controller.in
            try {
                controller.insert(itemName, itemDesc, itemPrice,itemReview);
            } catch (IOException e) {
                e.printStackTrace();
            }
            controller.close();
            saveToast.show();
            finish();
        }
        else {
            emptyToast.show();
            controller.close();
        }


    }
}
