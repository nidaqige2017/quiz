package cn.test.myproject.iopractice.tool;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;

/**
 * FileName：ReadFile
 *
 * @author lzq
 * Date：2021/1/6 5:30 下午
 * Description：
 */
@Slf4j
public class FileUtils {

    /**
     * 单行读文件返回分隔好的字符串
     * @param path 文件绝对路径
     * @return 文件全部内容字符串形式
     */
    public static StringBuffer readFile(String path){
            StringBuffer sb = new StringBuffer();
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String str;
            while ((str = in.readLine()) != null) {
                System.out.println(str);
                sb.append(str).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }

    /**
     * 单行读文件返回字符串数组，按行分隔
     * @param path 文件绝对路径
     * @return 文件全部内容集合形式
     */
    public static List<String> readFile4List(String path){
            List<String> list = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String str;
            while ((str = in.readLine()) != null) {
                System.out.println(str);
                list.add(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Test
    public void testWriteFile(){
        writeFile("牛逼class!","/Users/lvzeqi/Desktop/最新简历资料/aa/bb/cc.txt",true,true);
    }

    /**
     * 根据字符串写文件
     * @param content 写入内容
     * @param targetPath 目标地址
     * @param create 文件不存在时是否创建
     * @param append 是否写入还是覆盖
     */
    public static void writeFile(String content,String targetPath,boolean create,boolean append){
        try {
            File file = new File(targetPath);
            File dir = file.getParentFile();
            if (!dir.exists()){
                dir.mkdirs();
            }
            if(!file.exists()){
                if (create){
                    file.createNewFile();
                }else {
                    throw new Exception("文件不存在");
                }
            }
            FileWriter fileWriter = new FileWriter(file.getName(),append);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            bufferWriter.write(content);
            bufferWriter.flush();
            bufferWriter.close();
            log.info("文件写入成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据集合写文件
     * @param list 写入内容
     * @param targetPath 目标地址
     * @param create 文件不存在时是否创建
     * @param append 是否写入还是覆盖
     */
    public static void writeFile(List<String> list,String targetPath,boolean create,boolean append){
        try {
            File file = new File(targetPath);
            File dir = file.getParentFile();
            if (!dir.exists()){
                dir.mkdirs();
            }
            if(!file.exists()){
                if (create){
                    file.createNewFile();
                }else {
                    throw new Exception("文件不存在");
                }
            }
            FileWriter fileWriter = new FileWriter(file.getName(),append);
            BufferedWriter bufferWriter = new BufferedWriter(fileWriter);
            for (String s:list) {
                bufferWriter.write(s + "\n");
            }
            bufferWriter.flush();
            bufferWriter.close();
            log.info("文件写入成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSplitFile(){
        splitFile("/Users/lvzeqi/Desktop/最新简历资料/吕泽奇简历.pdf",
                null,6,null,true,"_");
    }

    /**
     * 拆分文件
     * 拆分的思路，先把源文件的所有内容读取到内存中，然后从内存中挨个分到子文件里
     * @param srcPath 源文件路径
     * @param targetPath 目标路径（填null代表原文件当前路径）
     * @param fileCount 分成文件个数（与eachSize二选一）
     * @param eachSize 分成的每个文件大小（与fileCount二选一）
     * @param create 如果目标路径没有是否创建
     * @param spiltSign 分隔标志
     */
    private static void splitFile(String srcPath,String targetPath,Integer fileCount,Integer eachSize,boolean create,String spiltSign) {
        if (!ifOnlyOne(fileCount,eachSize)) throw new RuntimeException("拆分模式必须二选一");
        //源目录
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) throw new RuntimeException("源文件不存在");
        if (0 == srcFile.length()) throw new RuntimeException("文件长度为0，不可拆分");
        //目的目录
        File targetFile = setTargetPath(targetPath, create, srcFile);
        //创建一个源文件字节数组
        int srcFileLength = (int) srcFile.length();
        byte[] fileContent = new byte[srcFileLength];
        // 先把文件读取到数组中
        try {
            FileInputStream fis = new FileInputStream(srcFile);
            fis.read(fileContent);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //文件名主体
        String fileNameMain = srcFile.getName() + spiltSign;
        // 判断拆分模式
        if (null != fileCount){
            if (fileCount >= 2){
                //拆成若干个文件
                eachSize = srcFileLength / fileCount;
                for (int i = 0; i < fileCount; i++) {
                    //创建文件
                    File eachFile = new File(targetFile,fileNameMain + (i + 1));
                    //写内容
                    byte[] eachContent;
                    //非最后一次
                    if (i != fileCount - 1){
                        eachContent = Arrays.copyOfRange(fileContent,eachSize * i,eachSize * (i + 1));
                    } else {
                        eachContent = Arrays.copyOfRange(fileContent, eachSize * i, fileContent.length);
                    }
                    try {
                        // 写出去
                        FileOutputStream fos = new FileOutputStream(eachFile);
                        fos.write(eachContent);
                        fos.close();
                        log.info("输出子文件{}，其大小是{}字节", eachFile.getAbsoluteFile(), eachFile.length());
                    } catch (IOException e) {
                        log.error("按份拆分失败！");
                        e.printStackTrace();
                    }
                }
            }else {
                throw new RuntimeException("拆分数量必须大于等于2");
            }
        } else {
            // 计算需要被划分成多少份子文件
            int fileNumber;
            // 文件是否能被整除得到的子文件个数是不一样的
            // (假设文件长度是25，每份的大小是5，那么就应该是5个)
            // (假设文件长度是26，每份的大小是5，那么就应该是6个)
            if (0 == fileContent.length % eachSize)
                fileNumber = fileContent.length / eachSize;
            else
                fileNumber = (fileContent.length / eachSize) + 1;

            for (int i = 0; i < fileNumber; i++) {
                File eachFile = new File(srcFile.getParent(), fileNameMain + (i + 1));
                byte[] eachContent;
                // 从源文件的内容里，复制部分数据到子文件
                // 除开最后一个文件，其他文件大小都是100k
                // 最后一个文件的大小是剩余的
                if (i != fileNumber - 1)
                    eachContent = Arrays.copyOfRange(fileContent, eachSize * i, eachSize * (i + 1));
                else // 最后一个
                    eachContent = Arrays.copyOfRange(fileContent, eachSize * i, fileContent.length);
                try {
                    // 写出去
                    FileOutputStream fos = new FileOutputStream(eachFile);
                    fos.write(eachContent);
                    // 记得关闭
                    fos.close();
                    log.info("输出子文件{}，其大小是{}字节", eachFile.getAbsoluteFile(), eachFile.length());
                } catch (IOException e) {
                    log.error("按大小拆分失败！");
                    e.printStackTrace();
                }
            }
        }
    }

    private static boolean ifOnlyOne(Object a,Object b){
        if (null == a){
            return b != null;
        }else {
            return b == null;
        }
    }

    @Test
    public void testMergeFile(){
        mergeFile("/Users/lvzeqi/Desktop/最新简历资料/吕泽奇简历.pdf_5",null,6,"_"
                ,"abc",true,false);
    }

    /**
     * 合并文件
     * @param srcPath 任意一个需要合并的文件路径
     * @param targetPath 目标路径（填null代表原文件当前路径）
     * @param fileCount 要合并的文件个数
     * @param spiltSign 分隔标记
     * @param newFileName 新文件名（null代表用原文件分隔标记前的名字）
     * @param create 目标路径不存在是否创建
     * @param delete 是否删除源文件
     */
    private static void mergeFile(String srcPath,String targetPath,int fileCount,String spiltSign,String newFileName,boolean create,boolean delete){
        //源目录
        File srcFile = new File(srcPath);
        if (!srcFile.exists()) throw new RuntimeException("源文件不存在");
        //目的目录
        File targetFile = setTargetPath(targetPath, create, srcFile);
        //创建新文件
        String mainName = srcFile.getName().split(spiltSign)[0];
        //提取后缀
        String end = findEnd(mainName, "\\.");
        //文件名
        if (null == newFileName){
            newFileName = mainName;
        } else {
            String newFileNameEnd = findEnd(newFileName, "\\.");
            if (!end.equals(newFileNameEnd)){
                newFileName = newFileName + "." + end;
            }
        }
        File newFile = new File(targetFile + "/" + newFileName);
        try {
            newFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(newFile);
            // 循环将子文件写入合并文件
            for (int i = 0; i < fileCount; i++) {
                File childFile = new File(srcFile.getParentFile() + "/" + mainName + spiltSign + (i + 1));
                InputStream fis = new FileInputStream(childFile);
                byte[] bytes = new byte[(int)childFile.length()];
                fis.read(bytes);
                fis.close();
                fos.write(bytes);
                // 删除拆分文件
                if (delete) childFile.delete();
            }
            log.info("合并完成！新文件：{},{}字节",newFile.getName(),newFile.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String findEnd(String str,String sign){
        String[] split = str.split(sign);
        System.out.println("split = " + Arrays.toString(split));
        return split[split.length - 1];
    }


    private static File setTargetPath(String targetPath, boolean create, File srcFile) {
        File targetFile;
        if (null != targetPath){
            targetFile = new File(targetPath);
            if(!targetFile.exists()){
                if (create) targetFile.mkdirs();
                else throw new RuntimeException("文件不存在");
            }
        }else {
            targetFile = srcFile.getParentFile();
        }
        return targetFile;
    }
}
