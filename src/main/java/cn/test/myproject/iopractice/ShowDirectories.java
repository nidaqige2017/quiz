package cn.test.myproject.iopractice;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

/**
 * FileName：Practice1
 *
 * @author lzq
 * Date：2021/1/6 4:34 下午
 * Description：
 * 遍历这个目录下所有的文件(不用遍历子目录)
 * 找出这些文件里，最大的和最小(非0)的那个文件，打印出他们的文件名
 */
@Slf4j
public class ShowDirectories {

    private static void showDirectories(String path,boolean recursion){
        File file = new File(path);
        Arrays.stream(Objects.requireNonNull(file.listFiles())).forEach(
            e -> {
                if (e.isDirectory()){
                    if (recursion){
                        showDirectories(e.getAbsolutePath(), true);
                    }else {
                        log.info("文件夹名：{},大小：{}",e.getName(),e.length());
                    }
                }else {
                    log.info("文件名:{},大小：{}",e.getName(),e.length());
                }
            }
        );
    }

    public static void main(String[] args) {
        String path = "/Users/lvzeqi/Downloads/IT";
        showDirectories(path,true);
    }
}
