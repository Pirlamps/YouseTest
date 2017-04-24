package br.com.pirlamps.yousetest.app.main;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.pirlamps.yousetest.R;
import br.com.pirlamps.yousetest.databinding.ActivityMainBinding;
import br.com.pirlamps.yousetest.foundation.application.YouseApplication;
import br.com.pirlamps.yousetest.foundation.custom.EndlessRecyclerViewScrollListener;
import br.com.pirlamps.yousetest.foundation.custom.SpacesItemDecoration;
import br.com.pirlamps.yousetest.foundation.custom.joat.JoatObject;
import br.com.pirlamps.yousetest.foundation.custom.joat.JoatRecyclerAdapter;
import br.com.pirlamps.yousetest.foundation.model.Child;
import br.com.pirlamps.yousetest.foundation.model.RedditRequest;

import static android.content.ContentValues.TAG;

/*
 * Created by root-matheus on 23/04/17.
 */

public class MainActivity extends Activity implements MainContract.View, JoatRecyclerAdapter.JoatRecyclerDelegate {

    @Inject
    MainPresenter mPresenter;

    private ActivityMainBinding mBinding;
    private LinearLayoutManager mLayoutManager;
    private JoatRecyclerAdapter mAdapter;
    private EndlessRecyclerViewScrollListener mScrollListener;
    private int mTotalItensCount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        DaggerMainComponent.builder()
                .netComponent(((YouseApplication) getApplicationContext()).getmNetComponent())
                .mainModule(new MainModule(this))
                .build()
                .inject(this);

        Log.i(TAG, "onCreate: settingUp view");
        //Inicializando componentes para lista
        mLayoutManager = new LinearLayoutManager(this);
        SpacesItemDecoration space = new SpacesItemDecoration(20);
        mAdapter = new JoatRecyclerAdapter(R.layout.teste);
        mAdapter.setmDelegate(this);
        mScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                mPresenter.loadAfter();
            }
        };

        mBinding.outletMainList.setHasFixedSize(true);
        mBinding.outletMainList.setLayoutManager(mLayoutManager);
        mBinding.outletMainList.addItemDecoration(space);
        mBinding.outletMainList.setAdapter(mAdapter);
        mBinding.outletMainList.addOnScrollListener(mScrollListener);

        mPresenter.firstLoad();


    }

    @Override
    public void showPosts(RedditRequest redditRequest) {

        List<JoatObject> dataSource = new ArrayList<>();

        Log.i(TAG, "showPullRequests: assembling JoatList for adapter");
        for (Child item: redditRequest.getData().getChildren() ) {

            dataSource.add(new JoatObject(BR.teste, item));
        }

        mAdapter.addData(mTotalItensCount,dataSource);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showComplete() {

    }

    @Override
    public void didTouchItem(int position) {

    }
}
