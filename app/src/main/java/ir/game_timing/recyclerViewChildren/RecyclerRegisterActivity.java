package ir.game_timing.recyclerViewChildren;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ir.game_timing.BaseActivity;
import ir.game_timing.LoginActivity;
import ir.game_timing.MainActivity;
import ir.game_timing.PublicMethods;
import ir.game_timing.R;
import ir.game_timing.customViews.MyEditText;
import ir.game_timing.models.MyResponse;
import ir.game_timing.retrofit.GameTimingInterface;
import ir.game_timing.retrofit.GameTimingService;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RecyclerRegisterActivity extends BaseActivity implements RecyclerRegisterAdapter.CallBackInterface {
    MyEditText count_child, register_full_name_child, register_national_code;
    ImageView img_upload;
    RecyclerView recycle_children;
    Button btn_calender, btn_register_children;
    List<ChildrenModel> registerList;
    private RecyclerRegisterAdapter myAdapter;
    int SelectedPositionForFile;
    String TAG = "fileeee";


    @Override
    public void selectFile(int position) {
        SelectedPositionForFile = position;
        count_child.requestFocus();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 200);
    }

    @Override
    public void registerChild(int position) {
        try {
            String phoneNumber = PublicMethods.getValue("user_mobile", "09122819699");//todo change number
            if (!phoneNumber.equals("")) {
                ChildrenModel model = registerList.get(position);
                MultipartBody.Part filePart = null;
                Uri image_Uri = model.getLocal_Image_Uri();

                if (image_Uri != null && !image_Uri.equals("")) {
                    InputStream inputStream = getContentResolver().openInputStream(image_Uri);
                    byte[] bytes = getBytes(inputStream);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("imag/*"), bytes);
                    String extension = getFileExtension(image_Uri);
                    filePart = MultipartBody.Part.createFormData("fileName", "myImage." + extension, requestFile);
                }
                //todo correct phone number
                registerChildByWebService(filePart, model.getChild_fullName(),
                        phoneNumber, model.getChild_date_born(), model.getChild_gender(), model.getChild_relation(), model.getChild_national_code(), position);
            } else {
                PublicMethods.showToast("اطلاعات ثبت نامی شما موجود نیست لطفا دوباره لاگین یا ثبت نام نمایید");
                startActivity(new Intent(RecyclerRegisterActivity.this, LoginActivity.class));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: ");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            ChildrenModel item = registerList.get(SelectedPositionForFile);
            item.setLocal_Image_Uri(data.getData());
            registerList.set(SelectedPositionForFile, item);
            myAdapter.notifyItemChanged(SelectedPositionForFile);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_children);
        dialog dia = new dialog(RecyclerRegisterActivity.this);
        dia.show();
        setResult(RESULT_OK);
        bind();
        registerList = new ArrayList<>();
        updateRegisterList(1);
        myAdapter = new RecyclerRegisterAdapter(mContext, registerList);
        LinearLayoutManager li = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recycle_children.setAdapter(myAdapter);
        recycle_children.setLayoutManager(li);
    }

    private void updateRegisterList(int count) {
        registerList.clear();
        for (int i = 1; i <= count; i++) {
            ChildrenModel model = new ChildrenModel();
            model.setChild_fullName("");
            model.setChild_firstName("");
            model.setChild_lastName("");
            model.setChild_date_born("انتخاب از تقویم");
            model.setChild_gender("");
            model.setChild_relation("");
            model.setChild_national_code("");
            model.setChild_image("");
            registerList.add(model);

        }
    }

    private void removeItemFromRecycler(int position) {
        registerList.remove(position);
        myAdapter.notifyItemRemoved(position);
        if (registerList.size() == 0) {
            PublicMethods.showToast("شما میتوانید مشخصات فرزندان خود را از منوی سمت چپ مشاهده یا ویرایش نمایید .");
            Intent intent = new Intent(RecyclerRegisterActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void bind() {
        count_child = findViewById(R.id.count_child);
        register_full_name_child = findViewById(R.id.register_full_name);
        register_national_code = findViewById(R.id.register_national_code);
        img_upload = findViewById(R.id.img_upload);
        btn_calender = findViewById(R.id.btn_calender);
        btn_register_children = findViewById(R.id.btn_register_children);
        recycle_children = findViewById(R.id.recycle_children);

        count_child.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String count = count_child.text();
                if (count.equals("")) {
                    count = "0";

                }
                int countInt = Integer.parseInt(count);
                if (countInt > 0 && countInt <= 5) {
                    updateRegisterList(countInt);
                    myAdapter.notifyDataSetChanged();
                } else {
                    registerList.clear();

                    PublicMethods.showToast("تعداد فرزندان قابل قبول تا 5 نفر می باشد");
                }
            }
        });

    }


    private String getFileExtension(Uri uri) {
        ContentResolver cr = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));

    }

    private byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();

    }


    // private void registerChildByWebService(String extension, byte[] bytes, String fullName, String phoneNumber, String birthDate, String Gender,String Relation,String nationalCode ,final int position)
    private void registerChildByWebService(MultipartBody.Part filePart, String fullName, String phoneNumber, String birthDate, String Gender, String Relation, String nationalCode, final int position) {
        RequestBody fullNameBody = RequestBody.create(okhttp3.MultipartBody.FORM, fullName);
        RequestBody phoneNumberBody = RequestBody.create(okhttp3.MultipartBody.FORM, phoneNumber);
        RequestBody NationalCodeBody = RequestBody.create(okhttp3.MultipartBody.FORM, nationalCode);
        RequestBody birthDateBody = RequestBody.create(okhttp3.MultipartBody.FORM, birthDate);
        RequestBody GenderBody = RequestBody.create(okhttp3.MultipartBody.FORM, Gender);
        RequestBody RelationBody = RequestBody.create(okhttp3.MultipartBody.FORM, Relation);


        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Log.d(TAG, "registerChildByWebService: fullName" + fullName);
        Call<MyResponse<ChildrenModel>> call = gameTimingInterface.RegisterChild(filePart, fullNameBody, NationalCodeBody, GenderBody, phoneNumberBody, birthDateBody, RelationBody);
        call.enqueue(new Callback<MyResponse<ChildrenModel>>() {
            @Override
            public void onResponse(Call<MyResponse<ChildrenModel>> call, Response<MyResponse<ChildrenModel>> response) {
                Log.d(TAG, "onResponse: code " + response.code());
                //Log.d(TAG, "onResponse: errorBody "+response.errorBody().toString());
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse: " + response.body().getResultCode());
                    if (response.body().getResultCode() == -1)
                        PublicMethods.showToast(response.body().getMessage());
                    else {
                        Log.d(TAG, "onResponse: 200" + response.body().getResults().getChild_image());
                        Log.d(TAG, "onResponse: 200" + response.body().getResults().getChild_fullName());
                        Log.d(TAG, "onResponse: 200" + response.body().getResults().getChild_date_born());
                        Log.d(TAG, "onResponse: 200" + response.body().getResults().getChild_national_code());
                        Log.d(TAG, "onResponse: 200" + response.body().getResults().getChild_relation());
                        Log.d(TAG, "onResponse: 200" + response.body().getResults().getChild_gender());
                        PublicMethods.showToast("اطلاعات" + " " + response.body().getResults().getChild_fullName() + " " + "با موفقیت در سیستم ثبت شد");
                        removeItemFromRecycler(position);
                    }
                } else if (response.code() == 303) {

                    if (response.errorBody() != null) {
                        try {
                            String meesage = response.errorBody().string();
                            Log.d(TAG, "onResponse: errorBody" + meesage);
                            PublicMethods.showToast(meesage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else
                    PublicMethods.showToast("در برنامه خطایی رخ داده است!");
            }

            @Override
            public void onFailure(Call<MyResponse<ChildrenModel>> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.toString());
                if (t instanceof IOException) {
                    PublicMethods.showToast("Connection Problem!" + t.toString());
                }
            }
        });
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
