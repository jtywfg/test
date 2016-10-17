package com.jty.utils;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlUtil {
	public static List<String> getAttrbutionVal(String content,String tagName,String attrName){
		List<String> results = new ArrayList<String>();
		try {
			Parser parser = new Parser(content);
			for (NodeIterator i = parser.elements (); i.hasMoreNodes(); ) {
                Node node = i.nextNode();
                List<String> tempResult = getTagsHtml(node,tagName,attrName);
                if(null!=tempResult && tempResult.size() > 0){
                	results.addAll(tempResult);
                }
            }
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return results;
	}
	public static List<String> getTagsHtmlByName(String content,String tagName){
		List<String> results = new ArrayList<String>();
		try {
			Parser parser = new Parser(content);
			for (NodeIterator i = parser.elements (); i.hasMoreNodes(); ) {
                Node node = i.nextNode();
                List<String> tempResult = getTagsHtml(node,tagName,null);
                if(null!=tempResult && tempResult.size() > 0){
                	results.addAll(tempResult);
                }
            }
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return results;
	}
	private static List<String> getTagsHtml(Node node,String tagName,String attrName){
		List<String> results = new ArrayList<String>();
		NodeList nodes = node.getChildren();
		if(null==nodes){
			return null;
		}
		for(int i=0;i<nodes.size();i++){
			Node childrenNode = nodes.elementAt(i);
			if(childrenNode instanceof TextNode){
				continue;
			}
			Tag tag = (Tag)childrenNode;
			String text = tag.getTagName().toLowerCase();
			if(text.equals(tagName.toLowerCase())){
				if(null!=attrName && !"".equals(attrName)){
				   String attrVal = tag.getAttribute(attrName);
				   if(null!=attrVal && !"".equals(attrVal)){
				      results.add(attrVal);
				   }
				}else{
					 results.add(childrenNode.toHtml());
				}
			}else{
				List<String> tempResult = getTagsHtml(childrenNode,tagName,attrName);
				if(null!=tempResult && tempResult.size() > 0){
					results.addAll(tempResult);
	            }
			}
		}
		return results;
	}
}
