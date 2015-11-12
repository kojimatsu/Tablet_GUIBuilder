package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.ScreenEditActivity;

/**
 * Created by matsu on 2015/10/29.
 */
public class OutputWidget extends View implements View.OnClickListener{

    private boolean isOutputWidget = true;

    protected int widgetID;                 // ウィジェットの種類を判別するID
    private static int createCount = 0;     // OutputWidget（コンストラクタ）が実行された回数
    protected int uniqueID;                 // 固有のID（重複なし）
    protected LinearLayout view;                    // 自分自身 (クリック時の色変更の挙動のため、LinearLayout内に格納されている)

    protected OutputWidget(Context context, int widgetID) {
        super(context);
        this.widgetID = widgetID;
        uniqueID = createCount++;
    }

    protected OutputWidget(Context context, int widgetID, String uniqueID) {
        super(context);
        this.widgetID = widgetID;
        this.uniqueID = Integer.parseInt(uniqueID);
    }

    protected void setIsInputwidget(){
        isOutputWidget = false;
    }

    protected void setView(View view){
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOnClickListener(this);
        linearLayout.setId(uniqueID);
        linearLayout.addView(view);
        this.view = linearLayout;
    }

    public View getView(){
        return view;
    }

    public boolean isOutputWidget(){
        return isOutputWidget;
    }

    public int getWidgetID() {
        return widgetID;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(int uniqueID){
        this.uniqueID = uniqueID;
        createCount--;
    }

    public String getWidgetName(){
        return CreationWidgetController.getWidgetName(widgetID);
    }

    private static View beforeClickedView = null;

    @Override
    public void onClick(View v) {

        if (beforeClickedView != null){
            if(beforeClickedView.getId() != v.getId()) {
                beforeClickedView.setBackgroundColor(Color.WHITE);
                v.setBackgroundResource(R.drawable.widget_click);
                ScreenEditActivity screenEditActivity = (ScreenEditActivity) getContext();
                screenEditActivity.noticeFromOutputWidget();
            }
        }else {
            v.setBackgroundResource(R.drawable.widget_click);
            ScreenEditActivity screenEditActivity = (ScreenEditActivity) getContext();
            screenEditActivity.noticeFromOutputWidget();
        }
        beforeClickedView = v;

    }
}
