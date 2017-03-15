package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class DrawerListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<ViewHolder> buttonNames;

    public DrawerListAdapter(Context context, ArrayList<ViewHolder> buttonNames) {
        mContext = context;
        this.buttonNames = buttonNames;
    }

    @Override
    public int getCount() {
        return  buttonNames.size();
    }

    @Override
    public Object getItem(int position) {
        return buttonNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = new TextView(mContext);
            holder = new ViewHolder(buttonNames.get(position).text, buttonNames.get(position).color);
            convertView.setTag(holder);
            convertView.setMinimumHeight(200);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.text = buttonNames.get(position).text;
        holder.color = buttonNames.get(position).color;
        convertView.setBackgroundColor(Color.parseColor(holder.color));
        //((TextView)convertView).setText(holder.text);
        return convertView;
    }
}