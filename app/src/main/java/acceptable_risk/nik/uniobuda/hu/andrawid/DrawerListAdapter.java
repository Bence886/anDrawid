package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class DrawerListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<String> buttonNames;

    public DrawerListAdapter(Context context, ArrayList<String> buttonNames) {
        mContext = context;
        buttonNames = buttonNames;
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
        Button holder;
        if (convertView == null) {
            holder = new Button(mContext);
            convertView.setTag(holder);
        } else
            holder = (Button) convertView.getTag();
        holder.setText(buttonNames.get(position));
        return convertView;
    }
}
