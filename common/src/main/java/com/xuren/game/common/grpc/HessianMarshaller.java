package com.xuren.game.common.grpc;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.xuren.game.common.log.Log;
import io.grpc.MethodDescriptor;
import org.testng.collections.Lists;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuren
 */
public class HessianMarshaller implements MethodDescriptor.Marshaller<Object[]> {
    private Type[] types;

    public HessianMarshaller(Type... types) {
        this.types = types;
    }

    @Override
    public InputStream stream(Object... values) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Hessian2Output hessian2Output = new Hessian2Output(outputStream);
        try {
            hessian2Output.startMessage();
            hessian2Output.writeInt(values.length);
            for (var value : values) {
                hessian2Output.writeObject(value);
            }
            hessian2Output.completeMessage();
            hessian2Output.close();
        } catch (IOException e) {
            Log.system.error("stream error", e);
            throw new RuntimeException(e);
        }
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    @Override
    public Object[] parse(InputStream stream) {
        try {
            Hessian2Input hessian2Input = new Hessian2Input(new ByteArrayInputStream(stream.readAllBytes()));
            hessian2Input.startMessage();
            List<Object> list = new ArrayList<>();
            var size = hessian2Input.readInt();
            for (int i = 0; i < size; i++) {
                list.add(hessian2Input.readObject());
            }
            hessian2Input.completeMessage();
            hessian2Input.close();
            return list.toArray();
        } catch (IOException e) {
            Log.system.error("parse error", e);
            throw new RuntimeException(e);
        }
    }
}
