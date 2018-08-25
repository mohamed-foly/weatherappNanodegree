package com.example.mohamed.weatherapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mohamed.weatherapp.Adapters.AreaAdapter;
import com.example.mohamed.weatherapp.Interfaces.AreaClickInterface;
import com.example.mohamed.weatherapp.Models.AreaListViewModel;
import com.example.mohamed.weatherapp.Models.AreaModel;
import com.example.mohamed.weatherapp.Tasks.UpdateAreaTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AreaClickInterface {

    RecyclerView recyclerView;
    AreaAdapter areaAdapter ;
    AreaListViewModel areaListViewModel ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView  = findViewById(R.id.area_recycler);
        areaAdapter = new AreaAdapter(new ArrayList<AreaModel>(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(areaAdapter);
        areaListViewModel = ViewModelProviders.of(this).get(AreaListViewModel.class);
        areaListViewModel.getAreaModels().observe(MainActivity.this, new Observer<List<AreaModel>>() {
            @Override
            public void onChanged(@Nullable List<AreaModel> areaModels) {
                Log.e("Change","Happened");
                if (areaModels != null) {
                    for (AreaModel a:areaModels
                            ) {
                        Log.e("AreaID",String.valueOf(a.id));
                        Log.e("AreaName",a.getName());
                    }
                }
                areaAdapter.refresh(areaModels);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_refresh:
                Toast.makeText(this, "Refresh", Toast.LENGTH_SHORT).show();
                List<AreaModel> areaModels = areaListViewModel.getAreaModels().getValue();
                //UpdateAreaTask updateAreaTask = new UpdateAreaTask(this.getApplication());

                //updateAreaTask.execute(areaModels.get(1));
                for (AreaModel a:areaModels
                        ) {
                    UpdateAreaTask updateAreaTask = new UpdateAreaTask(this.getApplication());
                    updateAreaTask.execute(a);
                    Log.e("AreaID",String.valueOf(a.id));
                    Log.e("AreaName",a.getName());
                }

                break;
            case R.id.action_add:
                Intent intent = new Intent(this, AreaAddActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public void onAreaClick(AreaModel areaModel) {
        Intent intent =new Intent(this,AreaDisplayActivity.class);
        intent.putExtra("area_id",areaModel.id);
        intent.putExtra("area", areaModel);
        startActivity(intent);
    }
}
