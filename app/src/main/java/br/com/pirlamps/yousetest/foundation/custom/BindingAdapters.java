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
                .placeholder(R.drawable.ic_user_placeholder)
                .into(view);
    }


    @BindingAdapter({"bind:showData"})
    public static void formatDate(TextView view, String stringDate) {

        Log.i(TAG, "formatDate: preparing to handle dateString: "+stringDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
        newFormat.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
        try {
            Date dateFromString = formatter.parse(stringDate);
            String finalString = newFormat.format(dateFromString);
            Log.i(TAG, "formatDate: success on parsing dateString");
            view.setText(finalString);
        } catch (ParseException e) {
            Log.e(TAG, "formatDate: error on parsing dateString",e);
            e.printStackTrace();
        }


    }

    @BindingAdapter({"bind:setBody"})
    public static void bodyCheck(TextView view, String body) {

        Log.i(TAG, "bodyCheck: preparing to check if body is empty");
        if(body == null || body.isEmpty()){
            Log.i(TAG, "bodyCheck: body is empty, seting default body as description");
          view.setText(R.string.no_body_text);
        }else{
            Log.i(TAG, "bodyCheck: body not empty, seting body as description");
          view.setText(body);
        }


    }


}
