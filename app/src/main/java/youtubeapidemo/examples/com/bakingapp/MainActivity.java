package youtubeapidemo.examples.com.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/* Activity displays all available Recipies*/

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ListItemClickListener {
    Communicator communicator;

    public interface Communicator {
        public void respond();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
    }
    @Override
    public void onListItemClick(int clickedItemIndex) {
        if(Configuration.ORIENTATION_PORTRAIT==getResources().getConfiguration().orientation) {
            Intent intent = new Intent(MainActivity.this, IngredientActivity.class);
            intent.putExtra(getString(R.string.LIST_KEY), clickedItemIndex);
            startActivity(intent);
        }
    }

}
