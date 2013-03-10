package com.baidu.dan.conan.server.mongodb;

import com.mongodb.DB;

public final class MongoLogic {

	private MongoLogic() {

	}

	/**
	 * 
	 * @Description: 登录mongo
	 * 
	 * @param host
	 * @param port
	 * @param userName
	 * @param passwd
	 * @throws Exception
	 * @return void
	 * @author liaoqiqi
	 * @date 2012-11-7
	 */
	public static void auth(final String host, final int port,
			final String userName, final String passwd) throws Exception {

		MongoService.getInstance().init(host, port);

		DB db = MongoService.getInstance().getMongo().getDB("admin");
		db.authenticate(userName, passwd.toCharArray());
	}

	public static void setOperateDb() {

		MongoService.getInstance().setDb("mydb");
	}
}
