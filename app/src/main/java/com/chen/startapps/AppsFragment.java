package com.chen.startapps;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class AppsFragment extends Fragment{

    RecyclerView rv_apps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apps, container, false);
        rv_apps = (RecyclerView) view.findViewById(R.id.rv_apps);
        rv_apps.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapter();
        return view;
    }

    public void setupAdapter(){
        Intent startIntent = new Intent(Intent.ACTION_MAIN);
        startIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        PackageManager pm = getActivity().getPackageManager();
        //查询所有可启动应用：
        List<ResolveInfo> activities = pm.queryIntentActivities(startIntent, 0);
        //遍历并排序？
        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo a, ResolveInfo b) {
                PackageManager pm = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(
                        a.loadLabel(pm).toString(),b.loadLabel(pm).toString()
                );
            }
        });
        rv_apps.setAdapter(new AppAdapter(activities));
    }

    public class AppHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private View mView;
        private ImageView iv_icon;
        private TextView tv_app_name;
        private TextView tv_about_app;

        private ResolveInfo mResolveInfo;

        public AppHolder(View itemView) {
            super(itemView);
            mView = itemView;
            initUI();
            itemView.setOnClickListener(this);
        }

        private void initUI(){
            iv_icon = (ImageView) mView.findViewById(R.id.iv_icon);
            tv_app_name = (TextView) mView.findViewById(R.id.tv_app_name);
            tv_about_app = (TextView) mView.findViewById(R.id.tv_about_app);
        }

        public void bindActivity(ResolveInfo resolveInfo){
            mResolveInfo = resolveInfo;
            PackageManager pm = getActivity().getPackageManager();
            tv_app_name.setText(resolveInfo.loadLabel(pm).toString());
            iv_icon.setImageDrawable(resolveInfo.loadIcon(pm));
            tv_about_app.setText(resolveInfo.resolvePackageName);
        }

        @Override
        public void onClick(View v) {
            ActivityInfo activityInfo = mResolveInfo.activityInfo;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setClassName(activityInfo.packageName,activityInfo.name);
            startActivity(intent);
        }
    }


    public class AppAdapter extends  RecyclerView.Adapter<AppHolder>{

        private List<ResolveInfo> mActivities;

        public AppAdapter(List<ResolveInfo> activities){
            mActivities=activities;
        }

        @Override
        public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.rv_item_app, parent, false);
            AppHolder appHolder = new AppHolder(view);
            return appHolder;
        }

        @Override
        public void onBindViewHolder(AppHolder holder, int position) {
            ResolveInfo resolveInfo = mActivities.get(position);
            holder.bindActivity(resolveInfo);
        }

        @Override
        public int getItemCount() {
            return mActivities.size();
        }
    }
}
