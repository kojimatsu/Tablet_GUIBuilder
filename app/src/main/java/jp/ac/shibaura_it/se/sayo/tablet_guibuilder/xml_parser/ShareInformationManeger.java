package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser;

import android.os.Environment;
import android.widget.LinearLayout;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.Debug;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.CreationWidgetController;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.OutputWidget;

/**
 * Created by 浩司 on 2015/11/06.
 */
public class ShareInformationManeger extends XMLWriting {

    protected static Document shareInformation;         // 共通設計情報
    protected static String shareInformationPath;          // 共通設計情報のパス

    // 共通設計情報（XMLファイル）の要素名や属性名
    public final static String TAG_USECASE = "UseCaseScreen";
    public final static String TAG_WIDGET = "Widget";
    public final static String TAG_GESTURE = "Gesture";

    public final static String ATTRIBUTE_TYPE = "type";
    public final static String ATTRIBUTE_NAME = "name";
    public final static String ATTRIBUTE_ID = "id";

    public final static String ATTRIBUTE_VALUE_INPUT = "Input";
    public final static String ATTRIBUTE_VALUE_OUTPUT = "Output";

    public static ShareInformationManeger newInstance(){
        testInit();
        return new ShareInformationManeger();
    }

    /**
     * テスト用の準備。
     * 実際はどこかのサーバ等に置いた共通設計情報を取得する処理に変わる。
     * 今回はテス地のため、端末内のSDカードから取得する
     */
    public static void testInit(){
        shareInformationPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Debug.testPath;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream inputStream = new FileInputStream(shareInformationPath);
            shareInformation = documentBuilder.parse(inputStream);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * useCaseName(ユースケース)を親に持つ,ユースケース(子)の名前を全て返す．
     * nullの場合はルートユースケース名を返す．
     * 追記:全てではなくルートユースケースの直下のみだった
     * @param useCaseName
     * @return
     */
    public List<String> getChildUseCaseNameList(String useCaseName) {
        List<String> useCaseNameList = new ArrayList<String>();
        if (useCaseName == null) {
            Element element = shareInformation.getDocumentElement();
            useCaseNameList.add(getAttribute(element, ATTRIBUTE_NAME));
            return useCaseNameList;
        }
        List<Element> elementList = getChildElementList(shareInformation, ATTRIBUTE_NAME, useCaseName);
        for (int i = 0; i < elementList.size(); i++) {
            useCaseNameList.add(getAttribute(elementList.get(i),ATTRIBUTE_NAME));
        }
        return useCaseNameList;
    }


    /**
     * Widgetを配置した時、共通設計情報に書き込む
     * @param useCaseName
     * @param view
     */
    public void writeWidget(String useCaseName, OutputWidget view) {
        Element widgetElement = shareInformation.createElement(TAG_WIDGET);
        if (view.isOutputWidget()){
            widgetElement.setAttribute(ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_OUTPUT);
        }else {
            widgetElement.setAttribute(ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_INPUT);
        }
        widgetElement.setAttribute(ATTRIBUTE_NAME, view.getWidgetName());
        widgetElement.setAttribute(ATTRIBUTE_ID, String.valueOf(view.getUniqueID()));
        if (!view.isOutputWidget()){
            Element gestureElement = shareInformation.createElement(TAG_GESTURE);
            gestureElement.setAttribute(ATTRIBUTE_NAME, "Tap");
            widgetElement.appendChild(gestureElement);
        }

        Element targetElement = getElement(shareInformation.getDocumentElement(), ATTRIBUTE_NAME, useCaseName);
        targetElement.appendChild(widgetElement);
        write(shareInformation, shareInformationPath);
    }


    /**
     * 共通設計情報に基づいてViewを再生成する
     * @param root
     * @param useCaseName
     * @return
     */
    public LinearLayout getScreen(LinearLayout root, String useCaseName) {
        List<Element> elementList = getChildElementList(shareInformation, ATTRIBUTE_NAME, useCaseName);
        for (int i = 0; i < elementList.size(); i++) {
            Element element = elementList.get(i);
            if (element.getTagName().equals(TAG_WIDGET)){
                int uniqueID = Integer.parseInt(element.getAttribute(ATTRIBUTE_ID));
                int widgetID = CreationWidgetController.getWidgetID(shareInformation, uniqueID);
                OutputWidget view = CreationWidgetController.createWidget(root.getContext(), widgetID, uniqueID);
                root.addView(view.getView());
            }
        }
        return root;
    }



}
