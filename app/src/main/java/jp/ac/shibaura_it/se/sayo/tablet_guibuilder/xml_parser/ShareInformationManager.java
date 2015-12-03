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
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.Mode;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.CreationWidgetController;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.Gesture;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.OutputWidget;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.WidgetType;

/**
 * Created by 浩司 on 2015/11/06.
 */
public class ShareInformationManager extends XMLWriting {

    protected static Document shareInformation;         // 共通設計情報
    protected static String shareInformationPath;          // 共通設計情報のパス

    // 共通設計情報（XMLファイル）の要素名や属性名
    public final static String TAG_USECASE = "UseCaseScreen";
    public final static String TAG_WIDGET = "Widget";
    public final static String TAG_GESTURE = "Gesture";

    public final static String ATTRIBUTE_TYPE = "type";
    public final static String ATTRIBUTE_NAME = "name";
    public final static String ATTRIBUTE_ID = "id";

    public final static String ATTRIBUTE_VALUE_INPUT = WidgetType.INPUT.name();
    public final static String ATTRIBUTE_VALUE_OUTPUT = WidgetType.OUTPUT.name();
    public final static String ATTRIBUTE_VALUE_MOVE = "move";

    public static ShareInformationManager newInstance(){
        testInit();
        return new ShareInformationManager();
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
     * 追記:指定したユースケースの直下のみ
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
        List<Element> elementList = getChildUseCaseList(useCaseName);
        for (int i = 0; i < elementList.size(); i++) {
            Element element = elementList.get(i);
            useCaseNameList.add(getAttribute(element,ATTRIBUTE_NAME));
        }
        return useCaseNameList;
    }

    /**
     * useCaseName(ユースケース)を親に持つ,ユースケース(子)の名前を全て返す．
     * nullの場合はルートユースケース名を返す．
     * 追記:指定したユースケースの直下のみ
     * @param useCaseName
     * @return
     */
    private List<Element> getChildUseCaseList(String useCaseName) {
        List<Element> childUseCaseList = new ArrayList<Element>();
        List<Element> elementList = getChildElementList(shareInformation, ATTRIBUTE_NAME, useCaseName);
        for (int i = 0; i < elementList.size(); i++) {
            Element element = elementList.get(i);
            if (element.getTagName().equals(ShareInformationManager.TAG_USECASE)){
                childUseCaseList.add(element);
            }
        }
        return childUseCaseList;
    }


    /**
     * Widgetを配置した時、共通設計情報に書き込む
     * @param useCaseName
     * @param view
     */
    public void writeWidget(String useCaseName, OutputWidget view) {
        Element widgetElement = shareInformation.createElement(TAG_WIDGET);
        if (view.getWidgetType() == WidgetType.OUTPUT){
            widgetElement.setAttribute(ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_OUTPUT);
        }else if(view.getWidgetType() == WidgetType.INPUT){
            widgetElement.setAttribute(ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_INPUT);
        }
        widgetElement.setAttribute(ATTRIBUTE_NAME, view.getWidgetName());
        widgetElement.setAttribute(ATTRIBUTE_ID, String.valueOf(view.getUniqueID()));
        if (view.getWidgetType() == WidgetType.INPUT){
            Element gestureElement = shareInformation.createElement(TAG_GESTURE);
            gestureElement.setAttribute(ATTRIBUTE_NAME, Gesture.Tap.name());
            widgetElement.appendChild(gestureElement);
        }
        Element targetElement = getElement(shareInformation, ATTRIBUTE_NAME, useCaseName);
        targetElement.appendChild(widgetElement);
        write(shareInformation, shareInformationPath);
    }


    /**
     * 共通設計情報に基づいてViewを再生成する
     * @param root
     * @param useCaseName
     * @return
     */
    public LinearLayout getScreen(Mode mode, String useCaseName, LinearLayout root) {
        List<Element> elementList = getChildElementList(shareInformation, ATTRIBUTE_NAME, useCaseName);
        for (int i = 0; i < elementList.size(); i++) {
            Element element = elementList.get(i);
            if (element.getTagName().equals(TAG_WIDGET)){
                int uniqueID = Integer.parseInt(element.getAttribute(ATTRIBUTE_ID));
                int widgetID = CreationWidgetController.getWidgetID(shareInformation, uniqueID);
                OutputWidget view = CreationWidgetController.createWidget(mode,root.getContext(), widgetID, uniqueID);
                root.addView(view.getView());
            }
        }
        return root;
    }

