package br.com.pirlamps.yousetest.foundation.component;

/**
 * Created by root-matheus on 21/04/17.
 */
import android.content.SharedPreferences;

import javax.inject.Singleton;

import br.com.pirlamps.yousetest.foundation.module.AppModule;
import br.com.pirlamps.yousetest.foundation.module.NetModule;
import dagger.Component;
import retrofit2.Retrofit;


@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    Retrofit retrofit();
    SharedPreferences sharedPreferences();
}
