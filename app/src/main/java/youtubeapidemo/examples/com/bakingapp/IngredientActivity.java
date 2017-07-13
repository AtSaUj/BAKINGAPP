package youtubeapidemo.examples.com.bakingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

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

public class IngredientActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.cook_custom_button)
    Button cooingButton;
    private ProgressDialog progressDialog;
    public static final String TAG = IngredientActivity.class.getSimpleName();
    private IngredientAdapter ingredientAdapter;
    private int pos;
    private ArrayList<String> arrayList;
    private ArrayList<Description> descriptionArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)

            actionBar.setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle(getString(R.string.progress_dialog));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        Intent intent = getIntent();

        arrayList = new ArrayList<>();

        descriptionArrayList=new ArrayList<>();
        if (intent != null) {
            pos = intent.getIntExtra(getString(R.string.LIST_KEY), 0);
        }
        makeJsonArrayRequest();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientAdapter = new IngredientAdapter(this, arrayList);
        recyclerView.setAdapter(ingredientAdapter);
        RecyclerView.OnItemTouchListener disabler = new RecyclerViewDisabler();
        recyclerView.addOnItemTouchListener(disabler);

        cooingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(IngredientActivity.this,DescriptionActivity.class);
                Bundle bundle=new Bundle();
                bundle.putInt("position",pos);
                bundle.putParcelableArrayList("list",  descriptionArrayList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void makeJsonArrayRequest() {
        String mUrlBaking = "https://go.udacity.com/android-baking-app-json";
        JsonArrayRequest req = new JsonArrayRequest(mUrlBaking,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String ingredientsRequirent = null;
                        try {
                            JSONObject recipe = (JSONObject) response.get(pos);
                            JSONArray ingredients = recipe.getJSONArray("ingredients");
                            for (int j = 0; j < ingredients.length(); j++) {
                                JSONObject ingr = (JSONObject) ingredients.get(j);
                                ingredientsRequirent = ingr.getString("ingredient") + " - " +
                                        ingr.getInt("quantity") + " " + ingr.getString("measure");
                                Log.i(TAG, ingredientsRequirent);
                                arrayList.add(ingredientsRequirent);
                            }
                            JSONArray steps = recipe.getJSONArray("steps");
                            for (int i = 0; i < steps.length(); i++) {
                                JSONObject step = (JSONObject) steps.get(i);
                                int id = step.getInt("id");
                                String shortDescription = step.getString("shortDescription");
                                String describe = step.getString("description");
                                String videoURL = step.getString("videoURL");
                                descriptionArrayList.add(new Description(id, shortDescription, describe, videoURL));
                            }
                            ingredientAdapter.changeData(arrayList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i(TAG, "Error: " + e.getMessage());
                        } finally {
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        MySingleton.getInstance(this).addToRequestQueue(req);
    }

    public class RecyclerViewDisabler implements RecyclerView.OnItemTouchListener {

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return true;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
