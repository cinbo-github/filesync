package com.cinbo.filesync;

import java.io.*;
import java.util.Date;

public class TestClass {
    public void println(){
        System.out.println("打印我们的信息.");
    }
    public static void main(String[] args) throws Exception{
        String fileName="d:\\tmp\\lorem2.txt";
        BufferedWriter out = null;

        try {

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true)));
            while(true) {

                Thread.sleep(2000);
                out.write(new Date() + " 我的测试内容1\n");
                out.flush();
                break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
