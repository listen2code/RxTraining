package com.listen.rx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxSortActivity extends AppCompatActivity {

    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_result)
    TextView mTvResult;

    private Integer[] mWords = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout9);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvDesc.setText("使用toSortedList()为给定数据列表排序：10, 9, 8, 7, 6, 5, 4, 3, 2, 1");
    }

    @OnClick(R.id.button)
    public void onClick() {
        mTvResult.setText("");
        start();
    }

    private void start() {
        Observable.from(mWords).toSortedList().flatMap(new Func1<List<Integer>, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(List<Integer> strings) {
                return Observable.from(strings);
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer strings) {
                mTvResult.append(strings + "\n");
            }
        });
    }

}
