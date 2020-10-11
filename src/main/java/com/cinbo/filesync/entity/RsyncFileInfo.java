package com.cinbo.filesync.entity;

import java.io.Serializable;

public class RsyncFileInfo implements Serializable {

    /**
     * 每次读取文件的block大小
     */
    private int blocksize;

    /**
     * 滑动检验的block大小，默认值是1024;
     */
    private int rsyncRollingCheckBlockSize = 1024;

    /**
     * 同步文件，目标文件的文件路径包含文件名
     */
    private String destFilePath;

    /**
     * 同步文件，目标文件的文件名
     */
    private String destFileName;

    private String destFileCreateTime;
    private String destFileModifyTime;


}
