package com.baidu.dan.conan.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.socket.DatagramChannel;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.dan.conan.common.core.message.RequestMessage;
import com.baidu.dan.conan.common.exception.ConanException;

/**
 * 
 * 计费数据客户端连接器
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public class ConanClientConnector {

	private static final int WORKER_THREAD = 3;

	// 日志
	private static final Logger logger = LoggerFactory
			.getLogger(ConanClientConnector.class);

	// 连接器
	private ConnectionlessBootstrap bootstrap = new ConnectionlessBootstrap(
			new NioDatagramChannelFactory(Executors.newCachedThreadPool(),
					WORKER_THREAD));

	// channel 对象
	private DatagramChannel channel = null;

	private String host;
	private int port;

	/**
	 * 构造函数
	 * 
	 * @param host
	 * @param port
	 */
	public ConanClientConnector(final String host, final int port) {

		this.host = host;
		this.port = port;

		bootstrap.setPipelineFactory(new ClientPipelineFactory());
		channel = (DatagramChannel) bootstrap.bind(new InetSocketAddress(0));
	}

	/**
	 * 
	 * @Description: 发送消息
	 * @param cmd
	 */
	public final void sendCommand(final RequestMessage requestMessage)
			throws ConanException {

		try {

			// 发送消息
			channel.write(requestMessage, new InetSocketAddress(host, port));

		} catch (Exception e) {

			// 最终失败了
			logger.error(e.toString());
			throw new ConanException(e.toString());
		}
	}

	/**
	 * 
	 * @Description: 关闭连接
	 */
	public final void close() {

		if (channel != null) {
			if (!channel.getCloseFuture().awaitUninterruptibly(5000)) {
				logger.info("we will close the socket within 5 seconds.");
				channel.close().awaitUninterruptibly();
			}
		}

		// Shut down all thread pools to exit.
		bootstrap.releaseExternalResources();
	}
}