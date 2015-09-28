package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_select;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.Debug;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.XMLReading;

/**
 * Created by matsu on 2014/10/21.
 */
public class Screen extends LinearLayout {

    private final int SCREEN_SIZE_WIDTH = 12;
    private final int SCREEN_SIZE_HEIGHT = 5;

    private Point start;     // draw開始座標
    private Point end;       // draw終了座標

    private String screenName;      // 自身のユースケース名
    private List<Screen> children;  // 子スクリーンリスト

    private static XMLReading xmlReading;

    protected Screen(Context context, XMLReading xmlReading) {
        super(context);
        this.xmlReading = xmlReading;
        String rootUseCaseName = xmlReading.getChildUseCaseNameList(null).get(0);
        setScreen(rootUseCaseName);
    }

    private Screen(Context context, String useCaseName) {
        super(context);
        setScreen(useCaseName);
    }

    private void setScreen(String screenName){
        setId(View.generateViewId());
        setOrientation(LinearLayout.VERTICAL);
        setOnClickListener((ScreenSelectionActivity) getContext());
        // 画面の表示
        setScreenImage();
        // ユースケース名の表示
        setScreenName(screenName);
        // 再帰的に子を生成
        children = new ArrayList<Screen>();
        List<String> childUseCaseNameList  = xmlReading.getChildUseCaseNameList(this.screenName);
        if(!childUseCaseNameList.isEmpty()){
            for(String childUseCaseName : childUseCaseNameList){
                children.add(new Screen(getContext(),childUseCaseName));
            }
        }
    }

    private void setScreenImage(){
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(R.drawable.border);
        // 画面の画像を挿入．ない場合は背景白
        imageView.setBackgroundColor(Color.WHITE);
        ScreenSelectionActivity activity = (ScreenSelectionActivity) getContext();
        // いつか動的にサイズ処理する
        //addView(imageView, new LayoutParams(activity.getDeviceWidth() / SCREEN_SIZE_WIDTH, activity.getDeviceHeight() / SCREEN_SIZE_HEIGHT));
        addView(imageView, new LayoutParams(120,160));
    }



    private void setScreenName(String screenName) {
        this.screenName = screenName;
        TextView textView = new TextView(getContext());
        textView.setText(screenName);
        // いつか動的にサイズ処理する
        textView.setTextSize(5);
        addView(textView);
    }

    protected String getScreenName() {
        return this.screenName;
    }

    protected void setPoint() {
        Activity activity = (Activity) getContext();
        RelativeLayout root = (RelativeLayout) activity.findViewById(R.id.screen_selection_root_relativeLayout);
        ImageView imageView = (ImageView) getChildAt(0);
        int[] location = new int[2];
        imageView.getLocationOnScreen(location);
        int padding = root.getPaddingLeft();
        start = new Point(location[0] - padding + imageView.getWidth(),
                location[1]);
        end = new Point(location[0] - padding,
                location[1]);
    }

    protected Point getStart() {
        return start;
    }

    protected Point getEnd() {
        return end;
    }

    protected List<Screen> getChildren() {
        return children;
    }

}