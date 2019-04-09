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

public class updatedata extends AppCompatActivity {
    dbHelper dbup;
    EditText editText, nameup, mealup;
    Button button;
    TextView textView, dateup;
    DatePickerDialog datePicker;
    Calendar cal;
    AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_updatedata );
        adView = findViewById( R.id.adView );
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd( adRequest );
        dbup = new dbHelper( this );
        editText = findViewById( R.id.updateidno );
        button = findViewById( R.id.btnupdate );
        textView = findViewById( R.id.textupdate );
        dateup = findViewById( R.id.dateupdate );
        nameup = findViewById( R.id.fixnamup );
        mealup = findViewById( R.id.fixmealup );

        dateup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                int year = cal.get( Calendar.YEAR );
                int month = cal.get( Calendar.MONTH );
                int day = cal.get( Calendar.DAY_OF_MONTH );
                datePicker = new DatePickerDialog( updatedata.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int nyear, int nmonth, int dayOfMonth) {
                        dateup.setText( dayOfMonth + "/" + (nmonth + 1) + "/" + nyear );
                    }
                }, day, month, year );
                datePicker.getDatePicker().setMaxDate( System.currentTimeMillis() );
                Calendar dal = Calendar.getInstance();
                datePicker.updateDate( dal.get( Calendar.YEAR ), dal.get( Calendar.MONTH ), dal.get( Calendar.DAY_OF_MONTH ) );
                datePicker.show();
            }
        } );

        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean updated = dbup.updatedata( editText.getText().toString(), dateup.getText().toString(), nameup.getText().toString(), mealup.getText().toString() );
                if (updated == true)
                    Toast.makeText( updatedata.this, "Data updated", Toast.LENGTH_LONG ).show();
                else
                    Toast.makeText( updatedata.this, "Data not updated", Toast.LENGTH_LONG ).show();

                Cursor res = dbup.getalldata();
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append( "id : " + res.getString( 0 ) + " " + "তারিখ : " + res.getString( 1 ) + " " +
                            "নাম : " + res.getString( 2 ) + " " + "মিল সংখ্যা : " + res.getString( 3 ) + "\n" );
                }
                textView.setText( buffer.toString() );

            }
        } );
    }


}

