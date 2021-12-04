package com.example.marketplaceapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private List<Bitmap> mImages = new ArrayList<>();

    public ImageAdapter(Context context){
        mContext = context;
    }
    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject( View view,  Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Bitmap[] arr = new Bitmap[mImages.size()];
        mImages.toArray(arr); // fill the array
        //imageView.setImageResource(arr[position]);
        imageView.setImageBitmap(arr[position]);
        container.addView(imageView,0);
        return imageView;
        //return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position,  Object object) {
        container.removeView((ImageView) object);
    }
    public void addImage(Bitmap image){
        mImages.add(image);
        notifyDataSetChanged();
    }
}
