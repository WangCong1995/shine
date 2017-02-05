package com.bow.demo.module.hessian.serialize;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * @author vv
 * @since 2017/2/5.
 */
public class DemoSerialize {

    @Test
    public void test1() throws Exception {
        System.out.println(Arrays.toString(serialize('A')));
    }

    @Test
    public void test2() throws Exception {
        byte[] ary = serialize('A');
        Object a = deserialize(ary);
        System.out.println(a);
    }

    private byte[] serialize(Object object) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Hessian2Output out = new Hessian2Output(os);
        out.writeObject(object);
        out.flush();//不加会报EOFException
        return os.toByteArray();
    }

    private Object deserialize(byte[] ary) throws Exception {
        ByteArrayInputStream is = new ByteArrayInputStream(ary);
        Hessian2Input hi = new Hessian2Input(is);
        return hi.readObject();
    }

}
