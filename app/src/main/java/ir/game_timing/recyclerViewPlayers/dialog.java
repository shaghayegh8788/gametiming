package ir.game_timing.recyclerViewPlayers;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import ir.game_timing.R;

public class dialog extends Dialog implements View.OnClickListener {
    public Activity c;
    public Dialog d;
    public Button gotIt,no;


    public dialog(Activity a) {
        super(a);
        this.c = a;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
  /*      gotIt = (Button) findViewById(R.id.gotIt);
        gotIt.setOnClickListener(this);*/
    }

    @Override
    public void onClick(View v) {

            switch (v.getId()) {
                case R.id.gotIt:
                    dismiss();
                    break;

            }
            dismiss();
        }

}
