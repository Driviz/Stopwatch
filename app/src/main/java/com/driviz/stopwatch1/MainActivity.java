package com.driviz.stopwatch1;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button start_stop, lap_reset;
    int seconds=0,min,sec,mil_sec;
    boolean running;
    TextView Timer_view;
    Handler handler;
    String time;

    ListView listView;
    Laps_class obj=new Laps_class("","");
    List<Laps_class> data_list;
    Custom_Adapter adapter;
    int lap_count=1;
    boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        Timer_view=(TextView)findViewById(R.id.Timer_view);
        listView=(ListView)findViewById(R.id.Lap_List);
        data_list=new ArrayList<>();

        setButtonInitials();
        runTimer();

        start_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((Integer) view.getTag()==1)
                {
                    running=true;
                    start_stop.setText("STOP");
                    start_stop.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.StopBG));
                    start_stop.setTextColor(Color.RED);
                    lap_reset.setText("LAP");
                    lap_reset.setTextColor(Color.WHITE);
                    lap_reset.setBackgroundColor(Color.GRAY);
                    lap_reset.setEnabled(true);
                    addClickEffect(start_stop);
                    addClickEffect(lap_reset);
                    view.setTag(0);
                    runTimer();
                }
                else
                {
                    running=false;
                    start_stop.setText("START");
                    start_stop.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.StartBG));
                    start_stop.setTextColor(Color.GREEN);
                    lap_reset.setText("RESET");
                    addClickEffect(start_stop);
                    view.setTag(1);
                    handler.removeCallbacksAndMessages(null);
                }
            }
        });

        lap_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((Integer)start_stop.getTag()==0)
                {
                    lap_count++;
                    addClickEffect(lap_reset);
                    obj=new Laps_class("","");
                    flag=true;
                }
                else
                {
                    running=false;
                    lap_count=1;
                    seconds=0;
                    lap_reset.setText("LAP");
                    lap_reset.setBackgroundColor(Color.BLACK);
                    lap_reset.setTextColor(Color.GRAY);
                    lap_reset.setEnabled(false);
                    for(int i=data_list.size()-2;i>=0;i--)
                    {
                        data_list.remove(i);
                        adapter.notifyDataSetChanged();
                    }
                    enterDataList("00:00.00");
                    addClickEffect(lap_reset);

                }
            }
        });

    }

    private void setButtonInitials()
    {
        start_stop=(Button)findViewById(R.id.start_stop_button);
        start_stop.setTag(1);
        start_stop.setText("START");
        start_stop.setBackgroundColor(ContextCompat.getColor(MainActivity.this,R.color.StartBG));
        start_stop.setTextColor(Color.GREEN);
        addClickEffect(start_stop);

        lap_reset=(Button)findViewById(R.id.lap_reset_button);
        lap_reset.setTag(1);
        lap_reset.setText("LAP");
        lap_reset.setBackgroundColor(Color.BLACK);
        lap_reset.setTextColor(Color.GRAY);
        lap_reset.setEnabled(false);
    }

    private void runTimer()
    {
        handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                min=seconds/3600;
                sec=(seconds%3600)/60;
                mil_sec=seconds%60;

                time=String.format("%02d:%02d.%02d",min,sec,mil_sec);

                enterDataList(time);
                if(running)
                    seconds++;
                handler.postDelayed(this,10);
            }
        });
    }

    void addClickEffect(View view)
    {
        Drawable drawableNormal = view.getBackground();

        Drawable drawablePressed = view.getBackground().getConstantState().newDrawable();
        drawablePressed.mutate();
        drawablePressed.setColorFilter(Color.argb(50, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);

        StateListDrawable listDrawable = new StateListDrawable();
        listDrawable.addState(new int[] {android.R.attr.state_pressed}, drawablePressed);
        listDrawable.addState(new int[] {}, drawableNormal);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            //noinspection deprecation
            view.setBackgroundDrawable(listDrawable);
        } else {
            view.setBackground(listDrawable);
        }
    }

    private void enterDataList(String time)
    {
        Timer_view.setText(time);

        obj.setLap_number("Lap "+Integer.toString(lap_count));
        obj.setLap_Time(time);

        if(flag)
        {
            data_list.add(obj);
            flag=false;
        }
        else {
            int size=data_list.size()-1;
            data_list.set(size,obj);
        }
        adapter = new Custom_Adapter(MainActivity.this, R.layout.list_layout, data_list);
        listView.setAdapter(adapter);
    }

}
