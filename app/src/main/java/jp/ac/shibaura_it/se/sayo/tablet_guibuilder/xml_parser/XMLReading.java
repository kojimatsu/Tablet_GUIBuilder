package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Created by matsu on 2014/11/12.
 */
public class XMLReading {

    protected Document XML;                // パース対象のDocument

    /**
     * attrNameで指定した属性の値を取得
     * @param element
     * @param attrName
     * @return
     */
    protected  static String getAttribute(Element element, String attrName) {
        return element.getAttribute(attrName);
    }

    /**
     * attrNameで指定した属性の値を取得
     * @param node
     * @param attrName
     * @return
     */
    protected static String getAttribute(Node node, String attrName) {
        Element element = (Element) node;
        return getAttribute(element, attrName);
    }

}
