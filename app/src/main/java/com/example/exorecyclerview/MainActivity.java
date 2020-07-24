package com.example.exorecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    ArrayList<ListModel> listModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);

        ListModel listModel = new ListModel();
        listModelList.add(new ListModel("One test",
                "https://res.cloudinary.com/druve/image/upload/q_auto,f_auto/ads/eaa0038b-520b-4c82-a7ab-ffbb7b0989a8-IMG-20200708-WA0007_tct9md.jpg", "image"));
        listModelList.add(new ListModel("One test",
                "https://res.cloudinary.com/dqrs5xew4/video/upload/v1595538345/HNG/b5bc3f170b7d5c388410bcca0a501ecd_lnhzwn.mp4", "video"));
        listModelList.add(new ListModel("One test",
                "https://res.cloudinary.com/druve/image/upload/q_auto,f_auto/ads/eaa0038b-520b-4c82-a7ab-ffbb7b0989a8-IMG-20200708-WA0007_tct9md.jpg", "image"));
        listModelList.add(new ListModel("One test",
                "https://res.cloudinary.com/dqrs5xew4/video/upload/v1595538331/HNG/VID-20180713-WA0009_esbmff.mp4", "video"));
        listModelList.add(new ListModel("One test",
                "https://res.cloudinary.com/dqrs5xew4/image/upload/v1591992323/HNG/Screenshot_228_dgv7mi.png", "image"));
        listModelList.add(new ListModel("One test",
                "https://res.cloudinary.com/dqrs5xew4/video/upload/v1595535168/HNG/Druve_explainer_video_bad_draft_zcdrq4.mp4", "video"));
        initRecyclerView();
    }

    private void initRecyclerView() {
        // use a linear layout manager
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerAdapter mAdapter = new RecyclerAdapter(listModelList, MainActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}