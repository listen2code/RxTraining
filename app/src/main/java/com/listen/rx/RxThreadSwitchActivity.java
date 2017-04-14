package com.listen.rx;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @desc RxJava切换线程
 * @author listen
 */
public class RxThreadSwitchActivity extends AppCompatActivity {

    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_result)
    TextView mTvResult;
    @BindView(R.id.linearlayout)
    LinearLayout mLinearlayout;

    private StringBuffer mSb = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout3);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvDesc.setText("根据resId获取资源，展示到ImageView，最后将ImageView添加到LinearLayout");
    }

    @OnClick(R.id.button)
    public void onClick() {
        mTvResult.setText("");
        start();
    }

    private void start() {
        // 创建被观察者
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                mSb.append("create: 线程: " + Thread.currentThread().getName() + "\n");
                Drawable dd = getResources().getDrawable(R.mipmap.wallpapger);
                subscriber.onNext(dd);
                subscriber.onCompleted();
            }
        })
        // 指定创建被观察者时的执行线程
            .subscribeOn(Schedulers.io())
            // 指定处理数据的线程
            .observeOn(Schedulers.newThread())
            // 处理数据, 指定观察者的执行线程, 订阅观察者
            .map(new Func1<Drawable, ImageView>() {
                @Override
                public ImageView call(Drawable drawable) {
                    mSb.append("map: drawable-->imageview 的线程: " + Thread.currentThread().getName() + "\n");
                    ImageView img = new ImageView(RxThreadSwitchActivity.this);
                    LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    img.setLayoutParams(params);
                    img.setImageDrawable(drawable);
                    return img;
                }
            })
            // 指定观察者的线程
            .observeOn(AndroidSchedulers.mainThread())
            // 订阅观察者
            .subscribe(new Action1<ImageView>() {
                @Override
                public void call(ImageView imageView) {
                    mSb.append("call: 线程: " + Thread.currentThread().getName() + "\n");
                    mTvResult.setText(mSb);
                    mLinearlayout.addView(imageView);
                }
            });
    }

}
