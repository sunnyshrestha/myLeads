package dev.suncha.myleads;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sunny on 5/16/2015.
 */
public class DisplayAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> _id;
    private ArrayList<String> _com_name;
    private ArrayList<String> _per_name;
    private ArrayList<String> _mobile;
    private ArrayList<String> _email;

    public DisplayAdapter(Context mContext, ArrayList<String> _id, ArrayList<String> _com_name, ArrayList<String> _per_name, ArrayList<String> _mobile, ArrayList<String> _email) {
        this.mContext = mContext;
        this._id = _id;
        this._com_name = _com_name;
        this._per_name = _per_name;
        this._mobile = _mobile;
        this._email = _email;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder mHolder;
        LayoutInflater layoutInflater;

        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.display_summary_listview, null);
            mHolder = new ListViewHolder();

            mHolder.companyname = (TextView) convertView.findViewById(R.id.companyname);
            mHolder.personname = (TextView) convertView.findViewById(R.id.personname);
            mHolder.personphone = (TextView) convertView.findViewById(R.id.personphone);
            mHolder.personemail = (TextView) convertView.findViewById(R.id.personemail);

            convertView.setTag(mHolder);
        } else {
            mHolder = (ListViewHolder) convertView.getTag();
        }

        mHolder.companyname.setText(_com_name.get(position));
        mHolder.personname.setText(_per_name.get(position));
        mHolder.personphone.setText(_mobile.get(position));
        mHolder.personemail.setText(_email.get(position));

        return convertView;
    }

    public class ListViewHolder {
        TextView companyname;
        TextView personname;
        TextView personphone;
        TextView personemail;
    }
}
