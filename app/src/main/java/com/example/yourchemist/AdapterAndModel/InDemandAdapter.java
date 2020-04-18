package com.example.yourchemist.AdapterAndModel;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourchemist.R;

public class InDemandAdapter extends RecyclerView.Adapter<InDemandAdapter.MyViewModel> {
    @NonNull
    @Override
    public InDemandAdapter.MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull InDemandAdapter.MyViewModel holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
