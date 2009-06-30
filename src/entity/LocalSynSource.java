package entity;

/**
 * 本地获取用户信息的资源接口，可以是制定一个Outlook，或者一个本地文件，或者一个数据库链接
 * @author Administrator
 *
 */
public interface LocalSynSource {
	String getName();
}
