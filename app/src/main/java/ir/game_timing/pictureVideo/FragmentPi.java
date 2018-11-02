package ir.game_timing.pictureVideo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class FragmentPi extends Fragment {
    View view;
    List<BaseModel> lstPic;
    RecyclerView recyclerView;

    public FragmentPi() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_pic, container, false);
        recyclerView = view.findViewById(R.id.recycle);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lstPic = new ArrayList<>();
        getBase();
    }

    public void getBase() {
        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Call<MyResponse<List<BaseModel>>> call = gameTimingInterface.GetAllPictures();
        call.enqueue(new Callback<MyResponse<List<BaseModel>>>() {
            @Override
            public void onResponse(Call<MyResponse<List<BaseModel>>> call, Response<MyResponse<List<BaseModel>>> response) {
                if (response.code() == 200) {
                    Log.d("12222", "onResponse: " + response.code() + "");
                    if (response.body().getResultCode() == 1) {
                        Log.d("12222", "onResponse: " + response.body().getResultCode() + "");
                        for (BaseModel get : response.body().getResults()) {
                            BaseModel model = new BaseModel();
                            model.setId(get.getId());
                            model.setPath(get.getPath());
                            model.setTitle(get.getTitle());
                            Log.d("12222", "onResponse: " + get.getId());
                            Log.d("12222", "onResponse: " + get.getPath());
                            lstPic.add(model);
                        }
                        Log.d("12222", "onResponse: " + lstPic.size() + "");

                        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), lstPic);
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                        recyclerView.setAdapter(adapter);
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
    }
}