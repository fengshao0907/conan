package com.baidu.dan.conan.client;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;

import com.baidu.dan.conan.common.handler.Decoder;

/**
 * 
 * @author liaoqiqi
 * @email liaoqiqi@baidu.com
 * 
 */
public class ClientPipelineFactory implements ChannelPipelineFactory {

	public final ChannelPipeline getPipeline() throws Exception {

		// Create a default pipeline implementation.
		ChannelPipeline pipeline = pipeline();

		pipeline.addLast("decoder", new Decoder());
		pipeline.addLast("encoder", new ClientEncoder());

		// and then business logic.
		pipeline.addLast("handler", new ClientHandler());

		return pipeline;
	}
}
