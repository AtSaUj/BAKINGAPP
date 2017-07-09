package youtubeapidemo.examples.com.bakingapp;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
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

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    ArrayList<Recipes> arrayList;
    ArrayList<Recipes.Ingredients> arrayList2;
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
        progressDialog.show();
        makeJsonArrayRequest();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.item_separator);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        //        = new DividerItemDecoration(recyclerView.getContext(),
          //      linearLayoutManager.getOrientation());
      //  mDividerItemDecoration.setDrawable(R.g);
        recyclerView.addItemDecoration(mDividerItemDecoration);
        recipeAdapter = new RecipeAdapter(this, arrayList);
        recyclerView.setAdapter(recipeAdapter);
    }

    /**
     * Method to make json array request where response starts with [
     */
    private void makeJsonArrayRequest() {
        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();
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
                                    arrayList2.add(new Recipes.Ingredients(ingredientsRequirent));
                                }/*

                                JSONArray steps = person.getJSONArray("Steps");
                                for (int j = 0; j < ingredients.length(); j++) {

                                }
*/
                                arrayList.add(new Recipes(id, dish_Name, servings));
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
}
