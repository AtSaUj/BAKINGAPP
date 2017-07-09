package youtubeapidemo.examples.com.bakingapp;

import android.content.Context;
import android.support.v7.widget.CardView;
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

class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private Context mContext;
    public ArrayList<Recipes> mRecipes;

    RecipeAdapter(Context context, ArrayList<Recipes> arrayList) {
        mContext = context;
        mRecipes = arrayList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecipeViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.card_recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.title.setText(mRecipes.get(position).getDish_Name());
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }


    public void changeData(ArrayList<Recipes> arrayList) {
        mRecipes = arrayList;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.card_view)
        CardView cardView;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
