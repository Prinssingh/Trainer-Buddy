package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.content.Context;
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

import gym.exercise.workout.trainerbuddy.Entities.Trainee;
import gym.exercise.workout.trainerbuddy.R;

public class TrainerEarningsListAdapter extends RecyclerView.Adapter<TrainerEarningsListAdapter.ViewHolder> {

    private final ArrayList<Trainee> listdata;
    private final Context context;

    public TrainerEarningsListAdapter(ArrayList<Trainee> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @NonNull
    @Override
    public TrainerEarningsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.trainer_earnings_item_view, parent, false);
        return new TrainerEarningsListAdapter.ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Trainee myListData = listdata.get(position);
        holder.traineeNameEarning.setText(listdata.get(position).getName());
        holder.planNameEarning.setText(listdata.get(position).getMySubscriptionPlan().getTitle());
        holder.subscriptionLeftDetail.setText(listdata.get(position).getMySubscriptionPlan().getDaysLeft() + " Days left");
        holder.traineeImage.setImageBitmap(listdata.get(position).getPhoto());
        holder.feeEarning.setText(listdata.get(position).getMySubscriptionPlan().getPrize());
        holder.DateTimeEarning.setText(listdata.get(position).getMySubscriptionPlan().getDays());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView traineeImage;
        public TextView planNameEarning, traineeNameEarning, DateTimeEarning, feeEarning, subscriptionLeftDetail;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.traineeNameEarning = (TextView) itemView.findViewById(R.id.planNameEarning);
            this.planNameEarning = (TextView) itemView.findViewById(R.id.subscriptionLeftDetail);
            this.DateTimeEarning = (TextView) itemView.findViewById(R.id.DateTimeEarning);
            this.traineeImage = (ImageView) itemView.findViewById(R.id.traineeImage);
            this.feeEarning = (TextView) itemView.findViewById(R.id.feeEarning);
            this.subscriptionLeftDetail = (TextView) itemView.findViewById(R.id.subscriptionLeftDetail);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.trainerEarningsClickSensor);
        }
    }

}
