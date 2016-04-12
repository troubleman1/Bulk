package mspace.com.bulk;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by root on 4/12/16.
 */
public class CustomList extends ArrayAdapter<String> {

    private String[] ids;
    private String[] names;
    private String[] emails;
    private Activity context;


    public CustomList(Activity context, String[] ids, String[] names, String[] emails) {
        super(context, R.layout.list_view_layout, ids);
        this.context = context;
        this.ids = ids;
        this.names = names;
        this.emails = emails;

    }


}
