package com.xuren.game.common.proto;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.xuren.game.common.utils.ServNetUtils;

public class MsgBase {
    public String protoName;

    public String getProtoName() {
        return protoName;
    }

    public void setProtoName(String protoName) {
        this.protoName = protoName;
    }

    public static MsgBase Decode(String protoName, byte[] bytes, int offset, int count){
        String s = new String(bytes, offset, count);
        MsgBase msgBase = JSONObject.parseObject(s, MsgBase.class);
        return msgBase;
    }

    public static MsgBase decode(byte[] bytes) {
        return JSONObject.parseObject(new String(bytes), MsgBase.class);
//        return JSONObject.parseObject(bytes, MsgBase.class, Feature.values());
    }

    //编码协议名（2字节长度+字符串）
    public static byte[] EncodeName(MsgBase msgBase){
        //名字bytes和长度
        byte[] nameBytes = msgBase.protoName.getBytes();
        int len = nameBytes.length;
        //申请bytes数值
        byte[] bytes = new byte[4+len];
        //组装2字节的长度信息
        byte[] s = ServNetUtils.intToByteArray(len);
        for (int i=0;i<s.length;i++){
            bytes[i] = s[i];
        }
//        bytes[0] = (byte)(len%256);
//        bytes[1] = (byte)(len/256);
        //组装名字bytes
        ServNetUtils.copy(nameBytes, 0, bytes, 4, len);

        return bytes;
    }

    public static byte[] Encode(MsgBase msgBase){
        return JSON.toJSONString(msgBase).getBytes();
    }
    //解码协议名（2字节长度+字符串）
    public static String DecodeName(byte[] bytes, int offset,int count){
        count = 0;
        //必须大于2字节
        if(offset + 2 > bytes.length){
            return "";
        }
        //读取长度
        int len = (int)((bytes[offset+1] << 8 )| bytes[offset] );
        if(len <= 0){
            return "";
        }
        //长度必须足够
        if(offset + 2 + len > bytes.length){
            return "";
        }
        //解析
        count = 2+len;
        String name = new String(bytes, offset+2, len);
        return name;
    }
}
