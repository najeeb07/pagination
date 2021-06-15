package com.example.pagnation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    //Imageloader to load image
    private ImageLoader imageLoader;
    private Context context;

    //List to store all superheroes
    List<Passenger> passengerList;

    //Constructor of this class
    public CardAdapter(List<Passenger> passengerList, Context context){

        //Getting all superheroes
        this.passengerList = passengerList;
        this.context = context;
    }
    public void UpdateData(List<Passenger> list) {
        this.passengerList = new ArrayList<Passenger>();
        this.passengerList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.superheroes_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Getting the particular item from the list
        Passenger superHero =  passengerList.get(position);

        //Loading image from url
//        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
//        imageLoader.get(superHero.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.drawable.image, android.R.drawable.ic_dialog_alert));

        //Showing data on the views
//        holder.imageView.setImageUrl(superHero.getImageUrl(), imageLoader);
        holder.textViewName.setText(superHero.getName());
        holder.textViewtrips.setText(String.valueOf(superHero.getTrips()));

    }

    @Override
    public int getItemCount() {
        return passengerList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views
//        public NetworkImageView imageView;
        public TextView textViewName;
        public TextView textViewtrips;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
//            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewtrips = (TextView) itemView.findViewById(R.id.texttrips);
        }
    }
}