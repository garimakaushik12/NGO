package com.garima.sukhmayfoundation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garima.sukhmayfoundation.adapter.VolunteerListAdapter;
import com.garima.sukhmayfoundation.model.VolunteerModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class VolunteerListFrg extends Fragment {

    RecyclerView recyclerView;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<VolunteerModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_volunteer_list_frg, container, false);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Volunteer");
        recyclerView=v.findViewById(R.id.recycle_3);
        list=new ArrayList<VolunteerModel>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    VolunteerModel obj=dataSnapshot.getValue(VolunteerModel.class);
                    list.add(obj);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new VolunteerListAdapter(getContext(),list));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }
}