package com.liqihua.common.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件工具类
 * @author liqihua
 * @since 2018/11/15
 */
public class SysFileUtil {

    /**
     * 获取项目绝对路径
     * 如：/opt/aa/bb.jar
     * 返回：/opt/aa
     * @return
     */
    public static String getProjectPath(){
        String jarPath = FileUtil.getAbsolutePath("").replace("!/BOOT-INF/classes!/","");
        String dir = jarPath.substring(0,jarPath.lastIndexOf("/"));
        return dir;
    }


    /**
     * 文件上传
     * @param file
     * @param dir 默认/upload
     * @return
     */
    public static String uploadFile(MultipartFile file, String dir){
        try {
            if (StrUtil.isBlank(dir)) {
                dir = "/upload";
            }
            String saveDir = getProjectPath() + dir;
            String newFileName = file.getOriginalFilename();
            String suffix = "";
            if (file.getOriginalFilename().lastIndexOf(".") != -1) {
                newFileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf("."));
                suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            }
            while (FileUtil.exist(saveDir + "/" + newFileName + suffix)) {
                newFileName = newFileName + "-1";
            }
            File parent = new File(saveDir);
            parent.mkdirs();
            File newFile = new File(saveDir, newFileName + suffix);
            newFile.createNewFile();
            file.transferTo(newFile);
            return dir + "/" + newFileName + suffix;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

}
