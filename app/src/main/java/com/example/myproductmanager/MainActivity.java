package com.example.myproductmanager;

import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.sql.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    TextView idView;
    EditText productBox;
    EditText priceBox;
    ListView productlist;
    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idView = (TextView) findViewById(R.id.productID);
        productBox = (EditText) findViewById(R.id.productName);
        priceBox = (EditText) findViewById(R.id.productPrice);
        // productlist = (TextView) findViewById(R.id.productListView);

        MyDBHandler dbHandler = new MyDBHandler(this);
        listItem = new ArrayList<>();

        viewData();
        //productlist.setOnItemClockListerner(new View.OnClickListener()){
        //            @Override
        //            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
        //                String text = productlist.getItemAtPosition(i).toString();
        //                Toast.makeText(MainActivity.this, ""+text, Toast.LENGTH_SHORT).show();
        //            }
        //        });



    }

    public void newProduct (View view) {

        double price = Double.parseDouble(priceBox.getText().toString());

        Product product = new Product(productBox.getText().toString(), price);

        // TODO: add to database


        MyDBHandler dbHandler = new MyDBHandler(this);
        dbHandler.addProduct(product);
        //C

        productBox.setText("");

        priceBox.setText("");

        listItem.clear();
        viewData();


    }


    public void lookupProduct (View view) {

        // TODO: get from Database
        MyDBHandler dbHandler= new MyDBHandler(this);
        Product product =dbHandler.findProduct((productBox.getText()).toString());// null;

        if (product != null) {
            idView.setText(String.valueOf(product.getID()));
            priceBox.setText(String.valueOf(product.getPrice()));
        } else {
            idView.setText("No Match Found");
        }
    }


    public void removeProduct (View view) {

        // TODO: remove from database
        MyDBHandler dbHandler= new MyDBHandler(this);
        boolean result = dbHandler.deleteProduct(productBox.getText().toString());//false;

        if (result) {
            idView.setText("Record Deleted");
            productBox.setText("");
            priceBox.setText("");
        }
        else
            idView.setText("No Match Found");
    }

    private void viewData(){
        MyDBHandler dbHandler = new MyDBHandler(this);

        Cursor cursor = dbHandler.viewData();

        if(cursor.getCount() == 0){
            listItem.add(cursor.getString(1));
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
            productlist.setAdapter(adapter);
        }

    }
}