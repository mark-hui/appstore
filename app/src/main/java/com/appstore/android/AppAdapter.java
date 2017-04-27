package com.appstore.android;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    private Context mContext;

    private List<App> mAppList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView appImage;
        TextView appName;
        TextView appContent;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            appImage = (ImageView) view.findViewById(R.id.app_icon);
            appName = (TextView) view.findViewById(R.id.app_name);
            appContent = (TextView) view.findViewById(R.id.content);
        }
    }

    public AppAdapter(/*Context context, */List<App> appList) {
        /*mContext = context;*/
        mAppList = appList;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                App app = mAppList.get(position);
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.APP_NAME, app.getName());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        App app = mAppList.get(position);
        holder.appImage.setImageResource(app.getAppIcon());
        holder.appName.setText(app.getAppName());
        holder.appContent.setText(app.getAppText());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }
}
