package com.baidu.dan.conan.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.ChannelException;
import org.jboss.netty.channel.FixedReceiveBufferSizePredictorFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.dan.conan.server.mongodb.MongoLogic;

public final class ConanServer {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConanServer.class);

	private ConanServer() {

	}

	private static final int PORT = 8900;
	private static final int WORKER_THREAD = 3;

	private static final String MONGDB_HOST = "10.237.20.35";
	private static final int MONGDB_PORT = 8017;
	private static final String MONGO_USERNAME = "root";
	private static final String MONGO_PASSWD = "111";

	/**
	 * 
	 * @Description: 登录 mongodb
	 * 
	 * @return void
	 * @author liaoqiqi
	 * @date 2013-3-4
	 */
	@SuppressWarnings("unused")
	private static void loginMongo() {

		//
		// mongo login
		//
		try {
			MongoLogic.auth(MONGDB_HOST, MONGDB_PORT, MONGO_USERNAME,
					MONGO_PASSWD);
		} catch (Exception e) {
			LOGGER.error("cannot connect to mongo", e);
			return;
		}
		MongoLogic.setOperateDb();
	}

	/**
	 * 
	 * @Description:
	 * 
	 * @param args
	 * @throws Exception
	 * @return void
	 * @author liaoqiqi
	 * @date 2013-3-5
	 */
	public static void main(final String[] args) throws Exception {

		//
		// 实时计算线程开始计算
		//
		ComputeThread.run();

		//
		// 登录Mongodb
		//
		// loginMongo();

		//
		// real ops
		//

		// Configure the server.
		ConnectionlessBootstrap bootstrap = new ConnectionlessBootstrap(
				new NioDatagramChannelFactory(Executors.newCachedThreadPool(),
						WORKER_THREAD));

		// Configure the pipeline factory.
		bootstrap.setPipelineFactory(new ServerPipeline());

		// Enable broadcast
		bootstrap.setOption("broadcast", "false");

		// Allow packets as large as up to 1024 bytes (default is 768).
		// You could increase or decrease this value to avoid truncated packets
		// or to improve memory footprint respectively.
		//
		// Please also note that a large UDP packet might be truncated or
		// dropped by your router no matter how you configured this option.
		// In UDP, a packet is truncated or dropped if it is larger than a
		// certain size, depending on router configuration. IPv4 routers
		// truncate and IPv6 routers drop a large packet. That's why it is
		// safe to send small packets in UDP.
		bootstrap.setOption("receiveBufferSizePredictorFactory",
				new FixedReceiveBufferSizePredictorFactory(1024));

		try {
			// Bind to the port and start the service.
			bootstrap.bind(new InetSocketAddress(PORT));
		} catch (ChannelException e) {

			LOGGER.error("Bind Failed!", e);
		}

	}
}
