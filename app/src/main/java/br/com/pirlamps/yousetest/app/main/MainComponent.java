package br.com.pirlamps.yousetest.app.main;

import br.com.pirlamps.yousetest.foundation.component.NetComponent;
import br.com.pirlamps.yousetest.foundation.custom.CustomScope;
import dagger.Component;

/**
 * Created by root-matheus on 23/04/17.
 */

@CustomScope
@Component(dependencies = NetComponent.class, modules = MainModule.class)
interface MainComponent {
    void inject(MainActivity activity);
}