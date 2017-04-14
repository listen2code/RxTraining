package com.listen.rx;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button7, R.id.button6,
        R.id.button8, R.id.button9, R.id.button10, R.id.button11, R.id.button12, R.id.button13})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                startActivity(new Intent(MainActivity.this, RxBasicActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(MainActivity.this, RxMapActivity.class));
                break;
            case R.id.button3:
                startActivity(new Intent(MainActivity.this, RxThreadSwitchActivity.class));
                break;
            case R.id.button4:
                startActivity(new Intent(MainActivity.this, RxFlatMapActivity.class));
                break;
            case R.id.button5:
                startActivity(new Intent(MainActivity.this, RxMergeActivity.class));
                break;
            case R.id.button6:
                startActivity(new Intent(MainActivity.this, RxFilterActivity.class));
                break;
            case R.id.button7:
                startActivity(new Intent(MainActivity.this, RxTakeActivity.class));
                break;
            case R.id.button8:
                startActivity(new Intent(MainActivity.this, RxIntervalActivity.class));
                break;
            case R.id.button9:
                startActivity(new Intent(MainActivity.this, RxSortActivity.class));
                break;
            case R.id.button10:
                startActivity(new Intent(MainActivity.this, RxConnectActivity.class));
                break;
            case R.id.button11:
                startActivity(new Intent(MainActivity.this, RxTimestampActivity.class));
                break;
            case R.id.button12:
                startActivity(new Intent(MainActivity.this, RxEditTextActivity.class));
                break;
            case R.id.button13:
                startActivity(new Intent(MainActivity.this, RxButtonActivity.class));
                break;
        }
    }

}
