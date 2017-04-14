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
 * @desc flatMap操作符的使用，将数据铺平
 */
public class RxFlatMapActivity extends AppCompatActivity {

    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.tv_result)
    TextView mTvResult;

    private Tree[] mTrees = new Tree[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout4);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        mTvDesc.setText("打印树的层级");
    }

    private void initData() {
        TreeNode[] nodes1 = new TreeNode[5];
        for (int i = 0; i < 5; i++) {
            nodes1[i] = new TreeNode("tree1,node" + i);
        }
        mTrees[0] = new Tree(nodes1);

        TreeNode[] nodes2 = new TreeNode[5];
        for (int i = 0; i < 5; i++) {
            nodes2[i] = new TreeNode("tree2,node" + i);
        }
        mTrees[1] = new Tree(nodes2);
    }

    @OnClick(R.id.button)
    public void onClick() {
        mTvResult.setText("");
        start();
    }

    private void start() {
        Observable.from(mTrees)
                // 通过flatMap，将Student一个个取出，并包装成新的Observable继续传递
                .flatMap(new Func1<Tree, Observable<TreeNode>>() {
                    @Override
                    public Observable<TreeNode> call(Tree tree) {
                        // 使用from生成新的Observable
                        return Observable.from(tree.getNodes());
                    }
                })
                .subscribe(new Action1<TreeNode>() {
                    @Override
                    public void call(TreeNode node) {
                        mTvResult.append(node + "\n");
                    }
                });
    }
}

class Tree {
    TreeNode[] nodes;

    public Tree(TreeNode[] s) {
        this.nodes = s;
    }

    public TreeNode[] getNodes() {
        return nodes;
    }
}

class TreeNode {
    String name;

    public TreeNode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TreeNode{" + "name='" + name + '\'' + '}';
    }
}
