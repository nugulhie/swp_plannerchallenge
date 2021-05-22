package com.example.swp_challenge.CustomCalendarView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swp_challenge.Activity.CalendarActivity;
import com.example.swp_challenge.CustomCalendarView.Adapters.MyCalenderAdapter;
import com.example.swp_challenge.CustomCalendarView.Helpers.Badge;
import com.example.swp_challenge.CustomCalendarView.Helpers.ClickInterface;
import com.example.swp_challenge.R;
import com.example.swp_challenge.controller.UserController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by vamsi on 23/12/15.
 */
public class CustomCalendar extends LinearLayout {

    Context context;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mRLayoutManager;
    RecyclerView.Adapter mAdapter;

    ImageView ivPreviceMonth, ivNextMonth;

    Calendar calendar;

    TextView tvMonth;

    FrameLayout fcell;

    int iCellSize = 50;

    LinearLayout llMonth, llRecyclear;

    List<Badge> badges;

    String months[] = {
            "1월", "2월", "3월", "4월", "5월",
            "6월", "7월", "8월", "9월", "10월",
            "11월", "12월"
    };

    TextView tvMonthYear;


    static ClickInterface clickDate;


    public CustomCalendar(Context context) {
        super(context);

        this.context = context;

        init();

    }


    public CustomCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);


        this.context = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomCalendar, 0, 0);
        iCellSize = a.getInt(R.styleable.CustomCalendar_cellSize, 50);

        a.recycle();

        init();

    }

    private void init() {
        UserController user = UserController.getInstance();
        inflate(getContext(), R.layout.calenderview, this);

        llMonth = (LinearLayout) findViewById(R.id.llMonth);
        llRecyclear = (LinearLayout) findViewById(R.id.llMyRecyclearView);

        tvMonthYear = (TextView) findViewById(R.id.tvMonthYear);

        llMonth.setLayoutParams(new LayoutParams(iCellSize * 7, LayoutParams.WRAP_CONTENT));
        llRecyclear.setLayoutParams(new LayoutParams(iCellSize * 7, iCellSize * 7));

        fcell = findViewById(R.id.flCell);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        mRecyclerView = (RecyclerView) findViewById(R.id.rView);
        this.mRLayoutManager = new GridLayoutManager(context, 7);
        mRecyclerView.setLayoutManager(mRLayoutManager);


        setMonth(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_MONTH));

        ivPreviceMonth = (ImageView) findViewById(R.id.ivPrevesMonth);
        ivNextMonth = (ImageView) findViewById(R.id.ivNextMonth);

        tvMonth = (TextView) findViewById(R.id.tvMonthYear);

        tvMonth.setText(calendar.get(Calendar.YEAR)+"년"+ months[calendar.get(Calendar.MONTH)] );


        ivPreviceMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH) - 1));

                int month = calendar.get(Calendar.MONTH);

                tvMonth.setText(calendar.get(Calendar.YEAR)+"년"+ months[calendar.get(Calendar.MONTH)]);
                CalendarActivity.loadEvent(user.getContext());

                setMonth(month, calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_MONTH));

            }
        });


        ivNextMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar.set(Calendar.MONTH, (calendar.get(Calendar.MONTH) + 1));

                int month = calendar.get(Calendar.MONTH);

                tvMonth.setText(calendar.get(Calendar.YEAR)+"년"+ months[calendar.get(Calendar.MONTH)]);
                CalendarActivity.loadEvent(user.getContext());
                setMonth(month, calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_MONTH));

            }
        });

    }


    public void setMonth(int month, int year, int toDay) {

        int dayMax = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int firstDay = calendar.get(Calendar.DAY_OF_WEEK);

        this.mAdapter = new MyCalenderAdapter(context, dayMax, firstDay, month, year, toDay, iCellSize, badgsMonth(month));
        this.mRecyclerView.setAdapter(mAdapter);

    }

    //this method is for handling date onClickListener
    public static ClickInterface staticClickInterface() {

        if (clickDate != null)
            return clickDate;
        else
            return null;

    }

    //this method is for implementing Date onClickListener
    public void setOnClickDate(ClickInterface clickDate) {

        this.clickDate = clickDate;


    }


    // month textview specifications
    public void setMonthTextSize(float sizeInSP) {

        tvMonthYear.setTextSize(pixelsToSp(sizeInSP));

    }

    public void setMonthTextColor(int color) {

        tvMonthYear.setTextColor(color);

    }



    public void setBadgeDateList(List<Badge> badges) {

        this.badges = badges;

        setMonth(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_MONTH));

    }



    private List<Badge> badgsMonth(int month) {

        List<Badge> monthBadge = new ArrayList<>();
        if (badges != null) {
            for (int i = 0; i < badges.size(); i++) {
                if (badges.get(i).getMonth() == (month + 1)) {
                    monthBadge.add(badges.get(i));
                }
            }
        }

        return monthBadge;
    }


    public void setFullScreenWidth(boolean isFullScreen) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        if (isFullScreen) {
            iCellSize = (width) / 7;
            llMonth.setLayoutParams(new LayoutParams(iCellSize * 7, LayoutParams.WRAP_CONTENT));
            llRecyclear.setLayoutParams(new LayoutParams(iCellSize * 7, iCellSize * 7));
            setMonth(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_MONTH));


        }
    }


    private float pixelsToSp(float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px/scaledDensity;
    }

}
