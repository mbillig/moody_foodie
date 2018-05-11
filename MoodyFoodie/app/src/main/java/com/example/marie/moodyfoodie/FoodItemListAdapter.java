package com.example.marie.moodyfoodie;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class FoodItemListAdapter extends ArrayAdapter<FoodItem> {
    private List<FoodItem> foodItems;

    public FoodItemListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<FoodItem> objects) {
        super(context, resource, objects);
        foodItems = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_item_row, parent, false);
        }

        FoodItem foodItem = foodItems.get(position);

        if (position % 2 == 1) {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.lightIndigo));
        } else {
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.lightLBlue));
        }

        TextView foodNameTextView = (TextView) convertView.findViewById(R.id.foodNameTextView);
        foodNameTextView.setText(foodItem.getItemName());
        TextView foodCategoryTextView = (TextView) convertView.findViewById(R.id.foodCategoryTextView);
        foodCategoryTextView.setText(foodItem.getItemCategory());

        return convertView;
    }
}
