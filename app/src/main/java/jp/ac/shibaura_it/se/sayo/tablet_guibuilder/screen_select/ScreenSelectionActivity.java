package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_select;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.Debug;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.ScreenEditActivity;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.XMLReading;


public class ScreenSelectionActivity extends Activity implements View.OnClickListener {

    private final static int BETWEEN_SCREEN_SPACE_X = 10;

    private Screen rootScreen;    // ルートユースケースと対応するScreen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.createXML();
        XMLReading xmlReading = XMLReading.newInstance();
        xmlReading.testInit();
        drawTree(xmlReading);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setPoint(rootScreen);
        drawLine(rootScreen);
    }

    /**
     * 画面間に線を描画すためにの座標を取得し設定する.
     * 描画が完了したonWindowFocusChangedで実行する
     * @param screen
     */
    private void setPoint(Screen screen) {
        screen.setPoint();
        List<Screen> childScreenList = screen.getChildren();
        for (Screen childScreen : childScreenList) {
            setPoint(childScreen);
        }
    }

    /**
     * 画面間に線を引く
     */
    private void drawLine(Screen screen){
        RelativeLayout root = (RelativeLayout) findViewById(R.id.screen_selection_root_relativeLayout);
        Point start = screen.getStart();
        List<Screen> childScreenList = screen.getChildren();
        for (Screen childScreen : childScreenList) {
            Point end = childScreen.getEnd();
            Arrow arrow = new Arrow(this,start,end);
            root.addView(arrow);
            drawLine(childScreen);
        }
    }


    /**
     * スクリーンをツリー状に描画する
     * @param xmlReading
     */
    protected void drawTree(XMLReading xmlReading) {
        rootScreen = new Screen(this,xmlReading);
        setContentView(R.layout.screen_selection_activity);
        drawScreen(null);
    }

    /**
     * スクリーンを再帰的に描画する。
     * @param sameLevelScreenList
     */
    private void drawScreen(List<Screen> sameLevelScreenList){
        LinearLayout root = (LinearLayout) findViewById(R.id.screen_selection_root_linearLayout);
        LinearLayout screenGroup = null;
        if (sameLevelScreenList == null){    // 初回
            sameLevelScreenList = new ArrayList<Screen>();
            sameLevelScreenList.add(rootScreen);
            screenGroup = getScreenGroup(sameLevelScreenList);
            root.addView(screenGroup);
        }
        List<Screen> newSameLevelScreenList = new ArrayList<Screen>();
        for (Screen sameLevelScreen : sameLevelScreenList){
            newSameLevelScreenList.addAll(sameLevelScreen.getChildren());
        }
        screenGroup = getScreenGroup(newSameLevelScreenList);
        root.addView(screenGroup);
        if(!newSameLevelScreenList.isEmpty()){
            drawScreen(newSameLevelScreenList);
        }
    }

    /**
     * screenListを1つのLinearLayoutに挿入し，取得
     * @param screenList
     * @return
     */
    private LinearLayout getScreenGroup(List<Screen> screenList){
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);   // 垂直方向中央が効いてない
        linearLayout.setId(View.generateViewId());
        linearLayout.setPadding(getDeviceWidth() / BETWEEN_SCREEN_SPACE_X, 0, 0, 0);
        for (Screen screen : screenList){
            linearLayout.addView(screen);
        }
        return  linearLayout;
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
        Screen screen = (Screen) v;
        Intent intent = new Intent(ScreenSelectionActivity.this, ScreenEditActivity.class);
        intent.putExtra("NAME",screen.getScreenName()); // ユースケース名
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
