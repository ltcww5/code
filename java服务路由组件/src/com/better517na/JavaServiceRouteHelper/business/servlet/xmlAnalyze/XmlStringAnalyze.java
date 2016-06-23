/*
 * 文件名：XmlStringAnalyze.java
 * 版权：Copyright 2007-2015 517na Tech. Co. Ltd. All Rights Reserved. 
 * 描述： XmlStringAnalyze.java
 * 修改人：chunfeng
 * 修改时间：2015年4月20日
 * 修改内容：新增
 */
package com.better517na.JavaServiceRouteHelper.business.servlet.xmlAnalyze;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.better517na.JavaServiceRouteHelper.business.log.LogOperate;
import com.better517na.JavaServiceRouteHelper.serviceRouteHelper.ServiceRouteAttachHelper;
import com.better517na.JavaServiceRouteHelper.util.StringUtil;

/**
 * . 定义cxf的xml内容解析的方法
 * 
 * @author chunfeng
 */
public class XmlStringAnalyze {

    /**
     * . 协议开头字符 以s*:Envelope 开头的字符串匹配
     */
    public static final String PROTOCALPREFIX = "^<s\\w*:Envelope[\\s\\S]*";

    /**
     * . xml开头的格式
     */
    public static final String XMLPREFIX = "^<\\?xml[\\s\\S]*";

    /**
     * . soap:Header
     */
    public static final String HEADKEY = "s\\w*:Header";

    /**
     * . soap:Body
     */
    public static final String BODYKEY = "s\\w*:Body";

    /**
     * . ns2
     */
    public static final String BODYPRFIX = "ns2";

    // 返回map固定的key
    /**
     * . attachvalue
     */
    public static final String MAPKEY_ATTACH = "attachvalue";

    /**
     * . methodname
     */
    public static final String MAPKEY_METHODNAME = "methodname";

    /**
     * . parameters
     */
    public static final String MAPKEY_PARAMETERS = "parameters";

    /**
     * . returnVal
     */
    public static final String MAPKEY_RETURNVAL = "returnVal";

