package com.garima.sukhmayfoundation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garima.sukhmayfoundation.adapter.RawListAdapter;
import com.garima.sukhmayfoundation.model.Rawmodel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RawListFrg extends Fragment {

RecyclerView recyclerView;
DatabaseReference reference;
FirebaseDatabase database;
ArrayList<Rawmodel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_raw_list_frg, container, false);
        recyclerView=v.findViewById(R.id.recycle_4);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Raw");
        list=new ArrayList<Rawmodel>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Rawmodel obj=dataSnapshot.getValue(Rawmodel.class);
                    list.add(obj);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new RawListAdapter(getContext(),list));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }
}