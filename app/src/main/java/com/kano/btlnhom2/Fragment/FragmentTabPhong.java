package com.kano.btlnhom2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kano.btlnhom2.Adapter.TabPhongViewPagerAdapter;
import com.kano.btlnhom2.R;

public class FragmentTabPhong extends Fragment {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager2;
    private TabPhongViewPagerAdapter tabPhongViewPagerAdapter;


    public FragmentTabPhong() {
        // Required empty public constructor
    }


    public static FragmentTabPhong newInstance(String param1, String param2) {
        FragmentTabPhong fragment = new FragmentTabPhong();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_phong, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTabLayout = view.findViewById(R.id.id_tablayout_phong);
        mViewPager2 = view.findViewById(R.id.id_viewpager_phong);
        tabPhongViewPagerAdapter  = new TabPhongViewPagerAdapter(getActivity());
        mViewPager2.setAdapter(tabPhongViewPagerAdapter);
        TabLayoutMediator mediator = new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Phòng");
                        break;
                    case 1:
                        tab.setText("Loại Phòng");
                        break;

                }
            }
        });
        mediator.attach();
    }
}