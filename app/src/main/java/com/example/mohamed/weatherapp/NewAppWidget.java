package com.example.mohamed.weatherapp;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.mohamed.weatherapp.Interfaces.AreaRetrieved;
import com.example.mohamed.weatherapp.Models.AreaModel;
import com.example.mohamed.weatherapp.Tasks.RetrieveAreaTask;

import java.text.SimpleDateFormat;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link NewAppWidgetConfigureActivity NewAppWidgetConfigureActivity}
 */
public class NewAppWidget extends AppWidgetProvider {
    static String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public void onReceive(Context context, Intent intent) {

        if (intent != null){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                int app_id =   bundle.getInt("app_id");
                if (app_id > 0){
                    //Log.e("AppID",String.valueOf(app_id));
                    updateAppWidget(context , AppWidgetManager.getInstance(context) , app_id);
                }

            }

        }
    }

    // Construct the RemoteViews object
   static void updateAppWidget(Context context, final AppWidgetManager appWidgetManager, final int appWidgetId) {

        int area_id = NewAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);

        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        //views.setTextViewText(R.id.area_name, widgetText);

        // Instruct the widget manager to update the widget
        //appWidgetManager.updateAppWidget(appWidgetId, views);


        Intent intentSync = new Intent(context, NewAppWidget.class);
        intentSync.putExtra("app_id",appWidgetId);
        intentSync.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        PendingIntent pendingSync = PendingIntent.getBroadcast(context,0, intentSync, PendingIntent.FLAG_UPDATE_CURRENT);

        views.setOnClickPendingIntent(R.id.widget,pendingSync);

//        appWidgetManager.updateAppWidget(appWidgetId, views);
       @SuppressLint("SimpleDateFormat") final SimpleDateFormat outputFormat = new SimpleDateFormat(dateFormat);

       AreaRetrieved areaRetrieved = new AreaRetrieved() {
           @Override
           public void onAreaRetrieved(AreaModel areaModel) {

               views.setTextViewText(R.id.area_name, areaModel.getName());
               views.setTextViewText(R.id.short_desc_tv, areaModel.getShortDesc());
               views.setTextViewText(R.id.long_desc_tv, areaModel.getLongDesc());

               views.setTextViewText(R.id.temperature_val_tv, String.valueOf(areaModel.getTemperature()));
               views.setTextViewText(R.id.last_update, outputFormat.format(areaModel.getLastUpdate()));
               appWidgetManager.updateAppWidget(appWidgetId, views);
           }
       };

new RetrieveAreaTask(areaRetrieved, context).execute(area_id);


    }









    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            NewAppWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

