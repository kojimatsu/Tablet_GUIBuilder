package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.listener;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.EditControlFragment;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.ScreenEditActivity;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.OutputWidget;

/**
 * Created by 浩司 on 2015/12/03.
 */
public class EditModeClickListener implements View.OnClickListener {

    private static View beforeClickedView = null;

    private EditControlFragment editControlFragment;

    public EditModeClickListener(Context context){
        ScreenEditActivity screenEditActivity = (ScreenEditActivity) context;
        editControlFragment = (EditControlFragment) screenEditActivity.getFragmentManager().findFragmentById(R.id.edit_control_fragment);
    }

    @Override
    public void onClick(View v) {
        if (beforeClickedView != null){
            if(beforeClickedView.getId() != v.getId()) {
                beforeClickedView.setBackgroundColor(Color.WHITE);
                v.setBackgroundResource(R.drawable.widget_click);
                editControlFragment.addSettingWidgetFragment(v.getId());
            }
        }else {
            v.setBackgroundResource(R.drawable.widget_click);
            editControlFragment.addSettingWidgetFragment(v.getId());
        }
        beforeClickedView = v;
    }
}
