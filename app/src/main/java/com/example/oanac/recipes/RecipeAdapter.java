package com.example.oanac.recipes;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RecipeAdapter {

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
