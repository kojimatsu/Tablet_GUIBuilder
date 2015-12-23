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
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.CRUD;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_edit.Mode;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.CreationWidgetController;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.Gesture;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.OutputWidget;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.WidgetType;

/**
 * Created by 浩司 on 2015/11/06.
 */
public class ShareInformationManager extends XMLWriting {

    private static Document shareInformation;         // 共通設計情報
    private static String shareInformationPath;          // 共通設計情報のパス
    private static List<OutputWidget> outputWidgetList = new ArrayList<OutputWidget>();

    // 共通設計情報（XMLファイル）の要素名や属性名
    public final static String TAG_USECASE = "UseCaseScreen";
    public final static String TAG_GESTURE = "Gesture";
    public final static String TAG_ENTITY_DATA = "EntityData";

    public final static String ATTRIBUTE_TYPE = "type";
    public final static String ATTRIBUTE_NAME = "name";
    public final static String ATTRIBUTE_ROLL = "roll";

    public final static String ATTRIBUTE_VALUE_INPUT = WidgetType.INPUT.name();
    public final static String ATTRIBUTE_VALUE_OUTPUT = WidgetType.OUTPUT.name();
    public final static String ATTRIBUTE_VALUE_SCREEN = "screen";
    public final static String ATTRIBUTE_VALUE_MOVE = "move";

    public ShareInformationManager(Document shareInformation, String shareInformationPath) {
        super(shareInformation, shareInformationPath);
    }

    public static ShareInformationManager newInstance(){
        testInit();
        return new ShareInformationManager(shareInformation,shareInformationPath);
    }

