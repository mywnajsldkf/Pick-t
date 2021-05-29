package com.example.pickt;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pickt.utils.BusProvider;

public class BaseFragment extends Fragment {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onDestroyView() {
        BusProvider.getInstance().unregister(this);
            super.onDestroyView();
    }
}