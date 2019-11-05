package ro.simonamihai.a2lei;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;

public class PopupMenu extends android.widget.PopupMenu {
    public PopupMenu(Context context, View anchor) {
        super(context, anchor);
    }

    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_expense:

                return true;

            default:
                return false;
        }
    }

}
