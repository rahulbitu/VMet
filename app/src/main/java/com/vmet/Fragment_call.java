package com.vmet;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class Fragment_call extends Fragment {

    RecyclerView callRecyclerView;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call_fragment, container, false);

       /* getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.myStatusBarColor));*/

        ArrayList<Model_call> myCallArray = new ArrayList<>();

        myCallArray.add(new Model_call(R.drawable.mensclothes,R.drawable.call_made_green_600_24dp,R.drawable.videocall_black_24dp,"Marry","21 June","12:00 pm"));
        myCallArray.add(new Model_call(R.drawable.mensclothes,R.drawable.ic_call_missed_red_600_24dp,R.drawable.videocall_black_24dp,"Marry","21 June","12:00 pm"));
        myCallArray.add(new Model_call(R.drawable.mensclothes,R.drawable.call_made_green_600_24dp,R.drawable.videocall_black_24dp,"Marry","21 June","12:00 pm"));
        myCallArray.add(new Model_call(R.drawable.mensclothes,R.drawable.ic_call_received_green_600_24dp,R.drawable.videocall_black_24dp,"Marry","21 June","12:00 pm"));
        myCallArray.add(new Model_call(R.drawable.mensclothes,R.drawable.call_made_green_600_24dp,R.drawable.videocall_black_24dp,"Marry","21 June","12:00 pm"));
        myCallArray.add(new Model_call(R.drawable.mensclothes,R.drawable.call_made_green_600_24dp,R.drawable.videocall_black_24dp,"Marry","21 June","12:00 pm"));
        myCallArray.add(new Model_call(R.drawable.mensclothes,R.drawable.ic_call_missed_red_600_24dp,R.drawable.videocall_black_24dp,"Marry","21 June","12:00 pm"));
        myCallArray.add(new Model_call(R.drawable.mensclothes,R.drawable.call_made_green_600_24dp,R.drawable.videocall_black_24dp,"Marry","21 June","12:00 pm"));
        myCallArray.add(new Model_call(R.drawable.mensclothes,R.drawable.call_made_green_600_24dp,R.drawable.videocall_black_24dp,"Marry","21 June","12:00 pm"));
        myCallArray.add(new Model_call(R.drawable.mensclothes,R.drawable.ic_call_received_green_600_24dp,R.drawable.videocall_black_24dp,"Marry","21 June","12:00 pm"));
        myCallArray.add(new Model_call(R.drawable.mensclothes,R.drawable.call_made_green_600_24dp,R.drawable.videocall_black_24dp,"Marry","21 June","12:00 pm"));
        myCallArray.add(new Model_call(R.drawable.mensclothes,R.drawable.ic_call_missed_red_600_24dp,R.drawable.videocall_black_24dp,"Marry","21 June","12:00 pm"));
        myCallArray.add(new Model_call(R.drawable.mensclothes,R.drawable.call_made_green_600_24dp,R.drawable.videocall_black_24dp,"Marry","21 June","12:00 pm"));
        myCallArray.add(new Model_call(R.drawable.mensclothes,R.drawable.ic_call_missed_red_600_24dp,R.drawable.videocall_black_24dp,"Marry","21 June","12:00 pm"));
        myCallArray.add(new Model_call(R.drawable.mensclothes,R.drawable.call_made_green_600_24dp,R.drawable.videocall_black_24dp,"Marry","21 June","12:00 pm"));

        callRecyclerView= view.findViewById(R.id.callRecyclerView);
        callRecyclerView.setHasFixedSize(true);
        callRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Adapter_call adapter = new Adapter_call(myCallArray, getActivity().getApplicationContext());
        callRecyclerView.setAdapter(adapter);

        return view;
    }
}
