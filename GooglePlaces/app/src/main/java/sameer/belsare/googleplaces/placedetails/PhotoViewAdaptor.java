package sameer.belsare.googleplaces.placedetails;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import sameer.belsare.googleplaces.R;

/**
 * Created by sameer.belsare on 15/4/17.
 */

public class PhotoViewAdaptor extends RecyclerView.Adapter<PhotoViewAdaptor.PhotoListHolder>{

    private PlaceDetailsFragment mContext;
    private PlaceDetailsPresenter mPresenter;
    private ArrayList<String> mPhotoList;

    public PhotoViewAdaptor(PlaceDetailsFragment context, PlaceDetailsPresenter presenter, ArrayList<String> photoList){
        mContext = context;
        mPresenter = presenter;
        mPhotoList = photoList;
    }

    @Override
    public PhotoListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_item, parent,
                false);
        return new PhotoListHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoListHolder holder, final int position) {
        Glide.with(mContext).load(mPhotoList.get(holder.getAdapterPosition())).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.handleItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }

    public static class PhotoListHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;

        public PhotoListHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.ivPhoto);
        }

    }
}
