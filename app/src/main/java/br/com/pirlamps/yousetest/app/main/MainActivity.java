package br.com.pirlamps.yousetest.app.main;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.pirlamps.yousetest.R;
import br.com.pirlamps.yousetest.app.detail.DetailActivity;
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
        mAdapter = new JoatRecyclerAdapter(R.layout.row_main);
        mAdapter.setmDelegate(this);
        mScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                System.out.println("pagina "+page);
                    mTotalItensCount = totalItemsCount;
                    Child lastItem = mAdapter.getLastItem(Child.class);
                    mPresenter.loadAfter(lastItem.getData().getName());


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

            dataSource.add(new JoatObject(BR.redditPost, item));
        }

        mAdapter.addData(mTotalItensCount,dataSource);
    }

    @Override
    public void showError(String message) {

        Log.i(TAG, "showError: preparing to  show error snackbar");
        Snackbar snackbar = Snackbar
                .make(mBinding.getRoot(), R.string.snack_message, Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction(R.string.snack_action_reload, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Child lastItem = mAdapter.getLastItem(Child.class);
                mPresenter.loadAfter(lastItem.getData().getName());
            }
        });
        snackbar.show();
    }

    @Override
    public void showComplete() {
    }

    @Override
    public void didTouchItem(int position) {
        Log.i(TAG, "didTouchItem: at position"+position);
        Child child = mAdapter.getItemWithType(position, Child.class);

        String url = child.getData().getUrl();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(getResources().getColor(R.color.colorPrimaryDark));
        builder.setStartAnimations(this, R.anim.slide_in_right, R.anim.slide_out_left);
        builder.setExitAnimations(this, R.anim.slide_in_left, R.anim.slide_out_right);

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));

    }
}
