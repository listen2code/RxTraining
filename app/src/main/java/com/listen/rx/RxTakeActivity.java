package com.listen.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * @author listen
 * @desc take操作符的用法
 */
public class RxTakeActivity extends AppCompatActivity {

    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_result)
    TextView mTvResult;
    private Integer[] mNumber = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout7);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvDesc.setText("输出[1,2,3,4,5,6,7,8,9,10]中第三个和第四个偶数");
    }

    @OnClick(R.id.button)
    public void onClick() {
        mTvResult.setText("");
        start();
    }

    private void start() {
        Observable.from(mNumber)
        // 过滤掉奇数
            .filter(new Func1<Integer, Boolean>() {
                @Override
                public Boolean call(Integer integer) {
                    return integer % 2 == 0;
                }
            })
            // 取前四个
            .take(4)
            // 取前四个中的后两个
            .takeLast(2)
            // onNext调用之前调用
            .doOnNext(new Action1<Integer>() {
                @Override
                public void call(Integer integer) {
                    mTvResult.append("before call:" + integer + "\n");
                }
            })
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer integer) {
                    mTvResult.append("call:" + integer + "\n");
                }
            });
    }

}
