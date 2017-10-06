package com.example.lablnet.tictac;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * Created by lablnet on 10/4/2017.
 */

public class CustomTableLayout extends TableLayout {
    public interface OnTableLayoutItemClick {
        void onClick(TextView txt, int row,int column);
    }

    private OnTableLayoutItemClick click;

    public CustomTableLayout(Context context) {
        super(context);
        setStretchAllColumns(true);
        addChildView();
    }

    public void setOnItemClickListner(OnTableLayoutItemClick click) {
        this.click = click;
    }

    public CustomTableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setStretchAllColumns(true);
        addChildView();
    }

    public void addChildView() {
        final int noOfRow = 3;
        LayoutParams params = new LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(20, 0, 0, 0);
        TableRow tableRow = null;
        for (int i = 0; i < noOfRow; i++) {
            tableRow = new TableRow(getContext());
            addView(tableRow);
            TextView textView1 = new TextView(getContext());
            TextView textView2 = new TextView(getContext());
            TextView textView3 = new TextView(getContext());
            setTextViewAttribute(textView1,textView2,textView3);
            tableRow.addView(textView1);
            tableRow.addView(textView2);
            tableRow.addView(textView3);
            final int finalI = i + 1;
            textView1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.onClick((TextView) v, finalI,1);
                }
            });
            textView2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.onClick((TextView) v, finalI,2);
                }
            });
            textView3.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.onClick((TextView) v, finalI,3);
                }
            });
        }
    }
    public void setTextViewAttribute(TextView textView1,TextView textView2,TextView textView3) {
        textView1.setTextSize(40);
        textView2.setTextSize(40);
        textView3.setTextSize(40);
        textView1.setTypeface(Typeface.DEFAULT_BOLD);
        textView2.setTypeface(Typeface.DEFAULT_BOLD);
        textView3.setTypeface(Typeface.DEFAULT_BOLD);
        textView1.setBackground(getResources().getDrawable(R.drawable.border));
        textView2.setBackground(getResources().getDrawable(R.drawable.border));
        textView3.setBackground(getResources().getDrawable(R.drawable.border));
    }
}
