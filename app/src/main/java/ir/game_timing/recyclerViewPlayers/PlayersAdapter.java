package ir.game_timing.recyclerViewPlayers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

import ir.game_timing.PublicMethods;
import ir.game_timing.R;
import ir.game_timing.customViews.MyEditText;
import ir.game_timing.models.MyResponse;
import ir.game_timing.recyclerViewChildren.ChildrenModel;
import ir.game_timing.recyclerViewChildren.RecyclerRegisterAdapter;
import ir.game_timing.retrofit.GameTimingInterface;
import ir.game_timing.retrofit.GameTimingService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {
    List<ChildrenModel> playersList;
    Context mContext;
    int TicketId;
    private CallBackInterface mCallBack;

    public interface CallBackInterface {
        void EndRegister();
    }

    public PlayersAdapter(Context mContext, List<ChildrenModel> playersList, int ticketId) {
        this.playersList = playersList;
        this.mContext = mContext;
        TicketId = ticketId;
        mCallBack = (CallBackInterface) mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MyEditText FirstName, LastName, age;
        Button btn_player_register;
        RadioGroup genderPlayer;
        RadioButton radio_femailP, radio_mailP;


        public ViewHolder(View itemView) {
            super(itemView);
            FirstName = itemView.findViewById(R.id.FirstName);
            LastName = itemView.findViewById(R.id.LastName);
            age = itemView.findViewById(R.id.age);
            btn_player_register = itemView.findViewById(R.id.btn_player_register);
            genderPlayer = itemView.findViewById(R.id.genderPlayer);
            radio_femailP = itemView.findViewById(R.id.radio_femailP);
            radio_mailP = itemView.findViewById(R.id.radio_mailP);


            FirstName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    int position = getAdapterPosition();
                    Log.d("222", "afterTextChanged: getChild_firstName 0" + playersList.get(position).getChild_firstName());
                    if (playersList.get(position).getChild_firstName() != s.toString()) {
                        Log.d("111", "afterTextChanged: getChild_firstName" + s.toString());
                        ChildrenModel model = playersList.get(position);
                        model.setChild_firstName(FirstName.text());
                        Log.d("111", "afterTextChanged: getChild_firstName 2" + model.getChild_firstName());
                        playersList.set(position, model);

                    }
                }
            });

            LastName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int position = getAdapterPosition();
                    if (playersList.get(position).getChildAge() != s.toString()) {
                        ChildrenModel model = playersList.get(position);
                        model.setChild_lastName(LastName.text());
                        playersList.set(position, model);

                    }
                }
            });

            age.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    int position = getAdapterPosition();
                    int ageInt;
                    if (!s.toString().equals("")) {
                        ageInt = Integer.parseInt(s.toString());
                        if (ageInt > 2 && ageInt < 13) {
                            ChildrenModel model = playersList.get(position);
                            model.setChildAge(age.text());
                            Log.d("1111", "getChildAge: " + model.getChildAge());
                            playersList.set(position, model);

                        } else {
                            PublicMethods.showToast("بازه سنی بین 3 تا 12 سال باید باشد.");
                            ChildrenModel model = playersList.get(position);

                            age.setText("");

                        }
                    }

                }
            });

            genderPlayer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    String type = "";
                    int position = getAdapterPosition();
                    if (checkedId == R.id.radio_mailP) {
                        type = "0";
                    } else if (checkedId == R.id.radio_femailP) {
                        type = "1";
                    }
                    ChildrenModel model = playersList.get(position);
                    model.setChild_gender(type);
                    Log.d("111", "onCheckedChanged: " + model.getChild_gender());
                    playersList.set(position, model);

                }
            });

            btn_player_register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ChildrenModel model = playersList.get(position);
                    String firstName = model.getChild_fullName();
                    String lastName = model.getChild_lastName();
                    model.setChild_fullName(firstName + " " + lastName);
                    model.setChild_gender(model.getChild_gender());
                    model.setChildAge(model.getChildAge());
                    if (!model.getChild_fullName().equals("") && !model.getChildAge().equals("") && !model.getChild_gender().equals("")) {

                        final String fullName = model.getChild_fullName();
                        String playerAge = model.getChildAge();
                        int Age = Integer.parseInt(playerAge);
                        String genderId = model.getChild_gender();
                        int gender = Integer.parseInt(genderId);
                        String user_Mobile = PublicMethods.getValue("user_mobile", "");

                        Log.d("pll", "fullName: " + fullName);
                        Log.d("pll", "Age: " + Age);
                        Log.d("pll", "gender: " + gender);
                        Log.d("pll", "TicketId: " + TicketId);
                        Log.d("pll", "user_Mobile: " + user_Mobile);
                        int id = TicketId;
                        GameTimingInterface gameTimingInterface = GameTimingService.getClient().create(GameTimingInterface.class);
                        Call<MyResponse<ChildrenModel>> call = gameTimingInterface.registerPlayer(fullName, id, Age, gender, user_Mobile);

                        call.enqueue(new Callback<MyResponse<ChildrenModel>>() {
                            @Override
                            public void onResponse(Call<MyResponse<ChildrenModel>> call, Response<MyResponse<ChildrenModel>> response) {
                                Log.d("pll", "message: " + response.body().getMessage());
                                Log.d("pll", "code: " + response.code());
                                if (response.code() == 200) {
                                    if (response.body().getResultCode() == 1) {
                                        Log.d("pll", "getResultCode: " + response.body().getResultCode());
                                        int position = getAdapterPosition();
                                        PublicMethods.showToast("اطلاعات" + " " + fullName + " " + "با موفقیت ثبت شد.");
                                        removeItemFromRecycler(position);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<MyResponse<ChildrenModel>> call, Throwable t) {

                            }
                        });
                    } else PublicMethods.showToast("لطفا تمامی فیلدها را پر نمایید .");

                }
            });

        }

        private void removeItemFromRecycler(int adapterPosition) {
            playersList.remove(adapterPosition);
            notifyItemRemoved(adapterPosition);
            if (playersList.size() == 0) {

                PublicMethods.showToast("مشخصات با موفقیت ثبت شد لطفا دکمه تایید اطلاعات را بزنید .");
                mCallBack.EndRegister();
            }
        }

    }

    @NonNull
    @Override
    public PlayersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.item_register_player, parent, false);
        ViewHolder holder = new ViewHolder(row);
        return holder;

    }


    @Override
    public void onBindViewHolder(@NonNull PlayersAdapter.ViewHolder holder, int position) {
        final ChildrenModel model = playersList.get(position);
        holder.FirstName.setText(model.getChild_firstName());
        holder.LastName.setText(model.getChild_lastName());
        holder.age.setText(model.getChild_lastName());
        /* holder.gender.set(model.getChild_lastName());*/
        holder.btn_player_register.setText("ثبت اطلاعات کودک ");

    }


    @Override
    public int getItemCount() {
        return playersList.size();
    }


}
