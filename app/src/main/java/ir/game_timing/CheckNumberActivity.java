package ir.game_timing;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class CheckNumberActivity extends AppCompatActivity {

    EditText mobile_number;
    TextView edit_send_again,edit_info,txt_sign_up;
    Button btn_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_number);
        bind();
    }

    public void bind()
    {
        mobile_number = (EditText)findViewById(R.id.mobile_number);
        edit_info = (TextView)findViewById(R.id.edit_info);
        edit_send_again = (TextView)findViewById(R.id.edit_send_again);
        btn_info = (Button)findViewById(R.id.btn_info);
    }
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
