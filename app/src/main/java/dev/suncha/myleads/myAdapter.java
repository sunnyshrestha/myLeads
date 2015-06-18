package dev.suncha.myleads;
//https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView

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
    private Context context;
    private SparseBooleanArray mSelectedItemsIds;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     */
    public myAdapter(Context context,
                     ArrayList<myLeads> myLeadsList) {
        super(context, R.layout.display_summary_listview,myLeadsList);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.myLeadsList = myLeadsList;
        inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View view, ViewGroup parent) {
        //Get the data item for this position
        myLeads mylead = getItem(position);
        final ListViewHolder holder;
        if (view == null) {
            holder = new ListViewHolder();
            LayoutInflater inflater=LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.display_summary_listview, parent,false);

            //Locate the TextViews in layout;
            holder.companyname = (TextView) view.findViewById(R.id.companyname);
            holder.personname = (TextView) view.findViewById(R.id.personname);
            holder.personphone = (TextView) view.findViewById(R.id.personphone);
            holder.personemail = (TextView) view.findViewById(R.id.personemail);
            view.setTag(holder);

        } else {
            holder = (ListViewHolder) view.getTag();
        }

        holder.companyname.setText(mylead.ofic_name);
        holder.personname.setText(mylead.per_name);
        holder.personphone.setText(mylead.mobile);
        holder.personemail.setText(mylead.email);
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

    private static class ListViewHolder {
        TextView companyname;
        TextView personname;
        TextView personphone;
        TextView personemail;
    }

}
