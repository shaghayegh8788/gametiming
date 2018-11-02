package ir.game_timing.pictureVideo;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.game_timing.PublicMethods;
import ir.game_timing.R;
import ir.game_timing.models.BaseModel;
import ir.game_timing.models.MyResponse;
import ir.game_timing.retrofit.GameTimingInterface;
import ir.game_timing.retrofit.GameTimingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {
    VideoView videoView;
    public static final String ARG_PAGE = "ARG_PAGE";
    MediaController mc;
    private Uri uri;
    private int mPage;
    ImageView media;
    List<BaseModel> lstVideo;
    ArrayAdapter adapter;


    public static VideoFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        videoView = view.findViewById(R.id.videoView);
        final ListView listView;
        /*    final MediaController mc = new MediaController(getContext());*/
        listView = view.findViewById(R.id.videoList);
        final ArrayList<String> videoTilte;
        lstVideo = new ArrayList<>();
        videoTilte = new ArrayList<>();
        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Call<MyResponse<List<BaseModel>>> call = gameTimingInterface.GetAllVideos();
        call.enqueue(new Callback<MyResponse<List<BaseModel>>>() {
            @Override
            public void onResponse(Call<MyResponse<List<BaseModel>>> call, Response<MyResponse<List<BaseModel>>> response) {
                if (response.code() == 200) {
                    Log.d("22222", "onResponse: " + response.code() + "");
                    if (response.body().getResultCode() == 1) {
                        Log.d("22222", "onResponse: " + response.body().getResultCode() + "");
                        for (BaseModel get : response.body().getResults()) {
                            BaseModel model = new BaseModel();
                            model.setId(get.getId());
                            model.setPath(get.getPath());
                            model.setTitle(get.getTitle());
                            Log.d("22222", "onResponse: " + get.getId());
                            Log.d("22222", "onResponse: " + get.getPath());
                            lstVideo.add(model);
                            String title = get.getTitle();
                            videoTilte.add(title);
                            Log.d("121111", "onResponse: "+videoTilte.size());
                            Log.d("121111", "onResponse: "+title);
                        }
                        Log.d("22222", "onResponse: " + lstVideo.size() + "");

                        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,videoTilte);
                        listView.setAdapter(adapter);

                       /* videoView.setVideoURI(Uri.parse(lstVideo.get(0).getPath()));
                        videoView.seekTo(400);*/


                    } else if (response.body().getResultCode() == -1) {
                        PublicMethods.showToast(response.body().getMessage());
                    } else {
                        try {
                            PublicMethods.showToast(response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse<List<BaseModel>>> call, Throwable t) {
                Log.d("basemodell", "onFailure: " + t.toString());
                PublicMethods.showToast(PublicMethods.noConnection);
            }
        });




        videoView.setMediaController(new MediaController(getActivity()));
        videoView.requestFocus();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PublicMethods.showToast("ویدئو در حال لود شدن است لطفا منتظر بمانید ...");
                if(lstVideo.size()>0)
                {
                    for(int i = 0;i<lstVideo.size();i++)
                    {
                        videoView.setVideoURI(Uri.parse(lstVideo.get(position).getPath()));
                    }
                }


                mc = new MediaController(getContext());
                videoView.setMediaController(mc);
                mc.setAnchorView(videoView);
                videoView.requestFocus();
                videoView.start();


            }
        });

        return view;
    }


    public void getBase() {

    }
}