    /**
     * . TODO 解析request流中的数据
     * 
     * @param content
     *            解析的内容
     * @param getHeadOnly
     *            是否只解析头的内容
     * @return 解析结果 以key->value的形式展示
     */
    // <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
    // <soap:Header>
    // <serviceRouteHelper_attach_key>
    // {"attach":{"aaaaa":"iamwwf往"},"generateTime":"2015-04-20 11:46:40"}
    // </serviceRouteHelper_attach_key>
    // </soap:Header>
    // <soap:Body>
    // <ns2:login xmlns:ns2="http://service.KRBasicInfoJavaDataService.better517na.com/">
    // <arg0>xiangqicc</arg0><arg1>111111</arg1>
    // </ns2:login>
    // </soap:Body>
    // </soap:Envelope>
    public static Map<String, String> analyzeRequestXml(final String content, boolean getHeadOnly) {
        // 默认返回值
        Map<String, String> result = new HashMap<String, String>();

        // 判空处理
        if (content == null || "".equals(content)) {
            return result;
        }

        // 不是需要解析的协议内容
        if (!judgeCanAnalyze(content)) {
            return result;
        }

        String analyzeStr = content;

        DocumentBuilder builder = null;
        try {
            builder = XmlBuilderPool.getInstance().getXmlBuilder();
            // 开始解析
            Document doc = builder.parse(new InputSource(new StringReader(analyzeStr)));

            Element root = doc.getDocumentElement();
            NodeList rootChildes = root.getChildNodes();
            if (rootChildes != null) {
                for (int i = 0; i < rootChildes.getLength(); i++) {
                    Node child = rootChildes.item(i);

                    // 处理头 寻找attach值
                    if (Pattern.matches(HEADKEY, child.getNodeName())) {
                        NodeList headChild = child.getChildNodes();
                        if (headChild != null) {
                            for (int j = 0; j < headChild.getLength(); j++) {
                                Node headc = headChild.item(j);
                                if (ServiceRouteAttachHelper.ATTACH_ELEMENT_KEY_VAL.equals(headc.getNodeName())) {
                                    // 加入header值
                                    result.put(MAPKEY_ATTACH, StringUtil.replaceESCStr(headc.getTextContent()));
                                }

                                // 加入trackId相关内容
                                if (LogOperate.TRACKID_KEY.equals(headc.getNodeName())) {
                                    // 继续查询子node
                                    NodeList cchild = headc.getChildNodes();
                                    for (int k = 0; k < cchild.getLength(); k++) {
                                        Node cheadc = cchild.item(k);
                                        if (LogOperate.TRACKID_VAL_KEY.equals(cheadc.getNodeName())) {
                                            result.put(LogOperate.TRACKID_VAL_KEY, StringUtil.replaceESCStr(cheadc.getTextContent()));
                                        }
                                        if (LogOperate.TRACKID_STARTTIME_KEY.equals(cheadc.getNodeName())) {
                                            result.put(LogOperate.TRACKID_STARTTIME_KEY, StringUtil.replaceESCStr(cheadc.getTextContent()));
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (getHeadOnly) {
                        break;
                    }

                    // 处理body
                    if (Pattern.matches(BODYKEY, child.getNodeName())) {
                        NodeList bodyChild = child.getChildNodes();
                        if (bodyChild != null) {
                            for (int j = 0; j < bodyChild.getLength(); j++) {
                                Node bodyc = bodyChild.item(j);
                                // body下面的全是方法
                                if (bodyc.getNodeName().startsWith(BODYPRFIX)) {
                                    // 加入header值
                                    String methodelement = bodyc.getNodeName();
                                    String[] sts = methodelement.split(":");
                                    // 找到调用的参数
                                    if (sts.length != 2) {
                                        continue;
                                    }
                                    result.put(MAPKEY_METHODNAME, StringUtil.replaceESCStr(sts[1]));
                                } else {
                                    // 没有ns2的那种
                                    result.put(MAPKEY_METHODNAME, StringUtil.replaceESCStr(bodyc.getNodeName()));
                                }
                                // 寻找参数
                                NodeList childchild = bodyc.getChildNodes();
                                StringBuffer sb = new StringBuffer();
                                for (int k = 0; k < childchild.getLength(); k++) {
                                    Node item = childchild.item(k);
                                    if (item.hasChildNodes()){
                                        StringBuffer sb1 = new StringBuffer();
                                        NodeList ttchild = item.getChildNodes();
                                        for (int l = 0; l < ttchild.getLength(); l++){
                                            sb1.append(ttchild.item(l).getNodeName());
                                            sb1.append(":");
                                            sb1.append(ttchild.item(l).getTextContent());
                                            if (l != (ttchild.getLength() -1)){
                                                sb1.append("|");
                                            }
                                        }
                                        sb.append(sb1.toString());
                                    } else {
                                        sb.append(item.getTextContent());
                                        if (k != (childchild.getLength() - 1)) {
                                            sb.append(",");
                                        }
                                    }
                                }
                                result.put(MAPKEY_PARAMETERS, sb.toString());
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("[" + content + "]解析出问题");
        } finally {
            // 退还xmlBuilder
            if (builder != null) {
                XmlBuilderPool.getInstance().returnXmlBuilder(builder);
            }
        }
        return result;
    }

    // public static void main(String[] args) {
    // String url = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
    // + "<soap:Header><TrackId xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"TrackId.517na.com\">"
    // + "<_x003C_Level_x003E_k__BackingField xmlns=\"http://schemas.datacontract.org/2004/07/Better.Infrastructures.Log\">Info</_x003C_Level_x003E_k__BackingField>"
    // + "<_x003C_OriginalTrackID_x003E_k__BackingField xmlns=\"http://schemas.datacontract.org/2004/07/Better.Infrastructures.Log\" i:nil=\"true\"/>"
    // + "<exceptionId xmlns=\"http://schemas.datacontract.org/2004/07/Better.Infrastructures.Log\">00000000-0000-0000-0000-000000000000</exceptionId>"
    // + "<innerSequence xmlns=\"http://schemas.datacontract.org/2004/07/Better.Infrastructures.Log\">1"
    // + "</innerSequence><prefix xmlns=\"http://schemas.datacontract.org/2004/07/Better.Infrastructures.Log\">0-</prefix>"
    // + "<sequence xmlns=\"http://schemas.datacontract.org/2004/07/Better.Infrastructures.Log\">1</sequence>"
    // + "<startTime xmlns=\"http://schemas.datacontract.org/2004/07/Better.Infrastructures.Log\">2015-07-15T08:49:38</startTime>"
    // + "<trackID xmlns=\"http://schemas.datacontract.org/2004/07/Better.Infrastructures.Log\">jseroute15071508493837</trackID>"
    // + "</TrackId><serviceRouteHelper_attach_key>{\"attach\":{\"517slowlogtime\":\"1436921378844\"},\"generateTime\":\"2015-07-15 08:49:39\"}"
    // + "</serviceRouteHelper_attach_key></soap:Header><soap:Body><ns2:returnSomeValue xmlns:ns2=\"http://service.CXFServer.better517na.com/\">"
    // + "<arg0><picName>wwf</picName></arg0></ns2:returnSomeValue></soap:Body></soap:Envelope>";
    // Map<String, String> sb = analyzeRequestXml(url, false);
    // Set<String> sets = sb.keySet();
    // for (String key : sets) {
    // System.out.println("key:" + key + ",value:" + sb.get(key));
    // }
    // }

    /**
     * . TODO 解析response流中的数据
     * 
     * @param content
     *            解析的内容
     * @return 解析结果 以key->value的形式展示
     */
    // <soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
    // <soap:Body>
    // <ns2:loginResponse xmlns:ns2="http://service.KRBasicInfoJavaDataService.better517na.com/">
    // <return>
    // {"code":1,"content":{"keyID":"1502251637017588858XXXX001","userNo":"xiangqicc","userName":"xiangqicc",
    // "loginPassword":"111111","department":"","position":"","isAvailable":1,"isAdmin":1,"phoneNo":"132-5623-5789",
    // "email":"xiangqicc78@qq.com","status":0,"userType":0,"sellerId":"999999","buyerId":"00025","loginTimes":2546,
    // "lastTime":"2015-04-20 11:33:38","addTime":"2015-02-25 16:37:01","modifyTime":"2015-04-20 11:33:38","updateUser":"",
    // "isDelete":0},"time":1429501600632}
    // </return>
    // </ns2:loginResponse>
    // </soap:Body>
    // </soap:Envelope>
    public static Map<String, String> analyzeResponseXml(final String content) {
        // 默认返回值
        Map<String, String> result = new HashMap<String, String>();

        // 判空处理
        if (content == null || "".equals(content)) {
            return result;
        }

        // 不是需要解析的协议内容
        if (!judgeCanAnalyze(content)) {
            return result;
        }

        // 去除转义字符
        String analyzeStr = content;

        DocumentBuilder builder = null;
        try {
            builder = XmlBuilderPool.getInstance().getXmlBuilder();
            // 开始解析
            Document doc = builder.parse(new InputSource(new StringReader(analyzeStr)));

            Element root = doc.getDocumentElement();
            NodeList rootChildes = root.getChildNodes();
            if (rootChildes != null) {
                for (int i = 0; i < rootChildes.getLength(); i++) {
                    Node child = rootChildes.item(i);

                    // 处理body 寻找返回值
                    if (Pattern.matches(BODYKEY, child.getNodeName())) {
                        NodeList bodyChild = child.getChildNodes();
                        if (bodyChild != null) {
                            for (int j = 0; j < bodyChild.getLength(); j++) {
                                Node bodyc = bodyChild.item(j);
                                if (bodyc.getNodeName().startsWith(BODYPRFIX)) {
                                    String returnVal = bodyc.getTextContent();
                                    result.put(MAPKEY_RETURNVAL, StringUtil.replaceESCStr(returnVal));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("[" + content + "]解析出问题");
        } finally {
            // 还解析器
            if (builder != null) {
                XmlBuilderPool.getInstance().returnXmlBuilder(builder);
            }
        }
        return result;
    }

    /**
     * . TODO 判断是否能够通过这种方式解析
     * 
     * @param content
     *            requestString
     * @return 是否需要处理
     */
    public static boolean judgeCanAnalyze(String content) {
        // 没有请求内容的
        if (content == null || "".equals(content)) {
            return false;
        }
        content = content.trim();

        if (Pattern.matches(XMLPREFIX, content) || Pattern.matches(PROTOCALPREFIX, content)) {
            return true;
        }
        return false;
    }
}
