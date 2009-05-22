package entity.message;

import java.io.Serializable;

/**
 * 所有服务器给客户端主动发送的东西都继承自这个接口，
 * 比如各种通知、邀请、申请等。
 * @author Administrator
 *
 */
public interface Message extends Serializable{

}
