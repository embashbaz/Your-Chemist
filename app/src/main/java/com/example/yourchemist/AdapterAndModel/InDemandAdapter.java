package com.example.yourchemist.AdapterAndModel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourchemist.R;

import java.util.ArrayList;

public class InDemandAdapter extends RecyclerView.Adapter<InDemandAdapter.MyViewModel> {
    private ArrayList<Indemand> inDemandList;

    public InDemandAdapter(ArrayList<Indemand> inDemandList){
        this.inDemandList = inDemandList;
    }
    @NonNull
    @Override
    public InDemandAdapter.MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.in_demand_item, parent, false);
        return new MyViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InDemandAdapter.MyViewModel holder, int position) {
        Indemand indemand = inDemandList.get(position);
        if(indemand.getNumberRequest() == 1) holder.peopleNumber.setText("Requested by "+indemand.getNumberRequest()+" person");
        else holder.peopleNumber.setText("Requested by "+indemand.getNumberRequest()+" people");
        holder.nameTxt.setText(indemand.getDrugName());
        holder.statusTxt.setText(indemand.getTownName());

    }

    @Override
    public int getItemCount() {
        return inDemandList.size();
    }

    public class MyViewModel extends RecyclerView.ViewHolder {
        TextView nameTxt, statusTxt, peopleNumber;
        public MyViewModel(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.medecine_item_demand);
            statusTxt = itemView.findViewById(R.id.availabilty_item_demand);
            peopleNumber = itemView.findViewById(R.id.number_item_demand);

        }
    }
}
