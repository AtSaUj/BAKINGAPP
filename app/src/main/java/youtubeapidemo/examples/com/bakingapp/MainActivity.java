package youtubeapidemo.examples.com.bakingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/* Activity displays all available Recipies*/

public class MainActivity extends AppCompatActivity implements RecipeAdapter.ListItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    ArrayList<Recipes> arrayList;
    RecipeAdapter recipeAdapter;
    private static final int VERTICAL_ITEM_SPACE = 48;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle(getString(R.string.progress_dialog));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        makeJsonArrayRequest();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recipeAdapter = new RecipeAdapter(this, arrayList);
        recyclerView.setAdapter(recipeAdapter);
        recyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));
   //     recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,R.drawable.divider));
    }

    /**
     * Method to make json array request where response starts with [
     */
    private void makeJsonArrayRequest() {
        arrayList = new ArrayList<>();
        String mUrlBaking = "https://go.udacity.com/android-baking-app-json";
        JsonArrayRequest req = new JsonArrayRequest(mUrlBaking,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject person = (JSONObject) response.get(i);
                                String dish_Name = person.getString("name");
                                int id = person.getInt("id");
                                int servings = person.getInt("servings");
                                arrayList.add(new Recipes(id, dish_Name, servings));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i(TAG, "Error: " + e.getMessage());
                        } finally {
                            progressDialog.dismiss();
                        }
                        recipeAdapter.changeData(arrayList);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(req);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, IngredientActivity.class);
        intent.putExtra(getString(R.string.LIST_KEY), clickedItemIndex);
        startActivity(intent);
    }


}
