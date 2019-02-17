package cn.nongph.jiamei.pay.wechat;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * xml和map相互转换工具
 * @author felix.wu
 *
 */
public class XmlMapper {
	public static String map2Xml(Map<String, String> map){
	    Document document = DocumentHelper.createDocument();
	    Element nodeElement = document.addElement("xml");
	    for (String key : map.keySet()) {
	      if (map.get(key) != null) {
	        Element keyElement = nodeElement.addElement(key);
	        keyElement.setText(String.valueOf(map.get(key)));
	      }
	    }
	    return map2String(document);
	}
	
	public static Map<String, String> xml2Map(String xml) {
	    try {
	    	Map<String, String> map = new HashMap<String, String>();
	        Document document = DocumentHelper.parseText(xml);
	        Element nodesElement = document.getRootElement();
	        for( Object node : nodesElement.elements() ){
	        	Element elm = (Element)node;
	        	map.put(elm.getName(), elm.getText());
	        }
	        return map;
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return null;
	}
	
	private static String map2String(Document document){
	    String s = "";
	    try {
	    	ByteArrayOutputStream out = new ByteArrayOutputStream();
	    	OutputFormat format = new OutputFormat(" ", true, "UTF-8");
	    	XMLWriter writer = new XMLWriter(out, format);
	    	writer.write(document);
	    	s = out.toString("UTF-8");
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    }
	    return s;
	}
}
