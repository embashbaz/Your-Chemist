package com.example.yourchemist.AdapterAndModel;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yourchemist.R;

public class UserResultAdapter extends RecyclerView.Adapter<UserResultAdapter.MyViewModel> {
    @NonNull
    @Override
    public UserResultAdapter.MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserResultAdapter.MyViewModel holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewModel extends RecyclerView.ViewHolder {

        TextView chemistTxt, drugTxt, statusTxt, adressTxt, phoneTxt;
        public MyViewModel(@NonNull View itemView) {
            super(itemView);
            chemistTxt = itemView.findViewById(R.id.chemist_item_user);
            drugTxt = itemView.findViewById(R.id.medecine_item_user);
            statusTxt = itemView.findViewById(R.id.status_item_user);
            adressTxt = itemView.findViewById(R.id.adress_item_user);
            phoneTxt = itemView.findViewById(R.id.phone_txt_item);
        }
    }
}
