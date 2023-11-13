package com.example.edumate.views.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.lifecycle.LifecycleRegistryOwner;

import com.example.edumate.views.activitites.mainScreen.GenerateFragment;
import com.example.edumate.views.activitites.mainScreen.HistoryFragment;

public class TabsAdapter extends FragmentStateAdapter {

    public TabsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new GenerateFragment();
            case 1:
                return new HistoryFragment();
            default:
                return new Fragment(); // This shouldn't happen
        }
    }

    @Override
    public int getItemCount() {
        return 2; // We have 2 tabs
    }
}
