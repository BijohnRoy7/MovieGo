package invenz.movie.go.moviego1.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import invenz.movie.go.moviego1.models.Catagory;
import invenz.movie.go.moviego1.activities.MovieListActivity;
import invenz.movie.go.moviego1.R;

public class CatagoryCustomAdapter extends RecyclerView.Adapter<CatagoryCustomAdapter.MyViewHolder> {

    private Context context;
    private List<Catagory> catagories;

    public CatagoryCustomAdapter(Context context, List<Catagory> catagories) {
        this.context = context;
        this.catagories = catagories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.single_catagory_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Catagory catagory = catagories.get(position);
        int image = catagory.getCatagoryImage();
        String name = catagory.getCatagoryName();

        holder.imageViewMovie.setImageResource(image);
        holder.tvMovie.setText(name);

        /*######### applying OnClickListener method #############*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, ""+holder.tvMovie.getText(), Toast.LENGTH_SHORT).show();

                /*####                 Animation when cliked image                 ####*/
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.image_click));

                String catagory = holder.tvMovie.getText().toString();

                /*########## sending to next activity with catagory name ############*/
                Intent intent = new Intent(context.getApplicationContext(), MovieListActivity.class);
                intent.putExtra("catagory", catagory);
                context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return catagories.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewMovie ;
        private TextView tvMovie ;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewMovie = itemView.findViewById(R.id.idCatagoryImage);
            tvMovie = itemView.findViewById(R.id.idCatagoryName);
        }
    }
}
