package com.me.code.customize.generator.data;


/**
 * @author zhull
 * @date 2018/6/13
 * <P>文件成分信息</P>
 */
public class ConfigInfo {

    /**
     * 包路径
     */
    private String modulePackagePath;

    /**
     * domain包名
     */
    private String domainPackageName;

    /**
     * mapper文件夹名
     */
    private String mapperDir;

    /**
     * domain文件名后缀(不包括文件类型)
     * eg: Po
     */
    private String domainPostfix = "";

    /**
     * dao文件名后缀(不包括文件类型)
     * eg: Dao
     */
    private String daoPostfix = "";

    /**
     * dao文件名后缀(不包括文件类型)
     * eg: Mapper
     */
    private String mapperPostfix = "";

    /**
     * domain文件后缀
     */
    public String getDomainFilePostfix() {
        return this.domainPostfix + ".java";
    }

    /**
     * dao文件后缀
     */
    public String getDaoFilePostfix() {
        return this.daoPostfix + ".java";
    }

    /**
     * mapper文件后缀
     */
    public String getMapperFilePostfix() {
        return this.mapperPostfix + ".xml";
    }

    /**
     * domain包路径
     */
    public String getDomainPackagePath() {
        return this.modulePackagePath + "." + this.domainPackageName;
    }

    public String getModulePackagePath() {
        return modulePackagePath;
    }

    public void setModulePackagePath(String modulePackagePath) {
        this.modulePackagePath = modulePackagePath;
    }

    public String getDomainPackageName() {
        return domainPackageName;
    }

    public void setDomainPackageName(String domainPackageName) {
        this.domainPackageName = domainPackageName;
    }

    public String getMapperDir() {
        return mapperDir;
    }

    public void setMapperDir(String mapperDir) {
        this.mapperDir = mapperDir;
    }

    public String getDomainPostfix() {
        return domainPostfix;
    }

    public void setDomainPostfix(String domainPostfix) {
        this.domainPostfix = domainPostfix;
    }

    public String getDaoPostfix() {
        return daoPostfix;
    }

    public void setDaoPostfix(String daoPostfix) {
        this.daoPostfix = daoPostfix;
    }

    public String getMapperPostfix() {
        return mapperPostfix;
    }

    public void setMapperPostfix(String mapperPostfix) {
        this.mapperPostfix = mapperPostfix;
    }
}
