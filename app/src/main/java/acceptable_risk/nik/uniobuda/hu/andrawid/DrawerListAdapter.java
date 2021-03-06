package acceptable_risk.nik.uniobuda.hu.andrawid;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import static acceptable_risk.nik.uniobuda.hu.andrawid.R.drawable.color;

public class DrawerListAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<MyColor> buttonNames;
    public int selectedNum=0;

    public DrawerListAdapter(Context context, ArrayList<MyColor> buttonNames) {
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
        /*MyColor holder;
        if (convertView == null) {
            convertView = new TextView(mContext);
            holder = new MyColor(buttonNames.get(position).colorName, buttonNames.get(position).myColor);
            convertView.setTag(holder);
            convertView.setMinimumHeight(200);
        } else
            holder = (MyColor) convertView.getTag();
        holder.colorName = buttonNames.get(position).colorName;
        holder.myColor = buttonNames.get(position).myColor;
        convertView.setBackgroundColor(android.graphics.MyColor.parseColor(holder.myColor));
        //((TextView)convertView).setText(holder.colorName);
        return convertView;*/

        ImageView shape = (ImageView) convertView;
        if(shape == null){
            shape = (ImageView) View.inflate(parent.getContext(), R.layout.color_layout, null);
        }

        MyColor myColor = buttonNames.get(position);

        if (myColor.colorint == selectedNum){ //selected myColor
            shape.setImageResource(R.drawable.selected_color);
        }else { //not selected colors
            shape.setImageResource(color);
        }

        shape.getDrawable().setColorFilter(myColor.colorint, PorterDuff.Mode.ADD);
        shape.setAdjustViewBounds(true);
        return shape;
    }
}