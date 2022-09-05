package org.test.movies.Videos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.test.movies.R;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {
    ArrayList<Video> videos;
    Context context;
    public VideoAdapter(){}
    public VideoAdapter(Context myContext){
        context = myContext;
    }
    public VideoAdapter(Context myContext,ArrayList <Video> myVideos){
        videos = myVideos;
        context = myContext;
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.videoplay_view,parent,false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
        try {
            String KEY = "SUXWAEX2jlg";
            KEY = videos.get(position).getKey().toString();
            String youtube_link = "https://www.youtube.com/watch?v=" + KEY;
            final Uri trailer_uri = Uri.parse(youtube_link);
            int number = position + 1;
            holder.trailer_number.setText("Trailer " + number);
            holder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, trailer_uri);
                    context.startActivity(intent);
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return videos.size();
//        return 1;
    }

    public class VideoHolder extends RecyclerView.ViewHolder {
        ImageView playButton ;
        TextView trailer_number;
        public VideoHolder(View itemView) {
            super(itemView);
            playButton = (ImageView) itemView.findViewById(R.id.play_trailer_button);
            trailer_number = (TextView) itemView.findViewById(R.id.trailer_number);
            playButton.setBackground(null);
        }
    }
}
