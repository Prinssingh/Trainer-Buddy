package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import gym.exercise.workout.trainerbuddy.Entities.SubscriptionPlan;
import gym.exercise.workout.trainerbuddy.R;
import gym.exercise.workout.trainerbuddy.TrainerOfferingPlans;

public class OfferingPlansListAdapter extends RecyclerView.Adapter<OfferingPlansListAdapter.ViewHolder> {


    private final SubscriptionPlan[] listdata;
    private final Context context;

    // RecyclerView recyclerView;
    public OfferingPlansListAdapter(SubscriptionPlan[] listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
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
        final SubscriptionPlan myListData = listdata[position];
        holder.Title.setText(listdata[position].getTitle());
        holder.Prize.setText("₹"+listdata[position].getPrize());
        holder.Days.setText(listdata[position].getDays()+" Days");
        holder.About.setText(listdata[position].getAbout());


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TrainerOfferingPlans.class);
                intent.putExtra("PlanID",myListData.getID());
                context.startActivity(intent);
                Toast.makeText(view.getContext(),"click on item with prize : "+myListData.getID(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.length;
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
