package com.example.musicplayer;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class fragment2 extends Fragment {
    //创建一个View
    private View zhuanji;
    //显示布局
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        zhuanji = inflater.inflate(R.layout.fragment2, null);
        return zhuanji;
    }
}
