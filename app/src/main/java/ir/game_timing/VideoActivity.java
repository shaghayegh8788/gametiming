package ir.game_timing;

import android.app.Dialog;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.Toolbar;
import android.widget.VideoView;

import static ir.game_timing.R.id.video1;

public class VideoActivity extends BaseActivity implements View.OnClickListener {
    VideoView video1, video2, video3, video4, video5, video6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        bind();
        video1.setOnClickListener(this);

        video1.setMediaController(new MediaController(this));
        video1.setVideoURI(Uri.parse("https://hw19.cdn.asset.aparat.com/aparat-video/8b3dd8cbc7490cf652e1ffa0f944a14311309069-240p__60187.mp4"));
        video1.requestFocus();

        video1.pause();


        video2.setMediaController(new MediaController(this));
        video2.setVideoURI(Uri.parse("https://hw19.cdn.asset.aparat.com/aparat-video/f7a3c69108e1b5486b5776ef5d809d2911516515-240p__51541.mp4"));
        video2.requestFocus();

        video2.pause();
        video2.pause();

        video3.setMediaController(new MediaController(this));
        video3.setVideoURI(Uri.parse("https://hw17.cdn.asset.aparat.com/aparat-video/c4d7502ac86e04ef347133f8e30e113311387192-240p__27439.mp4"));
        video3.requestFocus();

        video3.pause();


        video4.setMediaController(new MediaController(this));
        video4.setVideoURI(Uri.parse("https://hw19.cdn.asset.aparat.com/aparat-video/8b3dd8cbc7490cf652e1ffa0f944a14311309069-240p__60187.mp4"));
        video4.requestFocus();

        video4.pause();

        video5.setMediaController(new MediaController(this));
        video5.setVideoURI(Uri.parse("https://hw19.cdn.asset.aparat.com/aparat-video/f7a3c69108e1b5486b5776ef5d809d2911516515-240p__51541.mp4"));
        video5.requestFocus();

        video5.pause();


        video6.setMediaController(new MediaController(this));
        video6.setVideoURI(Uri.parse("https://hw17.cdn.asset.aparat.com/aparat-video/c4d7502ac86e04ef347133f8e30e113311387192-240p__27439.mp4"));
        video6.requestFocus();

        video6.pause();

    }

    private void bind() {
        video1 = findViewById(R.id.video1);
        video2 = findViewById(R.id.video2);
        video3 = findViewById(R.id.video3);
        video4 = findViewById(R.id.video4);
        video5 = findViewById(R.id.video5);
        video6 = findViewById(R.id.video6);
    }


    public void onPrepared(MediaPlayer mp) {
        video1.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onClick(View view) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_video);
        dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        lp.copyFrom(dialog.getWindow().getAttributes());
        dialog.getWindow().setAttributes(lp);
        final VideoView videoview = (VideoView) dialog.findViewById(R.id.video1);
/*        Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.introvideo);*/
        video1.setVideoURI(Uri.parse("https://hw17.cdn.asset.aparat.com/aparat-video/c4d7502ac86e04ef347133f8e30e113311387192-240p__27439.mp4"));
        video1.start();
    }
}
