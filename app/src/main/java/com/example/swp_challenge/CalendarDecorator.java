package com.example.swp_challenge;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Calendar;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;

public class CalendarDecorator {

    static class SaturdayDecorator implements DayViewDecorator {                                // 달력 decorator 달력 꾸미는 메소드 자바 파일
        private final Calendar calendar = Calendar.getInstance();

        public SaturdayDecorator(){}

        @Override
        public boolean shouldDecorate(CalendarDay day){
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SATURDAY;
        }

        @Override
        public void decorate(DayViewFacade view){
            view.addSpan(new ForegroundColorSpan(Color.BLUE));
        }
    }

    static class SundayDecorator implements DayViewDecorator{
        private final  Calendar calendar = Calendar.getInstance();

        public SundayDecorator(){}

        @Override
        public boolean shouldDecorate(CalendarDay day){
            day.copyTo(calendar);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            return weekDay == Calendar.SUNDAY;
        }
        @Override
        public void decorate(DayViewFacade view){
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }
    static class OneDayDecorator implements DayViewDecorator{
        private CalendarDay date;

        public OneDayDecorator(){
            date = CalendarDay.today();
        }

        @Override
        public boolean shouldDecorate(CalendarDay day){
            return day.equals(date);
        }

        @Override
        public void decorate(DayViewFacade view){
            view.addSpan(new StyleSpan(Typeface.BOLD));
            view.addSpan(new RelativeSizeSpan(1.4f));
            view.addSpan(new ForegroundColorSpan(Color.BLACK));
        }

        public void setDate(Date date){
            this.date = CalendarDay.from(date);
        }
    }

    static class MySelectorDecorator implements DayViewDecorator{
        private final Drawable drawable;
        public MySelectorDecorator(Activity context){
            drawable = context.getResources().getDrawable(R.drawable.my_selector);
        }
        @Override
        public boolean shouldDecorate(CalendarDay day){
            return true;
        }
        @Override
        public void decorate(DayViewFacade view){
            view.setSelectionDrawable(drawable);
        }
    }
}
