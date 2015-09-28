package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;

/**
 * Created by matsu on 2014/11/07.
 */
public class ScreenEditActivity extends Activity
        implements WidgetSelectionFragment.WidgetClickListener
{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    /**
     * 通知方向を逆にしたほうが効率的，今度修正
     * WidgetSelectionFragmentから通知
     */
    @Override
    public void widgetClicked(int viewId) {
        EditControlFragment editControlFragment = (EditControlFragment) getFragmentManager().findFragmentById(R.id.edit_control_fragment);
        editControlFragment.notice(viewId);
    }

}