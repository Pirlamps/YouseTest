package br.com.pirlamps.yousetest.app.main;

import br.com.pirlamps.yousetest.foundation.custom.CustomScope;
import dagger.Module;
import dagger.Provides;

/**
 * Created by root-matheus on 23/04/17.
 */


@Module
public class MainModule {

    private final MainContract.View mView;

    public MainModule(MainContract.View mView){
        this.mView = mView;
    }

    @Provides
    @CustomScope
    MainContract.View provideConceptContractView(){
        return this.mView;
    }

}
