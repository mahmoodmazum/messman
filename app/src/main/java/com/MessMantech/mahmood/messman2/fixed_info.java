package com.MessMantech.mahmood.messman2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class fixed_info extends AppCompatActivity {
    public static float sum, tot;
    public static String delete = "delete";
    TextView date, mestext, sumtext, submit1, paymsgtext, sumtextcheck;
    EditText basavara, panibil, gasbil, netbil, buabil, memberno, summaryname, deletetext, namepay, joma, bazarcost;
    DatePickerDialog datepic;
    Calendar cal;
    dbHelper db;
    EditText nam, meal;
    Button messub, fixhisab, updatedata, paymentupdate, summarybtn, deletebtn, paysubmit, viewdata, dataviewpay, jomacostbtn;
    int a;
    AdView adView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (MainActivity.flag == 0) {
            setContentView( R.layout.activity_fixed_info );
            adView = findViewById( R.id.adView );
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd( adRequest );
            basavara = findViewById( R.id.basavara );
            panibil = findViewById( R.id.panibil );
            gasbil = findViewById( R.id.gasbil );
            netbil = findViewById( R.id.netbil );
            buabil = findViewById( R.id.buabil );
            memberno = findViewById( R.id.memberno );
            fixhisab = findViewById( R.id.fixtotto );
            submit1 = findViewById( R.id.fixtext );
            fixhisab.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((basavara.getText().toString().isEmpty() || panibil.getText().toString().isEmpty() || gasbil.getText().toString().isEmpty() ||
                            netbil.getText().toString().isEmpty() || buabil.getText().toString().isEmpty() || memberno.getText().toString().isEmpty())) {

                        Toast.makeText( fixed_info.this, "missing or invalid data", Toast.LENGTH_LONG ).show();


                    } else {

                        tot = sum = Float.parseFloat( basavara.getText().toString() ) + Float.parseFloat( panibil.getText().toString() ) +
                                Float.parseFloat( gasbil.getText().toString() ) + Float.parseFloat( netbil.getText().toString() ) +
                                Float.parseFloat( buabil.getText().toString() );
                        sum = (sum) / (Float.parseFloat( memberno.getText().toString() ));
                        submit1.setText( "প্রত্যেকের জন্য ফিক্সড খরচ " + sum );
                        SharedPreferences sharedPreferences = getSharedPreferences( "db", Context.MODE_PRIVATE );
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putFloat( "fix", sum );
                        editor.putFloat( "tot", tot );
                        editor.putInt( "mem", Integer.parseInt( memberno.getText().toString() ) );
                        editor.commit();
                    }


                }
            } );


        }
        if (MainActivity.flag == 1) {
            setContentView( R.layout.mess_hisab );
            adView = findViewById( R.id.adView );
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd( adRequest );

            date = findViewById( R.id.date );
            db = new dbHelper( this );
            nam = findViewById( R.id.fixnam );
            meal = findViewById( R.id.fixmeal );
            messub = findViewById( R.id.mesbtn );
            mestext = findViewById( R.id.mestext );
            updatedata = findViewById( R.id.update );
            viewdata = findViewById( R.id.viewdata );
            updatedata.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent( fixed_info.this, updatedata.class );
                    startActivity( update );

                }
            } );

            messub.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (nam.getText().toString().contains( " " )) {
                        showmsg( "Error", "Name can not contain space" );
                    } else {
                        boolean isinserted = db.insertdata( date.getText().toString(), nam.getText().toString(), meal.getText().toString() );
                        if (isinserted == true)
                            Toast.makeText( fixed_info.this, "Data inserted", Toast.LENGTH_LONG ).show();
                        else
                            Toast.makeText( fixed_info.this, "Data not inserted", Toast.LENGTH_LONG ).show();

                        Cursor res = db.getalldata();
                        if (res.getCount() == 0) {
                            showmsg( "Error", "No data found" );
                            return;
                        } else {
                            StringBuffer buffer = new StringBuffer();
                            while (res.moveToNext()) {
                                buffer.append( "  id : " + res.getString( 0 ) + " " + "তারিখ : " + res.getString( 1 ) + " " +
                                        "নাম : " + res.getString( 2 ) + " " + "মিল সংখ্যা : " + res.getString( 3 ) + "\n" );
                            }
                            mestext.setText( buffer.toString() );
                        }
                    }

                }
            } );
            viewdata.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor res = db.getalldata();
                    if (res.getCount() == 0) {
                        showmsg( "Error", "No data found" );
                        return;
                    } else {
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append( "  id : " + res.getString( 0 ) + " " + "তারিখ : " + res.getString( 1 ) + " " +
                                    "নাম : " + res.getString( 2 ) + " " + "মিল সংখ্যা : " + res.getString( 3 ) + "\n" );
                        }
                        mestext.setText( buffer.toString() );
                    }
                }
            } );


            date.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cal = Calendar.getInstance();
                    int year = cal.get( Calendar.YEAR );
                    int month = cal.get( Calendar.MONTH );
                    int day = cal.get( Calendar.DAY_OF_MONTH );
                    datepic = new DatePickerDialog( fixed_info.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int nyear, int nmonth, int dayOfMonth) {
                            date.setText( dayOfMonth + "/" + (nmonth + 1) + "/" + nyear );
                        }
                    }, day, month, year );
                    datepic.getDatePicker().setMaxDate( System.currentTimeMillis() );
                    Calendar dal = Calendar.getInstance();
                    datepic.updateDate( dal.get( Calendar.YEAR ), dal.get( Calendar.MONTH ), dal.get( Calendar.DAY_OF_MONTH ) );
                    datepic.show();
                }
            } );
        }
        if (MainActivity.flag == 2) {
            setContentView( R.layout.payment );
            adView = findViewById( R.id.adView );
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd( adRequest );

            date = findViewById( R.id.date );
            final dbHelper dbpay = new dbHelper( this );
            namepay = findViewById( R.id.namepay );
            joma = findViewById( R.id.joma );
            bazarcost = findViewById( R.id.bazarcost );
            paysubmit = findViewById( R.id.paysubmit );
            paymsgtext = findViewById( R.id.paymestext );
            dataviewpay = findViewById( R.id.dataviewpay );
            paymentupdate = findViewById( R.id.paymentupdatebtn );
            paymentupdate.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent( fixed_info.this, updatepaydata.class );
                    startActivity( update );
                }
            } );
            date.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cal = Calendar.getInstance();
                    int year = cal.get( Calendar.YEAR );
                    int month = cal.get( Calendar.MONTH );
                    int day = cal.get( Calendar.DAY_OF_MONTH );
                    datepic = new DatePickerDialog( fixed_info.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int nyear, int nmonth, int dayOfMonth) {
                            date.setText( dayOfMonth + "/" + (nmonth + 1) + "/" + nyear );
                        }
                    }, day, month, year );
                    datepic.getDatePicker().setMaxDate( System.currentTimeMillis() );
                    Calendar dal = Calendar.getInstance();
                    datepic.updateDate( dal.get( Calendar.YEAR ), dal.get( Calendar.MONTH ), dal.get( Calendar.DAY_OF_MONTH ) );
                    datepic.show();
                }
            } );
            dataviewpay.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor res = dbpay.getalldatapay();
                    if (res.getCount() == 0) {
                        showmsg( "Error", "No data found" );
                    } else {
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append( "id : " + res.getString( 0 ) + " " + "তারিখ : " + res.getString( 1 ) + " " +
                                    "নাম : " + res.getString( 2 ) + " " + "জমা : " + res.getString( 3 ) + " " + "বাজার খরচ : " + res.getString( 4 ) + " " + "\n" );
                        }
                        paymsgtext.setText( buffer.toString() );
                    }
                }
            } );
            paysubmit.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (namepay.getText().toString().contains( " " )) {
                        showmsg( "Error", "Name can not contain space" );
                    } else {
                        boolean isinserted = dbpay.insertdatapay( date.getText().toString(), namepay.getText().toString(), joma.getText().toString(), bazarcost.getText().toString() );
                        if (isinserted == true)
                            Toast.makeText( fixed_info.this, "Data inserted", Toast.LENGTH_LONG ).show();
                        else
                            Toast.makeText( fixed_info.this, "Data not inserted", Toast.LENGTH_LONG ).show();

                        Cursor res = dbpay.getalldatapay();
                        if (res.getCount() == 0) {
                            showmsg( "Error", "No data found" );
                            return;
                        } else {
                            StringBuffer buffer = new StringBuffer();
                            while (res.moveToNext()) {
                                buffer.append( "id : " + res.getString( 0 ) + " " + "তারিখ : " + res.getString( 1 ) + " " +
                                        "নাম : " + res.getString( 2 ) + " " + "জমা : " + res.getString( 3 ) + " " + "বাজার খরচ : " + res.getString( 4 ) + " " + "\n" );
                            }
                            paymsgtext.setText( buffer.toString() );
                        }
                    }
                }
            } );
        }
        if (MainActivity.flag == 3) {
            setContentView( R.layout.taka_dibe );
            adView = findViewById( R.id.adView );
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd( adRequest );
            deletetext = findViewById( R.id.deletetext );
            deletebtn = findViewById( R.id.deletebtn );
            final dbHelper db1 = new dbHelper( this );
            deletebtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder( fixed_info.this );
                    builder.setMessage( "Are you sure you want to delete all data" ).setCancelable( false )
                            .setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (deletetext.getText().toString().equals( delete )) {
                                        db1.deletealldata();
                                        showmsg( "done", "all data deleted" );

                                    } else {
                                        showmsg( "error", "try again" );
                                    }
                                }
                            } ).setNegativeButton( "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    } );
                    AlertDialog alert = builder.create();
                    alert.setTitle( "ALERT!" );
                    alert.show();

                }
            } );

        }
        if (MainActivity.flag == 4) {
            setContentView( R.layout.taka_nibe );
            adView = findViewById( R.id.adView );
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd( adRequest );
        }
        if (MainActivity.flag == 5) {

            setContentView( R.layout.summary );
            adView = findViewById( R.id.adView );
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd( adRequest );
            final dbHelper db1 = new dbHelper( this );
            sumtext = findViewById( R.id.sumtext );
            sumtextcheck = findViewById( R.id.sumtextcheck );
            summaryname = findViewById( R.id.summaryname );
            summarybtn = findViewById( R.id.summarybtn );
            jomacostbtn = findViewById( R.id.jomaandcostbtn );
            jomacostbtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent jomacost = new Intent( fixed_info.this, jomaandcost.class );
                    startActivity( jomacost );
                }
            } );
            SharedPreferences sharedPreferences = getSharedPreferences( "db", Context.MODE_PRIVATE );
            final Float fix = sharedPreferences.getFloat( "fix", 0 );
            final int mem = sharedPreferences.getInt( "mem", 0 );
            final Float tot = sharedPreferences.getFloat( "tot", 0 );
            final float a = db1.mealno();
            int realtotaljoma = db1.realtotaljoma();
            final int totalbazarcost = db1.totalbazarcost();
            final float mealrate = (float) totalbazarcost / a;
            Cursor res = db1.personalallmealno();
            if (res.getCount() == 0) {
                showmsg( "Error", "No data found" );
                return;
            } else {
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append( res.getString( 0 ) + " এর " + "মিল সংখ্যা : " + res.getString( 1 ) + "\n" );
                }
                sumtext.setText( "টোটাল ফিক্সড খরচ " + tot + "\nমেম্বার সংখ্যা " + mem + "\nসবার জন্য ফিক্সড খরচ " + fix + "\nসর্বমোট মিল সংখ্যা " + a + "\nসর্বমোট বাজার খরচ " + totalbazarcost + "\n" +
                        "সর্বমোট জমা পড়েছে " + realtotaljoma + " টাকা" +
                        "\nবর্তমান মিলরেট " + mealrate + "\n\n" + buffer.toString() );
            }
            summarybtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    float ab = db1.personalmealno( summaryname.getText().toString() );
                    int totaljoma = db1.totaljoma( summaryname.getText().toString() );
                    float personalmealcost = ab * mealrate;
                    int personalbazarcost = db1.personalbazarcost( summaryname.getText().toString() );
                    float flag = (personalmealcost + fix) - (float) totaljoma;
                    if (flag > 0) {
                        sumtextcheck.setText( summaryname.getText().toString() + " এর " + flag + " টাকা পরিশোধ করতে হবে" );
                    } else {
                        sumtextcheck.setText( summaryname.getText().toString() + " " + (flag * -1) + " টাকা পাবে" );
                    }
                    sumtext.setText( summaryname.getText().toString() + " এর মিল সংখ্যা " + ab + "। তার টোটাল মিল খরচ " + personalmealcost + "। তিনি" +
                            " জমা দিয়েছেন " + totaljoma + " টাকা" +
                            "। তিনি " + personalbazarcost + " টাকার বাজার করেছেন।\n\nসুতরাং তাকে মিল খরচ ও অন্যান্য খরচ বাবদ " + (personalmealcost + fix) + " টাকা দিতে হবে\n"
                            + "তার " + (totaljoma) + " টাকা জমা দেয়া আছে" );

                }
            } );
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
