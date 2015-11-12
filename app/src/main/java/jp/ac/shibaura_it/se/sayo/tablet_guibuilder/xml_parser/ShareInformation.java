package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser;

import android.os.Environment;
import android.view.View;
import android.widget.LinearLayout;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.R;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.GenerationWidget;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.OutputWidget;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.Widget;

/**
 * Created by 浩司 on 2015/11/06.
 */
public class ShareInformation extends XMLWriting {

    // 共通設計情報（XMLファイル）の要素名や属性名
    public final static String TAG_USECASE = "UseCaseScreen";
    public final static String TAG_WIDGET = "Widget";
    public final static String TAG_GESTURE = "Gesture";

    public final static String ATTRIBUTE_TYPE = "type";
    public final static String ATTRIBUTE_NAME = "name";
    public final static String ATTRIBUTE_ID = "id";

    public final static String ATTRIBUTE_VALUE_INPUT = "Input";
    public final static String ATTRIBUTE_VALUE_OUTPUT = "Output";

    protected static Document shareInf_XML;                // 共通設計情報
    private static String filePath;                         // 共通設計情報の絶対パス

    /**
     * テスト用の準備。
     * 実際はどこかのサーバ等に置いた共通設計情報を取得する処理に変わる。
     * 今回はテス地のため、端末内のSDカードから取得する
     */
    public static void testInit(){
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Debug.testPath;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream inputStream = new FileInputStream(filePath);
            shareInf_XML = documentBuilder.parse(inputStream);
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
     * @param useCaseName
     * @return
     */
    public static List<String> getChildUseCaseNameList(String useCaseName) {
        Element rootElement = shareInf_XML.getDocumentElement();
        List<String> useCaseNameList = new ArrayList<String>();
        if (useCaseName == null) {
            useCaseNameList.add(getAttribute(rootElement, ATTRIBUTE_NAME));
            return useCaseNameList;
        }
        NodeList nodeList = rootElement.getElementsByTagName(TAG_USECASE);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            Node parentNode = childNode.getParentNode();
            if (getAttribute(parentNode, ATTRIBUTE_NAME).equals(useCaseName)) {
                useCaseNameList.add(getAttribute(childNode, ATTRIBUTE_NAME));
            }
        }
        return useCaseNameList;
    }


    /**
     * Widgetを配置した時、共通設計情報に書き込む
     * @param usecaseName
     * @param view
     */
    public static void writeWidget(String usecaseName, OutputWidget view) {
        Element rootElement = shareInf_XML.getDocumentElement();
        if (getAttribute(rootElement, ATTRIBUTE_NAME).equals(usecaseName)){
            setDocument(rootElement, view);
        }else {
            NodeList nodeList = rootElement.getElementsByTagName(TAG_USECASE);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node childNode = nodeList.item(i);
                if (getAttribute(childNode, ATTRIBUTE_NAME).equals(usecaseName)) {
                    setDocument(childNode, view);
                }
            }
        }
    }

    private static void setDocument(Node node, OutputWidget view){
        Element tagWidget = shareInf_XML.createElement(TAG_WIDGET);
        if (view.isOutputWidget()){
            tagWidget.setAttribute(ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_OUTPUT);
        }else {
            tagWidget.setAttribute(ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_INPUT);
        }
        tagWidget.setAttribute(ATTRIBUTE_NAME, getWidget(view));
        tagWidget.setAttribute(ATTRIBUTE_ID, String.valueOf(view.getUniqueID()));
        if (!view.isOutputWidget()){
            Element tagGesture = shareInf_XML.createElement(TAG_GESTURE);
            tagGesture.setAttribute(ATTRIBUTE_NAME,"Tap");
            tagWidget.appendChild(tagGesture);
        }
        node.appendChild(tagWidget);
        write(shareInf_XML,filePath);
    }
//
    private static String getWidget(OutputWidget view){
        int widgetID = view.getWidgetID();
        if (widgetID == R.id._button){
            return Widget.BUTTON.name();
        }else if(widgetID == R.id._label){
            return Widget.LABEL.name();
        }else {
            return null;
        }

    }

    public static LinearLayout getScreen(LinearLayout root, String usecaseName) {
        Element rootElement = shareInf_XML.getDocumentElement();
        if (getAttribute(rootElement, ATTRIBUTE_NAME).equals(usecaseName)){
            root = setScreen(root,usecaseName);
        }else {
            NodeList nodeList = rootElement.getElementsByTagName(TAG_USECASE);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node childNode = nodeList.item(i);
                if (getAttribute(childNode, ATTRIBUTE_NAME).equals(usecaseName)) {
                    root = setScreen(root,usecaseName);
                }
            }
        }
        return root;
    }

    private static LinearLayout setScreen(LinearLayout root, String usecaseName){
        Element rootElement = shareInf_XML.getDocumentElement();
        NodeList nodeList = rootElement.getElementsByTagName(TAG_WIDGET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            Node parentNode = childNode.getParentNode();
            if (getAttribute(parentNode, ATTRIBUTE_NAME).equals(usecaseName)) {
                Element element = (Element) childNode;
                View view = GenerationWidget.createWidget(root.getContext(),element.getAttribute(ATTRIBUTE_NAME), element.getAttribute(ATTRIBUTE_ID));
                root.addView(view);
            }
        }
        return root;
    }

}
