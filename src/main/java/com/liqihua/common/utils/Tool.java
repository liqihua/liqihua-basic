package com.liqihua.common.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liqihua
 * @since 2018/10/12
 */
public class Tool {





    /**
     * 拷贝整个list
     * 由于hutool只有单个bean的拷贝，没有整个List的拷贝，需要封装一个list的拷贝
     * @param source
     * @param cls
     * @param <T>
     * @return
     */
    public static<T> List<T> copyList(List<?> source, Class<T> cls) {
        if(source == null || source.size() == 0){
            return new ArrayList<>();
        }
        List<T> targetList = new ArrayList<>(source.size());
        for (Object obj : source) {
            T target = null;
            if(obj != null){
                target = ReflectUtil.newInstance(cls);
                BeanUtil.copyProperties(obj, target);
            }
            targetList.add(target);
        }
        return targetList;
    }


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
    public static String uploadFile(MultipartFile file,String dir){
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
