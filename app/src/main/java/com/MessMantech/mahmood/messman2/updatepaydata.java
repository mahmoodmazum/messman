package com.MessMantech.mahmood.messman2;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

public class updatepaydata extends AppCompatActivity {
    dbHelper dbpayment;
    EditText jomaup, bazarcostup, namepayment, updatedpaymentid;
    Button paymentupdatesubmit;
    TextView textViewpayment, datepayment;
    DatePickerDialog datePicker;
    Calendar cal;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_updatepaydata );
        adView = findViewById( R.id.adView );
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd( adRequest );
        dbpayment = new dbHelper( this );
        jomaup = findViewById( R.id.updatepaymentjoma );
        bazarcostup = findViewById( R.id.updatepaymentbazarcost );
        namepayment = findViewById( R.id.updatepaymentname );
        textViewpayment = findViewById( R.id.updatepaymenttextview );
        datepayment = findViewById( R.id.updatepaymentdate );
        paymentupdatesubmit = findViewById( R.id.paymentupdatesubmit );
        updatedpaymentid = findViewById( R.id.updatepaymentid );

        datepayment.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                int year = cal.get( Calendar.YEAR );
                int month = cal.get( Calendar.MONTH );
                int day = cal.get( Calendar.DAY_OF_MONTH );
                datePicker = new DatePickerDialog( updatepaydata.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int nyear, int nmonth, int dayOfMonth) {
                        datepayment.setText( dayOfMonth + "/" + (nmonth + 1) + "/" + nyear );
                    }
                }, day, month, year );
                datePicker.getDatePicker().setMaxDate( System.currentTimeMillis() );
                Calendar dal = Calendar.getInstance();
                datePicker.updateDate( dal.get( Calendar.YEAR ), dal.get( Calendar.MONTH ), dal.get( Calendar.DAY_OF_MONTH ) );
                datePicker.show();
            }
        } );
        paymentupdatesubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (namepayment.getText().toString().contains( " " )) {
                    Toast.makeText( updatepaydata.this, "Name Can not have space", Toast.LENGTH_LONG ).show();
                } else {
                    boolean updated = dbpayment.updatedatapayment( updatedpaymentid.getText().toString(), datepayment.getText().toString(), namepayment.getText().toString(), bazarcostup.getText().toString(), jomaup.getText().toString() );
                    if (updated == true)
                        Toast.makeText( updatepaydata.this, "data inserted", Toast.LENGTH_LONG ).show();
                    else
                        Toast.makeText( updatepaydata.this, "Data not inserted", Toast.LENGTH_LONG ).show();

                    Cursor res = dbpayment.getalldatapay();
                    if (res.getCount() == 0) {
                        Toast.makeText( updatepaydata.this, "no data", Toast.LENGTH_LONG ).show();
                        return;
                    } else {
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append( "id : " + res.getString( 0 ) + " " + "তারিখ : " + res.getString( 1 ) + " " +
                                    "নাম : " + res.getString( 2 ) + " " + "জমা : " + res.getString( 3 ) + " " + "বাজার খরচ : " + res.getString( 4 ) + " " + "\n" );
                        }
                        textViewpayment.setText( buffer.toString() );
                    }
                }
            }
        } );
    }
}
