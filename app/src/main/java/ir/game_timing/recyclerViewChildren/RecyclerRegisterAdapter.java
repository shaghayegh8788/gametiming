package ir.game_timing.recyclerViewChildren;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
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

import java.util.List;

import ir.game_timing.PublicMethods;
import ir.game_timing.R;
import ir.game_timing.customViews.MyEditText;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

public class RecyclerRegisterAdapter extends RecyclerView.Adapter<RecyclerRegisterAdapter.ViewHolder> {
    Context mContext;
    List<ChildrenModel> registerList;
    Activity pActivity;
    private static int RESULT_LOAD_IMAGE = 1;
    View iteView;
    private CallBackInterface mCallBack;
    String TAG = "filee";


    public interface CallBackInterface {
        void selectFile(int position);

        void registerChild(int position);
    }

    public RecyclerRegisterAdapter(Context mContext, List<ChildrenModel> registerList) {
        this.mContext = mContext;
        this.registerList = registerList;
        mCallBack = (CallBackInterface) mContext;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private PersianDatePickerDialog picker;
        Button btn_calender, btn_register_children;
        MyEditText register_first_name,getRegister_last_name, register_national_code;
        ImageView img_upload;
        RadioGroup gender,relation;
        TextView txt_date;
        RadioButton radio_mail, radio_femail,radio_un_relation,radio_relation;

        public ViewHolder(View itemView) {
            super(itemView);
            register_first_name = itemView.findViewById(R.id.register_first_name);
            getRegister_last_name = itemView.findViewById(R.id.register_last_name);
            register_national_code = itemView.findViewById(R.id.register_national_code);
            btn_calender = itemView.findViewById(R.id.btn_calender);
            gender = itemView.findViewById(R.id.gender);
            relation = itemView.findViewById(R.id.relation);
            radio_mail = itemView.findViewById(R.id.radio_mail);
            radio_femail = itemView.findViewById(R.id.radio_femail);
            radio_un_relation = itemView.findViewById(R.id.radio_un_relation);
            radio_relation = itemView.findViewById(R.id.radio_relation);
            txt_date = itemView.findViewById(R.id.txt_date);

            btn_register_children = itemView.findViewById(R.id.btn_register_children);
            img_upload = itemView.findViewById(R.id.img_upload);

            img_upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "ViewHolder: getAdapterPosition " + getAdapterPosition());
                    mCallBack.selectFile(getAdapterPosition());
                }
            });

            register_national_code.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    //mCallBack. setNationalCode(getAdapterPosition() , s.toString());
                    int position = getAdapterPosition();
                    if (registerList.get(position).getChild_national_code() != s.toString()) {
                        Log.d(TAG, "afterTextChanged: setNationalCode");
                        ChildrenModel model = registerList.get(position);
                        model.setChild_national_code(register_national_code.text());
                        registerList.set(position, model);

                    }
                }
            });
            register_first_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int position = getAdapterPosition();
                    if (registerList.get(position).getChild_fullName() != s.toString()) {
                        Log.d(TAG, "afterTextChanged: setFullName");
                        //mCallBack. setFullName(getAdapterPosition() , s.toString());
                        ChildrenModel model = registerList.get(getAdapterPosition());
                        model.setChild_firstName(register_first_name.text());
                        registerList.set(getAdapterPosition(), model);
                        Log.d("checkkkk", "positionName: "+getAdapterPosition()+"");

                    }
                }
            });

            getRegister_last_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int position = getAdapterPosition();
                    if (registerList.get(position).getChild_fullName() != s.toString()) {
                        Log.d(TAG, "afterTextChanged: setFullName");
                        //mCallBack. setFullName(getAdapterPosition() , s.toString());
                        ChildrenModel model = registerList.get(getAdapterPosition());
                        model.setChild_lastName(getRegister_last_name.text());
                        registerList.set(getAdapterPosition(), model);
                        Log.d("checkkkk", "positionName: "+getAdapterPosition()+"");

                    }
                }
            });


            gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String type;
                    if (checkedId == R.id.radio_mail) {
                        type = "0";
                    } else {

                        type = "1";
                    }
                    ChildrenModel model = registerList.get(getAdapterPosition());
                    model.setChild_gender(type);
              /*      notifyItemChanged(getAdapterPosition());*/
                    registerList.set(getAdapterPosition(), model);

                    Log.d("checkkkk", "positionGender: "+getAdapterPosition()+"");
                }
            });


            relation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String type;
                    if (checkedId == R.id.radio_relation) {
                        type = "0";
                    } else {

                        type = "1";
                    }
                    ChildrenModel model = registerList.get(getAdapterPosition());
                    model.setChild_relation(type);
                    /*      notifyItemChanged(getAdapterPosition());*/
                    registerList.set(getAdapterPosition(), model);

                    Log.d("checkkkk", "positionGender: "+getAdapterPosition()+"");
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
                                    ChildrenModel model = registerList.get(getAdapterPosition());
                                    model.setChild_date_born(checkDateString);
                                    notifyItemChanged(getAdapterPosition());
                                    registerList.set(getAdapterPosition(), model);

                                    Log.d("checkkkk", "positionCal: "+getAdapterPosition()+"");


                                }

                                @Override
                                public void onDismissed() {
                                }
                            });
                    picker.show();

                }
            });


            btn_register_children.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChildrenModel model = registerList.get(getAdapterPosition());
                    String name = model.getChild_firstName()+" "+model.getChild_lastName();
                    String born = model.getChild_date_born();
                    String gender = model.getChild_gender();
                    String nationalCode = model.getChild_national_code();
                    String Relation = model.getChild_relation();
                    Uri ImageFile = model.getLocal_Image_Uri();

                    Log.d("checkkkk", "fullName: "+name);
                    Log.d("checkkkk", "gender: "+model.getChild_gender());
                    Log.d("checkkkk", "code: "+model.getChild_national_code());
                    Log.d("checkkkk", "born: "+model.getChild_date_born());
                    Log.d("checkkkk", "relation: "+model.getChild_relation());

                    Log.d("userrr", "onClick: " + name + born + gender);
                    if ((!name.equals("")) && (!born.equals("انتخاب از تقویم") && born.contains("/")) && (!gender.equals("")) &&(!Relation.equals("") && (!nationalCode.equals(""))))
                    {
                        PublicMethods.showToast("اطلاعات در حال ارسال است لطفا منتظر بمانید.");
                        mCallBack.registerChild(getAdapterPosition());
                    }
                    else
                    {
                        PublicMethods.showToast("لطفا  فیلد نام و نام خانوادگی و تاریخ تولد و جنسیت و کد ملی و نسبت را پر نمایید.");
                    }

                }
            });


        }

 /*       private void setDisableFormOption() {
            btn_register_children.setEnabled(false);
            img_upload.setEnabled(false);
            btn_calender.setEnabled(false);
            register_.setEnabled(false);
            register_national_code.setEnabled(false);
        }*/
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.item_register_children, parent, false);
        ViewHolder holder = new ViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ChildrenModel model = registerList.get(position);
        holder.register_first_name.setText(model.getChild_firstName());
        holder.getRegister_last_name.setText(model.getChild_lastName());
        holder.register_national_code.setText(model.getChild_national_code());
        holder.btn_calender.setText((CharSequence) model.getChild_date_born());
        holder.btn_register_children.setText("ثبت اطلاعات فرزند " );
        Glide.with(mContext).load(model.getLocal_Image_Uri()).transform(new CircleTransform((RecyclerRegisterActivity) pActivity)).into(holder.img_upload);
    }


    @Override
    public int getItemCount() {
        Log.d("testt", "getItemCount: " + registerList.size());
        return registerList.size();
    }

    String getStringPosition(int position) {
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

    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(RecyclerRegisterActivity customClass) {
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

