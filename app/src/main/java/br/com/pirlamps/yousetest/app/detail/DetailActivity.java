package br.com.pirlamps.yousetest.app.detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;

import br.com.pirlamps.yousetest.foundation.model.Child;

/**
 * Created by root-matheus on 24/04/17.
 */

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Child child = ((Child) getIntent().getSerializableExtra("Child"));




    }
}
