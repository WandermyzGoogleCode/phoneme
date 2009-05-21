package logiccenter;

import java.util.Observable;
import java.util.Date;

/**
 * �ṩ��GUI�Ĵ�VirtualProxy�Ľ������߲����ȡ��̳���
 * Observable�Ӷ��ܹ���GUI���Ӹý��������״̬��
 * ͨ��state��GUI֪ͨ��ǰ��״̬�����ڲ��ϸ��µĽ����ͨ��
 * updateTime��֪ͨGUI�Ƿ���Ҫ���¡�
 * 
 * ���������������get�ĺ����Ի�ȡ����ʵ����Ҫ�Ľ������Щ
 * ��Դͨ���������Դ���һ��������߳�����ȡ��
 * 
 * ��ǰ���Կ�{@link MessageBox}�����һ�������Example��
 * @author Administrator
 *
 */
public abstract class VirtualResult extends Observable {
	protected VirtualState state;//��ǰResult��ȡ��״̬
	protected Error err;//�����ǰ״̬Ϊ������ô�����¼�˳�����Ϣ
	protected Date updateTime;//��ǰResult���һ�θ��µ�ʱ�䣬��Result���ϸ��µ�ʱ������
	
	public VirtualState getState(){
		return state;
	}
	
	public Error getError(){
		return err;
	}
	
	public Date getUpdateTime(){
		return updateTime;
	}
}
