package org.test.movies.Reviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.test.movies.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    private Context context;
    private  ArrayList<Review> reviews;
    public ReviewAdapter(){}
    public ReviewAdapter(Context myContext){
        this.context = myContext;
    }
    public ReviewAdapter(Context myContext,ArrayList <Review> myReviews){
        this.reviews = myReviews;
        this.context = myContext;
    }
    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.each_review_item,parent,false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder holder, int position) {
        try {
            String authorName = reviews.get(position).getAuthor();
            String content = reviews.get(position).getContent();
            holder.reviewAuthor.setText(authorName);
            holder.reviewContent.setText(content);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return  reviews.size();
//        return 4;
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {
        TextView reviewAuthor;
        TextView reviewContent;
        public ReviewHolder(@NonNull View itemView) {
            super(itemView);
            reviewAuthor = itemView.findViewById(R.id.review_author);
            reviewContent = itemView.findViewById(R.id.review_content);
        }
    }
}
