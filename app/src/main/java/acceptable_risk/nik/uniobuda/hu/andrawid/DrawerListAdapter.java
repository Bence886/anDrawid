package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.shapes.Shape;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.nio.channels.Selector;
import java.util.ArrayList;

public class DrawerListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Color> buttonNames;
    public int selectedNum=0;

    public DrawerListAdapter(Context context, ArrayList<Color> buttonNames) {
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
        /*Color holder;
        if (convertView == null) {
            convertView = new TextView(mContext);
            holder = new Color(buttonNames.get(position).text, buttonNames.get(position).color);
            convertView.setTag(holder);
            convertView.setMinimumHeight(200);
        } else
            holder = (Color) convertView.getTag();
        holder.text = buttonNames.get(position).text;
        holder.color = buttonNames.get(position).color;
        convertView.setBackgroundColor(android.graphics.Color.parseColor(holder.color));
        //((TextView)convertView).setText(holder.text);
        return convertView;*/
        ImageView shape = (ImageView) convertView;
        if(shape==null){
            shape= (ImageView) View.inflate(parent.getContext(), R.layout.color_layout, null);
        }
        Color color = buttonNames.get(position);
        if (color.colorint == selectedNum){ //selected color
            shape.setImageResource(R.drawable.selected_color);
        }else { //not selected colors
            shape.setImageResource(R.drawable.color);
        }
        shape.getDrawable().setColorFilter(color.colorint, PorterDuff.Mode.ADD);

        return shape;
    }
}