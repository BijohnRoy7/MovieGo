package invenz.movie.go.moviego1.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import invenz.movie.go.moviego1.models.Movie;
import invenz.movie.go.moviego1.models.MovieLinks;
import invenz.movie.go.moviego1.R;
import invenz.movie.go.moviego1.utils.YoutubeApiKey;

public class MovieInfoCustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private int flag = 1;

    private Context context;
    private Movie movie;
    private MovieLinks movieLink;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private String vedioId;

    private InterstitialAd mInterstitialAd;

    public MovieInfoCustomAdapter(Context context, Movie movie, MovieLinks movieLink) {
        this.context = context;
        this.movie = movie;
        this.movieLink = movieLink;

        /*################## Loading Ad ####################*/
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_header, parent, false);
            return  new VHHeader(v);
        }
        else if(viewType == TYPE_ITEM)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_movie_link, parent, false);
            return new VHItem(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }





    private MovieLinks getItem(int position)
    {
        return movieLink;
    }






    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VHHeader)
        {
            VHHeader VHheader = (VHHeader)holder;
            vedioId = movie.getYoutubeVideoId();
            VHheader.movieDesc.setText(movie.getMovieDesc());

            if (vedioId.isEmpty()){


                VHheader.constraintLayout.setVisibility(View.VISIBLE);
                VHheader.movieName.setText(movie.getMovieName());

                /*#####                   Load the image using Glide                  ####*/
                Picasso.with(context).load(movie.getImageLink()).resize(600, 200).into(VHheader.imageViewMovie);
            }


            VHheader.movieNameYoutube.setText(movie.getMovieName());
            /*#####                            youtube player                              ####*/
            onInitializedListener = new YouTubePlayer.OnInitializedListener() {

                @Override
                public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                    if (!b){
                        youTubePlayer.cueVideo(vedioId);
                    }
                }

                @Override
                public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                    Toast.makeText(context, "Failed to initialize Youtube", Toast.LENGTH_SHORT).show();
                }
            };

            ((VHHeader) holder).youTubePlayerView.initialize(YoutubeApiKey.getApiKey(), onInitializedListener);
               /////////////////////////////////////////////////////////////////////////////////////

        }
        else if(holder instanceof VHItem)
        {
            final MovieLinks movieLink = getItem(position - 1);

            /*############ LINKS ##############*/
            ((VHItem) holder).tvMovieLink.setText("Movie Link 1");
            ((VHItem) holder).tvMovieLink2.setText("Movie Link 2");
            ((VHItem) holder).tvMovieLink3.setText("Movie Link 3");
            ((VHItem) holder).tvMovieLink4.setText("Movie Link 4");
            ((VHItem) holder).subtitle1.setText("Subtitle Link 1");
            ((VHItem) holder).subtitle2.setText("Subtitle Link 2");


            ((VHItem) holder).tvMovieLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, ""+((VHItem) holder).tvMovieLink.getText(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, ""+movieLink.getLink1(), Toast.LENGTH_SHORT).show();

                    /*####                 Animation when cliked image                 ####*/
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_click));

                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieLink.getLink1())));

/*
                    if (mInterstitialAd.isLoaded()){
                        //Toast.makeText(context, "Lodaded", Toast.LENGTH_SHORT).show();
                        mInterstitialAd.show();



                        mInterstitialAd.setAdListener(new AdListener(){
                            @Override
                            public void onAdClosed() {
                                super.onAdClosed();
                                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieLink.getLink1())));
                            }
                        });
                    }else {
                        //Toast.makeText(context, "Not loaded", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieLink.getLink1())));
                    }

 */

                }
            });

            ((VHItem) holder).tvMovieLink2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, ""+((VHItem) holder).tvMovieLink2.getText(), Toast.LENGTH_SHORT).show();
                    /*####                 Animation when cliked image                 ####*/
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.click_anim));

                    if (movieLink.getLink2().isEmpty()){
                        Toast.makeText(context, "No link available", Toast.LENGTH_SHORT).show();

                    }else {

                        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieLink.getLink2())));


                        /*######## For showing ad ##########*/
