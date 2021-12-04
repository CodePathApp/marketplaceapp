package com.example.marketplaceapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import java.util.HashMap;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;
    private HashMap<Post, List<Images>> map;

    public PostsAdapter(Context context, List<Post> posts,HashMap<Post, List<Images>>map){
        this.context=context;
        this.posts=posts;
        this.map = map;
    }

    @NonNull
    //@org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);
        List<Images> images = map.get(post);
        System.out.println(images);
        holder.bind(post,images,context);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvUserName;
        //private ImageView ivImage;
        private ViewPager vpPostImages;
        private TextView tvDescription;
        private TextView tvPrice;
        private TextView tvCaption;
        private ImageAdapter adapter;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUsername);
            //ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            vpPostImages = itemView.findViewById(R.id.vpPostImages);
        }

        public void bind(Post post,List<Images> images,Context context){
            tvDescription.setText(post.getDescription());
            tvUserName.setText(post.getUser().getUsername());
            tvPrice.setText(post.getPrice().toString());
            tvCaption.setText(post.getCaption());
            adapter = new ImageAdapter(context);
            vpPostImages.setAdapter(adapter);
            System.out.println("Images1 "+  images);
            if (images != null){
                System.out.println("Images "+  images);
                for(Images image :images ) {
                    image.getImage().getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                            adapter.addImage(bitmap);
                        }
                    });
                }
                /*adapter.addImage(Glide.with(context).load(image.getImage().getUrl()));
                adapter.addImage(image.getImage());
                image.getImage().getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] data, ParseException e) {
                        data
                        adapter.addImage(data);
                    }
                });
                adapter.addImage(image.getImage().);*/
            }
            /*ParseFile image = post.getImage();
            if(image !=null){
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            }*/
        }

    }


    public void clear(){
        posts.clear();
        map.clear();
        notifyDataSetChanged();
    }
    public void addAll(List<Post> postsList,HashMap<Post, List<Images>> mapList){
        posts.addAll(postsList);
        map.putAll(mapList);
        notifyDataSetChanged();
    }
}
