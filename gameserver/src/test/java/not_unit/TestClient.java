package not_unit;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.xuren.game.common.net.NetMsg;
import com.xuren.game.common.net.enums.PackageTypeEnum;
import com.xuren.game.common.net.enums.TypeEnum;
import com.xuren.game.common.net.tcp.client.NettyTcpClient;

/**
 * @author xuren
 */
public class TestClient {
    public static void main(String[] args) {
        NettyTcpClient nettyTcpClient = new NettyTcpClient();
        nettyTcpClient.conect("127.0.0.1", 55667);

//        MsgBase msgBase = new MsgBase();
//        msgBase.setProtoName("login");
//        nettyTcpClient.send(msgBase);

        NetMsg msg2 = new NetMsg();
        msg2.setMsgCode(10001);
        msg2.setRid("10001_1_1");
        msg2.setData("hahahah".getBytes());
        msg2.setType(TypeEnum.DATA);
        msg2.setPackageTypeEnum(PackageTypeEnum.REQUEST);
        msg2.setRequestId(1);
        msg2.setVersion("1");
        JSONObject object = new JSONObject();
        object.set("token", "xxxxx1111");
        msg2.setData(JSON.toJSONString(object).getBytes());
        nettyTcpClient.send(msg2);
    }
}
