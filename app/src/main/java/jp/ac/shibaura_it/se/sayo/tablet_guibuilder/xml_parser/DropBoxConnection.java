//package jp.ac.shibaura_it.se.sayo.tablet_guibuilder.xml_parser;
//
///**
// * Created by matsu on 2014/10/24.
// */
//
//import android.util.Base64;
//import android.util.Xml;
//
//import com.dropbox.sync.android.DbxAccountManager;
//import com.dropbox.sync.android.DbxException;
//import com.dropbox.sync.android.DbxFile;
//import com.dropbox.sync.android.DbxFileInfo;
//import com.dropbox.sync.android.DbxFileSystem;
//import com.dropbox.sync.android.DbxPath;
//
//import org.apache.commons.codec.binary.Hex;
//import org.w3c.dom.Document;
//import org.xml.sax.SAXException;
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//import org.xmlpull.v1.XmlPullParserFactory;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.StringReader;
//import java.io.StringWriter;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.List;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//
//import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.DebugLog;
//import jp.ac.shibaura_it.se.sayo.tablet_guibuilder.screen_select.ScreenSelectionActivity;
//
//public class DropBoxConnection {
//
//    // dropbox接続のためのフィールド
//    public final static int REQUEST_LINK_TO_DBX = 0;
//    private final static String APP_KEY = "b39t82jec9q2eis";
//    private final static String APP_SECRET = "bfddmpjxzh23qy5";
//    private static DbxAccountManager mDbxAcctMgr;
//
//    private final static String SHARE_INFO_XML = "/share_information.xml";
//
//    // 共通設計情報（XMLファイル）の要素名や属性名
//    protected final String TAG_USECASE = "UseCase";
//    protected final String USECASE_NAME = "name";
//
//    private static DbxFileInfo projectDbxFileInfo;             // ex 休講ナビ
//    protected Document document;                // パース対象のDocument
//
//    /**
//     *
//     * @param filePath 絶対パス ex:/休講/share_information.xml
//     */
//    protected DropBoxConnection(String filePath){
//        init(filePath);
//    }
//
//    /**
//     * DropBoxに接続
//     * @param activity
//     */
//    public static void linkToDropBox(ScreenSelectionActivity activity) {
//        // (1) 登録キーでアカウントマネージャを作成
//        mDbxAcctMgr = DbxAccountManager.getInstance(activity.getApplicationContext(),
//                APP_KEY, APP_SECRET);
//        if (!mDbxAcctMgr.hasLinkedAccount()) {
//            mDbxAcctMgr.startLink(activity, REQUEST_LINK_TO_DBX);
//        }
//    }
//
//    /**
//     * DOMパーサの準備．
//     * @param filePath 絶対パス ex /休講ナビ/share_information.xml
//     */
//    private void init(String filePath) {
//        document = null;
//        DbxFile XMLFile = null;
//        try {
//            DbxFileSystem dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
//            XMLFile = dbxFs.open(new DbxPath(filePath));
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = factory.newDocumentBuilder();
//            InputStream inputStream = XMLFile.getReadStream();
//            document = builder.parse(inputStream);
//        } catch (DbxException.Unauthorized unauthorized) {
//            unauthorized.printStackTrace();
//        } catch (DbxException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } finally {
//            XMLFile.close();
//        }
//    }
//
//    /**
//     *
//     * @param projectName ex:休講ナビ
//     */
//    public static void setProjectDbxFileInfo(String projectName) {
//        DbxFileSystem dbxFs = null;
//        try {
//            dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
//            List<DbxFileInfo> projectList = dbxFs.listFolder(DbxPath.ROOT);
//            for (DbxFileInfo dbxFileInfo : projectList){
//                if (projectName.equals(dbxFileInfo.path.getName())){
//                    projectDbxFileInfo = dbxFileInfo;
//                }
//            }
//        } catch (DbxException.Unauthorized unauthorized) {
//            unauthorized.printStackTrace();
//        } catch (DbxException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     *
//     * @return ex:/休講ナビ
//     */
//    public static String getProjectPath() {
//        String projectPath = "/"+projectDbxFileInfo.path.getName();
//        return projectPath;
//    }
//
//    /**
//     *
//     * @return ex:/休講ナビ/share_information.xml
//     */
//    public static String getShareInfoPath() {
//        if (projectDbxFileInfo != null){
//            return getProjectPath() + SHARE_INFO_XML;
//        }
//        return null;
//    }
//
//    /**
//     * 全てのプロジェクトを取得
//     * @return
//     */
//    public static String[] getProjects(){
//        DbxFileSystem dbxFs = null;
//        try {
//            dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
//            List<DbxFileInfo> projectList = dbxFs.listFolder(DbxPath.ROOT);
//            String projects[] = new String[projectList.size()];
//            for (int i = 0;i < projectList.size();i++){
//                projects[i] = projectList.get(i).path.getName();
//            }
//            return projects;
//        } catch (DbxException.Unauthorized unauthorized) {
//            unauthorized.printStackTrace();
//        } catch (DbxException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//
//
//
//    //ここから下はいらないかも
//
////    public static final String LAYOUT =
////            "AwAIABgEAAABABwA0AEAABQAAAAAAAAAAAEAAGwAAAAAAAAAAAAAAA4AAAAeAAAALQAAADoAAAA/" +
////                    "AAAATwAAAFwAAABsAAAAegAAAIkAAACaAAAAqgAAAL4AAADPAAAA8gAAAPwAAAApAQAALAEAAEoB" +
////                    "AAALC29yaWVudGF0aW9uAA0NbGF5b3V0X2hlaWdodAAMDGxheW91dF93aWR0aAAKCmJhY2tncm91" +
////                    "bmQAAgJpZAANDWxheW91dF93ZWlnaHQACgpwYWRkaW5nVG9wAA0NcGFkZGluZ0JvdHRvbQALC3Bh" +
////                    "ZGRpbmdMZWZ0AAwMcGFkZGluZ1JpZ2h0AA4Oc2Nyb2xsYmFyU3R5bGUADQ1jbGlwVG9QYWRkaW5n" +
////                    "ABERZHJhd1NlbGVjdG9yT25Ub3AADg5jYWNoZUNvbG9ySGludAAgIHNjcm9sbGJhckFsd2F5c0Ry" +
////                    "YXdWZXJ0aWNhbFRyYWNrAAcHYW5kcm9pZAAqKmh0dHA6Ly9zY2hlbWFzLmFuZHJvaWQuY29tL2Fw" +
////                    "ay9yZXMvYW5kcm9pZAAAAAAbG2FuZHJvaWQud2lkZ2V0LkxpbmVhckxheW91dAAXF2FuZHJvaWQu" +
////                    "d2lkZ2V0Lkxpc3RWaWV3AIABCABEAAAAxAABAfUAAQH0AAEB1AABAdAAAQGBAQEB1wABAdkAAQHW" +
////                    "AAEB2AABAX8AAQHrAAEB/AABAQEBAQFpAAEBAAEQABgAAAACAAAA/////w8AAAAQAAAAAgEQAHQA" +
////                    "AAACAAAA//////////8SAAAAFAAUAAQAAAAAAAAAEAAAAAAAAAD/////CAAAEAEAAAAQAAAAAwAA" +
////                    "AP////8IAAABDQAGARAAAAACAAAA/////wgAABD/////EAAAAAEAAAD/////CAAAEP////8CARAA" +
////                    "KAEAAAgAAAD//////////xMAAAAUABQADQAAAAAAAAAQAAAADgAAAP////8IAAAS/////xAAAAAK" +
////                    "AAAA/////wgAABEAAAACEAAAAAQAAAD/////CAAAAQoAAgEQAAAACAAAAP////8IAAAFARAAABAA" +
////                    "AAAGAAAA/////wgAAAUBAAAAEAAAAAkAAAD/////CAAABQEQAAAQAAAABwAAAP////8IAAAFAQAA" +
////                    "ABAAAAALAAAA/////wgAABIAAAAAEAAAAAIAAAD/////CAAAEP////8QAAAAAQAAAP////8IAAAF" +
////                    "AAAAABAAAAAMAAAA/////wgAABIAAAAAEAAAAA0AAAD/////CAAAAQ0ABgEQAAAABQAAAP////8I" +
////                    "AAAEAACAPwMBEAAYAAAAFAAAAP//////////EwAAAAMBEAAYAAAAFgAAAP//////////EgAAAAEB" +
////                    "EAAYAAAAFgAAAP////8PAAAAEAAAAA==";
//    /**
//     * return XmlPullParser
//     * @return XmlPullParser
//     */
////    public XmlPullParser getXmlPullParser() {
////        String xml = null;
////        try {
////            StringWriter sw = new StringWriter();
////            TransformerFactory tfactory = TransformerFactory.newInstance();
////            Transformer transformer = tfactory.newTransformer();
////            transformer.transform(new DOMSource(document), new StreamResult(sw));
////            xml = sw.toString();
////
////            ///////
////            byte[] data = Base64.decode(LAYOUT, Base64.DEFAULT);
////
////            // XmlBlock block = new XmlBlock(LAYOUT.getBytes("UTF-8"));
////            Class<?> clazz = Class.forName("android.content.res.XmlBlock");
////            Constructor<?> constructor = clazz.getDeclaredConstructor(byte[].class);
////            constructor.setAccessible(true);
////            Object block = constructor.newInstance(data);
////
////            // XmlPullParser parser = block.newParser();
////            Method method = clazz.getDeclaredMethod("newParser");
////            method.setAccessible(true);
////            XmlPullParser parser = (XmlPullParser) method.invoke(block);
////
//////            XmlPullParser parser = Xml.newPullParser();
//////            parser.setInput( new StringReader(xml));
////
////            return parser;
////        } catch (IllegalArgumentException e) {
////            e.printStackTrace();
////        } catch (TransformerConfigurationException e) {
////            e.printStackTrace();
////        } catch (TransformerException e) {
////            e.printStackTrace();
////        } catch (NoSuchMethodException e) {
////            e.printStackTrace();
////        } catch (InstantiationException e) {
////            e.printStackTrace();
////        } catch (IllegalAccessException e) {
////            e.printStackTrace();
////        } catch (InvocationTargetException e) {
////            e.printStackTrace();
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        }
////        DebugLog.log("error");
////        return null;
////    }
//
//
//
//
//}
//
