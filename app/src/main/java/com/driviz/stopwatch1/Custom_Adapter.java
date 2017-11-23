package com.driviz.stopwatch1;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Driviz on 16-07-2017.
 */

public class Custom_Adapter extends ArrayAdapter<Laps_class> {

    public Custom_Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_layout,null);

        Laps_class laps_class_obj=getItem(position);
        if(laps_class_obj!=null)
        {
            TextView lap_number=convertView.findViewById(R.id.Lap_Number_view);
            TextView lap_timer=convertView.findViewById(R.id.Lap_Timer_view);

            if(lap_number!=null)
                lap_number.setText(laps_class_obj.getLap_number());
            if(lap_timer!=null)
                lap_timer.setText(laps_class_obj.getLap_Time());
        }
        return convertView;
    }
}
