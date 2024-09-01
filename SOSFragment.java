package com.garima.sukhmayfoundation;

import static androidx.core.location.LocationManagerCompat.getCurrentLocation;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class SOSFragment extends Fragment {

    TextView tv;
    CircleImageView img;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_s_o_s, container, false);

        tv=v.findViewById(R.id.textview);
        img=v.findViewById(R.id.imageButton);
        fab=v.findViewById(R.id.floatingbutton2);
        pref=getActivity().getSharedPreferences("mypref",Context.MODE_PRIVATE);
        editor=pref.edit();

        if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED)
            {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 0);
        }
        if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
        }

              if (pref.getString("number",null)!=null)
        {

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   String msg= tv.getText().toString();
                    String num=pref.getString("number",null);
                    SmsManager.getDefault().sendTextMessage(num,null,""+msg,null,null);

                    Intent i=new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:"+num));
                    startActivity(i);
                }
            });
        }
               else
               {
                   Toast.makeText(getContext(), "Update Number First", Toast.LENGTH_SHORT).show();
               }

              if (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)
                      != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext()
                      ,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
              {
                  ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
              }

              LocationManager lm=(LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);

           // LocationManager lm=(LocationManager)requireActivity().getSystemService(getContext().LOCATION_SERVICE);

              lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                  @Override
                  public void onLocationChanged(@NonNull Location location) {

                      double lat=location.getLatitude();
                      double lon=location.getLongitude();
                     // tv.setText("Lat: "+lat+" \nLon: "+lon);
                      Geocoder geo=new Geocoder(getContext());
                     // Geocoder geo=new Geocoder(getActivity());

                      try {
                          List<Address> list=geo.getFromLocation(lat,lon,1);
                          String add=list.get(0).getAddressLine(0);
                          tv.setText("\n\nAddress: "+add);

                      }catch (Exception e){}
                  }
              });

              fab.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v)
                  {
                      Dialog dialog=new Dialog(getContext());
                      dialog.setContentView(R.layout.dialog);
                      EditText et=dialog.findViewById(R.id.edit);
                      Button bt=dialog.findViewById(R.id.btn);

                      bt.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                              String num=et.getText().toString();
                              editor.putString("number",num);
                              editor.commit();
                              dialog.dismiss();
                          }
                      });
                      dialog.show();
                  }
              });

        return v;
    }
}
