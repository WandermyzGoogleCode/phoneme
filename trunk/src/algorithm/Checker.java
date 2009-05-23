package algorithm;

/**
 * check相关的object是否合法，不如号码，邮件地址，文件名等
 * @author Administrator
 *
 */
public interface Checker {
	boolean check(Object obj);
}
