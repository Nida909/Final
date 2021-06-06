package com.example.chatting;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class orderDetails extends AppCompatActivity {
TextView t1,t2,t3,t4,t5,t6;
   DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Context context;
    Resources resources;
    String str;
    String languages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Intent intent=getIntent();
        String str=intent.getStringExtra("val");
        languages=intent.getStringExtra("language");
        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();
        t1=(TextView)findViewById(R.id.txt1);
        t2=(TextView)findViewById(R.id.txt2);
        t3=(TextView)findViewById(R.id.txt3);
        t4=(TextView)findViewById(R.id.txt4);
        t5=(TextView)findViewById(R.id.txt5);
        t6=(TextView)findViewById(R.id.txt6);
        String[] columns={DatabaseContract.OrderT.COL_PLACED_BY, DatabaseContract.OrderT.COL_QUALITY, DatabaseContract.OrderT.COL_QUANTITY,
        DatabaseContract.OrderT.COL_PRICE};
        Cursor c = db.query(DatabaseContract.OrderT.TABLE_NAME,columns, DatabaseContract.OrderT._ID + "=?", new String[] {str}
                , null, null, null, null);
        if (c.getCount()==0) {
            Toast.makeText(getApplicationContext(),"No Record exist",Toast.LENGTH_LONG).show();
        }
       c.moveToFirst();
        long by=c.getLong(0);
        String qlt=c.getString(1);
        String qnt=c.getString(2);
        long prc=c.getLong(3);
        String[] colum={DatabaseContract.Customers.COL_NAME, DatabaseContract.Customers.COL_CONTACT, DatabaseContract.Customers.COL_LOCATION};
        Cursor cr = db.query(DatabaseContract.Customers.TABLE_NAME,colum, DatabaseContract.Customers._ID + "=?", new String[] {String.valueOf(by)}
                , null, null, null, null);
        if (cr.getCount()==0) {
            Toast.makeText(getApplicationContext(),"No Record exist",Toast.LENGTH_LONG).show();
        }
        cr.moveToFirst();
        String s1=cr.getString(0);
        String s2=cr.getString(1);
        String s3=cr.getString(2);

        if(languages.equals("ENGLISH")) {

            context = LocalHelper.setLocale(orderDetails.this, "en");
            resources = context.getResources();
            t1.setText("Customer Name:   "+s1);
            t2.setText("Contact No:   "+s2);
            t3.setText("Destination:   "+s3);
            t4.setText("Milk Category:   "+qnt);
            t5.setText("Milk Quantity:   "+ qlt);
            t6.setText("Price:  "+String.valueOf(prc));


        }
        if(languages.equals("اردو")) {

            context = LocalHelper.setLocale(orderDetails.this, "an");
            resources = context.getResources();
            t1.setText("گاہک کا نام:   "+s1);
            t2.setText("رابطہ نمبر:   "+s2);
            t3.setText("منزل:   "+s3);
            t4.setText("دودھ کی قسم:   "+qnt);
            t5.setText("دودھ کی مقدار:   "+qlt);
            t6.setText("قیمت:  "+String.valueOf(prc));


        }


    }
}