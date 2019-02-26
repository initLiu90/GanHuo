package com.lzp.ganhuo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzp.ganhuo.R;
import com.lzp.ganhuo.databinding.FragmentRecyclerBinding;
import com.lzp.ganhuo.ui.adapter.RecycerviewAdapter;
import com.lzp.ganhuo.viewmodel.FragmentRecyclerViewmodel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclverViewFragment extends Fragment {
    public static final String KEY_CATEGORY = "category";
    private String mCategory;
    private FragmentRecyclerBinding mBinding;
    private RecycerviewAdapter mAdapter;
    private FragmentRecyclerViewmodel mViewModel;

    public static RecyclverViewFragment newFragment(Bundle bundle) {
        RecyclverViewFragment fragment = new RecyclverViewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategory = getArguments().getString(KEY_CATEGORY);
        mViewModel = ViewModelProviders.of(this).get(FragmentRecyclerViewmodel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler, container, false);
        mBinding.setLifecycleOwner(this);
        mBinding.setVm(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setupRecyclerview();
        mViewModel.requestCategoryItem(mCategory);
    }

    private void setupRecyclerview() {
        mAdapter = new RecycerviewAdapter();
        mBinding.fragmentCategoryList.setAdapter(mAdapter);
        mBinding.fragmentCategoryList.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));
        mBinding.fragmentCategoryList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    }
}
