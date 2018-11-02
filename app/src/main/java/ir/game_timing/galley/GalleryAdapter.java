package ir.game_timing.galley;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;


public class GalleryAdapter extends BaseAdapter {
    private final Context context;
    List<GalleryModel> galleryList;

    public GalleryAdapter(Context context, List<GalleryModel> galleryList) {
        this.context = context;
        this.galleryList = galleryList;
    }

    @Override
    public int getCount() {
       return galleryList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView picturesView;
        if (convertView == null) {
            picturesView = new ImageView(context);
            Bitmap bitmap;
            if(galleryList.get(position).getUrl("").contains(".jpg"))
            {
                bitmap = BitmapFactory.decodeFile(galleryList.get(position).getName("tt")); //Creation of Thumbnail of image
            }
            else if(galleryList.get(position).getUrl("").contains(".mp4"))
            {
                bitmap = ThumbnailUtils.createVideoThumbnail(galleryList.get(position).getName("tt"), 0); //Creation of Thumbnail of video
            }
            picturesView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            picturesView.setPadding(8, 8, 8, 8);
            picturesView.setLayoutParams(new GridView.LayoutParams(100, 100));
        }
        else
        {
            picturesView = (ImageView)convertView;
        }
        return picturesView;
    }
}


