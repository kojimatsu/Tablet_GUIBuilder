package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.execution.ExecutionActivity;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.OutputWidget;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformationManager;

/**
 * Created by matsu on 2014/11/07.
 */
public class ScreenEditActivity extends Activity {

    public static String useCaseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_edit_activity);
        EditControlFragment editControlFragment = (EditControlFragment) getFragmentManager().findFragmentById(R.id.edit_control_fragment);
        Intent intent = getIntent();
        useCaseName = intent.getStringExtra(ShareInformationManager.ATTRIBUTE_NAME);
        editControlFragment.addScreenFragment(useCaseName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // メニューの要素を追加して取得
        MenuItem actionItem = menu.add("Execution");
        // アイコンを設定
        actionItem.setIcon(R.drawable.ic_media_play);
        // SHOW_AS_ACTION_ALWAYS:常に表示
        actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(),ExecutionActivity.class);
        startActivity(intent);
        return true;
    }
}
