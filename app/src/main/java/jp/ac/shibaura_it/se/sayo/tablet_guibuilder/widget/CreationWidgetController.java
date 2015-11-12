package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.Debug;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformationManeger;

/**
 * Created by 浩司 on 2015/11/10.
 *
 * VIewを新たに実装したらWidget(Enum)と本クラス内のif文を追加する
 */
public class CreationWidgetController {

    /**
     * 画面設計中のViewの追加処理で呼ばれる
     * @param activity
     * @param widgetID
     * @return
     */
    public static OutputWidget createWidget(Context activity, int widgetID) {
        OutputWidget view = null;
        if (widgetID == R.id._button){
            view = new MyButton(activity);
        }else if (widgetID == R.id._label){
            view = new MyLabel(activity);
        }
        return view;
    }

    /**
     * 共通設計情報からViewを再生成する時に呼ばれる
     * @param context
     * @param uniqueID
     * @return
     */
    public static OutputWidget createWidget(Context context, int widgetID, int uniqueID) {
        OutputWidget view = createWidget(context, widgetID);
        view.setUniqueID(uniqueID);
        return view;
    }

    public static int getWidgetID(Document shareInformation, int uniqueID){
        String attrValue = String.valueOf(uniqueID);
        ShareInformationManeger shareInformationManeger = ShareInformationManeger.newInstance();
        Element element = shareInformationManeger.getElement(shareInformation.getDocumentElement(), ShareInformationManeger.ATTRIBUTE_ID, attrValue);
        if (shareInformationManeger.getAttribute(element, ShareInformationManeger.ATTRIBUTE_NAME).equals(Widget.BUTTON.name())){
            return R.id._button;
        }else if(shareInformationManeger.getAttribute(element, ShareInformationManeger.ATTRIBUTE_NAME).equals(Widget.LABEL.name())){
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
}
