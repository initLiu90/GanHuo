package com.lzp.ganhuo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
        initViewModel();
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

    private void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(FragmentRecyclerViewmodel.class);
        /*
        不再layout文件中绑定数据，改成直接为LiveData添加Observe的方式。
        因为如果在layout里面绑定数据的话，创建Binding对象时以及在Fragment的生命周期运行到onStart时都会触发executeBinding
        在viewpager滑动时，fragment被回收（ondestroyview），当fragment被重新创建时(oncreateview-onstart-onresume)，由于viewmode还是原来的，所以viewmode中数据还存在，
        所以在请求网络数据之前就会recyclerview中会展示出来之前的数据。
        当执行到onviewcreated之后会请求网络数据，当有数据返回时，更新了viewmode中的数据，这是又触发了executeBinding，由于调用的adapter.addData方法
        所以，就会把当前新请求到的数据添加到原来数据后面展示，因此出现了数据显示重复的问题。

        换成下面这种方式，由于在layout中不存在对viewmode中数据的引用，所以执行executeBinding时就不会展示对应的数据。只有当请求网络数据返回后更新viewmode中数据时，才会触下面代码中添加的observer。
        */
        mViewModel.getItem().observe(this, categoryItem -> mAdapter.addData(categoryItem.getResults()));
        mViewModel.getError().observe(this, error -> Toast.makeText(getContext(), error.second, Toast.LENGTH_SHORT).show());
    }
}
