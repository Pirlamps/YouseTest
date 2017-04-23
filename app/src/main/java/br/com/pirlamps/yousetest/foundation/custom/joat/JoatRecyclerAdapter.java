package br.com.pirlamps.yousetest.foundation.custom.joat;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by root-matheus on 21/04/17.
 */

public class JoatRecyclerAdapter extends RecyclerView.Adapter {

    private List<JoatObject> mDataSource;
    private int mRowRes;
    private JoatRecyclerDelegate mDelegate;

    public interface JoatRecyclerDelegate {

        void didTouchItem(int position);
    }
    public JoatRecyclerAdapter(int res) {
        mDataSource = new ArrayList<>();
        mRowRes = res;
    }

    public void setData(List<JoatObject> mDataSource) {
        this.mDataSource = mDataSource;

    }

    public void addData(int itenCount, List<JoatObject> newData){
        mDataSource.addAll(newData);
        notifyItemRangeInserted(itenCount,newData.size());
    }

    public <T>T getItemWithType(int position, Class<T> type){
        return  type.cast(((JoatObject) getItem(position)).getBindingObject());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater  = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewDataBinding viewDataBinding;
        viewDataBinding = DataBindingUtil.inflate(inflater, mRowRes, parent, false);
        return  new JoatViewHolder(viewDataBinding);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        JoatObject item = getItem(position);
        ((JoatViewHolder) holder).mView.setVariable(item.getBindingObjectID(),item.getBindingObject());
        ((JoatViewHolder) holder).mView.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDelegate.didTouchItem(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {

        return mDataSource.size();
    }

    public void setmDelegate(JoatRecyclerDelegate mDelegate) {
        this.mDelegate = mDelegate;
    }

    private JoatObject getItem(int position){
        return mDataSource.get(position);
    }

    private class JoatViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding mView;

        private JoatViewHolder(ViewDataBinding v) {
            super(v.getRoot());
            mView = v;
        }
    }


}

