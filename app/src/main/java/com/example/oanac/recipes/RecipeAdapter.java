package com.example.oanac.recipes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    ArrayList<Recipe> recipies;
    public RecipeAdapter(ArrayList<Recipe> recipies) {
        this.recipies = recipies;
    }
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_item, viewGroup, false);
        return new RecipeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int currentPosition) {
        Recipe recipe = recipies.get(currentPosition);
        recipeViewHolder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipies.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle,
                 tvAuthors,
                 tvDate,
                 tvPublisher;
        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvAuthors = (TextView) itemView.findViewById(R.id.tvAuthors);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvPublisher = (TextView) itemView.findViewById(R.id.tvPublisher);
        }

        public void bind(Recipe recipe) {
            String authors = "";
            int i = 0;
            for (String author : recipe.authors) {
                authors += author;
                i++;
                if (i < recipe.authors.length) {
                    authors += ", ";
                }
            }
            tvAuthors.setText(authors);
            tvTitle.setText(recipe.title);
            tvDate.setText(recipe.publishedDate);
            tvPublisher.setText(recipe.publisher);
        }
    }
}
