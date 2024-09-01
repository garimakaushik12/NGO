package com.garima.sukhmayfoundation;

import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.garima.sukhmayfoundation.adapter.MyCustomAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MyCustomAdapter myAdapter;
    Handler handler;
    Runnable runnable;
    int currentpos = 0;
    // List<ImageModel> list;
    List<String> list;
    DrawerLayout drawerLayout;
    TextView tv,tv2,tv3,tv4;

    ImageView img1,img2,img3,img4;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView=v.findViewById(R.id.recyclerView);
        drawerLayout=getActivity().findViewById(R.id.drawer);
        tv=v.findViewById(R.id.text6);
        tv2=v.findViewById(R.id.text7);
        tv3=v.findViewById(R.id.text8);
        tv4=v.findViewById(R.id.text9);
        img1 = v.findViewById(R.id.image);
        img2 = v.findViewById(R.id.image2);
        img3 = v.findViewById(R.id.image3);
        img4 = v.findViewById(R.id.image4);


        list=new ArrayList<>();
        list.add("http://www.sukhmayfoundation.org/new-images/my-img/health%20sukhmay/my-image-commpressed/img-001.jpg");
        list.add("http://www.sukhmayfoundation.org/new-images/my-img/education%20sukhmay/my-image/img-002.jpg");
        list.add("http://www.sukhmayfoundation.org/new-images/my-img/education%20sukhmay/my-image/img-003.jpg");
        list.add("http://www.sukhmayfoundation.org/new-images/my-img/health%20sukhmay/my-image-commpressed/img-004.jpg");
        list.add("http://www.sukhmayfoundation.org/new-images/my-img/health%20sukhmay/my-image-commpressed/img-005.jpg");

        myAdapter=new MyCustomAdapter(getContext(),list);
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);

        handler=new Handler();

        runnable=new Runnable() {
            @Override
            public void run()
            {
                if (currentpos==list.size())
                {
                    currentpos=0;
                }
                else
                {
                    recyclerView.smoothScrollToPosition(currentpos);
                    currentpos++;
                    handler.postDelayed(this,1000);
                }

            }
        };
        handler.postDelayed(runnable,1000);


        img1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                getFragmentManager().beginTransaction().replace(R.id.container2,new SchduleWorkFrg()).addToBackStack("").commit();

            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container2,new WorkDoneFrg()).addToBackStack("").commit();
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container2,new VolunteerListFrg()).addToBackStack("").commit();
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container2,new RawListFrg()).addToBackStack("").commit();
            }
        });


        return v;
    }
}