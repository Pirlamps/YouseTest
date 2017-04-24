package br.com.pirlamps.yousetest.foundation.custom;

import android.databinding.BindingAdapter;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import br.com.pirlamps.yousetest.R;


/**
 * Created by root-matheus on 21/04/17.
 */

public class BindingAdapters {

    private static final String TAG = BindingAdapters.class.getName();

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Log.i(TAG, "loadImage: preparing to load image with url: "+imageUrl);
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(view);
    }

    @BindingAdapter({"bind:showFormattedDate"})
    public static void showDate(TextView view, Long longDate){

        Date date = new Date(longDate * 1000);
        SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
        newFormat.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));

        String finalString = newFormat.format(date);
        view.setText(finalString);
    }





}
