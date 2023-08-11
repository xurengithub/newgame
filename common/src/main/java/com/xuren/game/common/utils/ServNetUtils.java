package com.xuren.game.common.utils;

public class ServNetUtils {


    public static void copy(byte[] sourceArray, byte[] destinationArray, int length){
        for(int i = 0; i < length; i++){
            destinationArray[i] = sourceArray[i];
        }
    }

    public static void copy(byte[] sourceArray, int sourceIndex, byte[] destinationArray, int destinationIndex, int length){
        for(int i = sourceIndex; i < sourceIndex+length; i++){
            destinationArray[destinationIndex] = sourceArray[i];
            destinationIndex++;
        }

    }

    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    public static int byteArrayToInt16(byte[] b){
        return   b[1] & 0xFF |
                (b[0] & 0xFF) << 8;
    }

    public static byte[] int16ToByteArray(int a){
        return new byte[] {
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }
}
