package com.bekh.george.netschool;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by George on 06.02.2016.
 */
public class MarksArrayAdapter extends ArrayAdapter<String> {
    Context context;
    ArrayList<String> values;

    public MarksArrayAdapter(Context context, ArrayList<String> values) {
        super(context,R.layout.marks_layout,values);
        this.context = context;
        this.values=values;
    }
    public View getView(int position,View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.marks_layout,parent,false);
        TextView textView = (TextView) rowView.findViewById(R.id.subj);
        Button button=(Button) rowView.findViewById(R.id.mark);
        String s = values.get(position);
        String subj;
        String mark;
        Double markdoub;
        subj=s.substring(0,s.indexOf("!@#"));
        s=s.substring(s.indexOf("!@#")+3,s.length());
        mark = s;
        mark=mark.replace(",", ".");
        markdoub=Double.parseDouble(mark);
        textView.setText(subj);
        button.setText(new DecimalFormat("#0.0").format(markdoub));
        int i = markdoub.intValue();
        switch (i){
            case 1:
                button.setBackgroundColor(Color.BLACK);
                break;
            case 2:
                button.setBackgroundColor(Color.RED);
                break;
            case 3:
                button.setBackgroundColor(Color.LTGRAY);
                break;
            case 4:
                button.setBackgroundColor(context.getResources().getColor(R.color.LYELLOW));
                break;
            case 5:
                button.setBackgroundColor(context.getResources().getColor(R.color.LGREEN));
                break;
            case 0:
                button.setVisibility(View.GONE);
                break;
        }
        return rowView;


    }
}
