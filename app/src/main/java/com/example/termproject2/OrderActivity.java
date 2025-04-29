package com.example.termproject2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.termproject2.R;

import org.w3c.dom.Text;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {
    private LinearLayout container;

    long now = System.currentTimeMillis();
    Date nowdate = new Date(now);
    SimpleDateFormat sdfNowdate = new SimpleDateFormat("MM/dd HH:mm:ss");
    String formatchkDate = sdfNowdate.format(nowdate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setTitle("주문 내역");

        Intent inIntent = getIntent();

        container = (LinearLayout) findViewById(R.id.ordercontents);

        String orderTime = inIntent.getStringExtra("orderTime");
        textview(orderTime + "에 주문하신 내역입니다.");

        String orderName = "";

        int orderCount = inIntent.getIntExtra("orderCount", 0);
        for (int i = 1; i < orderCount; i++) {
            orderName += inIntent.getStringExtra("orderList" + i) + "\n\n";

        }
        textview(orderName);

        final TextView delTimer = new TextView(this);
        delTimer.setId(R.id.delTimer);
        container.addView(delTimer);

        final Button delOrder = new Button(this);
        delOrder.setText("주문 취소");

        delOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                container.removeAllViews();
                Toast.makeText(getApplicationContext(), "주문이 취소되었습니다.", Toast.LENGTH_LONG).show();
            }
        });
        container.addView(delOrder);

        SimpleDateFormat transFormat = new SimpleDateFormat("MM/dd HH:mm:ss");
        Date getOrder = transFormat.parse(orderTime, new ParsePosition(0));
        Date getOrderCheck = transFormat.parse(formatchkDate, new ParsePosition(0));

        long remTime = getOrder.getTime() + 120000L - getOrderCheck.getTime();

        CountDownTimer countDownTimer = new CountDownTimer(remTime, 1000) {
            public void onTick(long millisUntilFinished) {
                delTimer.setText(String.format(Locale.getDefault(), "주문 취소 가능 시간 : %d:%d",
                        millisUntilFinished / (1000L * 60) % 60, millisUntilFinished / 1000L % 60));
            }

            public void onFinish() {
                delTimer.setText("주문 취소 가능 시간이 종료되었습니다.");
                delOrder.setEnabled(false);
                delOrder.setText("시간 초과로 인해 주문 취소가 불가능합니다.");
            }
        }.start();

        Button btnReturn = (Button) findViewById(R.id.btnReturn);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent outIntent = new Intent(getApplicationContext(), MainActivity.class);

                //outIntent.putExtra("Hap", );
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });
    }

    public void textview(String pack) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int size = Math.round(15 * dm.density);

        //부모 뷰
        container = (LinearLayout) findViewById(R.id.ordercontents);

        //TextView 생성
        TextView view1 = new TextView(this);
        view1.setText(pack);
        view1.setTextSize(15);
        view1.setTextColor(Color.BLACK);
        view1.setBackgroundColor(Color.rgb(253, 214, 146));
        view1.setPadding(20, 0, 0, 0);

        //layout_width, layout_height, gravity 설정
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        lp.topMargin = size;
        view1.setLayoutParams(lp);

        //부모 뷰에 추가
        container.addView(view1);
    }

}
