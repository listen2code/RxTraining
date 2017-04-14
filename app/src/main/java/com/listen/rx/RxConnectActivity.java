package com.listen.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.observables.ConnectableObservable;

/**
 * @author listen
 * @desc connect操作符
 */
public class RxConnectActivity extends AppCompatActivity {

    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_result)
    TextView mTvResult;
    private Integer[] integer = {1, 2, 3, 4, 5, 6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout10);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvDesc.setText("Observable发送事件1-6，两个观察者同时观察这个Observable \n要求：每发出一个事件，观察者A和观察者都会收到，而不是先把所有的时间发送A,然后再发送给B  \n\n");
    }

    @OnClick({R.id.button1, R.id.button2})
    public void onClick(View view) {
        mTvResult.setText("");
        switch (view.getId()) {
            case R.id.button1:
                normol();
                break;
            case R.id.button2:
                connect();
                break;
        }
    }

    private void normol() {
        Observable observable = Observable.from(integer);

        Action1 a1 = new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                mTvResult.append("A  call:  " + o + "\n");
            }
        };
        Action1 a2 = new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                mTvResult.append("B  call:  " + o + "\n");
            }
        };

        observable.subscribe(a1);
        observable.subscribe(a2);
    }

    private void connect() {
        ConnectableObservable observable = Observable.from(integer).publish();// 将一个Observable转换为一个可连接的Observable

        Action1 a1 = new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                mTvResult.append("A  call:  " + o + "\n");
            }
        };
        Action1 a2 = new Action1<Integer>() {
            @Override
            public void call(Integer o) {
                mTvResult.append("B  call:  " + o + "\n");
            }
        };

        observable.subscribe(a1);
        observable.subscribe(a2);
        observable.connect();
    }

}
