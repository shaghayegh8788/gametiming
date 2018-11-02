package ir.game_timing.drawer.children;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.orhanobut.hawk.Hawk;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import ir.game_timing.BaseActivity;
import ir.game_timing.PublicMethods;
import ir.game_timing.R;
import ir.game_timing.customViews.MyEditText;
import ir.game_timing.models.ChildModel;
import ir.game_timing.models.MyResponse;
import ir.game_timing.retrofit.GameTimingInterface;
import ir.game_timing.retrofit.GameTimingService;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static okhttp3.MultipartBody.FORM;

public class EditShowActivity extends BaseActivity {

    private PersianDatePickerDialog picker;
    Button btn_calender, btn_edit_children, btn_edit_back;
    MyEditText register_first_name, register_last_name, register_national_code;
    ImageView img_upload_edit_;
    RadioGroup gender, Relation;
    TextView txt_date;
    RadioButton radio_mail, radio_femail, radio_un_relation, radio_relation;
    int child_id;
    ChildModel child;
    private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_edit_children);
        bind();

        child = Hawk.get("childModel");


        register_first_name.requestFocus();
        TAG = "123456";


        /*   String url = "game.makapps.ir/" + child.getImagePath();*/
        Log.d("sendddd2", "onCreate: " + child.getFullName() + " " + child.getBirthDate() + " " + child.getImagePath());
        /*        Glide.with(mContext).load(url).transform(new CircleTransform(EditShowActivity.this)).into(img_upload_edit_);*/

        Glide.with(getApplicationContext()).load(child.getImagePath()).transform(new CircleTransform(EditShowActivity.this)).into(img_upload_edit_);
        String[] names = child.getFullName().split(" ");
        register_first_name.setText(names[0]);
        register_last_name.setText(names[1]);
        register_national_code.setText(child.getNationalCode());
        int check = child.getGenderId();
        if (check == 1) {
            radio_femail.setChecked(true);
        } else radio_mail.setChecked(true);
        btn_calender.setText(child.getBirthDate());
        int relation = child.getRelation();
        if (relation == 1) {
            radio_un_relation.setChecked(true);
        } else radio_relation.setChecked(true);


        img_upload_edit_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 200);
                Glide.with(mContext).load(child.getLocal_Image_Uri()).transform(new CircleTransform(EditShowActivity.this)).into(img_upload_edit_);


            }
        });

        btn_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PersianCalendar initDate = new PersianCalendar();
                initDate.setPersianDate(1385, 1, 10);
                picker = new PersianDatePickerDialog(mContext)
                        .setPositiveButtonString("باشه")
                        .setNegativeButton("بیخیال")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        .setMinYear(1300)
                        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                        //.setInitDate(initDate)
                        .setActionTextColor(Color.GRAY)
                        .setListener(new Listener() {
                            @Override
                            public void onDateSelected(PersianCalendar persianCalendar) {
                                String birthDate = persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay();
                                String checkDateString = checkDate(birthDate);
                                child.setBirthDate(checkDateString);
                                Log.d("checkkkk", "positionCal: " + child.getBirthDate());
                                btn_calender.setText(child.getBirthDate());


                            }

                            @Override
                            public void onDismissed() {
                            }
                        });
                picker.show();

            }
        });

        btn_edit_children.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PublicMethods.showToast("اطلاعات در حال ارسال است لطفا منتظر بمانید ...");
                try {
                    byte[] bytes = new byte[0];
                    String phoneNumber = PublicMethods.getValue("user_mobile", "09121234567");//todo change number
                    Log.d("sdsdsds", "phoneNumber: " + phoneNumber);
                    if (child.getLocal_Image_Uri() != null) {
                        child.setImagePath(child.getLocal_Image_Uri().getPath());
                    }

                    child.setFullName(register_first_name.text() + " " + register_last_name.text());
                    child.setNationalCode(register_national_code.text());

                    if (!phoneNumber.equals("")) {
                        MultipartBody.Part filePart = null;
                        Uri image_Uri = child.getLocal_Image_Uri();
                        Log.d("sdsdsds", "getLocal_Image_Uri_AfterCheck: " + child.getLocal_Image_Uri());
                        if (image_Uri != null && !image_Uri.equals("")) {
                            InputStream inputStream = getContentResolver().openInputStream(image_Uri);
                            byte[] bytess = getBytes(inputStream);
                            RequestBody requestFile = RequestBody.create(MediaType.parse("imag/*"), bytess);
                            String extension = getFileExtension(child.getLocal_Image_Uri());
                            filePart = MultipartBody.Part.createFormData("fileName", "myImage." + extension, requestFile);
                        }

                        registerChildByWebService(filePart, child.getFullName(),
                                phoneNumber, child.getBirthDate(), child.getGenderId(), child.getRelation(), child.getNationalCode(), child.getId());

                    }


                } catch (
                        Exception e)

                {
                    e.printStackTrace();
                }

            }
        });

        btn_edit_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditShowActivity.this, EditChildrenActivity.class));
            }
        });

        Relation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int type;
                if (checkedId == R.id.radio_relation) {
                    type = 0;
                } else {

                    type = 1;
                }

                child.setRelation(type);

            }
        });


        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int type;
                if (checkedId == R.id.radio_mail) {
                    type = 0;
                } else {

                    type = 1;
                }
                child.setGenderId(type);

            }
        });


    }

    public ChildModel getChild(int child_id) {
        Log.d("getId", "getChild: " + child_id + "");
        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Call<MyResponse<ChildModel>> call = gameTimingInterface.GetChild(child_id);
        call.enqueue(new Callback<MyResponse<ChildModel>>() {
            @Override
            public void onResponse(Call<MyResponse<ChildModel>> call, Response<MyResponse<ChildModel>> response) {
                Log.d("getId", "onResponse: " + response.code() + "");
                if (response.code() == 200) {
                    if (response.body().getResultCode() == 1) {
                        Log.d("getId", "onResponse: ");
                        child = new ChildModel();
                        child.setFullName(response.body().getResults().getFullName());
                        child.setImagePath(response.body().getResults().getImagePath());
                        child.setBirthDate(response.body().getResults().getBirthDate());
                        child.setGenderId(response.body().getResults().getGenderId());
                        child.setRelation(response.body().getResults().getRelation());
                        child.setNationalCode(response.body().getResults().getNationalCode());
                        child.setParentId(response.body().getResults().getParentId());

                        Log.d("getId", "onResponse: " + child.getId() + " " + child.getImagePath() + " " + child.getNationalCode());

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
        return child;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: ");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            child.setLocal_Image_Uri(data.getData());

            Log.d("sdsdsds", "onActivityResult: getData 200" + data.getData());
            Log.d("sdsdsds", "onActivityResult: getData 200" + child.getLocal_Image_Uri());
            Glide.with(mContext).load(child.getLocal_Image_Uri()).transform(new CircleTransform(EditShowActivity.this)).into(img_upload_edit_);
        }
    }

    private String checkDate(String birthDate) {
        String date;
        Log.d("datee", "checkDate: " + birthDate);
        String[] items = birthDate.split("/");

        String year = items[0];
        Log.d("datee", "checkDate: " + year);
        String month = items[1];
        Log.d("datee", "checkDate: " + month);
        String day = items[2];
        Log.d("datee", "checkDate: " + day);
        if (day.length() == 1) {
            String newDay = "0" + day;
            items[2] = newDay;

        }
        if (month.length() == 1) {
            String newMonth = "0" + month;
            items[1] = newMonth;
            Log.d("datee", "newMonth: " + newMonth);
            Log.d("datee", "month: " + month);

        }
        date = items[0] + "/" + items[1] + "/" + items[2];

        Log.d("datee", "checkDate: " + date);
        return date;
    }

    private void registerChildByWebService(MultipartBody.Part filePart, String fullName, String phoneNumber, String birthDate, int Gender, int Relation, String nationalCode, int Id) {
        Log.d("sdsdsd", "registerChildByWebService: fullName" + fullName);
        Log.d("sdsdsd", "registerChildByWebService: phoneNumber" + phoneNumber);
        Log.d("sdsdsd", "registerChildByWebService: birthDate" + birthDate);
        Log.d("sdsdsd", "registerChildByWebService: Gender" + Gender);
        Log.d("sdsdsd", "registerChildByWebService: nationalCode" + nationalCode);
        Log.d("sdsdsd", "registerChildByWebService: Relation" + Relation);
        Log.d("sdsdsd", "registerChildByWebService: extension" + Id);


        RequestBody fullNameBody = RequestBody.create(FORM, fullName);
        RequestBody phoneNumberBody = RequestBody.create(FORM, phoneNumber);
        RequestBody NationalCodeBody = RequestBody.create(FORM, nationalCode);
        RequestBody birthDateBody = RequestBody.create(FORM, birthDate);
        RequestBody GenderBody = RequestBody.create(FORM, String.valueOf(Gender));
        RequestBody RelationBody = RequestBody.create(FORM, String.valueOf(Relation));
        RequestBody IdBody = RequestBody.create(FORM, String.valueOf(Id));


        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
        Log.d("cooo", "nationalCode: " + NationalCodeBody);
        Log.d("cooo", "RelationBody: " + RelationBody);
        Log.d("cooo", "fullNameBody: " + fullNameBody);

        Call<MyResponse<ChildModel>> call = gameTimingInterface.UpdateChild(filePart, IdBody, fullNameBody, NationalCodeBody, GenderBody, phoneNumberBody, birthDateBody, RelationBody);
        call.enqueue(new Callback<MyResponse<ChildModel>>() {
            @Override
            public void onResponse(Call<MyResponse<ChildModel>> call, Response<MyResponse<ChildModel>> response) {
                Log.d("cooo", "onResponse: " + response.code() + "");

                if (response.code() == 200) {
                    Log.d("cooo", "onResponse: " + response.body().getResultCode());
                    if (response.body().getResultCode() == -1)
                        PublicMethods.showToast(response.body().getMessage());

                    else {
                        Log.d(TAG, "onResponse: 200" + response.body().getResults().getImagePath());
                        Log.d(TAG, "onResponse: 200" + response.body().getResults().getFullName());
                        Log.d(TAG, "onResponse: 200" + response.body().getResults().getBirthDate());
                        Log.d(TAG, "onResponse: 200" + response.body().getResults().getNationalCode());
                        Log.d(TAG, "onResponse: 200" + response.body().getResults().getRelation());
                        Log.d(TAG, "onResponse: 200" + response.body().getResults().getGenderId());
                        PublicMethods.showToast("تغییرات" + " " + response.body().getResults().getFullName() + " " + "با موفقیت در سیستم ثبت شد");
                        startActivity(new Intent(EditShowActivity.this, EditChildrenActivity.class));

                    }
                } else if (response.code() == 303) {

                    if (response.errorBody() != null) {
                        try {
                            String message = response.errorBody().string();
                            Log.d(TAG, "onResponse: errorBody" + message);
                            PublicMethods.showToast(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else
                    PublicMethods.showToast("در برنامه خطایی رخ داده است!");
            }

            @Override
            public void onFailure(Call<MyResponse<ChildModel>> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.toString());
                if (t instanceof IOException) {
                    PublicMethods.showToast("Connection Problem!" + t.toString());
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

    private void bind() {

        register_first_name = findViewById(R.id.register_first_name);
        register_last_name = findViewById(R.id.register_last_name);
        register_national_code = findViewById(R.id.register_national_code);
        btn_calender = findViewById(R.id.btn_calender);
        gender = findViewById(R.id.gender);
        Relation = findViewById(R.id.relation);
        radio_mail = findViewById(R.id.radio_mail);
        radio_femail = findViewById(R.id.radio_femail);
        radio_un_relation = findViewById(R.id.radio_un_relation);
        radio_relation = findViewById(R.id.radio_relation);
        txt_date = findViewById(R.id.txt_date);
        btn_edit_children = findViewById(R.id.btn_edit_children);
        btn_edit_back = findViewById(R.id.btn_edit_back);
        img_upload_edit_ = findViewById(R.id.img_upload_edit_);
    }

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(EditShowActivity customClass) {
            super(customClass);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        public static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }

    }
}
