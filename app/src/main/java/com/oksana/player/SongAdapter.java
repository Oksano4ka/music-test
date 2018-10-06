package com.oksana.player;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder>{


        public class SongViewHolder extends RecyclerView.ViewHolder {
            public TextView title, author;
            public ImageView image;
            public CardView cardView;

            public SongViewHolder(View view) {
                super(view);
                title = view.findViewById(R.id.song_name);
                author = view.findViewById(R.id.song_autor);
                image = view.findViewById(R.id.song_image);
                cardView = view.findViewById(R.id.song_card_view);
            }
        }

        private Context ctx;
        private List<PlayerActivity.Song> playList;

        public SongAdapter(Context context, List<PlayerActivity.Song> list){
            ctx = context;
            playList = list;
        }
    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_item_layout, parent, false);

        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
            PlayerActivity.Song item = playList.get(position);
        holder.title.setText(item.Name);
        holder.author.setText(item.Owner);
        Bitmap img = item.getImage();
        if(img == null){
            holder.image.setImageResource(R.drawable.nota);
        }else {
            holder.image.setImageBitmap(img);
        }

    }

    @Override
    public int getItemCount() {
        return playList.size();
    }
}
