package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser;

import android.os.Environment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.Debug;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.widget.OutputWidget;

/**
 * Created by 浩司 on 2015/12/17.
 */
public class GUIInformationManager extends XMLWriting {

    private static Document GUIInformation;         // GUI設計情報
    private static String GUIInformationPath;         // GUI設計情報のパス

    public final static String TAG_WIDGETLIST = "WidgetList";

    public final static String ATTRIBUTE_LABEL = "label";

    protected GUIInformationManager(Document GUIInformation, String GUIInformationPath) {
        super(GUIInformation, GUIInformationPath);
    }

    public static GUIInformationManager newInstance(){
        testInit();
        return new GUIInformationManager(GUIInformation,GUIInformationPath);
    }

    /**
     * テスト用の準備。
     * 実際はどこかのサーバ等に置いた共通設計情報を取得する処理に変わる。
     * 今回はテス地のため、端末内のSDカードから取得する
     */
    public static void testInit(){
        GUIInformationPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Debug.testGIPath;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream inputStream = new FileInputStream(GUIInformationPath);
            GUIInformation = documentBuilder.parse(inputStream);
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

    public void writeLabel(int uniqueID, String label) {
        String settedLabel = getLabel(uniqueID);
        if (settedLabel != null){
            Element widget = getElement(ATTRIBUTE_ID, String.valueOf(uniqueID));
            widget.setAttribute(ATTRIBUTE_LABEL, label);
        }else {
            Element root = GUIInformation.getDocumentElement();
            Element widget = GUIInformation.createElement(TAG_WIDGET);
            widget.setAttribute(ATTRIBUTE_ID, String.valueOf(uniqueID));
            widget.setAttribute(ATTRIBUTE_LABEL, label);
            root.appendChild(widget);
        }
        write();
    }

    public String getLabel(int uniqueID) {
        Element element = getElement(ATTRIBUTE_ID, String.valueOf(uniqueID));
        if (element == null){
            return null;
        }
        return getAttributeValue(element, ATTRIBUTE_LABEL);
    }
}
