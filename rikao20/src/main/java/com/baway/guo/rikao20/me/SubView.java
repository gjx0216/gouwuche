package com.baway.guo.rikao20.me;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baway.guo.rikao20.R;

public class SubView extends LinearLayout {

    private View mView;
    private TextView mJian;
    private TextView mNum;
    private TextView mJia;

    public SubView(Context context) {
        this(context, null);
    }

    public SubView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initListener();
    }

    private void initListener() {
        mJia.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        mJian.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                jian();
            }
        });
    }

    private void add() {
        String s = mNum.getText().toString();
        int i = Integer.parseInt(s);
        i++;
        setCurrentCount(i);
    }

    private void setCurrentCount(int i) {
        mNum.setText("" + i);
        if (mOnNumChangerListener != null) {
            mOnNumChangerListener.onNumChange(this, i);
        }
    }

    private void jian() {
        String s = mNum.getText().toString();
        int i = Integer.parseInt(s);
        if (i > 1) {
            i--;
            setCurrentCount(i);
        } else {
            Toast.makeText(getContext(), "不能再少了", Toast.LENGTH_LONG).show();
        }
    }

    public interface OnNumChangerListener {
        void onNumChange(View view, int curNum);
    }

    public OnNumChangerListener mOnNumChangerListener;

    public void setOnNumChangerListener(OnNumChangerListener onNumChangerListener) {
        this.mOnNumChangerListener = onNumChangerListener;
    }

    private void initView(Context context) {

        mView = inflate(context, R.layout.jia_jian, this);
        mJian = mView.findViewById(R.id.num_jian);
        mNum = mView.findViewById(R.id.num);
        mJia = mView.findViewById(R.id.num_jia);
    }
}
