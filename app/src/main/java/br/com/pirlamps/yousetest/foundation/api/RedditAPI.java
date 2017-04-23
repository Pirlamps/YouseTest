package br.com.pirlamps.yousetest.foundation.api;

import java.util.List;

import br.com.pirlamps.yousetest.foundation.model.RedditRequest;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by root-matheus on 21/04/17.
 */

public interface RedditAPI {

    //https://www.reddit.com/r/Android/new/.json?limit=2
    @GET("/r/Android/new/.json")
    Observable<RedditRequest> getFirstNews(
            @Query("limit") int limit
    );

    @GET("/r/Android/new/.json")
    Observable<RedditRequest> getAfterNews(
            @Query("after") String name,
            @Query("limit") int limit
    );

//    @GET("/repos/{login}/{repo_name}/pulls")
//    Observable<List<GitPullRequest>> getPullRequests(
//            @Path("login") String login,
//            @Path("repo_name") String repoName,
//            @Query("sort") String sort,
//            @Query("page") int page,
//            @Query("per_page") int per_page
//    );

}
