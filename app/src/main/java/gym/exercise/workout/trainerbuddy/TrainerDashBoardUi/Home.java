package gym.exercise.workout.trainerbuddy.TrainerDashBoardUi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import gym.exercise.workout.trainerbuddy.DataBaseHandler.LocalDataBaseHandler;
import gym.exercise.workout.trainerbuddy.Entities.SubscriptionPlan;
import gym.exercise.workout.trainerbuddy.R;

public class Home extends Fragment {
    private ViewPager2 SubscriptionPlanPager;
    private FragmentStateAdapter pagerAdapter;
    private static int NUM_PAGES;
    LocalDataBaseHandler LDB;
    static ArrayList<SubscriptionPlan> Plans = new ArrayList<SubscriptionPlan>();
    public static Home newInstance() {
        return new Home();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View Root=inflater.inflate(R.layout.trainer_home, container, false);

        LDB =new LocalDataBaseHandler(requireContext());
        NUM_PAGES=LDB.getTrainerSubscriptionGlobalPlansCountLDB();
        Plans=LDB.getTrainerSubscriptionGlobalPlansLDB();

        SubscriptionPlanPager =Root.findViewById(R.id.TrainerPlansPager);
        pagerAdapter= new SubscriptionPlanSlidePagerAdapter(getActivity());
        SubscriptionPlanPager.setAdapter(pagerAdapter);
        SubscriptionPlanPager.setCurrentItem(1, true);

        TabLayout tabLayout = (TabLayout) Root.findViewById(R.id.TrainerPlansPagerIndicator);
        new TabLayoutMediator(tabLayout, SubscriptionPlanPager,
                (tab, position) -> SubscriptionPlanPager.setCurrentItem(position, true)
        ).attach();
        SubscriptionPlanPager.setCurrentItem(1, true);



        return Root;
    }

    private static class SubscriptionPlanSlidePagerAdapter extends FragmentStateAdapter {
        public SubscriptionPlanSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }


        @Override
        public Fragment createFragment(int position) {
            // TODO pass SubscriptionPlan for different plans

            return new SubscriptionPlanCardFragment(Plans.get(position));
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }


}