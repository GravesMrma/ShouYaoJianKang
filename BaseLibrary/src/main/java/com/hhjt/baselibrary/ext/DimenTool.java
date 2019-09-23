package com.hhjt.baselibrary.ext;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by cdy on 2016/2/3.
 */
public class DimenTool {


    public static void gen() {

        File file = new File("./BaseLibrary/src/main/res/values/dimens.xml");
        BufferedReader reader = null;

        StringBuilder sw240 = new StringBuilder();
        StringBuilder sw320 = new StringBuilder();
        StringBuilder sw360 = new StringBuilder();
        StringBuilder sw384 = new StringBuilder();
        StringBuilder sw410 = new StringBuilder();
        StringBuilder sw411 = new StringBuilder();
        StringBuilder sw480 = new StringBuilder();
        StringBuilder sw533 = new StringBuilder();
        StringBuilder sw592 = new StringBuilder();
        StringBuilder sw600 = new StringBuilder();
        StringBuilder sw640 = new StringBuilder();
        StringBuilder sw662 = new StringBuilder();
        StringBuilder sw720 = new StringBuilder();
        StringBuilder sw768 = new StringBuilder();
        StringBuilder sw800 = new StringBuilder();
        StringBuilder sw811 = new StringBuilder();
        StringBuilder sw820 = new StringBuilder();
        StringBuilder sw960 = new StringBuilder();
        StringBuilder sw961 = new StringBuilder();
        StringBuilder sw1024 = new StringBuilder();
        StringBuilder sw1080 = new StringBuilder();
        StringBuilder sw1280 = new StringBuilder();
        StringBuilder sw1365 = new StringBuilder();

        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            int line = 1;

            while ((tempString = reader.readLine()) != null) {
                if (tempString.contains("</dimen>")) {
                    String start = tempString.substring(0, tempString.indexOf(">") + 1);
                    String end = tempString.substring(tempString.lastIndexOf("<") - 2);
                    float num = Float.valueOf(tempString.substring(tempString.indexOf(">") + 1, tempString.indexOf("</dimen>") - 2));

                    /**
                     * 根据UI画布大小比例进行换算，UI图相对分辨率为1334x750
                     * 设备默认缩放因子密度为 2  = 320 / 160
                     */
                    int density = 2;
                    int width = 750 / density;

                    /**
                     * 如果设计图尺寸为1080x1920
                     * 高分率缩放因子密度一般为 3 = 480 / 160 ，则：
                     * 可选项，根据你实际的UI设计图来定义
                     */
                    density = 3;
                    width = 1080 / density;

                    sw240.append(start).append(Math.round(num * 240 / width)).append(end).append("\n");
                    sw320.append(start).append(Math.round(num * 320 / width)).append(end).append("\n");
                    sw360.append(start).append(Math.round(num * 360 / width)).append(end).append("\n");
                    sw384.append(start).append(Math.round(num * 384 / width)).append(end).append("\n");
                    sw410.append(start).append(Math.round(num * 410 / width)).append(end).append("\n");
                    sw411.append(start).append(Math.round(num * 411 / width)).append(end).append("\n");
                    sw480.append(start).append(Math.round(num * 480 / width)).append(end).append("\n");
                    sw533.append(start).append(Math.round(num * 533 / width)).append(end).append("\n");
                    sw592.append(start).append(Math.round(num * 592 / width)).append(end).append("\n");
                    sw600.append(start).append(Math.round(num * 600 / width)).append(end).append("\n");
                    sw640.append(start).append(Math.round(num * 640 / width)).append(end).append("\n");
                    sw662.append(start).append(Math.round(num * 662 / width)).append(end).append("\n");
                    sw720.append(start).append(Math.round(num * 720 / width)).append(end).append("\n");
                    sw768.append(start).append(Math.round(num * 768 / width)).append(end).append("\n");
                    sw800.append(start).append(Math.round(num * 800 / width)).append(end).append("\n");
                    sw811.append(start).append(Math.round(num * 811 / width)).append(end).append("\n");
                    sw820.append(start).append(Math.round(num * 820 / width)).append(end).append("\n");
                    sw960.append(start).append(Math.round(num * 960 / width)).append(end).append("\n");
                    sw961.append(start).append(Math.round(num * 961 / width)).append(end).append("\n");
                    sw1024.append(start).append(Math.round(num * 1024 / width)).append(end).append("\n");
                    sw1080.append(start).append(Math.round(num * 1080 / width)).append(end).append("\n");
                    sw1280.append(start).append(Math.round(num * 1280 / width)).append(end).append("\n");
                    sw1365.append(start).append(Math.round(num * 1365 / width)).append(end).append("\n");

                } else {

                    sw240.append(tempString).append("\n");
                    sw320.append(tempString).append("\n");
                    sw360.append(tempString).append("\n");
                    sw384.append(tempString).append("\n");
                    sw410.append(tempString).append("\n");
                    sw411.append(tempString).append("\n");
                    sw480.append(tempString).append("\n");
                    sw533.append(tempString).append("\n");
                    sw592.append(tempString).append("\n");
                    sw600.append(tempString).append("\n");
                    sw640.append(tempString).append("\n");
                    sw662.append(tempString).append("\n");
                    sw720.append(tempString).append("\n");
                    sw768.append(tempString).append("\n");
                    sw800.append(tempString).append("\n");
                    sw811.append(tempString).append("\n");
                    sw820.append(tempString).append("\n");
                    sw960.append(tempString).append("\n");
                    sw961.append(tempString).append("\n");
                    sw1024.append(tempString).append("\n");
                    sw1080.append(tempString).append("\n");
                    sw1280.append(tempString).append("\n");
                    sw1365.append(tempString).append("\n");

                }
                line++;
            }
            reader.close();

            String sw240file = "./BaseLibrary/src/main/res/values-sw240dp/dimens.xml";
            String sw320file = "./BaseLibrary/src/main/res/values-sw320dp/dimens.xml";
            String sw360file = "./BaseLibrary/src/main/res/values-sw360dp/dimens.xml";
            String sw384file = "./BaseLibrary/src/main/res/values-sw384dp/dimens.xml";
            String sw410file = "./BaseLibrary/src/main/res/values-sw410dp/dimens.xml";
            String sw411file = "./BaseLibrary/src/main/res/values-sw411dp/dimens.xml";
            String sw480file = "./BaseLibrary/src/main/res/values-sw480dp/dimens.xml";
            String sw533file = "./BaseLibrary/src/main/res/values-sw533dp/dimens.xml";
            String sw592file = "./BaseLibrary/src/main/res/values-sw592dp/dimens.xml";
            String sw600file = "./BaseLibrary/src/main/res/values-sw600dp/dimens.xml";
            String sw640file = "./BaseLibrary/src/main/res/values-sw640dp/dimens.xml";
            String sw662file = "./BaseLibrary/src/main/res/values-sw662dp/dimens.xml";
            String sw720file = "./BaseLibrary/src/main/res/values-sw720dp/dimens.xml";
            String sw768file = "./BaseLibrary/src/main/res/values-sw768dp/dimens.xml";
            String sw800file = "./BaseLibrary/src/main/res/values-sw800dp/dimens.xml";
            String sw811file = "./BaseLibrary/src/main/res/values-sw811dp/dimens.xml";
            String sw820file = "./BaseLibrary/src/main/res/values-sw820dp/dimens.xml";
            String sw960file = "./BaseLibrary/src/main/res/values-sw960dp/dimens.xml";
            String sw961file = "./BaseLibrary/src/main/res/values-sw961dp/dimens.xml";
            String sw1024file = "./BaseLibrary/src/main/res/values-sw1024dp/dimens.xml";
            String sw1080file = "./BaseLibrary/src/main/res/values-sw1080dp/dimens.xml";
            String sw1280file = "./BaseLibrary/src/main/res/values-sw1280dp/dimens.xml";
            String sw1365file = "./BaseLibrary/src/main/res/values-sw1365dp/dimens.xml";

            writeFile(sw240file, sw240.toString());
            writeFile(sw320file, sw320.toString());
            writeFile(sw360file, sw360.toString());
            writeFile(sw384file, sw384.toString());
            writeFile(sw410file, sw410.toString());
            writeFile(sw411file, sw411.toString());
            writeFile(sw480file, sw480.toString());
            writeFile(sw533file, sw533.toString());
            writeFile(sw592file, sw592.toString());
            writeFile(sw600file, sw600.toString());
            writeFile(sw640file, sw640.toString());
            writeFile(sw662file, sw662.toString());
            writeFile(sw720file, sw720.toString());
            writeFile(sw768file, sw768.toString());
            writeFile(sw800file, sw800.toString());
            writeFile(sw811file, sw811.toString());
            writeFile(sw820file, sw820.toString());
            writeFile(sw960file, sw960.toString());
            writeFile(sw961file, sw961.toString());
            writeFile(sw1024file, sw1024.toString());
            writeFile(sw1080file, sw1080.toString());
            writeFile(sw1280file, sw1280.toString());
            writeFile(sw1365file, sw1365.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static void writeFile(String file, String text) throws IOException {
        CreateFileUtil.createFile(file);
        PrintWriter out = null;
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            out = new PrintWriter(new BufferedWriter(fileWriter));
            out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out != null){
                out.close();
            }
            if(fileWriter != null){
                fileWriter.close();
            }
        }
    }

    public static void main(String[] args) {
        gen();
    }
}
