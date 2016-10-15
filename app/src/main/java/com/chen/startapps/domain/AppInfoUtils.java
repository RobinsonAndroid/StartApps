package com.chen.startapps.domain;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/10/15.
 */
public class AppInfoUtils {
    private Context mContext;

    public static void getAppInfo(Context context){
        PackageManager pm = context.getPackageManager();
        Intent startIntent = new Intent(Intent.ACTION_MAIN);
        startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> activities = pm.queryIntentActivities(startIntent, 0);
        for (ResolveInfo resolveInfo:activities) {
            resolveInfo.loadLabel(pm);
        }


        /*List<ApplicationInfo> infos = pm.getInstalledApplications(0);
        for(ApplicationInfo info : infos){
            CharSequence charSequence = info.loadLabel(pm);
        }*/
    }
}
