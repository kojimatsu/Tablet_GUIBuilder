package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.Mode;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformationManager;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.XMLWriting;

/**
 * Created by 浩司 on 2015/11/10.
 *
 * Widgetを新たに実装したらWidget(Enumクラス)と本クラス内のif文を追加する。
 * つまり、新しいWidgetの追加による影響は上記に上げた2クラスのみ
 */
public class CreationWidgetController {

    /**
     * 画面上のViewを生成する際に呼ばれる。
     * 1.画面編集時
     * 2.共通設計情報を基にしたView描画
     * @param mode
     * @param context
     * @param widgetID
     * @param uniqueID -1の時はパターン1
     * @return
     */
    public static OutputWidget createWidget(Mode mode, Context context, int widgetID, int uniqueID) {
        OutputWidget outputWidget = null;
        if (widgetID == R.id._button){
            outputWidget = new InputWidget(context, mode, widgetID, uniqueID, createButton(context));
        }else if (widgetID == R.id._label){
            outputWidget = new OutputWidget(context, mode, widgetID, uniqueID, createTextView(context));
        }
        return outputWidget;
    }

    public static OutputWidget createWidget(Mode mode, Context context, String label, int widgetID, int uniqueID) {
        OutputWidget outputWidget = createWidget(mode, context, widgetID, uniqueID);
        TextView textView = (TextView) outputWidget.getVisualView();
        textView.setText(label);
        return outputWidget;
    }

    public static int getWidgetID(int uniqueID){
        String uniqueIDStr = String.valueOf(uniqueID);
        ShareInformationManager shareInformationManager = ShareInformationManager.newInstance();
        Element widget = shareInformationManager.getElement(XMLWriting.ATTRIBUTE_ID, uniqueIDStr);
        if (shareInformationManager.getAttributeValue(widget, ShareInformationManager.ATTRIBUTE_NAME).equals(Widget.BUTTON.name())){
            return R.id._button;
        }else if(shareInformationManager.getAttributeValue(widget, ShareInformationManager.ATTRIBUTE_NAME).equals(Widget.LABEL.name())){
            return R.id._label;
        }
        return -1;
    }


    public static String getWidgetName(int widgetID) {
        if (widgetID == R.id._button){
            return Widget.BUTTON.name();
        }else if(widgetID == R.id._label){
            return Widget.LABEL.name();
        }else {
            return null;
        }
    }

    private static View createButton(Context context){
        Button button = new Button(context);
        button.setText("ボタン");
        button.setClickable(false);
        return button;
    }

    private static View createTextView(Context context){
        TextView textView = new TextView(context);
        textView.setText("ラベル");
        return textView;
    }


}
