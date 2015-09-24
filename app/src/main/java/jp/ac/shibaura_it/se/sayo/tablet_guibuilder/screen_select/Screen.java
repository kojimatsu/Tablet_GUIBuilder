package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_select;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.DropBoxConnection;
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

    protected Screen(Context context, String useCaseName) {
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
        XMLReading xmlReading = new XMLReading(DropBoxConnection.getShareInfoPath());
        List<String> childUseCaseNameList = xmlReading.getChildUseCaseNameList(this.screenName);
        for(String childUseCaseName : childUseCaseNameList){
            children.add(new Screen(getContext(),childUseCaseName));
        }
    }

    private void setScreenImage(){
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(R.drawable.border);
        // 画面の画像を挿入．ない場合は背景白
        imageView.setBackgroundColor(Color.WHITE);
        ScreenSelectionActivity activity = (ScreenSelectionActivity) getContext();
        addView(imageView, new LayoutParams(activity.getDeviceWidth() / SCREEN_SIZE_WIDTH, activity.getDeviceHeight() / SCREEN_SIZE_HEIGHT));
    }



    private void setScreenName(String screenName) {
        this.screenName = screenName;
        TextView textView = new TextView(getContext());
        textView.setText(screenName);
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