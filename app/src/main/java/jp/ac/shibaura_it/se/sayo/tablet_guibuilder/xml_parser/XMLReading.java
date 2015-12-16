package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matsu on 2014/11/12.
 */
public class XMLReading {

    protected Document XML;                // パース対象のXML
    protected String path;          // パース対象のXMLのパス

    public XMLReading(Document XML, String path) {
        this.XML = XML;
        this.path = path;
    }

    /**
     * attrNameで指定した属性の値を取得
     * @param element
     * @param attrName
     * @return
     */
    public String getAttributeValue(Element element, String attrName) {
        return element.getAttribute(attrName);
    }

    /**
     * parentAttrNameの値がparentAttrValueである属性を親とし、親の直下の子を全て取得する。
     * @param key_value     keyとvalueが交互に格納されている(必ず偶数個)
     * @return
     */
    public List<Element> getChildElementList(String... key_value){
        Element element = getElement(key_value);
        if (element == null){
            return null;
        }
        return getChildElementList(element);
    }

    /**
     * element直下の子要素を全て取得する
     * @param element
     * @return
     */
    public List<Element> getChildElementList(Element element){
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
     * @param key_value     keyとvalueが交互に格納されている(必ず偶数個)
     * @return
     */
    public Element getElement(String... key_value){
        if (key_value.length % 2 == 1){
            return null;
        }
        Element element = XML.getDocumentElement();
        return getElement(element, key_value);
    }

    /**
     * 指定した属性値を持っている要素を取得する
     * @param key_value     keyとvalueが交互に格納されている
     * @return
     */
    public Element getElement(Element element, String... key_value){
        Element targetElement = null;
        boolean isMatch = true;
        for (int i = 0; i < key_value.length; i+=2) {
            String key = key_value[i];
            String value = key_value[i+1];
            if (getAttributeValue(element, key).equals(value) == false){
                isMatch = false;
            }
        }
        if (isMatch){
            return element;
        }else {
            List<Element> childElementList = getChildElementList(element);
            for (int i = 0; i < childElementList.size(); i++) {
                Element childElement = childElementList.get(i);
                targetElement = getElement(childElement, key_value);
                if (targetElement != null){
                    break;
                }
            }
        }
        return targetElement;
    }

    public Element getParentElemnt(String... key_value){
        Element element = getElement(key_value);
        if (element == null){
            return null;
        }
        return (Element) element.getParentNode();
    }



}
