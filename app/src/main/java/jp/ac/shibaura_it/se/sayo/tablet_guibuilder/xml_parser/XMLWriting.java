package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by matsu on 2014/11/12.
 */
public class XMLWriting extends XMLReading {

    protected XMLWriting() {
        super();
    }


    public static XMLWriting newInstance(){
        return new XMLWriting();
    }

    // Documentオブジェクトをファイルに出力
    private void writeDocument(Document document) throws Exception {

        File file =new File(XML_fileName);
        FileOutputStream fos = new FileOutputStream(file);
        StreamResult result = new StreamResult(fos);

        // Transformerファクトリを生成
        TransformerFactory transFactory = TransformerFactory.newInstance();
        // Transformerを取得
        Transformer transformer = transFactory.newTransformer();

        // エンコード：UTF-8、インデントありを指定
        transformer.setOutputProperty("encoding", "UTF-8");
        transformer.setOutputProperty("indent", "yes");

        // transformerに渡すソースを生成
        DOMSource source = new DOMSource(document);

        // 出力実行
        transformer.transform(source, result);
        fos.close();
    }
}
