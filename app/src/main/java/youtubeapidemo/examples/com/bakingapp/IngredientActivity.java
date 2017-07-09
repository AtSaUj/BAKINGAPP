package youtubeapidemo.examples.com.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientActivity extends AppCompatActivity {
 @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private IngredientAdapter ingredientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(this);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientAdapter=new IngredientAdapter(this,new ArrayList<Recipes.Ingredients>());
        recyclerView.setAdapter(ingredientAdapter);
    }

}
