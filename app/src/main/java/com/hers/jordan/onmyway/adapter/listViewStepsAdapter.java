package com.hers.jordan.onmyway.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hers.jordan.onmyway.R;
import com.hers.jordan.onmyway.model.Step;

/**
 * Created by Jordan on 23/12/2015.
 */
public class listViewStepsAdapter extends ArrayAdapter<Step>{

    private Context context;
    private int resource;
    private Step[] data = null;

    public listViewStepsAdapter(Context context, int resource, Step[] data) {
        super(context, resource, data);

        this.resource = resource;
        this.context= context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource,parent, false);
        }

        Step step = data[position];

        // init view items
        TextView textViewName = (TextView) convertView.findViewById(R.id.textViewStepName);
        TextView textViewPosition = (TextView) convertView.findViewById(R.id.textViewStepPosition);
        TextView textViewTime = (TextView) convertView.findViewById(R.id.textViewStepTime);

        textViewName.setText(step.getName());
        textViewName.setTag(step.get_id());
        textViewPosition.setText(step.getPosition());
        textViewTime.setText(step.getFormatTime());

        return super.getView(position, convertView, parent);
    }

    public void addStep(Step newStep){
        this.add(newStep);
    }

    public void removeStep(){
        //this.remove();
    }
}
