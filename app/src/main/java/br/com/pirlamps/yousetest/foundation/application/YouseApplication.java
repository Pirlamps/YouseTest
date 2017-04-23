package br.com.pirlamps.yousetest.foundation.application;

import android.app.Application;
import android.util.Log;

import br.com.pirlamps.yousetest.foundation.component.DaggerNetComponent;
import br.com.pirlamps.yousetest.foundation.component.NetComponent;
import br.com.pirlamps.yousetest.foundation.module.AppModule;
import br.com.pirlamps.yousetest.foundation.module.NetModule;


/**
 * Created by root-matheus on 21/04/17.
 */

public class YouseApplication extends Application {

    private static final String TAG = "DAApplication";

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: Initializing App");
        Log.i(TAG, "onCreate: Initializing dagger");
        this.mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(Parameters.BASE_URL))
                .build();

    }

    public NetComponent getmNetComponent(){
        return this.mNetComponent;
    }
}
