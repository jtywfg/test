package com.jty.utils;

import java.util.List;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class CheckIndexUtil {
	
	public static DBObject findIndexFromCollection(DBCollection collection ,String indexName){
		List<DBObject> indexes = collection.getIndexInfo();
		for (DBObject dbObject : indexes) {
			if(indexName.equals(dbObject.get("name"))){
				return dbObject;
			}
		}
		return null;
	}
}
