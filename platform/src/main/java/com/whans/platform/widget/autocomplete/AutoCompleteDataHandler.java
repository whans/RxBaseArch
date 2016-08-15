package com.whans.platform.widget.autocomplete;

import com.whans.platform.core.BaseAdapterData;

import java.util.List;

/**
 * @author hanson.
 */

public interface AutoCompleteDataHandler {
    List<BaseAdapterData> getData(CharSequence filter);

    void onItemSelect(BaseAdapterData data);
}
