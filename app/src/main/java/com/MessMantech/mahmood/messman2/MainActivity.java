package com.MessMantech.mahmood.messman2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    public static int flag;
    CardView fixed, messhisab, payment, takadibe, takapabe, summary;
    private AdView mAdView;
    private InterstitialAd interstitialAd, interstitialAd2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        MobileAds.initialize( this, "ca-app-pub-4797728656934365~1682241786" );
        setContentView( R.layout.activity_main );
        mAdView = findViewById( R.id.adView );
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd( adRequest );
        interstitialAd = new InterstitialAd( this );
        interstitialAd.setAdUnitId( "ca-app-pub-4797728656934365/3561589225" );
        interstitialAd.loadAd( new AdRequest.Builder().build() );
        interstitialAd2 = new InterstitialAd( this );
        interstitialAd2.setAdUnitId( "ca-app-pub-4797728656934365/3561589225" );
        interstitialAd2.loadAd( new AdRequest.Builder().build() );
        interstitialAd.setAdListener( new AdListener() {
                                          @Override
                                          public void onAdClosed() {
                                              Intent takadibe = new Intent( MainActivity.this, fixed_info.class );
                                              startActivity( takadibe );
                                              flag = 3;
                                              interstitialAd.loadAd( new AdRequest.Builder().build() );
                                          }
                                      }

        );
        interstitialAd2.setAdListener( new AdListener()

                                       {
                                           @Override
                                           public void onAdClosed() {
                                               Intent takapabe = new Intent( MainActivity.this, fixed_info.class );
                                               startActivity( takapabe );
                                               flag = 4;
                                               interstitialAd2.loadAd( new AdRequest.Builder().build() );
                                           }
                                       }
        );
        fixed = findViewById( R.id.fixedid );
        messhisab = findViewById( R.id.messhisab );
        payment = findViewById( R.id.payment );
        takadibe = findViewById( R.id.takadibe );
        takapabe = findViewById( R.id.takapabe );
        summary = findViewById( R.id.summary );
        takadibe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                } else {
                    Intent takadibe = new Intent( MainActivity.this, fixed_info.class );
                    startActivity( takadibe );
                    flag = 3;
                }
            }
        } );
        takapabe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd2.isLoaded()) {
                    interstitialAd2.show();
                } else {
                    Intent takapabe = new Intent( MainActivity.this, fixed_info.class );
                    startActivity( takapabe );
                    flag = 4;
                }

            }
        } );
        summary.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent summary = new Intent( MainActivity.this, fixed_info.class );
                startActivity( summary );
                flag = 5;
            }
        } );
        payment.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent payment = new Intent( MainActivity.this, fixed_info.class );
                startActivity( payment );
                flag = 2;
            }
        } );
        fixed.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fixed = new Intent( MainActivity.this, fixed_info.class );
                startActivity( fixed );
                flag = 0;
            }
        } );
        messhisab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent messhisab = new Intent( MainActivity.this, fixed_info.class );
                startActivity( messhisab );
                flag = 1;
            }
        } );
    }


}
