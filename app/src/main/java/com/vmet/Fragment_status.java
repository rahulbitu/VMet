package com.vmet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class Fragment_status extends Fragment {

    RecyclerView statusRecyclerView;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status_fragment, container, false);

        ArrayList<Model_status> myStatusArray = new ArrayList<>();

        myStatusArray.add(new Model_status(R.drawable.mensclothes,"William"));
        myStatusArray.add(new Model_status(R.drawable.mensclothes,"William"));
        myStatusArray.add(new Model_status(R.drawable.mensclothes,"William"));
        myStatusArray.add(new Model_status(R.drawable.mensclothes,"William"));
        myStatusArray.add(new Model_status(R.drawable.mensclothes,"William"));
        myStatusArray.add(new Model_status(R.drawable.mensclothes,"William"));
        myStatusArray.add(new Model_status(R.drawable.mensclothes,"William"));
        myStatusArray.add(new Model_status(R.drawable.mensclothes,"William"));
        myStatusArray.add(new Model_status(R.drawable.mensclothes,"William"));
        myStatusArray.add(new Model_status(R.drawable.mensclothes,"William"));
        myStatusArray.add(new Model_status(R.drawable.mensclothes,"William"));



        statusRecyclerView= view.findViewById(R.id.statusRecyclerView);
        statusRecyclerView.setHasFixedSize(true);
        statusRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        Adapter_status adapter = new Adapter_status(myStatusArray, getActivity().getApplicationContext());
        statusRecyclerView.setAdapter(adapter);


        return view;
    }


}
