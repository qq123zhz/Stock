package edu.dlnu.liuwenpeng.DataInterface;

import java.util.*;


/**
 * ��ȡ������Ϣ�Ľӿ�
 * @author Administrator
 */
public interface Data extends Iterable<DataItem> {
	
	/**
	 * ����Data����
	 */
	public void update();
	
	/**
	 * ��ȡ����Ҫ��DataItem
	 * @param s ����֤������List
	 * @return
	 */
	public DataView gets(List<String> s);
	
	/**
	 * ͨ��������ȡDataItem
	 * @param index
	 * @return
	 */
	public DataItem get(int index);
	
	/**
	 * ���������ֶ�
	 */
	public void setSortItem(String name);
	
	/**
	 * ��ȡData�ĳ���
	 * @return
	 */
	public int size();
	
	/**
	 * Dataת��ΪArrays
	 * @return
	 */
	public DataItem[] to_Array();
	
	/**
	 * 
	 */
	public List<DataItem> to_List();
	
	/**
	 * ��ȡ��ǰ���ݵ�Stream
	 * @return
	 */
	// public Stream<DataItem> stream();
	// public List<DataItem> sort();

}

/**
 * ��ȡ��ʷ��Ϣ�Ľӿ�
 * @author Administrator
 */
interface History extends Data {
	/**
	 * ָ�������ȡ��ʱ��
	 * @param date
	 */
	public void setDate(int date);
}

