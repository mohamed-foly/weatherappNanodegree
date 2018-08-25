package com.example.mohamed.weatherapp.Tasks;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mohamed.weatherapp.DB.AppDatabase;
import com.example.mohamed.weatherapp.Models.AreaModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class UpdateAreaTask  extends AsyncTask<AreaModel, Void, AreaModel> {

Context context ;
    public UpdateAreaTask (Context context){
        //this.onLoadReviews = onLoadReviews;
    this.context = context;
    }
    AppDatabase db;
    @Override
    protected AreaModel doInBackground(AreaModel... params) {
        InputStream inputStream;
        String result;
        JSONObject jsonObject;
        // HTTP
        try {
            URL mURL = new URL("https://api.openweathermap.org/data/2.5/weather?lat="+ String.valueOf(params[0].getLat()) +"&lon="+ String.valueOf(params[0].getLng())+"&APPID=52463e001ee7f06f2b216bdb992da4cd");
            HttpURLConnection conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("GET");

            inputStream = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"),8);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            inputStream.close();
            result = sb.toString();

            jsonObject = new JSONObject(result);
            JSONObject weatherJson =  jsonObject.getJSONArray("weather").getJSONObject(0);
            params[0].setShortDesc(weatherJson.getString("main"));
            params[0].setLongDesc(weatherJson.getString("description"));
            JSONObject mainJson =  jsonObject.getJSONObject("main");
            params[0].setTemperature(mainJson.getInt("temp"));
            params[0].setPressure(mainJson.getInt("pressure"));
            params[0].setHumidity(mainJson.getInt("humidity"));
            JSONObject windJson =  jsonObject.getJSONObject("wind");
            params[0].setSpeed(windJson.getInt("speed"));
            params[0].setDegrees(windJson.getInt("deg"));
            JSONObject cloudsJson =  jsonObject.getJSONObject("clouds");
            params[0].setCloud(cloudsJson.getInt("all"));
            params[0].setLastUpdate(new Date());
        } catch(Exception e) {
            return null;
        }
        db = AppDatabase.getDatabase(context);
        db.areaModelDao().updateArea(params[0]);
        return params[0];
    }

    @Override
    protected void onPostExecute(AreaModel areaModel) {

//        Log.e("Area sd",areaModel.getShortDesc());
//        Log.e("Area sd",areaModel.getLongDesc());
//        Log.e("Area sd",String.valueOf(areaModel.getTemperature()));
        //onLoadReviews.OnLoadReviewsFinished(jsonObject);
    }
}