package puzzle;
/**
* 定义XML文档解析的接口
* @author tao
* @version 1.0
*/

public interface XmlDocument{
    
    /**
     * 解析XML文档
     * 
     * @param fileName
     *            文件全路径名称
     */
    public void parserXml(String fileName);
}