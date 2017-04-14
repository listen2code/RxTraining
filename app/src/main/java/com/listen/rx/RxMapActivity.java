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
 * @desc map操作符的用法
 */
public class RxMapActivity extends AppCompatActivity {

    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_result)
    TextView mTvResult;

    private Integer[] number = {1, 2, 3, 4, 5, 6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvDesc.setText("判断数组中小于3的数\n输入Integer(int)：1,2,3,4,5,6 \n输出:true/false");
    }

    private void start() {
        // 创建被观察者
        Observable observable = Observable.from(number);

        // 处理数据, 订阅观察者, 两者要一起调用
        observable.map(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                boolean flag = integer < 3;
                mTvResult.append("\nmap,integer=" + integer + "<3:");
                mTvResult.append(String.valueOf(flag));
                return flag;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                mTvResult.append("\ncall:");
                mTvResult.append(aBoolean.toString());
            }
        });
    }

    @OnClick(R.id.button)
    public void onClick() {
        mTvResult.setText("输入：1,2,3,4,5,6\n");
        start();
    }
}
