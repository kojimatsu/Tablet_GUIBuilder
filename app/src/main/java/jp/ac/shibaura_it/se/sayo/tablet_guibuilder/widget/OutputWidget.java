package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Created by matsu on 2015/10/29.
 */
public class OutputWidget extends View {

    private boolean isOutputWidget = true;

    protected int widgetID;                 // ウィジェットを判別する程度のID
    private static int createCount = 0;     // OutputWidget（コンストラクタ）が実行された回数
    protected int uniqueID;                 // 固有のID（重複なし）
    protected View view = null;             // 自分自身を表すView

    protected OutputWidget(Context context, View clickedView) {
        super(context);
        widgetID = clickedView.getId();
        uniqueID = createCount++;
    }

    protected void setIsInputwidget(){
        isOutputWidget = false;
    }

    public View getView(){
        return view;
    }
}
