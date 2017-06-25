package com.example.avenger.mad2017retry.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.avenger.mad2017retry.R;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    private String[] dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout linearLayout;
        public TextView name;

        public ViewHolder(View v) {
            super(v);
            linearLayout = (LinearLayout) v.findViewById(R.id.textViewLinearLayout);
            name = (TextView) v.findViewById(R.id.item_name);
        }
    }

    public ToDoListAdapter(String[] aDataset) {
        dataset= aDataset;
    }

    @Override
    public ToDoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ToDoListAdapter.ViewHolder holder, int position) {
        holder.name.setText(dataset[position]);
    }

    @Override
    public int getItemCount() {
        return dataset.length;
    }
}
