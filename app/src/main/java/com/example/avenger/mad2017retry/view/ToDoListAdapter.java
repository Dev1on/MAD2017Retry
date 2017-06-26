package com.example.avenger.mad2017retry.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.avenger.mad2017retry.R;
import com.example.avenger.mad2017retry.ToDoDetailActivity;
import com.example.avenger.mad2017retry.ToDoListActivity;
import com.example.avenger.mad2017retry.model.Todo;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    private Todo[] dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public LinearLayout linearLayout;
        public long id;
        public TextView name;

        public ViewHolder(View v) {
            super(v);
            linearLayout = (LinearLayout) v.findViewById(R.id.textViewLinearLayout);
            name = (TextView) v.findViewById(R.id.todo_name);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i("onclick", "not working");
            Context context = itemView.getContext();
            Intent showTodoDetails = new Intent(context, ToDoDetailActivity.class);
            showTodoDetails.putExtra("id", id+1);
            showTodoDetails.putExtra("createItem", false);
            context.startActivity(showTodoDetails);
        }
    }

    public ToDoListAdapter(Todo[] aDataset) {
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
        holder.id = dataset[position].getId();
        holder.name.setText(dataset[position].getName());
    }

    @Override
    public int getItemCount() {
        return dataset.length;
    }
}
