package com.easyrr.EasyResponse;

import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class DemoServiceView extends JFrame implements EasyService {

	private String path;
	private EasyContext context;
	private Integer value = 5;
	private final JTextField field = new JTextField(value);

	public DemoServiceView() throws HeadlessException {
		super();
		this.path = "GUI";
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setSize(300, 600);
		this.setLayout(new GridLayout(5, 1));
		this.add(field);
		pack();
	}

	@Override
	public String getServicePath() {
		return path;
	}

	@Override
	public void updateServiceContext(EasyContext context) {
		this.context = context;
	}

	@Override
	public void configurePath(String path) {
		this.path = path;
	}

	@EasyRegistredAction(path = "increase_value")
	public void increaseValue() {
		
		value++;
		field.setText("current: "+value.intValue());
		System.out.println(value);
	}

}
