package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import gym.exercise.workout.trainerbuddy.Entities.SubscriptionPlan;
import gym.exercise.workout.trainerbuddy.R;
import gym.exercise.workout.trainerbuddy.TrainerOfferingPlans;

public class OfferingPlansListAdapter extends RecyclerView.Adapter<OfferingPlansListAdapter.ViewHolder> {


    private final ArrayList <SubscriptionPlan> listdata;
    private final Fragment fragment;
    private  Context context;

    // RecyclerView recyclerView;
    public OfferingPlansListAdapter(ArrayList <SubscriptionPlan>listdata, Fragment fragment, Context context) {
        this.listdata = listdata;
        this.fragment = fragment;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.trainer_offering_plan_item_view, parent, false);
        return new ViewHolder(listItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SubscriptionPlan myListData = listdata.get(position);
        holder.Title.setText(listdata.get(position).getTitle());
        holder.Prize.setText("₹"+listdata.get(position).getPrize());
        holder.Days.setText(listdata.get(position).getDays()+" Days");
        holder.About.setText(listdata.get(position).getAbout());


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Starting Edit Activity
                Intent intent = new Intent(context, TrainerOfferingPlans.class);
                Bundle extras = new Bundle();
                extras.putString("PlanID",listdata.get(position).getID());
                extras.putInt("Position",position);
                intent.putExtras(extras);
                //intent.putExtra("PlanID",listdata.get(position).getID());
                //intent.putExtra("Position",position);
                Log.e("TAG", "onClick: Adapter " +position );
                fragment.startActivityForResult(intent,3);

                 }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public void updateData(ArrayList<SubscriptionPlan> plan) {
        listdata.clear();
        listdata.addAll(plan);
        notifyDataSetChanged();
    }
    public void addItem(int position, SubscriptionPlan plan) {
        listdata.add(position, plan);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        listdata.remove(position);
        notifyItemRemoved(position);
    }
    public void Update(int Position,SubscriptionPlan Plan){
        listdata.set(Position,Plan);
        Log.e("TAG", "Update: "+ Position);
        notifyItemChanged(Position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Title,Prize,Days,About;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.Title = (TextView) itemView.findViewById(R.id.PlanTitle);
            this.Prize = (TextView) itemView.findViewById(R.id.PlanRupee);
            this.Days = (TextView) itemView.findViewById(R.id.PlanDays);
            this.About = (TextView) itemView.findViewById(R.id.PlanAbout);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.offeringPlanClickSensor);
        }
    }

}
