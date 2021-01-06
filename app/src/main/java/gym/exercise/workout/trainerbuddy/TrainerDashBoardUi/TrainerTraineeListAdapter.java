package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import gym.exercise.workout.trainerbuddy.Entities.SubscriptionPlan;
import gym.exercise.workout.trainerbuddy.Entities.Trainee;
import gym.exercise.workout.trainerbuddy.R;
import gym.exercise.workout.trainerbuddy.TrainerOfferingPlans;

public class TrainerTraineeListAdapter extends RecyclerView.Adapter<TrainerTraineeListAdapter.ViewHolder>  {

    private final ArrayList<Trainee> listdata;
    private final Context context;

    public TrainerTraineeListAdapter(ArrayList<Trainee> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }
    @NonNull
    @Override
    public TrainerTraineeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.trainer_trainee_list_item_view, parent, false);
        return new TrainerTraineeListAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainerTraineeListAdapter.ViewHolder holder, int position) {
        final Trainee myListData = listdata.get(position);
        holder.TraineeName.setText(listdata.get(position).getName());
        holder.OfferName.setText(listdata.get(position).getMySubscriptionPlan().getTitle());
        holder.SubscriptionLeftDetail.setText(listdata.get(position).getMySubscriptionPlan().getDaysLeft()+" Days left");
        holder.traineeImage.setImageBitmap(listdata.get(position).getPhoto());
        holder.suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"suggest me click kihis ", Toast.LENGTH_LONG).show();
            }
        });
        holder.newChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"newChart me click kihis ", Toast.LENGTH_LONG).show();
            }
        });
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(),"click on item with prize : "+myListData.getUID(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView traineeImage;
        public TextView SubscriptionLeftDetail,TraineeName,OfferName,suggest,newChart;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.TraineeName = (TextView) itemView.findViewById(R.id.trainerName);
            this.SubscriptionLeftDetail = (TextView) itemView.findViewById(R.id.subscriptionLeftDetail);
            this.OfferName = (TextView) itemView.findViewById(R.id.offerName);
            this.traineeImage = (ImageView) itemView.findViewById(R.id.traineeImage);
            this.suggest= (TextView) itemView.findViewById(R.id.suggest);
            this.newChart = (TextView) itemView.findViewById(R.id.newChart);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.traineeDetailClickSensor);
        }
    }


}
