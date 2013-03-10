package com.baidu.dan.conan.server;

import static org.jboss.netty.channel.Channels.pipeline;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;

import com.baidu.dan.conan.common.handler.Decoder;

public class ServerPipeline implements ChannelPipelineFactory {

	public final ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = pipeline();

		// Add the coder
		pipeline.addLast("decoder", new Decoder());
		pipeline.addLast("encoder", new ServerEncoder());

		// and then business logic.
		// Please note we create a handler for every new channel
		// because it has stateful properties.
		pipeline.addLast("handler", new ServerHandler());

		return pipeline;
	}
}
