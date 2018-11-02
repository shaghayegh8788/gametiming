package ir.game_timing;

import android.util.Log;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.game_timing.models.BaseModel;
import ir.game_timing.models.MyResponse;
import ir.game_timing.retrofit.GameTimingInterface;
import ir.game_timing.retrofit.GameTimingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicMethods {

    public static void setValue(String key, String value) {
        Hawk.put(key, value);
    }

    public static String getValue(String key, String defValue) {
        return Hawk.get(key, defValue);
    }

    public static void showToast(String msg) {
        Toast.makeText(MyApplication.applicationObject, msg, Toast.LENGTH_LONG).show();
    }

    public static String noConnection = "خطا در برقراری ارتباط با سرور";

    public static List<BaseModel> getBase() {
        final List<BaseModel> baseModelList = new ArrayList<>();
        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Call<MyResponse<List<BaseModel>>> call = gameTimingInterface.GetListSchool();
        call.enqueue(new Callback<MyResponse<List<BaseModel>>>() {
            @Override
            public void onResponse(Call<MyResponse<List<BaseModel>>> call, Response<MyResponse<List<BaseModel>>> response) {
                if (response.code() == 200) {
                    Log.d("basemodell", "onResponse: "+response.code()+"");
                    if (response.body().getResultCode() == 1) {
                        Log.d("basemodell", "onResponse: "+response.body().getResultCode()+"");
                        for(BaseModel get : response.body().getResults())
                        {
                            BaseModel model = new BaseModel();
                            model.setId(get.getId());
                            model.setPath(get.getPath());
                            Log.d("basemodell", "onResponse: "+get.getId());
                            Log.d("basemodell", "onResponse: "+get.getPath());
                        }
                    }
                    if (response.body().getResultCode() == -1) {
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
                PublicMethods.showToast(PublicMethods.noConnection);
            }
        });
        Log.d("basemodell", "onResponse: "+baseModelList.size());
        return baseModelList;
    }
}
