package com.garima.sukhmayfoundation;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.google.android.material.imageview.ShapeableImageView;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    CircleImageView imageView;
    TextView tv1,tv2;
    SharedPreferences pref;
    SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile, container, false);

       imageView=v.findViewById(R.id.imagev);
        tv1=v.findViewById(R.id.textv);
        tv2=v.findViewById(R.id.textv2);
        pref=getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        editor=pref.edit();


        tv1.setText(pref.getString("name",null));
        tv2.setText(pref.getString("phone",null));
        Glide.with(getContext()).load(pref.getString("img",null)).transform(new CircleCrop()).into(imageView);

        return v;
    }
}