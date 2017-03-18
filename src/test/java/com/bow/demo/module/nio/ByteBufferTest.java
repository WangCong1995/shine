package com.bow.demo.module.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

/**
 * @author vv
 * @since 2017/2/25.
 */
public class ByteBufferTest {


    @Test
    public void testSlice(){
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for(int i=0; i<buffer.capacity(); i++){
            buffer.put((byte)i);
        }

        //创建子缓冲区
        buffer.position(2);
        buffer.limit(4);
        ByteBuffer sliceBuffer = buffer.slice();
        for(int i=0; i< sliceBuffer.capacity(); i++){
            byte b = sliceBuffer.get(i);
            sliceBuffer.put(i, (byte) (b*10));
        }

        buffer.position(0);
        buffer.limit(buffer.capacity());
        while(buffer.remaining()>0){
            System.out.println(buffer.get());
        }
    }

    /**
     * 原缓冲区的内容发生改变，readOnlyBuffer也会随之改变(视图缓冲区)
     */
    @Test
    public void testReadOnly(){
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for(int i=0; i<buffer.capacity(); i++){
            buffer.put((byte)i);
        }

        //创建只读缓冲区
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        //改变媛缓冲区的内容
        for(int i=0; i< buffer.capacity(); i++){
            byte b = buffer.get(i);
            buffer.put(i, (byte) (b*10));
        }

        readOnlyBuffer.position(0);
        readOnlyBuffer.limit(buffer.capacity());
        while(readOnlyBuffer.remaining()>0){
            System.out.println(readOnlyBuffer.get());
        }
    }

    @Test
    public void testChannel() throws Exception{
        FileChannel fci = new FileInputStream("test.txt").getChannel();
        FileChannel fco = new FileOutputStream("test-out.txt").getChannel();

        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);

        while(true){
            buffer.clear();
            int r = fci.read(buffer);
            if(r == -1){
                break;
            }
            buffer.flip();
            fco.write(buffer);
        }
    }

    @Test
    public void testMappedByteBuffer() throws Exception{
        int start = 2;
        int size = 5;
        RandomAccessFile raf = new RandomAccessFile("test.txt", "rw");
        FileChannel fc = raf.getChannel();

        MappedByteBuffer mb = fc.map(FileChannel.MapMode.READ_WRITE, start, size);
        mb.put(0, (byte) 97);
        mb.put(1, (byte) 65);
        mb.put(2, (byte) 65);
        mb.put(3, (byte) 65);
        mb.put(4, (byte) 122);
        raf.close();
    }

    @Test
    public void testPut(){
        ByteBuffer buffer = ByteBuffer.allocate(5);
        buffer.put(0, (byte) 1);

        ByteBuffer b1 = ByteBuffer.allocate(2);
        b1.put(0, (byte) 20);
        b1.put(1, (byte) 21);

        buffer.put(b1);

        buffer.position(0);
        buffer.limit(buffer.capacity());
        for(int i=0; i< buffer.capacity(); i++){
            System.out.println(buffer.get(i));
        }
    }

}
