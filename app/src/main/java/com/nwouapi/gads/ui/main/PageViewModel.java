package com.nwouapi.gads.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.nwouapi.gads.models.LearnerDataModel;
import com.nwouapi.gads.models.SkillDataModel;

import java.util.ArrayList;

public class PageViewModel extends ViewModel {
     ArrayList<LearnerDataModel> list1 = new ArrayList<>();
     ArrayList<SkillDataModel> list2 = new ArrayList<>();
    public void update1(ArrayList<LearnerDataModel> group1){
        list1 = group1;
    }
    public void update2(ArrayList<SkillDataModel> group2){
        list2 = group2;
    }

    public MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private  LiveData<ArrayList<LearnerDataModel>> mList1 = Transformations.map(mIndex, new Function<Integer, ArrayList<LearnerDataModel>>() {
        @Override
        public ArrayList<LearnerDataModel> apply(Integer input) {
            return  list1;
        }
    });
    private  LiveData<ArrayList<SkillDataModel>> mList2 = Transformations.map(mIndex, new Function<Integer, ArrayList<SkillDataModel>>() {
        @Override
        public ArrayList<SkillDataModel> apply(Integer input) {
            return  list2;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<ArrayList<LearnerDataModel>> getList1() {
       return mList1; }
    public LiveData<ArrayList<SkillDataModel>> getList2() {
        return  mList2;
    }
}