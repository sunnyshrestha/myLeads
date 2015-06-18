package dev.suncha.myleads;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny on 6/18/2015.
 */
public class myAdapter extends ArrayAdapter<myLeads> {
    LayoutInflater inflater;
    List<myLeads> myLeadsList;
    private Context mContext;
    private ArrayList<String> id;
    private ArrayList<String> com_name;
    private ArrayList<String> per_name;
    private ArrayList<String> mobile;
    private ArrayList<String> email;
    private SparseBooleanArray mSelectedItemsIds;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     */
    public myAdapter(Context context, int resource,
                     List<myLeads> myLeadsList) {
        super(context, resource);
        mSelectedItemsIds = new SparseBooleanArray();
        this.mContext = context;
        this.myLeadsList = myLeadsList;
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View view, ViewGroup parent) {
        final ListViewHolder holder;
        if (view == null) {
            holder = new ListViewHolder();
            view = inflater.inflate(R.layout.display_summary_listview, null);
            //Locate the TextViews in layout;
            holder.companyname = (TextView) view.findViewById(R.id.companyname);
            holder.personname = (TextView) view.findViewById(R.id.personname);
            holder.personphone = (TextView) view.findViewById(R.id.personphone);
            holder.personemail = (TextView) view.findViewById(R.id.personemail);

            view.setTag(holder);
        } else {
            holder = (ListViewHolder) view.getTag();
        }
        return view;
    }

    /**
     * Removes the specified object from the array.
     *
     * @param object The object to remove.
     */
    @Override
    public void remove(myLeads object) {
        myLeadsList.remove(object);
        notifyDataSetChanged();
    }

    public List<myLeads> getMyLeadsList() {
        return myLeadsList;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getmSelectedItemsIds() {
        return mSelectedItemsIds;
    }

    private class ListViewHolder {
        TextView companyname;
        TextView personname;
        TextView personphone;
        TextView personemail;
    }

}
