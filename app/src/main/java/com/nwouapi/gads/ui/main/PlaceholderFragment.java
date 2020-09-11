package com.nwouapi.gads.ui.main;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.nwouapi.gads.R;
import com.nwouapi.gads.models.LearnerDataModel;
import com.nwouapi.gads.models.SkillDataModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private SkillRecyclerAdapter _skillAdapter = new SkillRecyclerAdapter(new ArrayList<SkillDataModel>());
    private LearnerRecyclerAdapter _learnAdapter = new LearnerRecyclerAdapter(new ArrayList<LearnerDataModel>());
    private View error, recycler, loading;

    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);

        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
       // pageViewModel.setIndex(index);
        new LearnerDataFetcher().execute("https://gadsapi.herokuapp.com/api/hours");
        new SkillDataFetcher().execute("https://gadsapi.herokuapp.com/api/skilliq");


    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        final RecyclerView recyclerView = root.findViewById(R.id.myList);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        error = root.findViewById(R.id.error);
        recycler = recyclerView;
        loading = root.findViewById(R.id.spinner);

        recyclerView.setAdapter(_learnAdapter);

        TabLayout tabs = getActivity().findViewById(R.id.tabs);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    recyclerView.setAdapter(_learnAdapter);
                    _learnAdapter.notifyDataSetChanged();
                }else {
                    recyclerView.setAdapter(_skillAdapter);
                    _skillAdapter.notifyDataSetChanged();
                }
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        return root;
    }



    private class LearnerDataFetcher extends AsyncTask<String, Integer, ArrayList<LearnerDataModel>> {
        @Override
        protected ArrayList<LearnerDataModel> doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            final ArrayList<LearnerDataModel> list = new ArrayList<>();


            try {
                Request request = new Request.Builder()
                        .url(strings[0])
                        .build();
                Response response = client.newCall(request).execute();
                JSONArray object = new JSONArray(response.body().string());
                for (int i = 0; i < object.length(); i++) {
                    final JSONObject map = (JSONObject) object.get(i);
                    list.add(new LearnerDataModel(map));
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handleMessage(false);
                        //pageViewModel.update1(list);
                        _learnAdapter.update(list);
                        _learnAdapter.notifyDataSetChanged();
                    }
                });
                return list;
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handleMessage(true);
                    }
                });
                return list;
            }
        }
    }

    private void handleMessage(boolean isError) {
        if (!isError) {
            recycler.setVisibility(View.VISIBLE);
            error.setVisibility(View.GONE);
        } else {
            recycler.setVisibility(View.GONE);
            error.setVisibility(View.VISIBLE);
        }
        loading.setVisibility(View.GONE);
    }

    private class SkillDataFetcher extends AsyncTask<String, Integer, ArrayList<SkillDataModel>> {
        @Override
        protected ArrayList<SkillDataModel> doInBackground(String... strings) {
            OkHttpClient client = new OkHttpClient();
            final ArrayList<SkillDataModel> list = new ArrayList<>();


            try {
                Request request = new Request.Builder()
                        .url(strings[0])
                        .build();
                Response response = client.newCall(request).execute();
                JSONArray object = new JSONArray(response.body().string());
                for (int i = 0; i < object.length(); i++) {
                    final JSONObject map = (JSONObject) object.get(i);
                    list.add(new SkillDataModel(map));
                }


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handleMessage(false);
                        //pageViewModel.update2(list);
                        _skillAdapter.update(list);
                        _skillAdapter.notifyDataSetChanged();
                    }
                });
                return list;
            } catch (Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handleMessage(true);
                    }
                });
                e.printStackTrace();
                return list;
            }
        }
    }
}