/*
                        if (mInterstitialAd.isLoaded()){
                            //Toast.makeText(context, "Lodaded", Toast.LENGTH_SHORT).show();
                            mInterstitialAd.show();

                            mInterstitialAd.setAdListener(new AdListener(){
                                @Override
                                public void onAdClosed() {
                                    super.onAdClosed();
                                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieLink.getLink2())));
                                }
                            });
                        }else {
                            //Toast.makeText(context, "Not loaded", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieLink.getLink2())));
                        }
*/

                    }
                }
            });

            ((VHItem) holder).tvMovieLink3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, ""+((VHItem) holder).tvMovieLink3.getText(), Toast.LENGTH_SHORT).show();

                    /*####                 Animation when cliked image                 ####*/
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.click_anim));

                    if (movieLink.getLink3().isEmpty()){
                        Toast.makeText(context, "No link available", Toast.LENGTH_SHORT).show();

                    }else {

                        /*######## For showing ad ##########*/
                        if (mInterstitialAd.isLoaded()){
                            //Toast.makeText(context, "Lodaded", Toast.LENGTH_SHORT).show();
                            mInterstitialAd.show();

                            mInterstitialAd.setAdListener(new AdListener(){
                                @Override
                                public void onAdClosed() {
                                    super.onAdClosed();
                                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieLink.getLink3())));
                                }
                            });
                        }else {
                            //Toast.makeText(context, "Not loaded", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieLink.getLink3())));
                        }

                    }

                }
            });


            ((VHItem) holder).tvMovieLink4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, ""+((VHItem) holder).tvMovieLink3.getText(), Toast.LENGTH_SHORT).show();

                    /*####                 Animation when cliked image                 ####*/
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.click_anim));

                    if (movieLink.getLink4().isEmpty()){
                        Toast.makeText(context, "No link available", Toast.LENGTH_SHORT).show();
                    }else {

                        /*######## For showing ad ##########*/
                        if (mInterstitialAd.isLoaded()){
                            //Toast.makeText(context, "Lodaded", Toast.LENGTH_SHORT).show();
                            mInterstitialAd.show();

                            mInterstitialAd.setAdListener(new AdListener(){
                                @Override
                                public void onAdClosed() {
                                    super.onAdClosed();
                                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieLink.getLink4())));
                                }
                            });
                        }else {
                            //Toast.makeText(context, "Not loaded", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieLink.getLink4())));
                        }

                    }

                }
            });


            ((VHItem) holder).subtitle1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, ""+((VHItem) holder).tvMovieLink3.getText(), Toast.LENGTH_SHORT).show();

                    /*####                 Animation when cliked image                 ####*/
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.click_anim));

                    if (movieLink.getSubtitle1().isEmpty()){
                        Toast.makeText(context, "No link available", Toast.LENGTH_SHORT).show();
                    }else {

                        /*######## For showing ad ##########*/
                        if (mInterstitialAd.isLoaded()){
                            //Toast.makeText(context, "Lodaded", Toast.LENGTH_SHORT).show();
                            mInterstitialAd.show();

                            mInterstitialAd.setAdListener(new AdListener(){
                                @Override
                                public void onAdClosed() {
                                    super.onAdClosed();
                                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieLink.getSubtitle1())));
                                }
                            });
                        }else {
                            //Toast.makeText(context, "Not loaded", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieLink.getSubtitle1())));
                        }

                    }

                }
            });


            ((VHItem) holder).subtitle2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context, ""+((VHItem) holder).tvMovieLink3.getText(), Toast.LENGTH_SHORT).show();

                    /*####                 Animation when cliked image                 ####*/
                    v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.click_anim));

                    if (movieLink.getSubtitle1().isEmpty()){
                        Toast.makeText(context, "No link available", Toast.LENGTH_SHORT).show();
                    }else {

                        /*######## For showing ad ##########*/
                        if (mInterstitialAd.isLoaded()){
                            //Toast.makeText(context, "Lodaded", Toast.LENGTH_SHORT).show();
                            mInterstitialAd.show();

                            mInterstitialAd.setAdListener(new AdListener(){
                                @Override
                                public void onAdClosed() {
                                    super.onAdClosed();
                                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieLink.getSubtitle2())));
                                }
                            });
                        }else {
                            //Toast.makeText(context, "Not loaded", Toast.LENGTH_SHORT).show();
                            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieLink.getSubtitle2())));
                        }

                    }

                }
            });



            /*VHItem VHitem = (VHItem)holder;
            VHitem.tvMovieLink.setText(currentItem.getLinks());
            VHitem.iv.setBackgroundResource(currentItem.getId());*/
        }
    }

    //    need to override this method
    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position)
    {
        return position == 0;
    }

    //increasing getItemcount to 1. This will be the row of movie.
    @Override
    public int getItemCount() {
        return 2;
    }





    class VHHeader extends RecyclerView.ViewHolder{
        TextView movieName, movieDesc, movieNameYoutube;
        ImageView imageViewMovie ;
        YouTubePlayerView youTubePlayerView;
        ConstraintLayout constraintLayout;

        public VHHeader(View itemView) {
            super(itemView);

            constraintLayout = itemView.findViewById(R.id.idConstraintLayout);
            movieName = itemView.findViewById(R.id.idMovieName_movieHeader);
            movieDesc = itemView.findViewById(R.id.idMovieDesc_movieHeader);
            imageViewMovie = itemView.findViewById(R.id.idMovieImage_movieHeader);

            youTubePlayerView = itemView.findViewById(R.id.idYoutubePlayer_movieHeader);
            movieNameYoutube = itemView.findViewById(R.id.idMovieNameYoutube_movieHeader);

        }
    }

    class VHItem extends RecyclerView.ViewHolder{
        TextView tvMovieLink, tvMovieLink2, tvMovieLink3, tvMovieLink4, subtitle1, subtitle2 ;

        public VHItem(View itemView) {
            super(itemView);
            tvMovieLink = itemView.findViewById(R.id.idMovieLink_singleMovieLink);
            tvMovieLink2 = itemView.findViewById(R.id.idMovieLink1_singleMovieLink);
            tvMovieLink3 = itemView.findViewById(R.id.idMovieLink2_singleMovieLink);
            tvMovieLink4 = itemView.findViewById(R.id.idMovieLink3_singleMovieLink);
            subtitle1 = itemView.findViewById(R.id.idSubtitleLink1_singleMovieLink);
            subtitle2 = itemView.findViewById(R.id.idSubtitleLink2_singleMovieLink);
        }
    }
}
