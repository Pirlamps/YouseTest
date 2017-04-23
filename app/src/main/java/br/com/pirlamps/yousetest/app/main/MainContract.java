package br.com.pirlamps.yousetest.app.main;

import br.com.pirlamps.yousetest.foundation.model.RedditRequest;

/**
 * Created by root-matheus on 23/04/17.
 */

public interface MainContract {

    interface View{
        void showPosts(RedditRequest redditRequest);

        void showError(String message);

        void showComplete();
    }

    interface Presenter{

        void firstLoad();

        void loadAfter(String name);

    }
}
