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

/**
 * Created by matsu on 2014/10/26.
 */
public class Debug  {

    static public  String testDir = "/xml/";
    static public  String testFileName = "sample.xml";
    static public  String testPath = testDir + testFileName;

    static public void createXML(){
        DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dbuilder = dbfactory.newDocumentBuilder();
            Document document = dbuilder.newDocument();
            /***********************/
            String USECASE_SCREEN="UseCaseScreen";
            Element root = document.createElement(USECASE_SCREEN);
            root.setAttribute("name","休講・補講情報を検索する");

            Element child = document.createElement(USECASE_SCREEN);
            child.setAttribute("name","休講情報を検索する");
            root.appendChild(child);

            child = document.createElement(USECASE_SCREEN);
            child.setAttribute("name","補講情報を検索する");
            root.appendChild(child);

            child = document.createElement(USECASE_SCREEN);
            child.setAttribute("name","検索条件を設定する");
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
            File file = new File(path + testFileName);
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
