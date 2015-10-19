package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser;

import android.os.Environment;
import android.util.Log;

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

/**
 * Created by matsu on 2014/11/12.
 */
public class XMLReading {

    // 共通設計情報（XMLファイル）の要素名や属性名
    protected final String TAG_USECASE = "UseCaseScreen";
    protected final String USECASE_NAME = "name";

    protected Document document;                // パース対象のDocument


    public static XMLReading newInstance(){
        return new XMLReading();
    }
    /**
     * テスト用の準備。
     * 実際はどこかのサーバ等に置いた共通設計情報を取得する処理に変わる。
     * 今回はテス地のため、端末内のSDカードから取得する
     */
    public void testInit(){
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Debug.testPath;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream inputStream = new FileInputStream(path);
            this.document = documentBuilder.parse(inputStream);
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
    public List<String> getChildUseCaseNameList(String useCaseName) {
        Element rootElement = document.getDocumentElement();
        List<String> useCaseNameList = new ArrayList<String>();
        if (useCaseName == null) {
            useCaseNameList.add(getUseCaseName(rootElement));
            return useCaseNameList;
        }
        NodeList nodeList = rootElement.getElementsByTagName(TAG_USECASE);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            Node parentNode = childNode.getParentNode();
            if (getUseCaseName(parentNode).equals(useCaseName)) {
                useCaseNameList.add(getUseCaseName(childNode));
            }
        }
        return useCaseNameList;
    }

    /**
     * ユースケースの名前を取得する
     *
     * @param element
     * @return
     */
    private String getUseCaseName(Element element) {
        return element.getAttribute(USECASE_NAME);
    }

    /**
     * ユースケースの名前を取得する
     *
     * @param node
     * @return
     */
    private String getUseCaseName(Node node) {
        Element element = (Element) node;
        return getUseCaseName(element);
    }
}
