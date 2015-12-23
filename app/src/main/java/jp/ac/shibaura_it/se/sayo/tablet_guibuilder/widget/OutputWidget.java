package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.listener.EditModeClickListener;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.listener.ExecutionModeClickListener;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.Mode;

/**
 * Created by matsu on 2015/10/29.
 */
public class OutputWidget extends View {

    private WidgetType widgetType = WidgetType.OUTPUT;

    protected int widgetID;                 // ウィジェットの種類を判別するID
    private static int createCount = 0;     // OutputWidget（コンストラクタ）が実行された回数
    protected int uniqueID;                 // 固有のID（重複なし）
    protected LinearLayout view;                    // 自分自身 (クリック時の色変更の挙動のため、LinearLayout内に格納されている)

    public OutputWidget(Context context, Mode mode, int widgetID, int uniqueID, View createView) {
        super(context);
        this.widgetID = widgetID;
        if (uniqueID == -1){
            this.uniqueID = createCount++;
        }else {
            this.uniqueID = uniqueID;
        }
        setView(mode,createView);
    }

    protected void setIsInputWidget(){
        widgetType = WidgetType.INPUT;
    }

    private void setView(Mode mode, View view){
        LinearLayout linearLayout = new LinearLayout(getContext());
        if (mode == Mode.EDITION){
            linearLayout.setOnClickListener(new EditModeClickListener(getContext()));
            linearLayout.setId(uniqueID);
        }else if(mode == Mode.EXECUTION){
            view.setOnClickListener(new ExecutionModeClickListener(getContext()));
            view.setId(uniqueID);
        }
        linearLayout.addView(view);
        this.view = linearLayout;
    }

    public View getView(){
        return view;
    }

    public View getVisualView(){
        return view.getChildAt(0);
    }

    public WidgetType getWidgetType(){
        return widgetType;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public String getWidgetName(){
        return CreationWidgetController.getWidgetName(widgetID);
    }

    public boolean setText(String text) {
        View view = getVisualView();
        if (view instanceof TextView){
            TextView textView = (TextView) view;
            if ( !text.equals("") ){
                textView.setText(text);
                return true;
            }
        }
        return false;
    }
}
