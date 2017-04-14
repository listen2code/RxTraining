package com.listen.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * @author listen
 * @desc 最基础的Rxjava的用法
 */
public class RxBasicActivity extends AppCompatActivity {

    private String str = "一\n二\n三\n四\n";

    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_result)
    TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout1);
        ButterKnife.bind(this);
        mTvDesc.setText(str);
    }

    private void start1() {
        // 被观察者 --订阅--> 观察者
        createObservable().subscribe(createSubscriber());
    }

    private void start2() {
        // 被观察者 --订阅--> ActionX()
        createObservable().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                // Action1也就意味着，只能传入一个参数 ----> String s,同理Action0，Action2....,
                // 在这个call方法中传入了onNext()的参数，相当于代替了onNext方法，但是就不能监听onComplete,onError方法了
                mTvResult.append("onNext(): + " + s + "\n");
            }
        });
    }

    @OnClick({R.id.button1, R.id.button2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                start1();
                break;
            case R.id.button2:
                start2();
                break;
        }
    }

    /**
     * @desc 创建观察者
     * @author listen
     * @date 2017/4/14 10:09
     */
    private Subscriber createSubscriber() {
        return new Subscriber<String>() {
            @Override
            public void onCompleted() {
                mTvResult.append("onCompleted()\n");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                mTvResult.append("onNext(): + " + s + "\n");
            }
        };
    }

    /**
     * @desc // 创建被观察者
     * @author listen
     * @date 2017/4/14 10:09
     */
    private Observable createObservable() {
        // 方式一：create
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("一");
                subscriber.onNext("二");
                subscriber.onNext("三");
                subscriber.onNext("四");
                subscriber.onCompleted();
            }
        });

        // 方式二：from(T[])
        // String[] kk = {"一", "三", "四"};
        // Observable observable = Observable.from(kk);

        // 方式三：just(T...)
        // Observable observable = Observable.just("一", "二", "四");

        return observable;
    }

}
