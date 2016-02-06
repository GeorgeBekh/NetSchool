package com.bekh.george.netschool;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by George on 14.01.2016.
 */
public class MyArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;

    public MyArrayAdapter(Context context, ArrayList<String> values) {
        super(context, R.layout.child_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.child_item, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        Button button=(Button) rowView.findViewById(R.id.mark);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(values.get(position));
        // Изменение иконки для Windows и iPhone
        String s = values.get(position);
        String b ="Оценка:";
        Resources res = Resources.getSystem();

       if(s.contains(b+"3")){
           button.setText("3");
           button.setBackgroundColor(Color.LTGRAY);
           textView.setText(s=s.replace(b+"3",""));
       } else {
           if (s.contains(b + "2")) {
               button.setText("2");
               button.setBackgroundColor(Color.RED);
               textView.setText(s = s.replace(b + "2", ""));

           } else {
               if (s.contains(b + "1")) {
                   button.setText("1");
                   button.setBackgroundColor(Color.BLACK);
                   textView.setText(s = s.replace(b + "1", ""));
               } else {
                   if (s.contains(b + "4")) {
                       button.setText("4");
                       button.setBackgroundColor(context.getResources().getColor(R.color.LYELLOW));
                       textView.setText(s = s.replace(b + "4", ""));

                   } else {
                       if (s.contains(b + "5")) {
                           button.setText("5");
                           button.setBackgroundColor(context.getResources().getColor(R.color.LGREEN));
                           textView.setText(s = s.replace(b + "5", ""));

                       } else {
                           if (s.contains(b + "-")) {
                               button.setVisibility(View.GONE);
                               textView.setText(s = s.replace(b + "-", ""));

                           }
                       }
                   }
               }
           }
       }
        if(s.contains("!@#")){
            button.setVisibility(View.GONE);
            textView.setText(s.replace("!@#", ""));
            rowView.setBackgroundColor(context.getResources().getColor(R.color.LLGREY));


        }
        button.setMinHeight(textView.getHeight()*2);

        return rowView;
    }
}