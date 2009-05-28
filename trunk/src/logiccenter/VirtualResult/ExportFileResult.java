package logiccenter.VirtualResult;

import java.rmi.RemoteException;

import algorithm.Checker;

import logiccenter.LogicCenter;

import entity.BoolInfo;

public class ExportFileResult extends OneTimeVirtualResult {
	private String fileName;
	static private Checker fileNameChecker;//TODO 当前还没有实现相关的检查类

	public ExportFileResult(String fileName, LogicCenter center) {
		super(center);
		this.fileName = fileName;
		
		//TODO 对文件名进行检查：if (!fileNameChecker.check(fileName))...
		
		thread.start();
	}

	@Override
	protected BoolInfo getResult(){
		center.getDataCenter().exportFile(fileName);
		//TODO 当前没有处理dataCenter的错误
		return new BoolInfo();
	}

}
