package com.kano.btlnhom2.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.kano.btlnhom2.Fragment.FragmentHoaDon;
import com.kano.btlnhom2.Fragment.FragmentSetting;
import com.kano.btlnhom2.Fragment.FragmentTabDichVu;
import com.kano.btlnhom2.Fragment.FragmentTabPhong;
import com.kano.btlnhom2.Fragment.Fragment_khachhang;


public class ViewpagerAdapter extends FragmentStateAdapter {


    public ViewpagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new FragmentHoaDon();
                break;
            case 1:
                fragment = new FragmentTabDichVu();
                break;
            case 2:
                fragment = new FragmentTabPhong();
                break;
            case 3:
                fragment = new Fragment_khachhang();
                break;
            case 4:
                fragment = new FragmentSetting();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
