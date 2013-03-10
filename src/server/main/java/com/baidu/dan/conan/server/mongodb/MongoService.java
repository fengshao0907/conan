package com.baidu.dan.conan.server.mongodb;

import java.util.HashMap;
import java.util.Map;

import com.mongodb.DB;
import com.mongodb.Mongo;

public final class MongoService {

	private Mongo mongo = null;

	private Map<String, DB> dbMap = null;

	private MongoService() {

		dbMap = new HashMap<String, DB>();
	}

	public void init(final String host, final int port) throws Exception {

		mongo = new Mongo(host, port);
	}

	private static class SingletonHolder {

		private static MongoService instance = new MongoService();
	}

	public static MongoService getInstance() {
		return SingletonHolder.instance;
	}

	public Mongo getMongo() {

		if (mongo != null) {
			return mongo;
		}
		return null;
	}

	public void setDb(final String dbName) {

		if (mongo != null) {
			DB db = mongo.getDB(dbName);
			if (db != null) {
				dbMap.put(dbName, db);
			}
		}
	}

	public DB getDb(final String dbName) {

		return dbMap.get(dbName);
	}

	public void close() {

		if (mongo != null) {
			mongo.close();
		}
	}

}