    /**
     * 画面遷移先を設定した際、共通設計情報に書き込む
     * @param uniqueID
     * @param gestureName
     * @param toTransitionUseCaseName
     */
    public void writeTransitionScreen(int uniqueID, String gestureName, String toTransitionUseCaseName){
        List<Element> gestureList = getChildElementList(shareInformation, ATTRIBUTE_ID, String.valueOf(uniqueID));
        Element useCase = shareInformation.createElement(TAG_USECASE);
        useCase.setAttribute(ATTRIBUTE_NAME, toTransitionUseCaseName);
        useCase.setAttribute(ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_MOVE);
        for (Element gesture : gestureList) {
            // 既に同じGestureが設定されている場合は上書きする
            if (getAttribute(gesture,ATTRIBUTE_NAME).equals(gestureName)){
                gesture.appendChild(useCase);
                write(shareInformation, shareInformationPath);
                return;
            }
        }
        // 設定したGestureがまだ共通設計情報に書き込まれていなければ新たなGesture要素を生成する
        Element widgetElement = getElement(shareInformation,ATTRIBUTE_ID, String.valueOf(uniqueID));
        Element gestureElement = shareInformation.createElement(TAG_GESTURE);
        gestureElement.setAttribute(ATTRIBUTE_NAME, gestureName);
        gestureElement.appendChild(useCase);
        widgetElement.appendChild(gestureElement);
        write(shareInformation, shareInformationPath);

    }

    /**
     * uniqueIDで指定したWidgetがInputかOutputかを取得する
     * @param uniqueID
     * @return
     */
    public WidgetType getWidgetType(int uniqueID){
        Element widgetElement = getElement(shareInformation, ATTRIBUTE_ID, String.valueOf(uniqueID));
        if (getAttribute(widgetElement,ATTRIBUTE_TYPE).equals(ATTRIBUTE_VALUE_INPUT)){
            return WidgetType.INPUT;
        }else if (getAttribute(widgetElement,ATTRIBUTE_TYPE).equals(ATTRIBUTE_VALUE_OUTPUT)){
            return WidgetType.OUTPUT;
        }
        return null;
    }


    /**
     * 遷移先のユースケース名を取得する
     * @param uniqueID
     * @param gesture
     * @return
     */
    public String getToTransitionUseCase(int uniqueID, Gesture gesture) {
        Element widgetElement = getElement(shareInformation, ATTRIBUTE_ID, String.valueOf(uniqueID));
        Element gestureElement = getElement(widgetElement, ATTRIBUTE_NAME, gesture.name());
        List<Element> childElementList = getChildElementList(gestureElement);
        for (Element useCaseElement : childElementList) {
            if (useCaseElement.getTagName().equals(TAG_USECASE)){
                return getAttribute(useCaseElement,ATTRIBUTE_NAME);
            }
        }
        return null;
    }

    public String getParentUseCase(String childUseCaseName){
        String rootUseCaseName = getChildUseCaseNameList(null).get(0);
        Element rootUseCaseElement = getElement(shareInformation,ATTRIBUTE_NAME,rootUseCaseName);
        return getParentUseCase(rootUseCaseElement,childUseCaseName);
    }

    private String getParentUseCase(Element parent, String childUseCaseName){
        String parentUseCaseName = null;
        List<Element> childUseCaseList = getChildUseCaseList(getAttribute(parent,ATTRIBUTE_NAME));
        for (Element childUseCase : childUseCaseList) {
            if (getAttribute(childUseCase,ATTRIBUTE_NAME).equals(childUseCaseName)){
                parentUseCaseName = getAttribute(parent,ATTRIBUTE_NAME);
            }
            if (parentUseCaseName != null){
                break;
            }
            parentUseCaseName = getParentUseCase(childUseCase,childUseCaseName);
        }
        return parentUseCaseName;
    }
}
