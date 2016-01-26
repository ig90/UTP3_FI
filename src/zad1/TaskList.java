package zad1;

import java.beans.*;

public class TaskList {
	
	String name;
	String data;
	private PropertyChangeListener pcl;
	
	public TaskList() {
	
	}
	public TaskList(String name) {
		this.name = name;
		data = "";
	}
	public void setPropertyChangeListener(PropertyChangeListener pcl) {
		this.pcl = pcl;
	}
	public void appendData(String line) {
		if(pcl != null)
			pcl.propertyChange(new PropertyChangeEvent(this, "data", data, data+=line));
		else
			data+=line;
	}
}
