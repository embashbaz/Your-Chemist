package com.example.yourchemist.AdapterAndModel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourchemist.R;

import java.util.ArrayList;
import java.util.List;

public class InDemandAdapter extends RecyclerView.Adapter<InDemandAdapter.MyViewModel> implements Filterable {

    private ArrayList<Indemand> inDemandList;
    private ArrayList<Indemand> inDemandListSearch;

    public InDemandAdapter(ArrayList<Indemand> inDemandList){

        this.inDemandList = inDemandList;
        inDemandListSearch = new ArrayList<>(inDemandList);
        notifyDataSetChanged();
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

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                List search = new ArrayList();
                if (charSequence == null || charSequence.length() == 0) {
                    search.addAll(inDemandListSearch);
                } else {
                    String pattern = charSequence.toString().toLowerCase().trim();
                    for (Indemand item : inDemandListSearch) {
                        if (item.getDrugName().toLowerCase().contains(pattern) || item.getNumberRequest() >= Integer.parseInt(pattern)) {
                            search.add(item);
                        }
                    }

                }
                FilterResults results = new FilterResults();
                results.values = search;
                return results;
                }

                @Override
                protected void publishResults (CharSequence charSequence, FilterResults filterResults){
                    inDemandList.clear();
                    inDemandList.addAll((List) filterResults.values);
                    notifyDataSetChanged();
                }
            };
        return filter;

    }

    public class MyViewModel extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTxt, statusTxt, peopleNumber;
        public MyViewModel(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.medecine_item_demand);
            statusTxt = itemView.findViewById(R.id.availabilty_item_demand);
            peopleNumber = itemView.findViewById(R.id.number_item_demand);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            Indemand indemand = inDemandList.get(position);
            Bundle bundle = new Bundle();
            bundle.putString("country",indemand.getCountryName());
            bundle.putString("town",indemand.getTownName());
            bundle.putString("drug", indemand.getDrugName());
            bundle.putInt("request",indemand.getNumberRequest());

            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_inDemandList_to_inDemandDetails, bundle);

        }
    }
}
