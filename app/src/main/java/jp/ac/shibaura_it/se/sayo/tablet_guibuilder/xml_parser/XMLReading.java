package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser;

import android.os.Environment;

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

    protected Document XML;                // パース対象のXML
    protected String path;          // パース対象のXMLのパス

    /**
     * attrNameで指定した属性の値を取得
     * @param element
     * @param attrName
     * @return
     */
    public String getAttribute(Element element, String attrName) {
        return element.getAttribute(attrName);
    }

    /**
     * attrNameで指定した属性の値を取得
     * @param node
     * @param attrName
     * @return
     */
    public String getAttribute(Node node, String attrName) {
        Element element = (Element) node;
        return getAttribute(element, attrName);
    }

    /**
     * * parentAttrNameの値がparentAttrValueである属性を親とし、親の直下の子を全て取得する。
     * つまり、指定する属性値はユニークでなければならない
     * @param document
     * @param parentAttrName    属性名
     * @param parentAttrValue   属性値
     * @return
     */
    public List<Element> getChildElementList(Document document, String parentAttrName, String parentAttrValue){
        Element element = getElement(document.getDocumentElement(),parentAttrName,parentAttrValue);
        return getChildElementList(element);
    }

    /**
     * element直下の子要素を全て取得する
     * @param element
     * @return
     */
    private List<Element> getChildElementList(Element element){
        NodeList childNodeList = element.getChildNodes();
        List<Element> elementList = new ArrayList<Element>();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            Node childNode = childNodeList.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                elementList.add((Element) childNode);
            }
        }
        return elementList;
    }

    /**
     * 指定した属性値を持っている要素を取得する
     * @param element
     * @param attrName      属性名
     * @param attrValue     属性値
     * @return
     */
    public Element getElement(Element element, String attrName, String attrValue){
        Element targetElement = null;
        if (getAttribute(element, attrName).equals(attrValue)){
            return element;
        }else {
            List<Element> childElementList = getChildElementList(element);
            for (int i = 0; i < childElementList.size(); i++) {
                Element childElement = childElementList.get(i);
                targetElement = getElement(childElement, attrName, attrValue);
                if (targetElement != null){
                    break;
                }
            }
        }
        return targetElement;
    }

}
