package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_select;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.ScreenEditActivity;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.XMLReading;


public class ScreenSelectionActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_selection_activity);

        /*******************************************************************/
        Debug.createXML();
        XMLReading xmlReading = XMLReading.newInstance();
        xmlReading.testInit();
        /*******************************************************************/
        initDraw(xmlReading);
    }

    /**
     * 初回View描画．ルート及びルート直下のユースケース．
     */
    private void initDraw(XMLReading shareInformation){
        LinearLayout root = (LinearLayout) findViewById(R.id.screen_selection_activity_root_linearLayout);
        LinearLayout linearLayout = getLinearLayout();
        String rootUsecaseScreenName = shareInformation.getChildUseCaseNameList(null).get(0);
        TextView textView = getTextView(rootUsecaseScreenName);
        linearLayout.addView(textView);
        root.addView(linearLayout);
        List<String> childUsecaseScreenName = shareInformation.getChildUseCaseNameList(rootUsecaseScreenName);
        linearLayout = getLinearLayout();
        for (String name : childUsecaseScreenName) {

            linearLayout.addView(getTextView(name));
        }
        root.addView(linearLayout);
    }

    private LinearLayout getLinearLayout(){
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        return linearLayout;
    }

    private TextView getTextView(String text){
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setOnClickListener(this);
        return textView;
    }




    /**
     * 端末の横幅を取得
     * @return
     */
    protected int getDeviceWidth(){
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 端末の縦を取得
     * @return
     */
    protected int getDeviceHeight(){
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


    @Override
    public void onClick(View v) {
        TextView textView = (TextView) v;
        Intent intent = new Intent(getApplicationContext(),ScreenEditActivity.class);
        String usecaseName = (String) textView.getText();
        intent.putExtra(XMLReading.USECASE_NAME, usecaseName);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_project_change) {
            //selectProject();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
