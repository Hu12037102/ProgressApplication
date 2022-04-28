package com.huxiaobai.receive;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * 作者: 胡庆岭
 * 创建时间: 2022/2/23 11:04
 * 更新时间: 2022/2/23 11:04
 * 描述:
 */
public class AppWightReceive extends AppWidgetProvider {
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.w("AppWightReceive--","onEnabled--");
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        Log.w("AppWightReceive--","onAppWidgetOptionsChanged--");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Log.w("AppWightReceive--","onDeleted--");
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        Log.w("AppWightReceive--","onDisabled--");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.w("AppWightReceive--","onReceive--");
    }

    @Override
    public void onRestored(Context context, int[] oldWidgetIds, int[] newWidgetIds) {
        super.onRestored(context, oldWidgetIds, newWidgetIds);
        Log.w("AppWightReceive--","onRestored--");
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.w("AppWightReceive--","onUpdate--");
    }
}
