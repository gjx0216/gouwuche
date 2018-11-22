package com.baway.guo.rikao20.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.baway.guo.rikao20.been.GoodsEntity;
import com.baway.guo.rikao20.R;
import com.baway.guo.rikao20.me.SubView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Exadapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<GoodsEntity.DataBean> data;

    public Exadapter(Context context, List<GoodsEntity.DataBean> data) {
        mContext = context;
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.main_group, null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.groupCk = convertView.findViewById(R.id.group_ck);
            groupViewHolder.groupName = convertView.findViewById(R.id.group_name);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.groupName.setText(data.get(groupPosition).getSellerName());
        groupViewHolder.groupCk.setChecked(data.get(groupPosition).isIschecked());
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.main_child, null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.childCk = convertView.findViewById(R.id.child_ck);
            childViewHolder.childImage = convertView.findViewById(R.id.child_image);
            childViewHolder.childName = convertView.findViewById(R.id.child_name);
            childViewHolder.childPrice = convertView.findViewById(R.id.child_price);
            childViewHolder.mSubView = convertView.findViewById(R.id.subview);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        //图片
        String images = data.get(groupPosition).getList().get(childPosition).getImages();
        String[] split = images.split("!");
        if (split.length > 0) {
            Picasso.with(mContext).load(split[0]).into(childViewHolder.childImage);
        }
        childViewHolder.childName.setText(data.get(groupPosition).getList().get(childPosition).getTitle());
        childViewHolder.childPrice.setText(""+data.get(groupPosition).getList().get(childPosition).getPrice());
        childViewHolder.childCk.setChecked(data.get(groupPosition).getList().get(childPosition).isCheck());
        childViewHolder.mSubView.setOnNumChangerListener(new SubView.OnNumChangerListener() {
            @Override
            public void onNumChange(View view, int curNum) {
                data.get(groupPosition).getList().get(childPosition).setNum(curNum);
                //未完...
                if (mOnNumChangerListener != null) {
                    mOnNumChangerListener.onNumChange(view, curNum);
                }
            }
        });


        return convertView;
    }

    private SubView.OnNumChangerListener mOnNumChangerListener;

    public void setOnNumChangerListener(SubView.OnNumChangerListener onNumChangerListener) {
        this.mOnNumChangerListener = onNumChangerListener;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //group viewHolder
    class GroupViewHolder {
        CheckBox groupCk;
        TextView groupName;
    }

    //child viewHolder
    class ChildViewHolder {
        CheckBox childCk;
        ImageView childImage;
        TextView childName;
        TextView childPrice;
        SubView mSubView;
    }
}
