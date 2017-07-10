package youtubeapidemo.examples.com.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    public static final String TAG = IngredientActivity.class.getSimpleName();
    private IngredientAdapter ingredientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        ArrayList<Recipes> arrayList = null;
        if (extras != null) {
          arrayList = extras.getParcelableArrayList(getString(R.string.LIST_KEY));
        }
        int position = extras.getInt(getString(R.string.POSITION_KEY), getResources().getInteger(R.integer.POSITION_DEFAULT));
          // Log.d(TAG,arrayList.get(position).get);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientAdapter = new IngredientAdapter(this, new ArrayList<Recipes.Ingredients>());
        recyclerView.setAdapter(ingredientAdapter);
    }

}
