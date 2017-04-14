package com.listen.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Timestamped;

/**
 * @author listen
 * @desc timestamp()给数据标上时间戳
 */
public class RxTimestampActivity extends AppCompatActivity {

    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_result)
    TextView mTvResult;
    private Integer[] mWords = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout11);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvDesc.setText("使用timestamp()为给定数据列表：1, 2, 3, 4, 5, 6, 7, 8, 9, 10中每一个数据加上一个对应的时间戳");
    }

    @OnClick(R.id.button)
    public void onClick() {
        mTvResult.setText("");
        start();
    }

    private void start() {
        Observable.from(mWords).timestamp()
                // .timestamp(Schedulers.io()) 可指定线程环境，如果指定到子线程，请在最后切换成主线程
                .subscribe(new Action1<Timestamped<Integer>>() {
                    @Override
                    public void call(Timestamped<Integer> integerTimestamped) {
                        mTvResult.append("value:" + integerTimestamped.getValue() + ",time:");
                        mTvResult.append(mSimpleDateFormat.format(new Date(integerTimestamped.getTimestampMillis())) + "\n");
                    }
                });
    }

}
