package com.baway.guo.rikao20;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.baway.guo.rikao20.View.UrlView;
import com.baway.guo.rikao20.adapter.Exadapter;
import com.baway.guo.rikao20.been.GoodsEntity;
import com.baway.guo.rikao20.me.SubView;
import com.baway.guo.rikao20.presenter.UrlPresenter;
import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements UrlView {

    private UrlPresenter mUrlPresenter;
    private String path = "http://120.27.23.105/product/getCarts?source=android&uid=99";
    private ExpandableListView mExpandview;
    private CheckBox mBox;
    private TextView mHeji;
    private Exadapter mExadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mUrlPresenter = new UrlPresenter(this);
        mUrlPresenter.Url(path);
        mBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setCheckAll();
            }
        });
    }

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        GoodsEntity entity = gson.fromJson(result, GoodsEntity.class);
        List<GoodsEntity.DataBean> data = entity.getData();
        mExadapter = new Exadapter(MainActivity.this, data);
        mExpandview.setAdapter(mExadapter);
        for (int i = 0; i < mExadapter.getGroupCount(); i++) {
            mExpandview.expandGroup(i);
        }

        initShopcartChange();
    }

    private void initShopcartChange() {
        mExpandview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                GoodsEntity.DataBean group = (GoodsEntity.DataBean) mExadapter.getGroup(groupPosition);
                //让group中的checkbox选中,之类就跟着选中
                group.setIschecked(!group.isIschecked());
                boolean flag = true;
                if (group.isIschecked()) {
                    flag = false;
                }
                List<GoodsEntity.DataBean.ListBean> list = group.getList();
                for (int i = 0; i < list.size(); i++) {
                    GoodsEntity.DataBean.ListBean listBean = list.get(i);
                    listBean.setCheck(!flag);
                }
                mExadapter.notifyDataSetChanged();
                getTotal();
                return true;
            }
        });
        //子类的合计
        mExpandview.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                GoodsEntity.DataBean.ListBean child = (GoodsEntity.DataBean.ListBean) mExadapter.getChild(groupPosition, childPosition);
                boolean check = child.isCheck();
                if (check) {
                    child.setCheck(true);
                } else {
                    child.setCheck(false);
                }
                mExadapter.notifyDataSetChanged();
                getTotal();
                return true;
            }
        });
        //setOnNumChangeListener
        mExadapter.setOnNumChangerListener(new SubView.OnNumChangerListener() {
            @Override
            public void onNumChange(View view, int curNum) {
                getTotal();
            }
        });
    }

    //=================价格总计===================
    private void getTotal() {
        float total = 0;
        int groupCount = mExadapter.getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            GoodsEntity.DataBean group = (GoodsEntity.DataBean) mExadapter.getGroup(i);
            List<GoodsEntity.DataBean.ListBean> list = group.getList();
            for (int j = 0; j < list.size(); j++) {
                GoodsEntity.DataBean.ListBean listBean = list.get(j);
                boolean check = listBean.isCheck();
                if (check == true) {
                    double price = listBean.getPrice();
                    total = (float) (total + price * listBean.getNum());
                }
            }
        }
        mHeji.setText("合计:" + total);
    }
    //==================主界面全选================
    private void setCheckAll(){
        int groupCount = mExadapter.getGroupCount();
        for (int i = 0; i <groupCount; i++) {
            GoodsEntity.DataBean group1 = (GoodsEntity.DataBean) mExadapter.getGroup(i);
            List<GoodsEntity.DataBean.ListBean> list = group1.getList();
            for (int j = 0; j <list.size() ; j++) {
                GoodsEntity.DataBean.ListBean listBean = list.get(j);
                if (mBox.isChecked()){
                    group1.setIschecked(true);
                    listBean.setCheck(true);
                }
                else {
                    group1.setIschecked(false);
                    listBean.setCheck(false);
                }
            }
        }
        mExadapter.notifyDataSetChanged();;
        getTotal();
    }

    @Override
    public void onFailer(String msg) {

    }

    private void initView() {
        mExpandview = findViewById(R.id.expandview);
        mBox = findViewById(R.id.box);
        mHeji = findViewById(R.id.heji);
    }
}
