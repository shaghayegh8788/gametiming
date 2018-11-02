package ir.game_timing.drawer.children;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ir.game_timing.BaseActivity;
import ir.game_timing.PublicMethods;
import ir.game_timing.R;
import ir.game_timing.models.ChildModel;
import ir.game_timing.models.MyResponse;
import ir.game_timing.recyclerViewChildren.RecyclerRegisterActivity;
import ir.game_timing.retrofit.GameTimingInterface;
import ir.game_timing.retrofit.GameTimingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditChildrenActivity extends BaseActivity implements EditChildrenAdapter.CallBackInterface {
    RecyclerView recycle_children;
    Button btn_add_child;
    List<ChildModel> ChildrenList;
    EditChildrenAdapter adapter;
    ProgressDialog progressDialog;
    ChildModel child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_children);
        getChildren();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("ارتباط با سرور");
        progressDialog.setMessage("لطفا منتظر بمانید");
        progressDialog.show();
        bind();

        btn_add_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditChildrenActivity.this, RecyclerRegisterActivity.class));
            }
        });
    }

    private void getChildren() {


        String PhoneNumber = Hawk.get("user_mobile", "09121234567");
        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Call<MyResponse<List<ChildModel>>> call = gameTimingInterface.GetAllChildren(PhoneNumber);
        call.enqueue(new Callback<MyResponse<List<ChildModel>>>() {
            @Override
            public void onResponse(Call<MyResponse<List<ChildModel>>> call, Response<MyResponse<List<ChildModel>>> response) {

                if (response.code() == 200) {
                    ChildrenList = new ArrayList<>();
                    if (response.body().getResultCode() == 1) {

                        for (int i = 0; i < response.body().getResults().size(); i++) {
                            ChildModel get = response.body().getResults().get(i);
                            ChildModel model = new ChildModel();
                            model.setFullName(get.getFullName());
                            model.setBirthDate(get.getBirthDate());
                            model.setGenderId(get.getGenderId());
                            model.setRelation(get.getRelation());
                            model.setParentId(get.getParentId());
                            model.setImagePath(get.getImagePath());
                            model.setId(get.getId());
                            model.setNationalCode(get.getNationalCode());
                            ChildrenList.add(model);
                            Log.d("ediiii", "onResponse: " + model.getFullName() + " " + model.getImagePath() + " " +
                                    model.getNationalCode() + " " + model.getGenderId() + " " + model.getParentId());
                            LinearLayoutManager li = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                            adapter = new EditChildrenAdapter(ChildrenList, mContext);
                            progressDialog.dismiss();
                            recycle_children.setLayoutManager(li);
                            recycle_children.setAdapter(adapter);

                        }


                    }
                    if (response.body().getResultCode() == -1) {
                        PublicMethods.showToast(response.body().getMessage());
                    }
                } else {
                    try {
                        PublicMethods.showToast(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse<List<ChildModel>>> call, Throwable t) {

                PublicMethods.showToast(PublicMethods.noConnection);
            }
        });
    }

    private void bind() {

        recycle_children = findViewById(R.id.recycle_children);
        btn_add_child = findViewById(R.id.btn_add_child);

    }


    @Override
    public void sendPosition(int position) {


        ChildModel model = ChildrenList.get(position);
        Hawk.put("childModel",model);
        Intent intent = new Intent(EditChildrenActivity.this, EditShowActivity.class);

        startActivity(intent);

    }

    ChildModel getchild(int child_id) {
        final ChildModel model = new ChildModel();
        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Call<MyResponse<ChildModel>> call = gameTimingInterface.GetChild(child_id);
        call.enqueue(new Callback<MyResponse<ChildModel>>() {
            @Override
            public void onResponse(Call<MyResponse<ChildModel>> call, Response<MyResponse<ChildModel>> response) {
                Log.d("getId", "onResponse: " + response.code() + "");
                if (response.code() == 200) {
                    if (response.body().getResultCode() == 1) {
                        Log.d("getId", "onResponse: ");

                        model.setFullName(response.body().getResults().getFullName());
                        model.setImagePath(response.body().getResults().getImagePath());
                        model.setBirthDate(response.body().getResults().getBirthDate());
                        model.setGenderId(response.body().getResults().getGenderId());
                        model.setRelation(response.body().getResults().getRelation());
                        model.setNationalCode(response.body().getResults().getNationalCode());
                        model.setParentId(response.body().getResults().getParentId());

                        Log.d("getIdd", "onResponse: " + model.getId() + " " + model.getImagePath() + " " + model.getNationalCode());

                    } else if (response.body().getResultCode() == 1) {
                        PublicMethods.showToast(response.body().getMessage());
                    }
                } else {
                    try {
                        PublicMethods.showToast(response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse<ChildModel>> call, Throwable t) {
                PublicMethods.showToast(PublicMethods.noConnection);
                Log.d("getId", "onFailure: " + t.toString());
            }
        });
        return model;
    }


    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
