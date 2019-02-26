package com.lzp.ganhuo.ui;

import android.os.Bundle;

import com.lzp.ganhuo.R;
import com.lzp.ganhuo.ui.adapter.HomeViewpagerAdapter;
import com.lzp.ganhuo.databinding.ActivityMainBinding;
import com.lzp.ganhuo.viewmodel.ActivityMainViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mBinding;
    ActivityMainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initViewModel();
        initBinding();

        setupViewpager();
    }

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(ActivityMainViewModel.class);
    }

    private void initBinding() {
        mBinding.setLifecycleOwner(this);
        mBinding.setVm(mViewModel);
    }

    private void setupViewpager() {
        HomeViewpagerAdapter adapter = new HomeViewpagerAdapter(getSupportFragmentManager(), mViewModel.getCategorys());
        mBinding.activityMainPager.setAdapter(adapter);
        mBinding.activityMainTab.setupWithViewPager(mBinding.activityMainPager);
    }
}
