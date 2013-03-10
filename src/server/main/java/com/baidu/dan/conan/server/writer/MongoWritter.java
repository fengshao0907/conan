package com.baidu.dan.conan.server.writer;

import com.baidu.dan.conan.server.handler.RequestHandler;
import com.baidu.dan.conan.server.mongodb.MongoService;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class MongoWritter implements ICommonWritter {

	@Override
	public final void doWork() throws Exception {
/*
		//
		// merge & get
		//
		RequestHandler.statisticsData.merge2Global();
		Double averageTime = RequestHandler.statisticsData.getAverageTime();

		DB db = MongoService.getInstance().getDb("mydb");
		DBCollection coll = db.getCollection("c1");

		BasicDBObject info = new BasicDBObject();
		info.put("test1", averageTime);
		coll.insert(info);*/
	}
}
