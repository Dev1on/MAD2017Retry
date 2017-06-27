package com.example.avenger.mad2017retry.adapter;

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
import com.example.avenger.mad2017retry.model.Todo;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {

    private Context context;
    private Todo[] todos;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public LinearLayout todoContainer;
        public long id;
        public TextView name;
        public CheckBox done;
        public CheckBox favourite;
        public TextView expiry;

        public ViewHolder(View v) {
            super(v);
            todoContainer = (LinearLayout) v.findViewById(R.id.message_container);
            name = (TextView) v.findViewById(R.id.todo_name);
            expiry = (TextView) v.findViewById(R.id.todo_expiry);
            done = (CheckBox) v.findViewById(R.id.todo_done);
            favourite = (CheckBox) v.findViewById(R.id.todo_favourite);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = itemView.getContext();
            Intent showTodoDetails = new Intent(context, ToDoDetailActivity.class);
            showTodoDetails.putExtra("id", id+1);
            showTodoDetails.putExtra("createItem", false);
            context.startActivity(showTodoDetails);
        }
    }

    public ToDoListAdapter(Context context, Todo[] aDataset) {
        context = context;
        todos = aDataset;
    }

    @Override
    public ToDoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ToDoListAdapter.ViewHolder holder, int position) {
        Todo todo = todos[position];

        holder.id = todo.getId();
        holder.name.setText(todo.getName());
        holder.expiry.setText(String.valueOf(todo.getExpiry()));
        holder.done.setChecked(todo.isDone());
        holder.favourite.setChecked(todo.isFavourite());
    }

    @Override
    public int getItemCount() {
        return todos.length;
    }
}
