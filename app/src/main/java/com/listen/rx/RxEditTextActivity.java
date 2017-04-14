package com.listen.rx;

/*
RxBinding这个库是基于RxJava的对于Android原生组件的绑定，是RxJava风格的，
相当于代替了OnClick,Listener这些东西，
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxEditTextActivity extends AppCompatActivity {

    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.et_input)
    EditText mEtInput;
    @BindView(R.id.tv_result)
    TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout12);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvDesc.setText("RxTextView.textChanges(mEtInput)\n");
        mTvDesc.append(".debounce(500, TimeUnit.MILLISECONDS)\n\n");
        mTvDesc.append("请输入电话号码前几位（例：136）:");

        // 用来监听edittext输入，同时匹配输入数数据来提示
        RxTextView.textChanges(mEtInput)
        // 缓冲时间：只有在500毫秒内没有连续输入的情况下，才发出这次事件
            .debounce(500, TimeUnit.MILLISECONDS)
            // 转换线程
            .observeOn(Schedulers.newThread())
            // 通过输入的数据，来匹配"数据库"中的数据从而提示。
            .map(new Func1<CharSequence, List<String>>() {
                List<String> list = new ArrayList<String>();

                @Override
                public List<String> call(CharSequence charSequence) {
                    if (charSequence.toString().contains("1")) {
                        for (int i = 0; i < 5; i++) {
                            list.add("13" + i);
                        }
                    }
                    return list;
                }
            })
            // 由于我不想要listl列表，所以使用了flatMap来分解成一个一个的数据发送
            .flatMap(new Func1<List<String>, Observable<String>>() {
                @Override
                public Observable<String> call(List<String> strings) {
                    return Observable.from(strings);
                }
            })
            // 因为要操作UI，所以需要切换成主线程
            .observeOn(AndroidSchedulers.mainThread())
            // 过滤
            .filter(new Func1<String, Boolean>() {
                @Override
                public Boolean call(String s) {
                    return !mEtInput.getText().toString().contains(s);
                }
            })
            // 订阅
            .subscribe(new Action1<String>() {
                @Override
                public void call(String s) {
                    // 这里展示提示数据
                    mTvResult.append(s + "\n");
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Log.w("error", throwable.getMessage().toString());
                }
            });

    }
}
