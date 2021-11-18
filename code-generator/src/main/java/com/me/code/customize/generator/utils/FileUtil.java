package com.me.code.customize.generator.utils;

import com.me.code.customize.generator.data.ConfigInfo;
import com.me.code.customize.generator.data.TableInfo;
import com.me.code.customize.generator.template.DomainGenerator;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.List;

/**
 * @author zhull
 * @date 2018/6/12
 * <P>文件生成器</P>
 */
public class FileUtil {
    /**
     * 写文件
     *
     * @param filePathName
     */
    public static void writeToFile(String filePathName, String content ) throws IOException {

        tool.utils.FileUtil.write( content,filePathName);
    }

}
