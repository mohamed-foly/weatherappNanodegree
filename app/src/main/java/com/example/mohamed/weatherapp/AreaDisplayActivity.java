package com.example.mohamed.weatherapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.weatherapp.DB.AppDatabase;
import com.example.mohamed.weatherapp.Models.AreaModel;
import com.example.mohamed.weatherapp.Tasks.DeleteAreaTask;

import java.text.SimpleDateFormat;

public class AreaDisplayActivity extends AppCompatActivity {

    TextView shortDescTV,longDescTV, temperatureTV,humidityTV,pressureTV,speedTV,degreesTV,cloudTV ,lastUpdateTV;
    CheckBox rainyCB ;

    String dateFormat = "yyyy-MM-dd HH:mm:ss";

    AreaModel areaModel;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_display);

        shortDescTV = findViewById(R.id.short_desc_tv);
        longDescTV = findViewById(R.id.long_desc_tv);
        temperatureTV = findViewById(R.id.temperature_val_tv);
        humidityTV = findViewById(R.id.humidity_val_tv);
        pressureTV = findViewById(R.id.pressure_val_tv);
        speedTV = findViewById(R.id.speed_val_tv);
        degreesTV = findViewById(R.id.degrees_val_tv);
        cloudTV = findViewById(R.id.cloud_val_tv);
        rainyCB = findViewById(R.id.rainy_val_cb);
        lastUpdateTV = findViewById(R.id.last_update_val_tv);

        db = AppDatabase.getDatabase(this.getApplication());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat outputFormat = new SimpleDateFormat(dateFormat);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            areaModel = (AreaModel) bundle.getSerializable("area");
            if (areaModel != null) {
                shortDescTV.setText(areaModel.getShortDesc());
                longDescTV.setText(areaModel.getLongDesc());
                temperatureTV.setText(String.valueOf(areaModel.getTemperature()));
                humidityTV.setText(String.valueOf(areaModel.getHumidity()));
                pressureTV.setText(String.valueOf(areaModel.getPressure()));
                speedTV.setText(String.valueOf(areaModel.getSpeed()));
                degreesTV.setText(String.valueOf(areaModel.getDegrees()));
                cloudTV.setText(String.valueOf(areaModel.getCloud()));
                lastUpdateTV.setText(outputFormat.format(areaModel.getLastUpdate()));
                rainyCB.setChecked(areaModel.isRainy());
            }else{
                Toast.makeText(this, "Empty Area", Toast.LENGTH_SHORT).show();
                finish();
            }
        }else{
            Toast.makeText(this, "Empty Bundle", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_delete:
                DeleteAreaTask deleteAreaTask = new DeleteAreaTask(db);
                deleteAreaTask.execute(areaModel);
                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.area_display_activity_menu, menu);
        return true;
    }

}
