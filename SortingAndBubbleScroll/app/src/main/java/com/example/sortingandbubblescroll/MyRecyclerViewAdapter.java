package com.example.sortingandbubblescroll;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyHolder> {

    private final List<Employee> list;
    private final Context context;

    public MyRecyclerViewAdapter(List<Employee> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.tvID.setText(list.get(position).getDishID());
        holder.tvName.setText(list.get(position).getDishName());
        holder.tvDesc.setText(list.get(position).getDishDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        private TextView tvID,tvName,tvDesc;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvID = itemView.findViewById(R.id.ID);
            tvName = itemView.findViewById(R.id.Name);
            tvDesc = itemView.findViewById(R.id.Desc);

        }
    }

}
