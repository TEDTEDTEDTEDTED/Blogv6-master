package com.example.jingjing.blogv6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter< MovieListAdapter.TheHolder>{

    private List<Movies> movielists;

    public MovieListAdapter(List<Movies> movielists) {

        this.movielists = movielists;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public TheHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.row_taba,parent,false);
        TheHolder theHolder = new TheHolder(view);
        return theHolder;




    }

    @Override
    public void onBindViewHolder(@NonNull final TheHolder holder, int position) {
        Movies Movies =movielists.get(position);
        holder.movieTextview.setText(Movies.getMovie());
        holder.timeTextview.setText(Movies.getMovietime());
        holder.scoreTextview.setText(String.valueOf(Movies.getScore()));

        new AsyncTask<String, Void, Bitmap>()
        {
            @Override
            protected Bitmap doInBackground(String... params)
            {
                String url = params[0];
                return getBitmapFromURL(url);
            }

            @Override
            protected void onPostExecute(Bitmap result)
            {
                holder.pictureImageView.setImageBitmap (result);
                super.onPostExecute(result);
            }
        }.execute(Movies.getPicture());




    }

    @Override
    public int getItemCount() {




        return movielists.size();
    }

    public  void setfilter(List<Movies> listitem){
    movielists = new ArrayList<>();
    movielists.addAll(listitem);
    notifyDataSetChanged();


    }



    private static Bitmap getBitmapFromURL(String imageUrl)
    {
        try
        {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }





    public static class TheHolder extends RecyclerView.ViewHolder {
        private final TextView movieTextview;
        private final TextView timeTextview;
        private final TextView scoreTextview;
        private final ImageView pictureImageView;


        public TheHolder(View itemView) {
            super(itemView);
            movieTextview = itemView.findViewById(R.id.movie_name);
            timeTextview = itemView.findViewById(R.id.movie_time);
            scoreTextview = itemView.findViewById(R.id.movie_score);
            pictureImageView=itemView.findViewById(R.id.movie_image);

        }






    }













}
