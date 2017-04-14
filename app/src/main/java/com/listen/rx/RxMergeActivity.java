package com.listen.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author listen
 * @desc merge，合并任务，阻塞处理
 */
public class RxMergeActivity extends AppCompatActivity {

    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_result)
    TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout5);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvDesc.setText("3个任务并发进行，全部处理完毕之后再进行展示");
    }

    @OnClick(R.id.button)
    public void onClick() {
        mTvResult.setText("");
        start();
    }

    private void start() {
        Observable obs1 = createObservable(1000);
        Observable obs2 = createObservable(2000);
        Observable obs3 = createObservable(3000);

        Observable.merge(obs1, obs2, obs3)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<String>() {
                StringBuffer sb = new StringBuffer();

                @Override
                public void onCompleted() {
                    mTvResult.append("onCompleted：" + sb + "\n");
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(String s) {
                    sb.append(s + ",");
                    mTvResult.append("onNext：" + s + "\n");
                }
            });
    }

    private Observable<String> createObservable(final int delay) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    Thread.sleep(delay);
                    subscriber.onNext("task " + delay + " delay");
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.newThread());
    }

}
