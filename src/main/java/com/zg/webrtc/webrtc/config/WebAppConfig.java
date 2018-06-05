package com.zg.webrtc.webrtc.config;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;


@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    //获取配置文件中图片的路径
    @Value("${cbs.imagesPath}")
    private String mImagesPath;

    //访问图片方法
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        File imageTargetFile = new File(mImagesPath.substring(5,mImagesPath.length()-1));
        if (!imageTargetFile.exists()) {
            if (imageTargetFile.mkdir()) {
                System.out.println("创建图片服务器目录成功");
            } else {
                System.out.println("创建图片服务器目录失败");
            }
        }

        if (mImagesPath.equals("") || mImagesPath.equals("${cbs.imagesPath}")) {
            String imagesPath = WebAppConfig.class.getClassLoader().getResource("").getPath();
            if (imagesPath.indexOf(".jar") > 0) {
                imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
            } else if (imagesPath.indexOf("classes") > 0) {
                imagesPath = "file:" + imagesPath.substring(0, imagesPath.indexOf("classes"));
            }
            imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/")) + "/upload/";
            mImagesPath = imagesPath;
        }


        LoggerFactory.getLogger(WebAppConfig.class).info("imagesPath=" + mImagesPath);
        registry.addResourceHandler("/upload/**").addResourceLocations(mImagesPath);
        super.addResourceHandlers(registry);
    }
}
