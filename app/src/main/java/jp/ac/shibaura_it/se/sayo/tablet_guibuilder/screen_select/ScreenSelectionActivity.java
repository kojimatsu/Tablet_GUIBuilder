package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_select;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.Debug;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.execution.ExecutionActivity;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.ScreenEditActivity;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformationManager;


public class ScreenSelectionActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_selection_activity);

        /*******************************************************************/
        Debug.createXML();
        ShareInformationManager.testInit();
        /*******************************************************************/
        initDraw();
    }

    /**
     * 初回View描画．ルート及びルート直下のユースケース．
     */
    private void initDraw(){
        LinearLayout root = (LinearLayout) findViewById(R.id.screen_selection_activity_root_linearLayout);
        LinearLayout linearLayout = createLinearLayout();
        ShareInformationManager shareInformationManager = ShareInformationManager.newInstance();
        String rootUsecaseScreenName = shareInformationManager.getRootUseCase();
        TextView textView = createTextView(rootUsecaseScreenName);
        linearLayout.addView(textView);
        root.addView(linearLayout);
        List<String> childUsecaseScreenName = shareInformationManager.getChildUseCaseNameList(rootUsecaseScreenName);
        linearLayout = createLinearLayout();
        for (String name : childUsecaseScreenName) {
            linearLayout.addView(createTextView(name));
        }
        root.addView(linearLayout);
    }

    private LinearLayout createLinearLayout(){
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        return linearLayout;
    }

    private TextView createTextView(String text){
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setOnClickListener(this);
        return textView;
    }


    @Override
    public void onClick(View v) {
        TextView textView = (TextView) v;
        Intent intent = new Intent(getApplicationContext(),ScreenEditActivity.class);
        String useCaseName = (String) textView.getText();
        intent.putExtra(ShareInformationManager.ATTRIBUTE_NAME, useCaseName);
        startActivity(intent);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.my, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_project_change) {
//            //selectProject();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


}
