package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by matsu on 2014/11/12.
 */
public class XMLReading extends DropBoxConnection {

    /**
     * 絶対パスを引数
     * @param filePath ex:/休講/share_information.xml
     */
    public XMLReading(String filePath){
        super(filePath);
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
