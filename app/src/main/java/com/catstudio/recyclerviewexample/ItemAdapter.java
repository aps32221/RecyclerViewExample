package com.catstudio.recyclerviewexample;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private ArrayList<UviInfo> data;

    //建構子，順便傳入要顯示的資料
    public ItemAdapter(ArrayList<UviInfo> data){
        this.data = data;
    }

    /*
    * 繼承RecyclerView.Adapter 必須實作2個functions & 1 method
    * 分別為：  onCreateViewHolder、onBindViewHolder、getItemCount
    * */

    //onCreateViewHolder 在 RecyclerView 需要新的 ViewHolder時被呼叫
    //比較需要注意LayoutInflater.infalte() 的第三個參數(boolean attachToRoot)必須設為false
    //不然會拋出java.lang.IllegalStateException
    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    //onBindViewHolder 在RecyclerView 在特定的位置要顯示資料時被呼叫
    //第一個參數為ViewHolder，第二個參數是位置
    //通常會在這設定layout裡對應的元件要顯示的內容
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        UviInfo info = data.get(i);
        viewHolder.cityName.setText(info.getCounty());
        viewHolder.location.setText("站名："+info.getSiteName()+" WGS84Lat："+info.getWgs84lat()+" WGS84Lon："+ info.getWgs84lon());
        viewHolder.agency.setText("發布單位："+info.getPublishAgency()+" 發布時間："+info.getPublishTime());
        viewHolder.uvi.setText(info.getUVI());
    }

    //getItemCount 回傳list裡面item的總數
    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView cityName, location, agency, uvi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityName_tv);
            location = itemView.findViewById(R.id.location_tv);
            agency = itemView.findViewById(R.id.agency_tv);
            uvi = itemView.findViewById(R.id.uvi_tv);
        }

    }


}
