package shengsiyuan.protobuf;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import shengsiyuan.protobuf.DataInfo;

import java.util.UUID;

/**
 * @description:
 * @author: xwy
 * @create: 下午2:36 2021/4/13
 **/

public class TestServerHadler extends SimpleChannelInboundHandler<DataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.MyMessage msg) throws Exception {
        DataInfo.MyMessage.DataType dataType = msg.getDataType();
        if (dataType == DataInfo.MyMessage.DataType.StudentType){
            DataInfo.Student student = msg.getStudent();
            System.out.println(student.getAddress());
            System.out.println(student.getName());
            System.out.println(student.getAge());
        }else if (dataType == DataInfo.MyMessage.DataType.DogType){
            DataInfo.Dog dog = msg.getDog();
            System.out.println(dog.getName());
            System.out.println(dog.getAge());
        }else {
            DataInfo.Cat cat = msg.getCat();
            System.out.println(cat.getName());
            System.out.println(cat.getCity());
            System.out.println(cat.getJiujiu());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}