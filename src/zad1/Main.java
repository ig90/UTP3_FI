/**
 *
 *  @author Filipiuk Igor S7334
 *
 */

package zad1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.beans.*;
import java.lang.reflect.Method;
import java.util.Vector;
import java.util.concurrent.*;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class Main extends JFrame implements ActionListener, ListSelectionListener, PropertyChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int n = 15;
	int k = 0;
	private DefaultListModel<TaskList> dataModel;
	private JButton stop;
	private JButton result;
	private JButton start;
	private JTextArea ta;

	Main() {
		dataModel = new DefaultListModel<TaskList>();
		JList<TaskList> list = new JList<TaskList>(dataModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener((ListSelectionListener) this);
		list.setCellRenderer(new ListCellRenderer<TaskList>() {

			@Override
			public Component getListCellRendererComponent(
			JList<? extends TaskList> list, TaskList value, int index, boolean isSelected, boolean cellHasFocus) {
				
			JLabel l = new JLabel(value.name);
				
				if (isSelected) {
					
					l.setOpaque(true);
				}
					return l;
			}
		});
		add(new JScrollPane(list), "North");
		ta = new JTextArea(20, 30);
		add(new JScrollPane(ta), "Center");
		JPanel p = new JPanel();
		start= new JButton("Start");
		start.addActionListener(this);
		p.add(start);
		stop = new JButton("Stop selected");
		stop.setEnabled(false);
		stop.setActionCommand("Stop");
		stop.addActionListener(this);
		p.add(stop);
		result = new JButton("Selected result");
		result.setEnabled(false);
		result.setActionCommand("Result");
		result.addActionListener(this);
		p.add(result);
		add(p, "South");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		try {
			Method m = this.getClass().getDeclaredMethod("task" + cmd);
			m.invoke(this);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	class TaskSum implements Callable<Integer> {

		private int num, limit;

		public TaskSum(int num, int limit) {
			this.num = num;
			this.limit = limit;
			dataModel.addElement(new TaskList("Task "+num));
		}

		public Integer call() throws Exception {
			int sum = 0;
			for (int i = 1; i <= limit; i++) {
				if (Thread.currentThread().isInterrupted()) {
					dataModel.get(num-1).appendData("execution interrupted\n");
					return null;
				}
				sum += i;
				dataModel.get(num-1).appendData("part result = " + sum + '\n');
				Thread.sleep(1000);
			}
			dataModel.get(num-1).appendData("finished execution\n");
			return sum;
		}
	};

	Vector<Future<Integer>> task = new Vector<Future<Integer>>();
	ExecutorService exec = Executors.newFixedThreadPool(4);
	
	private int selectedIndex;

	public void taskStart() {
		try {
			task.addElement(exec.submit(new TaskSum(++k, 15)));
		} catch (RejectedExecutionException exc) {

			return;
		}
	}

	public void taskResult() {
		String msg = "";
		if(selectedIndex < 0)
			return;
		if (task.get(selectedIndex).isCancelled())
			msg = "Task cancelled.";
		else if (task.get(selectedIndex).isDone()) {
			try {
				msg = "Done. Result = " + task.get(selectedIndex).get();
			} catch (Exception exc) {
				msg = exc.getMessage();
			}
		} else
			msg = "Task is running";
		JOptionPane.showMessageDialog(null, msg);
	}

	public void taskStop() {
		if(selectedIndex < 0)
			return;
		task.get(selectedIndex).cancel(true);
		stop.setEnabled(false);
	}

	public static void main(String[] args) {
		new Main();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(selectedIndex >= 0)
			dataModel.get(selectedIndex).setPropertyChangeListener(null);
		selectedIndex = ((JList<String>)e.getSource()).getSelectedIndex();
		stop.setEnabled(selectedIndex >= 0 && !task.get(selectedIndex).isCancelled() && !task.get(selectedIndex).isDone());
		result.setEnabled(selectedIndex >= 0);
		if(selectedIndex >= 0) {
			ta.setText(dataModel.get(selectedIndex).data);
			dataModel.get(selectedIndex).setPropertyChangeListener(this);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		ta.setText((String) evt.getNewValue());
	}


}
