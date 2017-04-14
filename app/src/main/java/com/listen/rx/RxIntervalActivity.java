package com.listen.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * @author listen
 * @desc rxJava的定时器实现
 */
public class RxIntervalActivity extends AppCompatActivity {

    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_result)
    TextView mTvResult;

    private Subscription mObservable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout8);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvDesc.setText("通过interval(1, TimeUnit.SECONDS)实现1秒/次的定时器功能");
    }

    @OnClick({R.id.button1, R.id.button2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                stop();
                start();
                break;
            case R.id.button2:
                stop();
                break;
        }
    }

    private void start() {
        // interval()方式创建的mObservable是运行在Scheduler线程的，因此需要转到主线程
        mObservable =
            Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        mTvResult.setText(aLong + "");
                    }
                });
    }

    private void stop() {
        // 取消订阅
        if (mObservable != null && !mObservable.isUnsubscribed()) {
            mObservable.unsubscribe();
        }
    }

}
