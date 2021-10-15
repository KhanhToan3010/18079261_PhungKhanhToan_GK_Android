package com.example.a18_18079261_phungkhanhtoan_appmusic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    List<Song> mSongs;
    OnClickListener listener;

    public CustomAdapter(List<Song> songs, OnClickListener listener) {
        mSongs = songs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rcv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Song song = mSongs.get(position);
        holder.mSong = song;
        holder.image.setImageResource(song.getImage());
        holder.txtSong.setText(song.getTitle());
        holder.txtSinger.setText(song.getSingle());

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView txtSong, txtSinger;
        Song mSong ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.rcv_image);
            txtSong = itemView.findViewById(R.id.rcv_singer);
            txtSinger = itemView.findViewById(R.id.rcv_singer);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.itemClick(mSong);
                }
            });
        }

    }
}
