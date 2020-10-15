package com.cinbo.filesync.test;

import com.laz.filesync.client.FileSendClient;
import com.laz.filesync.client.file.handler.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description: 接受服务器发送的文件
 * @author: cinbo，chenyinbo
 * @create: 2020-10-15 15:03
 */
public class FileClient {
    private String host;
    private int port;
    private static Logger logger = LoggerFactory.getLogger(FileSendClient.class);
    public FileClient(String host,int port) {
        this.host = host;
        this.port = port==0?8990:port;
    }
    private Channel channel;
    private NioEventLoopGroup group;
    public NioEventLoopGroup getGroup() {
        return group;
    }
    public Channel getChannel() {
        return channel;
    }
    public void start() {
        Bootstrap bootstrap = new Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();
        this.group = group;
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        //pipeline.addLast(new MessageEncoder());
                        pipeline.addLast(new FileReceiveHandler());

                    }
                });

        ChannelFuture future;
        try {
            future = bootstrap.connect(host, port).sync();
            if (future.isSuccess()) {
                logger.info("文件服务器连接成功");
                this.channel = future.channel();
//                future.channel().writeAndFlush("test\n");
            } else {
                logger.info("文件服务器连接失败");
            }
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
}



    public static void main(String[] args) {
        new FileClient("127.0.0.1",8023).start();
    }
}
