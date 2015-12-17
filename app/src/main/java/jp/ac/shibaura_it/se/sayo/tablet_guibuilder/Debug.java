package jp.ac.shibaura_it.se.sayo.tablet_guibuilder;

import android.os.Environment;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.GUIInformationManager;
import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser.ShareInformationManager;

/**
 * Created by matsu on 2014/10/26.
 */
public class Debug  {

    static public  String testDir = "/xml/";
    static public  String testSIFileName = "share_information.xml";
    static public  String testGIFileName = "GUI_information.xml";
    static public  String testSIPath = testDir + testSIFileName;
    static public  String testGIPath = testDir + testGIFileName;

    static public void createXML(){
        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dbuilder = dbfactory.newDocumentBuilder();
            Document document = dbuilder.newDocument();
            /***********************/
            Element root = document.createElement(ShareInformationManager.TAG_USECASE);
            root.setAttribute(ShareInformationManager.ATTRIBUTE_NAME,"休講・補講情報を検索する");
            root.setAttribute(ShareInformationManager.ATTRIBUTE_TYPE,ShareInformationManager.ATTRIBUTE_VALUE_SCREEN);

            Element child = document.createElement(ShareInformationManager.TAG_USECASE);
            child.setAttribute(ShareInformationManager.ATTRIBUTE_NAME,"休講情報を検索する");
            child.setAttribute(ShareInformationManager.ATTRIBUTE_TYPE,ShareInformationManager.ATTRIBUTE_VALUE_SCREEN);
            root.appendChild(child);

            child = document.createElement(ShareInformationManager.TAG_USECASE);
            child.setAttribute(ShareInformationManager.ATTRIBUTE_NAME,"補講情報を検索する");
            child.setAttribute(ShareInformationManager.ATTRIBUTE_TYPE,ShareInformationManager.ATTRIBUTE_VALUE_SCREEN);
            root.appendChild(child);

            child = document.createElement(ShareInformationManager.TAG_USECASE);
            child.setAttribute(ShareInformationManager.ATTRIBUTE_NAME,"検索条件を設定する");
            child.setAttribute(ShareInformationManager.ATTRIBUTE_TYPE,ShareInformationManager.ATTRIBUTE_VALUE_SCREEN);
            root.appendChild(child);

            document.appendChild(root);
            /***********************/
            TransformerFactory tffactory = TransformerFactory.newInstance();
            Transformer transformer = tffactory.newTransformer();
            String path = Environment.getExternalStorageDirectory().toString() + testDir;   //XML保存先ディレクトリ
            File dir = new File(path);
            if(!dir.exists()){
                dir.mkdir();
            }
            File file = new File(path + testSIFileName);
            file.createNewFile();
            transformer.transform(new DOMSource(document), new StreamResult(file));

            /***********************/
            document = dbuilder.newDocument();
            root = document.createElement(GUIInformationManager.TAG_WIDGETLIST);
            document.appendChild(root);
            /***********************/
            file = new File(path + testGIFileName);
            file.createNewFile();
            transformer.transform(new DOMSource(document), new StreamResult(file));


        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    static public void log(String text){
        Log.d("TAG",text);
    }
}
