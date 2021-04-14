package shengsiyuan.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import shengsiyuan.protobuf.DataInfo;

import java.util.Random;

/**
 * @description:
 * @author: xwy
 * @create: 下午2:53 2021/4/13
 **/

public class TestClientHandler extends SimpleChannelInboundHandler<DataInfo.Student> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Student msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        for (int i = 0; i < 10; i++) {
            int randomInt = new Random().nextInt(3);

            DataInfo.MyMessage myMessage = null;
            if (0 == randomInt) {
                myMessage = DataInfo.MyMessage.newBuilder().setDataType(DataInfo.MyMessage.DataType.StudentType)
                        .setStudent(DataInfo.Student.newBuilder().setAge(18).setName("xwy").setAddress("上海").build()).build();
            } else if (1 == randomInt) {
                myMessage = DataInfo.MyMessage.newBuilder().setDataType(DataInfo.MyMessage.DataType.DogType)
                        .setDog(DataInfo.Dog.newBuilder().setAge(5).setName("千千").build()).build();
            } else {
                myMessage = DataInfo.MyMessage.newBuilder().setDataType(DataInfo.MyMessage.DataType.CatType)
                        .setCat(DataInfo.Cat.newBuilder().setName("啾啾").setCity("家里蹲").setJiujiu("jiujiu").build()).build();
            }

            ctx.channel().writeAndFlush(myMessage);
        }

    }
}