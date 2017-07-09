package youtubeapidemo.examples.com.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 1515012 on 09-07-2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder> {
    private Context mContext;
    private ArrayList<Recipes.Ingredients> mIngredients;

    public IngredientAdapter(Context context, ArrayList<Recipes.Ingredients> ingredients) {
        mContext = context;
        mIngredients = ingredients;
    }


    @Override
    public IngredientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IngredientHolder(LayoutInflater.from(mContext).inflate(R.layout.ingredient_item, parent, false));
    }

    @Override
    public void onBindViewHolder(IngredientHolder holder, int position) {
        holder.textView.setText(mIngredients.get(position).getIngredients());
    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }

    public class IngredientHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_ingredient)
        TextView textView;

        public IngredientHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
