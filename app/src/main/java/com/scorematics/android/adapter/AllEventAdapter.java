package com.scorematics.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.scorematics.android.R;
import com.scorematics.android.model.AllEventsResponse;
import com.scorematics.android.view.AllArtcileView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sameer.madaan on 5/17/2017.
 */

public class AllEventAdapter extends RecyclerView.Adapter<AllEventAdapter.CarTypesHolder> {

    private List<AllEventsResponse.Result> mMessage;
    private Context mContext;
    AllArtcileView inbixView;

    public class CarTypesHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle,txtDate,txtDesc;
        public CheckBox checkBox;
        LinearLayout llRoot;



        public CarTypesHolder(View view) {
            super(view);
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtDate = (TextView) view.findViewById(R.id.txtDate);
            txtDesc = (TextView) view.findViewById(R.id.txtDesc);

        }
    }

    public AllEventsResponse.Result getItemAt(int pos){
        return mMessage.get(pos);
    }

    public AllEventAdapter(Context context, List<AllEventsResponse.Result> mClasses, AllArtcileView summaryView) {
        mContext = context;
        this.mMessage = mClasses;
        this.inbixView = summaryView;
    }


    @Override
    public CarTypesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_article_infaltor, parent, false);

        return new CarTypesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CarTypesHolder holder, final int position) {



        holder.txtTitle.setText(mMessage.get(position).getTitle());
        holder.txtDesc.setText(mMessage.get(position).getByline());
        holder.txtDate.setText(mMessage.get(position).getPublishedDate());



    }

    @Override
    public int getItemCount() {
        if (mMessage != null) {
            return mMessage.size();
        }
        return 0;
    }

    public void setSelection(CheckBox checkBox,boolean value) {
        checkBox.setChecked(value);

    }

}
