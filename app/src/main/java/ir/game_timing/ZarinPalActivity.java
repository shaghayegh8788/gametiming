package ir.game_timing;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

public class ZarinPalActivity extends BaseActivity {
Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zarin_pal);
        send = findViewById(R.id.send);

        Uri data = getIntent().getData();
        ZarinPal.getPurchase(mContext).verificationPayment(data, new OnCallbackVerificationPaymentListener() {
            @Override
            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {
                if(isPaymentSuccess)
                {
                    PublicMethods.showToast("پرداخت با موفقیت انجام شد");
                    Log.d("12345", "onCallbackResultVerificationPayment: "+refID);
                }

                else
                {
                    PublicMethods.showToast("پرداخت با موفقیت صورت نگرفت");
                }
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("12345", "goZaripal: ");
                ZarinPal purchase = ZarinPal.getPurchase(mContext);
                PaymentRequest payment = ZarinPal.getPaymentRequest();

                payment.setMerchantID("2d736812-beb6-11e6-a2ca-000c295eb8fc");
                payment.setAmount(100L);
                payment.setDescription("گیم تایمینگ");
                payment.setCallbackURL("return://zarinpalpayment");

                purchase.startPayment(payment, new OnCallbackRequestPaymentListener() {
                    @Override
                    public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {
                        if (status == 100) {

                        } else {
                            PublicMethods.showToast("خطا در ایجاد درخواست پرداخت");
                        }
                    }
                });
            }
        });
    }
}
