package com.listen.rx;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * @author listen
 * @desc 通过RxBinding防止按钮的连续点击
 */
public class RxButtonActivity extends AppCompatActivity {

    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_result)
    TextView mTvResult;
    @BindView(R.id.button)
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout13);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvDesc.setText("RxView.clicks(mButton)\n" + ".throttleFirst(2000, TimeUnit.MILLISECONDS)\n");
        RxView.clicks(mButton)
                // 2000毫秒内不能连续点击
                .throttleFirst(2000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        // 相当于OnClickListener中的OnClick方法回调
                        mTvResult.append("请勿连续点击 \n");
                    }
                });

    }
}
