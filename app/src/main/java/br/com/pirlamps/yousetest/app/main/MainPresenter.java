package br.com.pirlamps.yousetest.app.main;

import javax.inject.Inject;

import br.com.pirlamps.yousetest.foundation.api.RedditAPI;
import br.com.pirlamps.yousetest.foundation.model.RedditRequest;
import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by root-matheus on 23/04/17.
 */

public class MainPresenter implements MainContract.Presenter {

    private final String TAG = MainPresenter.class.getName();

    private Retrofit mRetrofit;
    private MainContract.View mView;

    @Inject
    public MainPresenter(Retrofit retrofit, MainContract.View mView){
        this.mRetrofit = retrofit;
        this.mView = mView;


    }


    @Override
    public void firstLoad() {


        mRetrofit.create(RedditAPI.class).getFirstNews(20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<RedditRequest>() {
                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(RedditRequest redditRequest) {
                        mView.showPosts(redditRequest);

                    }
                });
    }

    @Override
    public void loadAfter(String name) {

        mRetrofit.create(RedditAPI.class).getAfterNews(name,20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<RedditRequest>() {
                    @Override
                    public void onCompleted() {
                        mView.showComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(RedditRequest redditRequest) {
                        mView.showPosts(redditRequest);

                    }
                });
    }
}
