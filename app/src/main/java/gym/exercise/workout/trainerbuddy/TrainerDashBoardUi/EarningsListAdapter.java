package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import gym.exercise.workout.trainerbuddy.R;

public class EarningsListAdapter extends  RecyclerView.Adapter<EarningsListAdapter.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ProfilePic;
        public TextView Name,PavementDate,PaymentAmount;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.ProfilePic = (ImageView) itemView.findViewById(R.id.ProfilePic);
            this.Name = (TextView) itemView.findViewById(R.id.TraineeName);
            this.PavementDate = (TextView) itemView.findViewById(R.id.TraineePlaymentDate);
            this.PaymentAmount = (TextView) itemView.findViewById(R.id.PaymentAmount);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.Trainer_Earnings_Item_Holder);
        }
    }
}
