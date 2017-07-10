package youtubeapidemo.examples.com.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1515012 on 09-07-2017.
 */

public class Recipes implements Parcelable {
    private int id, servings;
    private String dish_Name;
    private List<Ingredients> ingredientsArrayList;

    public Recipes(int id, String dish_name, int servings, List<Ingredients> ingredientsArrayList) {
        this.id = id;
        this.dish_Name = dish_name;
        this.servings = servings;
        this.ingredientsArrayList=ingredientsArrayList;
    }

    protected Recipes(Parcel in) {
        id = in.readInt();
        servings = in.readInt();
        dish_Name = in.readString();
        this.ingredientsArrayList = new ArrayList<>();
        in.readTypedList(ingredientsArrayList, Ingredients.CREATOR);
    }

    public static final Creator<Recipes> CREATOR = new Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel in) {
            return new Recipes(in);
        }

        @Override
        public Recipes[] newArray(int size) {
            return new Recipes[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getServings() {
        return servings;
    }

    public String getDish_Name() {
        return dish_Name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(servings);
        parcel.writeString(dish_Name);
        parcel.writeTypedList(ingredientsArrayList);
    }


    static class Ingredients implements Parcelable {
        String ingredients;
        Ingredients(String ingredients){
            this.ingredients=ingredients;
        }

       Ingredients(Parcel in){
           this.ingredients=in.readString();
       }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ingredients);
        }

        static final Parcelable.Creator<Ingredients> CREATOR
                = new Parcelable.Creator<Ingredients>() {

            public Ingredients createFromParcel(Parcel in) {
                return new Ingredients(in);
            }

            public Ingredients[] newArray(int size) {
                return new Ingredients[size];
            }
        };


        public String getIngredients() {
            return ingredients;
        }
    }
}
