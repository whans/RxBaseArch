package com.whans.platform.widget.autocomplete;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.whans.platform.R;
import com.whans.platform.core.BaseAdapterData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hanson.
 */

public class AutoCompleteAdapter extends BaseAdapter implements Filterable {
    private AutoCompleteDataHandler mAutoCompleteDataHandler;
    private List<BaseAdapterData> mFilterData;
    private List<BaseAdapterData> mOriginalData;
    private Context mContext;
    private CustomerFilter mCustomerFilter;

    public AutoCompleteAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (mFilterData != null) {
            return mFilterData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mFilterData == null) {
            return null;
        }
        return mFilterData.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_widget_autocomplete_item, parent, false);
        }
        ((TextView) view.findViewById(R.id.content_txt)).setText(mFilterData.get(position).getName());
        return view;
    }

    @Override
    public Filter getFilter() {
        if (mCustomerFilter == null) {
            mCustomerFilter = new CustomerFilter();
        }
        return mCustomerFilter;
    }

    public void setAutoCompleteDataHandler(AutoCompleteDataHandler dataHandler) {
        mAutoCompleteDataHandler = dataHandler;
    }

    public Object getSelectItem(int position) {
        if (mFilterData == null) {
            return null;
        }
        return mFilterData.get(position);
    }

    private class CustomerFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint == null) {
                if (mOriginalData == null && mAutoCompleteDataHandler != null) {
                    mOriginalData = mAutoCompleteDataHandler.getData(constraint);
                }
                if (mOriginalData != null) {
                    filterResults.values = mOriginalData;
                    filterResults.count = mOriginalData.size();
                }
            } else if (mOriginalData != null) {
                List<BaseAdapterData> mFilterData = new ArrayList<>();
                for (BaseAdapterData autoCompleteData : mOriginalData) {
                    if (autoCompleteData.getName() != null
                            && autoCompleteData.getName().contains(constraint)) {
                        mFilterData.add(autoCompleteData);
                    }
                }
                filterResults.values = mFilterData;
                filterResults.count = mFilterData.size();
            }
            
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results != null && results.count > 0) {
                mFilterData = (List<BaseAdapterData>) results.values;
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
