package com.garima.sukhmayfoundation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garima.sukhmayfoundation.adapter.ScheduleWorkAdapter;
import com.garima.sukhmayfoundation.model.data;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SchduleWorkFrg extends Fragment
{

    FirebaseDatabase database;
    DatabaseReference reference;

    RecyclerView recyclerView;

    ArrayList<data>  list;

        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
        {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_schdule_work_frg, container, false);


        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Work");

        list=new ArrayList<data>();

            recyclerView = v.findViewById(R.id.recycler_1);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                   data obj= dataSnapshot.getValue(data.class);
                 list.add(obj);

                }

                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new ScheduleWorkAdapter(getContext(),list));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }
}