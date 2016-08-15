package edu.dlnu.liuwenpeng.DataInterface;

import java.util.List;

/**
 * �洢�������ݵĽӿ�
 * @author Administrator
 */
public interface DataItem extends Comparable<DataItem> { 
	
	/**
	 * ͨ��������ȡ����
	 * @param index
	 * @return
	 */
	public String get(int index);
	
	/** 
	 * ͨ���ַ������ƻ�ȡ����  
	 * @param name 
	 * @return
	 */
	public String get(String name);
	
	/**
	 * ������ֵ
	 * @param name
	 * @param value
	 */
	public void set(String name, String value);
	
	/**
	 * ������ֵ
	 * @param name
	 * @param value
	 */
	public void set(int index, String value);
	
	/**
	 * ��ȡ�����ֶ�
	 * @return
	 */
	public List<String> getAll();
}