    /**
     * テスト用の準備。
     * 実際はどこかのサーバ等に置いた共通設計情報を取得する処理に変わる。
     * 今回はテス地のため、端末内のSDカードから取得する
     */
    public static void testInit(){
        shareInformationPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Debug.testSIPath;
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

    public OutputWidget getOutputWidget(int uniqueID) {
        for (OutputWidget outputWidget : outputWidgetList) {
            if (outputWidget.getUniqueID() == uniqueID){
                return outputWidget;
            }
        }
        return null;
    }

    public String getRootUseCase(){
        Element root = XML.getDocumentElement();
        return getAttributeValue(root,ATTRIBUTE_NAME);
    }
    /**
     * useCaseName(ユースケース)を親に持つ,子ユースケース（直下）の名前を全て返す．
     * @param useCaseName
     * @return
     */
    public List<String> getChildUseCaseNameList(String useCaseName) {
        List<String> useCaseNameList = new ArrayList<String>();
        List<Element> elementList = getChildUseCaseList(useCaseName);
        for (int i = 0; i < elementList.size(); i++) {
            Element element = elementList.get(i);
            useCaseNameList.add(getAttributeValue(element, ATTRIBUTE_NAME));
        }
        return useCaseNameList;
    }

    /**
     * useCaseName(ユースケース)を親に持つ,子ユースケースのElementを全て返す．．
     * @param useCaseName
     * @return
     */
    private List<Element> getChildUseCaseList(String useCaseName) {
        List<Element> childUseCaseList = new ArrayList<Element>();
        List<Element> elementList = getChildElementList(ATTRIBUTE_NAME, useCaseName, ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_SCREEN);
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
        outputWidgetList.add(view);
        Element widgetElement = XML.createElement(TAG_WIDGET);
        if (view.getWidgetType() == WidgetType.OUTPUT){
            widgetElement.setAttribute(ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_OUTPUT);
        }else if(view.getWidgetType() == WidgetType.INPUT){
            widgetElement.setAttribute(ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_INPUT);
        }
        widgetElement.setAttribute(ATTRIBUTE_NAME, view.getWidgetName());
        widgetElement.setAttribute(ATTRIBUTE_ID, String.valueOf(view.getUniqueID()));
        Element useCaseElement = getElement(ATTRIBUTE_NAME, useCaseName, ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_SCREEN);
        useCaseElement.appendChild(widgetElement);
        write();
    }


    /**
     * 共通設計情報に基づいてViewを再生成する
     * @param root
     * @param useCaseName
     * @return
     */
    public LinearLayout getScreen(Mode mode, String useCaseName, LinearLayout root) {
        outputWidgetList = new ArrayList<OutputWidget>();
        List<Element> widgetList = getWidgetList(useCaseName);
        for (int i = 0; i < widgetList.size(); i++) {
            Element widget = widgetList.get(i);
            int uniqueID = Integer.parseInt(getAttributeValue(widget, ATTRIBUTE_ID));
            int widgetID = CreationWidgetController.getWidgetID(uniqueID);
            GUIInformationManager manager = GUIInformationManager.newInstance();
            Element entityDataElement = getElement(widget, ATTRIBUTE_TYPE, CRUD.Read.name());
            String entityDataName = null;
            if (entityDataElement != null){
                entityDataName = getAttributeValue(entityDataElement,ATTRIBUTE_NAME);
            }
            String label = manager.getLabel(uniqueID);
            OutputWidget view = null;
            if(entityDataElement != null){
                view = CreationWidgetController.createWidget(mode, root.getContext(), entityDataName, widgetID, uniqueID);
            }else if (label != null){
                view = CreationWidgetController.createWidget(mode, root.getContext(), label, widgetID, uniqueID);
            }else {
                view = CreationWidgetController.createWidget(mode, root.getContext(), widgetID, uniqueID);
            }
            outputWidgetList.add(view);
            root.addView(view.getView());
        }
        return root;
    }

    private List<Element> getWidgetList(String useCaseName){
        List<Element> widgetList = new ArrayList<Element>();
        List<Element> childList = getChildElementList(ATTRIBUTE_NAME, useCaseName, ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_SCREEN);
        for (int i = 0; i < childList.size(); i++) {
            Element child = childList.get(i);
            if (child.getTagName().equals(TAG_WIDGET)) {
                widgetList.add(child);
            }
        }
        return widgetList;
    }

    /**
     * 画面遷移先を設定した際、共通設計情報に書き込む
     * @param uniqueID
     * @param gestureName
     * @param toTransitionUseCaseName
     */
    public void writeTransitionScreen(int uniqueID, String gestureName, String toTransitionUseCaseName){
        Element toTransitionUseCaseElement = XML.createElement(TAG_USECASE);
        toTransitionUseCaseElement.setAttribute(ATTRIBUTE_NAME, toTransitionUseCaseName);
        toTransitionUseCaseElement.setAttribute(ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_MOVE);

        Element gesture = getGestureElement(uniqueID, gestureName);
        if (gesture == null){   // 設定したGestureがまだ共通設計情報に書き込まれていなければ新たなGesture要素を生成する
            Element gestureElement = XML.createElement(TAG_GESTURE);
            gestureElement.setAttribute(ATTRIBUTE_NAME, gestureName);
            gestureElement.appendChild(toTransitionUseCaseElement);
            Element widgetElement = getElement(ATTRIBUTE_ID, String.valueOf(uniqueID));
            widgetElement.appendChild(gestureElement);
        }else {                 // 既に同じGestureが設定されている場合はその中に遷移先ユースケースを追加する
            Element settedUseCaseElement = getToTransitionUseCaseElement(uniqueID, Gesture.getGesture(gestureName));
            if (settedUseCaseElement == null){
                gesture.appendChild(toTransitionUseCaseElement);
            }else {
                settedUseCaseElement.setAttribute(ATTRIBUTE_NAME,toTransitionUseCaseName);
            }
        }
        write();
    }

    private Element getGestureElement(int uniqueID, String gestureName){
        List<Element> widgetChildList = getChildElementList(ATTRIBUTE_ID, String.valueOf(uniqueID));
        for (Element gesture : widgetChildList) {
            if (gestureName.equals(getAttributeValue(gesture,ATTRIBUTE_NAME))){
                return gesture;
            }
        }
        return null;
    }

    /**
     * uniqueIDで指定したWidgetがInputかOutputかを取得する
     * @param uniqueID
     * @return
     */
    public WidgetType getWidgetType(int uniqueID){
        Element widgetElement = getElement(ATTRIBUTE_ID, String.valueOf(uniqueID));
        if (getAttributeValue(widgetElement, ATTRIBUTE_TYPE).equals(ATTRIBUTE_VALUE_INPUT)){
            return WidgetType.INPUT;
        }else if (getAttributeValue(widgetElement, ATTRIBUTE_TYPE).equals(ATTRIBUTE_VALUE_OUTPUT)){
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
    public String getToTransitionUseCaseName(int uniqueID, Gesture gesture) {
        Element useCaseElement = getToTransitionUseCaseElement(uniqueID, gesture);
        return getAttributeValue(useCaseElement,ATTRIBUTE_NAME);
    }


    public Element getToTransitionUseCaseElement(int uniqueID, Gesture gesture) {
        Element gestureElement = getGestureElement(uniqueID, gesture.name());
        List<Element> childElementList = getChildElementList(gestureElement);
        for (Element useCaseElement : childElementList) {
            if (useCaseElement.getTagName().equals(TAG_USECASE)){
                return useCaseElement;
            }
        }
        return null;
    }

    /**
     * childUseCaseNameの親ユースケースを取得する
     * @param childUseCaseName
     * @return
     */
    public String getParentUseCaseName(String childUseCaseName){
        Element parentElement = getParentElement(ATTRIBUTE_NAME, childUseCaseName, ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_SCREEN);
        return getAttributeValue(parentElement,ATTRIBUTE_NAME);
    }



    public void writeRoll(int uniqueID, String roll) {
        Element element = getElement(ATTRIBUTE_ID, String.valueOf(uniqueID));
        element.setAttribute(ATTRIBUTE_ROLL, roll);
        write();
    }

    public String getRoll(int uniqueID) {
        Element element = getElement(ATTRIBUTE_ID, String.valueOf(uniqueID));
        String roll = getAttributeValue(element,ATTRIBUTE_ROLL);
        if (roll.equals("")){
            return null;
        }
        return roll;
    }

    public String getEntityDataName(int uniqueID, String gestureName, CRUD crud) {
        Element widget = getElement(ATTRIBUTE_ID, String.valueOf(uniqueID));
        Element gesture = getElement(widget,ATTRIBUTE_NAME,gestureName);
        if (gesture == null){
            return null;
        }
        Element entityData = getElement(gesture, ATTRIBUTE_TYPE, crud.name());
        if (entityData == null){
            return null;
        }
        return getAttributeValue(entityData,ATTRIBUTE_NAME);
    }

    /**
     * 各C,R,U,DのEntityは1つのWidgetに対し、同時に定義できるが
     * 例えばCを複数個定義することはできない。
     * C,R,U,D : OK
     * C,C,R,U,D : NG
     * @param uniqueID
     * @param gestureName
     * @param crud
     * @param dataName
     */
    public void writeEntityData(int uniqueID, String gestureName, CRUD crud, String dataName) {
        Element widgetElement = getElement(ATTRIBUTE_ID, String.valueOf(uniqueID));
        Element entityDataElement = getElement(widgetElement, ATTRIBUTE_TYPE, crud.name());
        if (entityDataElement == null){
            entityDataElement = XML.createElement(TAG_ENTITY_DATA);
            entityDataElement.setAttribute(ATTRIBUTE_TYPE, crud.name());
        }
        entityDataElement.setAttribute(ATTRIBUTE_NAME, dataName);

        if (crud != CRUD.Read){
            Element gestureElement = getElement(widgetElement, ATTRIBUTE_NAME, gestureName);
            if (gestureElement == null){
                gestureElement = XML.createElement(TAG_GESTURE);
                gestureElement.setAttribute(ATTRIBUTE_NAME,gestureName);
            }

            Element parentElement = getParentElement(entityDataElement);
            if (gestureElement != parentElement && parentElement != null){
                parentElement.removeChild(entityDataElement);
                if (!parentElement.hasChildNodes()){
                    widgetElement.removeChild(parentElement);
                }
                gestureElement.appendChild(entityDataElement);
            }
            if (parentElement == null){
                gestureElement.appendChild(entityDataElement);
            }
            parentElement = getParentElement(gestureElement);
            if (parentElement == null){
                widgetElement.appendChild(gestureElement);
            }
        }else {
            Element parentElement = getParentElement(entityDataElement);
            if (parentElement == null){
                widgetElement.appendChild(entityDataElement);
            }
        }

        write();
    }

    public List<String> getSettableEntityDataNameList(int uniqueID) {
        Element widgetElement = getElement(ATTRIBUTE_ID, String.valueOf(uniqueID));
        Element useCaseElement = getParentElement(widgetElement);
        // ルートユースケースからuseCaseElementまでの遷移順序を取得する
        List<Element> moveScreenPath = new ArrayList<Element>();
        moveScreenPath.add(useCaseElement);
        String useCaseName = getAttributeValue(useCaseElement, ATTRIBUTE_NAME);
        moveScreenPath = getMoveScreenPath(moveScreenPath,useCaseName);

        List<String> settableEntityDataNameList = new ArrayList<String>();
        List<Element> entityDataElementList = new ArrayList<Element>();
        for (Element moveScreen : moveScreenPath) {
            List<Element> widgetElementList = getWidgetList(getAttributeValue(moveScreen,ATTRIBUTE_NAME));
            for (Element widget : widgetElementList) {
                entityDataElementList.addAll(getChildElementList(widget, ATTRIBUTE_TYPE, CRUD.Create.name()));
            }
        }
        for (Element entityDataElement : entityDataElementList) {
            settableEntityDataNameList.add(getAttributeValue(entityDataElement, ATTRIBUTE_NAME));
        }
        return settableEntityDataNameList;
    }

    private List<Element> getMoveScreenPath(List<Element> path, String useCaseName){
        Element typeMoveUseCaseElement = getElement(ATTRIBUTE_NAME, useCaseName, ATTRIBUTE_TYPE, ATTRIBUTE_VALUE_MOVE);
        if(typeMoveUseCaseElement != null){
            Element gestureElement = getParentElement(typeMoveUseCaseElement);
            Element widgetElement = getParentElement(gestureElement);
            Element postUseCaseElement = getParentElement(widgetElement);
            path.add(postUseCaseElement);
            getMoveScreenPath(path, getAttributeValue(postUseCaseElement,ATTRIBUTE_NAME));
        }
        return path;
    }

    public void setOutputWidgetText(int uniqueID, String dataName) {
        OutputWidget outputWidget = getOutputWidget(uniqueID);
        outputWidget.setText(dataName);
    }
}
