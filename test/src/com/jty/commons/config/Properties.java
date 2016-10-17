package com.jty.commons.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * properties类型配置文件解析单例模式实现。
 */
public class Properties
{
    private static Map<String, Properties> _instances = new HashMap<String, Properties>();
    
    private java.util.Properties _properties = null;
    
    private Properties(String path) throws FileNotFoundException, IOException
    {
        InputStream is = new FileInputStream(path);
        _properties = new java.util.Properties();
        _properties.load(is);
    }
    
    private java.util.Properties getProperties()
    {
        return _properties;
    }
    
    public static synchronized java.util.Properties getInstance(String path)
        throws FileNotFoundException, IOException
    {
        if (null == _instances.get(path))
        {
            _instances.put(path, new Properties(path));
        }
        return _instances.get(path).getProperties();
    }
}
