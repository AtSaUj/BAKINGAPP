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
    ArrayList<Recipes.Ingredients> arrayList2ingredientsArrayList;
    RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle(getString(R.string.please_wait));
        progressDialog.show();
        makeJsonArrayRequest();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
       // recyclerView.addItemDecoration(mDividerItemDecoration);
        recipeAdapter = new RecipeAdapter(this, arrayList);
        recyclerView.setAdapter(recipeAdapter);
    }

    /**
     * Method to make json array request where response starts with [
     */
    private void makeJsonArrayRequest() {
        arrayList = new ArrayList<>();
        arrayList2ingredientsArrayList = new ArrayList<>();
        String mUrlBaking = "https://go.udacity.com/android-baking-app-json";
        JsonArrayRequest req = new JsonArrayRequest(mUrlBaking,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String ingredientsRequirent;
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject person = (JSONObject) response.get(i);
                                String dish_Name = person.getString("name");
                                int id = person.getInt("id");
                                //*JSONObject phone = person.getJSONObject("phone");*//*
                                //  String ingredients = person.getString("ingredients");
                                int servings = person.getInt("servings");
                                JSONArray ingredients = person.getJSONArray("ingredients");
                                for (int j = 0; j < ingredients.length(); j++) {
                                    JSONObject ing = (JSONObject) ingredients.get(i);
                                    ingredientsRequirent = ing.getInt("quantity") +
                                            ing.getString("measure") +
                                            ing.getString("ingredient");
                                    arrayList2ingredientsArrayList.add(new Recipes.Ingredients(ingredientsRequirent));
                                }/*

                                JSONArray steps = person.getJSONArray("Steps");
                                for (int j = 0; j < ingredients.length(); j++) {
                                }
*/
                                arrayList.add(new Recipes(id, dish_Name, servings,arrayList2ingredientsArrayList));
                            }

                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i(TAG, "Error: " + e.getMessage());
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
        Intent intent = new Intent(MainActivity.this,IngredientActivity.class);
        Bundle b = new Bundle();
        b.putInt(getString(R.string.POSITION_KEY), clickedItemIndex);
        b.putParcelableArrayList(getString(R.string.LIST_KEY), arrayList);
        intent.putExtras(b);
        startActivity(intent);
    }


}
