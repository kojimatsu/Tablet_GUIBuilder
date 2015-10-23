package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.XMLReading;

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
        String usecaseName = intent.getStringExtra(XMLReading.USECASE_NAME);
        editControlFragment.setFileName(usecaseName);
    }

    public View onClickedFromScreenFragment() {
        WidgetSelectionFragment widgetSelectionFragment = (WidgetSelectionFragment) getFragmentManager().findFragmentById(R.id.wsFragment);
        View clickedView = widgetSelectionFragment.getClickedView();
        return clickedView;
    }
}
