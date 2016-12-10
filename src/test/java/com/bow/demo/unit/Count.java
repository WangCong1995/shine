package com.bow.demo.unit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author vv
 * @since 2016/12/10.
 */
public class Count {

    private int total=0;

    public void print(){
        System.out.println("total "+ total);
    }

    public void codeCount(File file){
        if(file.isDirectory()){
            String dir = file.getName();
            if("test".equals(dir) || "target".equals(dir)){
                return;
            }
            for(File f : file.listFiles()){
                codeCount(f);
            }
        }else{
            if(file.getName().endsWith(".java")){
                int count = 0;
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(file);
                    InputStreamReader reader = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(reader);
                    while(br.readLine()!=null){
                        count++;
                    }
//                    System.out.println(file.getName()+" lines "+count);
                    total += count;
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fis!= null){
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        File root = new File(".");
        Count count = new Count();
        count.codeCount(root);
        count.print();
    }
}
