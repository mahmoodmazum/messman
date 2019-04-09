package com.MessMantech.mahmood.messman2;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class jomaandcost extends AppCompatActivity {

    TextView textjoamanadcost;
    dbHelper dbHelper;
    AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_jomaandcost );
        adView = findViewById( R.id.adView );
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd( adRequest );
        textjoamanadcost = findViewById( R.id.textjomaandcost );
        dbHelper = new dbHelper( this );
        int totaljoma = dbHelper.realtotaljoma();
        int totalbazar = dbHelper.totalbazarcost();
        int preservedIncash = totaljoma - totalbazar;
        Cursor joma, bazar;
        joma = dbHelper.personaltotaljoma();
        bazar = dbHelper.personalbazarcost();
        if (joma.getCount() == 0) {
            showmsg( "Error", "no data found" );
            return;
        } else {

            StringBuffer buffer1 = new StringBuffer();
            StringBuffer buffer2 = new StringBuffer();
            while (bazar.moveToNext() && joma.moveToNext()) {

                buffer1.append( joma.getString( 0 ) + " এর জমা " + joma.getString( 1 ) + " টাকা\n" );
                buffer2.append( bazar.getString( 0 ) + " বাজার করেছে " + bazar.getString( 1 ) + " টাকার\n" );
            }

            textjoamanadcost.setText( "সর্বমোট জমা পড়েছে " + totaljoma + " টাকা\nসর্বমোট বাজার হয়েছে " + totalbazar + " টাকার\nএখন বাকী আছে " + preservedIncash + " টাকা\n\nজমার হিসাবঃ\n"
                    + buffer1.toString() + "\nবাজারের হিসাবঃ\n" + buffer2.toString() );

        }

    }

    public void showmsg(String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setCancelable( true );
        builder.setTitle( title );
        builder.setMessage( msg );
        builder.show();
    }
}
