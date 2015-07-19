package dev.suncha.myleads;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;


public class DisplayAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> id;
    private ArrayList<String> com_name;
    private ArrayList<String> per_name;
    private ArrayList<String> mobile;
    private ArrayList<String> email;
    private SparseBooleanArray mSelectedItemsIds;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;


    public DisplayAdapter(Context mContext, ArrayList<String> _id, ArrayList<String> _com_name, ArrayList<String> _per_name, ArrayList<String> _mobile, ArrayList<String> _email, ArrayList<String> _rating) {
        this.mContext = mContext;
        this.id = _id;
        this.com_name = _com_name;
        this.per_name = _per_name;
        this.mobile = _mobile;
        this.email = _email;

    }

    @Override
    public int getCount() {
        return id.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ListViewHolder mHolder;

        LayoutInflater layoutInflater;

        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.display_summary_listview, null);
            mHolder = new ListViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ListViewHolder) convertView.getTag();
        }


        TextDrawable drawable = TextDrawable.builder()
                .buildRect(String.valueOf(com_name.get(position).charAt(0)), mColorGenerator.getRandomColor());

        TextDrawable color = TextDrawable.builder()
                .buildRect(" ", mColorGenerator.getColor("Selected"));

        mHolder.checkedIcon.setImageDrawable(color);
        mHolder.imageView.setImageDrawable(drawable);
        mHolder.companyname.setText(com_name.get(position));
        mHolder.personname.setText(per_name.get(position));
        mHolder.personphone.setText(mobile.get(position));
        mHolder.personemail.setText(email.get(position));
        return convertView;
    }

    public void remove(int index) {
        com_name.remove(index);
        per_name.remove(index);
        mobile.remove(index);
        email.remove(index);
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    public class ListViewHolder {
        View view;
        ImageView imageView;
        ImageView checkedIcon;
        TextView companyname;
        TextView personname;
        TextView personphone;
        TextView personemail;

        private ListViewHolder(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.imageView);
            checkedIcon = (ImageView) view.findViewById(R.id.checkIcon);
            companyname = (TextView) view.findViewById(R.id.companyname);
            personname = (TextView) view.findViewById(R.id.personname);
            personphone = (TextView) view.findViewById(R.id.personphone);
            personemail = (TextView) view.findViewById(R.id.personemail);
        }
    }


}
