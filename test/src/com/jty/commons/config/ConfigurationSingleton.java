package com.jty.commons.config;

import java.util.HashMap;
import java.util.Map;

public class ConfigurationSingleton
{
    private static Map<String, Configuration> _instances = new HashMap<String, Configuration>();
    
    public synchronized static Configuration getInstance(String path)
    {
        if (!_instances.containsKey(path))
        {
            _instances.put(path, new Configuration(path));
        }
        return _instances.get(path);
    }
}
