package com.cinbo.filesync.test;

import com.laz.filesync.util.Coder;
import com.laz.filesync.util.FileSyncUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

/**
 * @description: 测试文件数据接收
 * @author: cinbo，chenyinbo
 * @create: 2020-10-15 16:56
 */
public class FileReceiveHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private boolean isStart=true;
    private long fileLength = 0;
    private long totoalbytes=0;
    private OutputStream of ;
    public FileReceiveHandler(){
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ByteBuf tmpbuf = UnpooledByteBufAllocator.DEFAULT.buffer(100);
        tmpbuf.writeBytes("d:\\tmp\\sortfilein1\n".getBytes());
        ctx.writeAndFlush(tmpbuf);
        of = new FileOutputStream("d:\\tmp\\sortfilein1_1");
        //ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        if(isStart){
            if(msg.readableBytes()<8){
                return;
            }else{
                fileLength = msg.readLong();
                System.out.println("file size:"+fileLength);
                isStart = false;
            }
        }
        totoalbytes += msg.readableBytes();
        byte[] tempbuf = new byte[msg.readableBytes()];
        msg.readBytes(tempbuf);
        of.write(tempbuf);
        System.out.println(totoalbytes);
        if(totoalbytes>=fileLength){
            System.out.println("接收完成. 文件大小："+totoalbytes);
            of.close();
            File file = new File("d:\\tmp\\sortfilein1_1");
            System.out.println(Coder.encryptBASE64(FileSyncUtil.generateFileDigest(file)));
            of.close();
            ctx.close();
        }
    }
}
