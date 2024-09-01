package com.garima.sukhmayfoundation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.garima.sukhmayfoundation.adapter.WorkDoneAdapter;
import com.garima.sukhmayfoundation.model.WorkdoneModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class WorkDoneFrg extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference reference;
    FirebaseDatabase database;
    ArrayList<WorkdoneModel> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_work_done_frg, container, false);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Work Done");
        recyclerView=v.findViewById(R.id.recycle_2);
        list=new ArrayList<WorkdoneModel>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    WorkdoneModel obj=dataSnapshot.getValue(WorkdoneModel.class);
                    list.add(obj);
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(new WorkDoneAdapter(getContext(),list));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;
    }
}