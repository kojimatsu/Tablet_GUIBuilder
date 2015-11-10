package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.Debug;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.OutputWidget;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformation;

/**
 * Created by matsu on 2014/11/07.
 */
public class ScreenEditActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_edit_activity);
        EditControlFragment editControlFragment = (EditControlFragment) getFragmentManager().findFragmentById(R.id.edit_control_fragment);
        Intent intent = getIntent();
        String usecaseName = intent.getStringExtra(ShareInformation.ATTRIBUTE_NAME);
        editControlFragment.addScreenFragment(usecaseName);
    }

    public OutputWidget onClickedFromScreenFragment() {
        WidgetSelectionFragment widgetSelectionFragment = (WidgetSelectionFragment) getFragmentManager().findFragmentById(R.id.wsFragment);
        OutputWidget clickedView = widgetSelectionFragment.createView();
        return clickedView;
    }

    public void noticeFromOutputWidget() {
        EditControlFragment editControlFragment = (EditControlFragment) getFragmentManager().findFragmentById(R.id.edit_control_fragment);
        editControlFragment.addSettingWidgetFragment();

    }
}
