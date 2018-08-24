package invenz.movie.go.moviego1.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import invenz.movie.go.moviego1.models.Header;
import invenz.movie.go.moviego1.models.Movie;
import invenz.movie.go.moviego1.activities.MovieActivity;
import invenz.movie.go.moviego1.R;

public class MovieListCUstomAdapterr extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    //private Movie currentItem;

    Header header;
    List<Movie> movies;
    private Context context;

    public MovieListCUstomAdapterr(Header header, List<Movie> movies, Context context) {
        this.header = header;
        this.movies = movies;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header_item, parent, false);
            return  new VHHeader(v);
        }
        else if(viewType == TYPE_ITEM)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_movie_list_cardview, parent, false);
            return new VHItem(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    private Movie getItem(int position)
    {
        return movies.get(position);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VHHeader)
        {
            VHHeader VHheader = (VHHeader)holder;
            VHheader.txtTitle.setText(header.getHeader());
        }
        else if(holder instanceof VHItem)
        {
            final Movie currentItem = getItem(position-1);
            VHItem VHitem = (VHItem)holder;
            VHitem.txtName.setText(currentItem.getMovieName());
            //VHitem.iv.setImageResource(R.drawable.pic1);

            /*############### Load the image using Picasso ############################ */
            Picasso.with(context).load(currentItem.getImageLink()).resize(600, 200).into(VHitem.iv);

            /*###########  setOnClickListener ############*/
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(context, ""+((VHItem) holder).txtName.getText(), Toast.LENGTH_SHORT).show();

                    /*####                 Animation when cliked image                 ####*/
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_click));

                    Intent intent = new Intent(context, MovieActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putInt("id", currentItem.getId() );
                    bundle.putString("MovieName", currentItem.getMovieName() );
                    bundle.putString("MovieDesc", currentItem.getMovieDesc() );
                    bundle.putString("MovieLink1", currentItem.getMovieLink() );
                    bundle.putString("MovieLink2", currentItem.getMovieLink1() );
                    bundle.putString("MovieLink3", currentItem.getMovieLink2() );
                    bundle.putString("MovieLink4", currentItem.getMovieLink3() );
                    bundle.putString("MovieYear", currentItem.getReleaseDate() );
                    bundle.putString("MovieImage", currentItem.getImageLink() );
                    bundle.putString("YoutubeVideoId", currentItem.getYoutubeVideoId() );
                    bundle.putString("subtitle1", currentItem.getSubtitle1() );
                    bundle.putString("subtitle2", currentItem.getSubtitle2() );

                    //Toast.makeText(context, ""+currentItem.getMovieName(), Toast.LENGTH_SHORT).show();
                    intent.putExtra("MovieInfo", bundle);
                    context.startActivity(intent);

                }
            });

        }
    }

    //    need to override this method
    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else
            return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position)
    {
        return position == 0;
    }

    //increasing getItemcount to 1. This will be the row of header.
    @Override
    public int getItemCount() {
        return movies.size()+1;
    }

    class VHHeader extends RecyclerView.ViewHolder{
        TextView txtTitle;
        public VHHeader(View itemView) {
            super(itemView);
            this.txtTitle = (TextView)itemView.findViewById(R.id.txtHeader);
        }
    }

    class VHItem extends RecyclerView.ViewHolder{
        TextView txtName;
        ImageView iv;
        public VHItem(View itemView) {
            super(itemView);
            this.txtName = (TextView)itemView.findViewById(R.id.idMovieName_cardview);
            this.iv = (ImageView)itemView.findViewById(R.id.idMovieImage_cardview);
        }
    }
}
