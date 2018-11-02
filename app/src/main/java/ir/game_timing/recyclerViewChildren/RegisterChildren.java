package ir.game_timing.recyclerViewChildren;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.util.ArrayList;
import java.util.List;

import ir.game_timing.BaseActivity;
import ir.game_timing.PublicMethods;
import ir.game_timing.R;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterChildren extends BaseActivity {

    EditText count_child, register_full_name_child, register_national_code;
    RecyclerView recycle_children;
    Button btn_calender, btn_register_children;
    List<ChildrenModel> registerList;
    ChildrenModel model;
    int count_children;
    String path = "";
    String count = "";
    public View customView;
    public ImageView upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_children);
        bind();
        count_child.getText().toString();
        checkCountOfChildren();
        dialog dia = new dialog(RegisterChildren.this);
        dia.show();

        count_child.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("txtt", "afterTextChanged: " + count_child.getText().toString());
                Integer number = Integer.parseInt(count_child.getText().toString());
                registerList.clear();
                if ((number <= 5) && (number >= 1)) {
                    checkCountOfChildren();
                } else PublicMethods.showToast("تعداد فرزندان نباید بیشتر از 5 نفر باشد.");

            }
        });

    }

    private void checkCountOfChildren() {
        registerList = new ArrayList<>();
        if (count_child.getText().toString().length() == 1) {
            count_children = Integer.parseInt(count_child.getText().toString());
            registerList = new ArrayList<>();
            if (count_children > 0 && count_children <= 5) {
                Log.d("testt", "if" + count_children);
                for (int i = 1; i <= count_children; i++) {

                    model = new ChildrenModel();
                    model.setChild_fullName("");
                    model.setChild_date_born("");
                    model.setChild_gender("");
                    model.setChild_national_code("");
                    model.setChild_image("");
                    registerList.add(model);
                    Log.d("testt", "onCreate: " + registerList.size());
                }

            }
            childrenAdapter adapter = new childrenAdapter(registerList, mContext);
            LinearLayoutManager li = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            Log.d("afterTextChanged", "LinearLayoutManager" + registerList.size());
            recycle_children.setAdapter(adapter);
            recycle_children.setLayoutManager(li);
        }
    }

    public class childrenAdapter extends RecyclerView.Adapter<childrenAdapter.ViewHolder> {
        List<ChildrenModel> childrenList;
        Context mContext;

        public childrenAdapter(List<ChildrenModel> childrenList, Context mContext) {
            this.childrenList = childrenList;
            this.mContext = mContext;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            List<ChildrenModel> click_children;
            private PersianDatePickerDialog picker;
            Button btn_calender, btn_register_children;
            EditText register_full_name, register_national_code;
            ImageView register_Image_child, btn_img_upload, img_upload;
            String dateOfBirthChild;
            RadioGroup gender;
            View dote1, dote2;
            String selectedType;
            TextView txt_date;
            RadioButton radio_mail, radio_femail;


            public ViewHolder(final View itemView) {
                super(itemView);

                customView = itemView;
                click_children = new ArrayList<>();
                register_full_name = itemView.findViewById(R.id.register_full_name);
                register_national_code = itemView.findViewById(R.id.register_national_code);
                btn_calender = itemView.findViewById(R.id.btn_calender);
                gender = itemView.findViewById(R.id.gender);
                radio_mail = itemView.findViewById(R.id.radio_mail);
                radio_femail = itemView.findViewById(R.id.radio_femail);
                txt_date = itemView.findViewById(R.id.txt_date);
                dote1 = itemView.findViewById(R.id.dote1);
                dote2 = itemView.findViewById(R.id.dote2);
                upload = itemView.findViewById(R.id.img_upload);
                register_Image_child = itemView.findViewById(R.id.img_upload);
                btn_img_upload = itemView.findViewById(R.id.btn_img_upload);
                btn_register_children = itemView.findViewById(R.id.btn_register_children);
                img_upload = itemView.findViewById(R.id.img_upload);

                img_upload.setOnClickListener(this);
             /*   int position = (int) v.getTag();
                getAdapterPosition()*/


                gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (checkedId == R.id.radio_mail) {
                            selectedType = radio_mail.getText().toString();
                        } else {
                            selectedType = radio_femail.getText().toString();
                        }

                    }
                });


                btn_register_children.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        String fullOname, code;
                        fullOname = register_full_name.getText().toString();
                        code = register_national_code.getText().toString();
                        PublicMethods.showToast(register_full_name.getText().toString());
                        ChildrenModel childrenModel = new ChildrenModel();
                        childrenModel.setChild_fullName(register_full_name.getText().toString());
                        childrenModel.setChild_date_born(dateOfBirthChild);
                        childrenModel.setChild_national_code(register_national_code.getText().toString());
                        childrenModel.setChild_gender(selectedType);
                        Log.d("regii", "onClick: " + fullOname + " " + dateOfBirthChild + " " + code + " " + selectedType);
                        click_children.add(childrenModel);


                        register_full_name.setVisibility(View.GONE);
                        register_national_code.setVisibility(View.GONE);
                        btn_calender.setVisibility(View.GONE);
                        gender.setVisibility(View.GONE);
                        register_Image_child.setVisibility(View.GONE);
                        dote1.setVisibility(View.GONE);
                        dote2.setVisibility(View.GONE);
                        btn_img_upload.setVisibility(View.GONE);
                        txt_date.setVisibility(View.GONE);
                        btn_register_children.setVisibility(View.GONE);

                        for (ChildrenModel child : click_children) {

                            Log.d("listt", "born: " + child.getChild_date_born());
                            Log.d("listt", "fullname: " + child.getChild_fullName());
                            Log.d("listt", "code: " + child.getChild_national_code());
                            Log.d("listt", "gender: " + selectedType);

                        }

                        Log.d("listt", "list: " + registerList.size());
                        Log.d("listt", "list: " + click_children.size());

                    }

                });

                btn_calender.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PersianCalendar initDate = new PersianCalendar();
                        initDate.setPersianDate(1370, 3, 13);

                        picker = new PersianDatePickerDialog(mContext)
                                .setPositiveButtonString("باشه")
                                .setNegativeButton("بیخیال")
                                .setTodayButton("امروز")
                                .setTodayButtonVisible(true)
                                .setMinYear(1300)
                                .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                                .setInitDate(initDate)
                                .setActionTextColor(Color.GRAY)
                                .setListener(new Listener() {
                                    @Override
                                    public void onDateSelected(PersianCalendar persianCalendar) {
                                        dateOfBirthChild = persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay();
                                        PublicMethods.showToast(dateOfBirthChild);
                                    }

                                    @Override
                                    public void onDismissed() {

                                    }
                                });

                        picker.show();

                    }
                });

            }

            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                selectImage();
                Log.d("posii", "onClick: " + position + "");

            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View row = LayoutInflater.from(mContext).inflate(R.layout.item_register_children, parent, false);
            ViewHolder holder = new ViewHolder(row);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            int i = position + 1;
            String number = getPosition(i);
            Log.d("ccc", "onBindViewHolder: " + count);
            holder.register_full_name.setText(registerList.get(position).getChild_fullName());
            holder.register_national_code.setText(registerList.get(position).getChild_national_code());
            holder.btn_register_children.setText("ثبت اطلاعات فرزند " + number);

        }

        @Override
        public int getItemCount() {
            return registerList.size();
        }


        public String getPosition(int position) {
            String stringPosition = "";
            int number = position;
            if (number <= 5 && number >= 1) {
                switch (number) {

                    case 1:
                        stringPosition = "اول";
                        break;

                    case 2:
                        stringPosition = "دوم";
                        break;
                    case 3:
                        stringPosition = "سوم";
                        break;

                    case 4:
                        stringPosition = "چهارم";
                        break;

                    case 5:
                        stringPosition = "پنجم";
                        break;
                }

            } else {
                PublicMethods.showToast("تعداد فرزندان نباید بیشتر از پنج نفر باشد");

            }

            return stringPosition;
        }

        public void selectImage() {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            ((RegisterChildren) mContext).startActivityForResult(intent, 100);

        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("filee", "onActivityResult: " + requestCode);
        if (requestCode == 100 && resultCode == RESULT_OK && null != data) {

            Glide.with(mContext).load(data.getData()).transform(new CircleTransform(RegisterChildren.this)).into(upload);
   /*         @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                img_upload.setImageDrawable(circularBitmapDrawable);
            }*/

            Log.d("filee", "afterGlide: " + data.getData());
            Uri selectedImage = data.getData();
            String[]
                    filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            path = picturePath;
            Log.d("filee", "onActivityResult: " + picturePath);

        }
    }


    public void bind() {
        count_child = findViewById(R.id.count_child);
        register_full_name_child = findViewById(R.id.register_full_name);
        register_national_code = findViewById(R.id.register_national_code);
        /*  img_upload = findViewById(R.id.img_upload);*/
        btn_calender = findViewById(R.id.btn_calender);
        btn_register_children = findViewById(R.id.btn_register_children);
        recycle_children = findViewById(R.id.recycle_children);

    }


    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(RegisterChildren customClass) {
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

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
