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
 * @desc 使用filter操作符过滤数据
 */
public class RxFilterActivity extends AppCompatActivity {

    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_result)
    TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout6);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvDesc.setText("输入1-10, 过滤掉奇数");
    }

    @OnClick(R.id.button)
    public void onClick() {
        start();
    }

    private void start() {
        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Observable.from(integers)
                // 过滤掉奇数
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
                    }
                })
                // 订阅观察者
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        mTvResult.append(integer.toString() + ",");
                    }
                });
    }

}
