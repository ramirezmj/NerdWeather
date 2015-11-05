package edu.jmramirez.nerdweather.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.jmramirez.nerdweather.R;

public class NWStationAdapter extends BaseAdapter {

    Context mContext;
    List<NWStation> mStationsList = new ArrayList<>();

    public NWStationAdapter() {}

    public NWStationAdapter (Context context, List<NWStation> stationsList) {
        this.mContext = context;
        this.mStationsList = stationsList;
    }

    @Override
    public int getCount() {
        return this.mStationsList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.mStationsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View elementView = convertView;

        if (elementView == null) {
            LayoutInflater inflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            elementView = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView stationNameTV = (TextView) elementView.findViewById(R.id.stationNameTV);
        stationNameTV.setText(this.mStationsList.get(position).getStationName());

        return elementView;
    }
}
