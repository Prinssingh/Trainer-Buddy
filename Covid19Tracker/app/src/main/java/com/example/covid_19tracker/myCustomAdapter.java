package com.example.covid_19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class myCustomAdapter extends ArrayAdapter<CountryModel> {

    private final Context mContext;
    private final List<CountryModel> countryModelsList;
    private List<CountryModel> countryModelsListFiltered;

    public myCustomAdapter(Context context, List<CountryModel> countryModelList) {
        super(context, R.layout.list_custom_iten,countryModelList);

        this.mContext = context;
        this.countryModelsList = countryModelList;
        this.countryModelsListFiltered = countryModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_custom_iten,null,true);
        TextView tvCountryName = view.findViewById(R.id.tvCountryName);
        ImageView imageView = view.findViewById(R.id.imageFlag);

        tvCountryName.setText(countryModelsListFiltered.get(position).getCountry());
        Glide.with(mContext).load(countryModelsList.get(position).getFlag()).into(imageView);

        return view;
    }

    @Override
    public int getCount() {
        return countryModelsListFiltered.size();
    }

    @Nullable
    @Override
    public CountryModel getItem(int position) {
        return countryModelsListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                FilterResults filterResults = new FilterResults();
                if(charSequence == null || charSequence.length() == 0){
                    filterResults.count = countryModelsList.size();
                    filterResults.values = countryModelsList;
                }else{
                    List<CountryModel> resultsModel = new ArrayList<>();
                    String searchStr = charSequence.toString().toLowerCase();

                    for(CountryModel itemsModel: countryModelsList){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);
                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                countryModelsListFiltered = (List<CountryModel>) filterResults.values;
                AffecrtedCountries.countryModelList = (List<CountryModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }
}
