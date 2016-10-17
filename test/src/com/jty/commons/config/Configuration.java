package com.jty.commons.config;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Configuration
{
    private Map<String, String> parameters = new HashMap<String, String>();
    
    public Configuration(String path)
    {
        String[] paths = path.split(";");
        for (String p : paths)
        {
            build(p);
        }
    }
    
    public String getPropertyValue(String name, String defaultValue)
    {
        String value = parameters.get(name);
        if (value == null || value.trim().length() == 0)
            value = defaultValue;
        return value;
    }
    
    public String getPropertyValue(String name)
    {
        return parameters.get(name);
    }
    
    public int getIntPropertyValue(String name, int defaultValue)
    {
        String value = getPropertyValue(name);
        if (null == value)
        {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }
    
    public int getIntPropertyValue(String name)
    {
        return getIntPropertyValue(name, 0);
    }
    
    private void build(String path)
    {
        try
        {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            // ignore all comments inside the xml file
            docBuilderFactory.setIgnoringComments(true);
            // allow includes in the xml file
            docBuilderFactory.setNamespaceAware(true);
            try
            {
                docBuilderFactory.setXIncludeAware(true);
            }
            catch (UnsupportedOperationException e)
            {
                e.printStackTrace();
            }
            DocumentBuilder builder = docBuilderFactory.newDocumentBuilder();
            File xmlfile = new File(path);
            if (!xmlfile.exists())
            {
                throw new java.io.FileNotFoundException(xmlfile.getAbsolutePath());
            }
            Document doc = builder.parse(xmlfile);
            NodeList nodeList = doc.getElementsByTagName("property");
            for (int i = nodeList.getLength() - 1; i >= 0; i--)
            {
                Node property = nodeList.item(i);
                Element elepro = (Element)property;
                String name = elepro.getElementsByTagName("name").item(0).getTextContent();
                String value = elepro.getElementsByTagName("value").item(0).getTextContent();
                parameters.put(name, value);
            }
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        catch (SAXException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
