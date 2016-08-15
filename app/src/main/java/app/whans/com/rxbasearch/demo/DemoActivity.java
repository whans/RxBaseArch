package app.whans.com.rxbasearch.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.whans.platform.core.BaseAdapterData;
import com.whans.platform.widget.AutoCompleteEditText;
import com.whans.platform.widget.autocomplete.AutoCompleteDataHandler;

import java.util.ArrayList;
import java.util.List;

import app.whans.com.rxbasearch.R;
import app.whans.com.rxbasearch.core.BaseActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hanson.
 */

public class DemoActivity extends BaseActivity {
    @BindView(R.id.autocomplete_edt)
    AutoCompleteEditText mAutocompleteEdt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        ButterKnife.bind(this);

        mAutocompleteEdt.setThreshold(2);
        mAutocompleteEdt.setAutoCompleteDataHandler(new AutoCompleteDataHandler() {
            @Override
            public List<BaseAdapterData> getData(CharSequence filter) {
                List testDatas = new ArrayList<>();

                AutoCompleteData data = new AutoCompleteData("first");
                testDatas.add(data);
                data = new AutoCompleteData("second");
                testDatas.add(data);
                data = new AutoCompleteData("third");
                testDatas.add(data);

                return testDatas;
            }

            @Override
            public void onItemSelect(BaseAdapterData data) {
                Logger.d(data.getName());
            }
        });

    }

    public class AutoCompleteData implements BaseAdapterData {
        private String mName;

        AutoCompleteData(String mName) {
            this.mName = mName;
        }

        @Override
        public String getName() {
            return mName;
        }
    }
}
