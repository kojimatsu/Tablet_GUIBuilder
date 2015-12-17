package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by matsu on 2014/11/12.
 */
public class XMLWriting extends XMLReading {

    public final static String TAG_WIDGET = "Widget";
    public final static String ATTRIBUTE_ID = "id";

    protected XMLWriting(Document XML, String path) {
        super(XML,path);
    }

    // Documentオブジェクトをファイルに出力
    protected void write() {

        File file =new File(path);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            StreamResult result = new StreamResult(fos);
            TransformerFactory transFactory = TransformerFactory.newInstance();
            // Transformerを取得
            Transformer transformer = transFactory.newTransformer();
            transformer.setOutputProperty("encoding", "UTF-8");
            transformer.setOutputProperty("indent", "yes");
            DOMSource source = new DOMSource(XML);
            transformer.transform(source, result);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

}
