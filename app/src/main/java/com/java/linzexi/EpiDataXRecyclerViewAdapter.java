package com.java.linzexi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EpiDataXRecyclerViewAdapter extends RecyclerView.Adapter<EpiDataXRecyclerViewAdapter.XRecyclerViewHolder> {
    private List<EpiDataModel> epiDataModelShow = new ArrayList<>();
    private Context mContext;
    private EpiDataFragment fragment;

    public EpiDataXRecyclerViewAdapter(Context context, EpiDataFragment _fragment){
        mContext = context;
        fragment = _fragment;
    }

    public void upDateList(List<EpiDataModel> l){
        epiDataModelShow.clear();
        epiDataModelShow.addAll(l);
    }

    @NonNull
    @Override
    public XRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EpiDataXRecyclerViewAdapter.XRecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_epi_data_recycler, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerViewHolder holder, final int position) {
        holder.textView.setText(epiDataModelShow.get(position).place);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //fragment.popup(epiDataModelShow.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return epiDataModelShow.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class XRecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public XRecyclerViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.data_title);
        }
    }
}
