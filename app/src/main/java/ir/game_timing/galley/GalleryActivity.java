package ir.game_timing.galley;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import ir.game_timing.BaseActivity;
import ir.game_timing.R;


public class GalleryActivity extends BaseActivity {
    GridView grid_gallery;
    List<GalleryModel> galleryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        GalleryModel galleryModel = new GalleryModel(GalleryActivity.this);
        galleryList = new ArrayList<>();
        bind();
        GalleryModel g1 = new GalleryModel(this);
        g1.getUrl("https://hw17.cdn.asset.aparat.com/aparat-video/c62d5f53437c4177f811ae2d7e033dfc11445289-144p__12323.mp4");
        g1.getId(1);
        g1.getName("tt");

        GalleryModel g2 = new GalleryModel(this);
        g2.getUrl("https://hw17.cdn.asset.aparat.com/aparat-video/c62d5f53437c4177f811ae2d7e033dfc11445289-144p__12323.mp4");
        g2.getId(1);
        g2.getName("nn");

        galleryList.add(g1);
        galleryList.add(g2);

        grid_gallery.setAdapter(new GalleryAdapter(mContext,galleryList));

    }

    private void bind() {
        grid_gallery = findViewById(R.id.gird_gallery);


    }
}
