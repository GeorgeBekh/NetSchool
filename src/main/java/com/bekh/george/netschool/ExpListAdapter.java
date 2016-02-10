package com.bekh.george.netschool;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bekh.george.netschool.R;

import java.util.ArrayList;

public class ExpListAdapter extends BaseExpandableListAdapter {
    String red = "#FFBBBB";
    String white = "#FFFFFF";


    private ArrayList<ArrayList<String>> mGroups;
    private ArrayList<String> groupNames;
    private Context mContext;

    public ExpListAdapter (Context context,ArrayList<ArrayList<String>> groups){
        mContext = context;
        mGroups = groups;
        groupNames=new ArrayList<String>();
        for(int i=0;i<mGroups.size();i++){

            groupNames.add(mGroups.get(i).get(0));
            mGroups.get(i).remove(0);
        }

    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mGroups.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mGroups.get(groupPosition).get(childPosition);
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                             ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_item, null);
        }


        TextView textGroup = (TextView) convertView.findViewById(R.id.label);
        RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.rellay);
        textGroup.setTextSize(mContext.getResources().getDimension(R.dimen.big));
        Button b = (Button) convertView.findViewById(R.id.mark);
        b.setVisibility(View.GONE);

        String s = groupNames.get(groupPosition).replace("!@#","");
        s=s.replace(", ", "\n");
        if(s.contains(red)){
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.LRED));
            s=s.replace(red,"");
        } else{
            convertView.setBackgroundColor(Color.WHITE);
            s=s.replace(white,"");
        }
        textGroup.setText(s);
       // textGroup.setTextColor(Color.GRAY);
      //  textGroup.setBackgroundColor(mContext.getResources().getColor(R.color.LLGREY));
       textGroup.setGravity(Gravity.RIGHT);
        textGroup.setMinHeight(60);
        textGroup.setTypeface(StartActivity.tf);



        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_item, null);
        }
        int mark;
        String s;
        s= mGroups.get(groupPosition).get(childPosition);
        if(s.contains(red)){
            s=s.replace(red,"");
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.LRED));
        }else{
            s=s.replace(white,"");
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.LLGREY));
        }

        TextView textChild = (TextView) convertView.findViewById(R.id.label);
        try {
            mark=Integer.parseInt(s.substring(s.length()-1,s.length()));
        }catch (NumberFormatException e){
            e.printStackTrace();
            mark=0;
        }

        s=s.substring(0,s.length()-8);

        textChild.setText(s);


        Button button = (Button) convertView.findViewById(R.id.mark);
        switch (mark){
            case 1:
                button.setVisibility(View.VISIBLE);
                button.setBackgroundColor(Color.BLACK);
                break;
            case 2:
                button.setVisibility(View.VISIBLE);
                button.setBackgroundColor(mContext.getResources().getColor(R.color.LRED));
                break;
            case 3:
                button.setVisibility(View.VISIBLE);
                button.setBackgroundColor(Color.GRAY);
                break;
            case 4:
                button.setVisibility(View.VISIBLE);
                button.setBackgroundColor(mContext.getResources().getColor(R.color.LYELLOW));
                break;
            case 5:
                button.setVisibility(View.VISIBLE);
                button.setBackgroundColor(mContext.getResources().getColor(R.color.LGREEN));
                break;
            case 0:
                button.setVisibility(View.GONE);
            break;
        }
        if(button!=null) {
            button.setText(Integer.toString(mark));
        }
        textChild.setTypeface(StartActivity.tf);
       // button.setTypeface(StartActivity.tf);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}