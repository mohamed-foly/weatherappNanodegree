package com.example.mohamed.weatherapp.Adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohamed.weatherapp.Interfaces.AreaClickInterface;
import com.example.mohamed.weatherapp.Models.AreaModel;
import com.example.mohamed.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaViewHolder> {
    private List<AreaModel> areaModels;
    String dateFormat = "yyyy-MM-dd HH:mm:ss";
    private AreaClickInterface areaClickInterface;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat outputFormat;
    public AreaAdapter(List<AreaModel> areaModels , AreaClickInterface areaClickInterface) {
        this.areaModels = areaModels;
        this.areaClickInterface = areaClickInterface;

        outputFormat = new SimpleDateFormat(dateFormat);
    }

    @NonNull
    @Override
    public AreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new AreaViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.area_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final AreaViewHolder holder, int position) {
        AreaModel areaModel = areaModels.get(position);
        holder.areaNameTv.setText(areaModel.getName());
        holder.lastUpdateTv.setText(outputFormat.format(areaModel.getLastUpdate()));
        holder.temperatureTv.setText(String.valueOf(areaModel.getTemperature()));
        holder.bind(areaModels.get(position), areaClickInterface);
        holder.itemView.setTag(areaModel);

    }


    @Override
    public int getItemCount() {
        return areaModels.size();
    }

    public void refresh(List<AreaModel> areaModels) {
        this.areaModels = areaModels;
        notifyDataSetChanged();
    }

     class AreaViewHolder extends RecyclerView.ViewHolder {
        private TextView areaNameTv;
        private TextView lastUpdateTv;
        private TextView temperatureTv;

        AreaViewHolder(View view) {
            super(view);
            areaNameTv = view.findViewById(R.id.area_name);
            lastUpdateTv = view.findViewById(R.id.last_update);
            temperatureTv = view.findViewById(R.id.temperature);
        }
         void bind(final AreaModel item, final AreaClickInterface listener) {
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override public void onClick(View v) {
                     listener.onAreaClick(item);
                 }
             });
         }

    }
}
