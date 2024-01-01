import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableModel;
import javax.swing.text.TabableView;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class users {

	private JFrame frmCrudOperationSwing;
	private JTextField txtId;
	private JTextField txtName;
	private JTextField txtAge;
	private JTextField txtCity;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					users window = new users();
					window.frmCrudOperationSwing.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public users() {
		initialize();
		Connect();
		loadData();
	}
	
	//Database Connection
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/dbSatheesh";
			String username = "root";
			String password = "Sat@123";
			con = DriverManager.getConnection(url, username, password);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	//Clear All
	public void clear() {
		txtId.setText("");
		txtName.setText("");
		txtAge.setText("");
		txtCity.setText("");
		txtName.requestFocus();
	}
	
	//Load Table
	public void loadData() {
		try {
			pst = con.prepareStatement("SELECT * FROM users");
			rs = pst.executeQuery();
			table.setModel(Dbutils.resultSetToTableModel(rs));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCrudOperationSwing = new JFrame();
		frmCrudOperationSwing.setTitle("CRUD Operation Swing MySQL");
		frmCrudOperationSwing.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		frmCrudOperationSwing.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("User Management System");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 21, 244, 39);
		frmCrudOperationSwing.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(20, 70, 374, 278);
		frmCrudOperationSwing.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Id");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 20, 45, 23);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Name");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(10, 53, 45, 23);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Age");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_2.setBounds(10, 86, 45, 23);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("City");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1_3.setBounds(10, 119, 45, 23);
		panel.add(lblNewLabel_1_3);
		
		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtId.setBounds(65, 20, 272, 26);
		panel.add(txtId);
		txtId.setColumns(10);
		
		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtName.setColumns(10);
		txtName.setBounds(65, 57, 272, 26);
		panel.add(txtName);
		
		txtAge = new JTextField();
		txtAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtAge.setColumns(10);
		txtAge.setBounds(65, 90, 272, 26);
		panel.add(txtAge);
		
		txtCity = new JTextField();
		txtCity.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtCity.setColumns(10);
		txtCity.setBounds(65, 123, 272, 26);
		panel.add(txtCity);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = txtId.getText();
				String name = txtName.getText();
				String age = txtAge.getText();
				String city = txtCity.getText();
				
				if(name == null || name.isEmpty() || name.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Valid Name");
					txtName.requestFocus();
					return;
				}
				if(age == null || age.isEmpty() || age.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Valid Age");
					txtAge.requestFocus();
					return;
				}
				if(city == null || city.isEmpty() || city.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Valid City");
					txtCity.requestFocus();
					return;
				}
				
				if(txtId.getText().isEmpty()) {
					try {
						String sql = "INSERT INTO users (NAME, AGE, CITY) values (?, ?, ?)";
						pst = con.prepareStatement(sql);
						pst.setString(1, name);
						pst.setString(2, age);
						pst.setString(3, city);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Data Inserted Successfully");
						clear();
						loadData();
					}
					catch(SQLException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSave.setBounds(62, 162, 85, 31);
		panel.add(btnSave);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//UpDate Details
				String id = txtId.getText();
				String name = txtName.getText();
				String age = txtAge.getText();
				String city = txtCity.getText();
				
				if(name == null || name.isEmpty() || name.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter Valid Name!");
					txtName.requestFocus();
					return;
				}
				if(age == null || age.isEmpty() || age.trim().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter valid Age!");
					txtAge.requestFocus();
					return;
				}
				if(city == null || city.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please Enter valid City!");
					txtCity.requestFocus();
					return;
				}
				
				if(!txtId.getText().isEmpty()) {
					try {
						String sql = "UPDATE users set NAME = ?, AGE = ?, CITY = ? WHERE ID = ?";
						pst = con.prepareStatement(sql);
						pst.setString(1, name);
						pst.setString(2, age);
						pst.setString(3, city);
						pst.setString(4, id);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "Data Updated Successfully");
						clear();
						loadData();
					}
					catch(SQLException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(157, 162, 85, 31);
		panel.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Delete Details
				String id = txtId.getText();
				if(!txtId.getText().isEmpty()) {
					
					int result = JOptionPane.showConfirmDialog(null, "Are You Sure! Want to Delete?", "Delete", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					if(result == JOptionPane.YES_OPTION) {
						try {
							String sql = "DELETE FROM users WHERE ID = ?";
							pst = con.prepareStatement(sql);
							pst.setString(1, id);
							pst.executeUpdate();
							JOptionPane.showMessageDialog(null, "Data Deleted Successfully");
							clear();
							loadData();
						}
						catch(SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(252, 162, 85, 31);
		panel.add(btnDelete);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(423, 70, 721, 643);
		frmCrudOperationSwing.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				TableModel model = table.getModel();
				//ID NAME AGE CITY
				txtId.setText(model.getValueAt(index, 0).toString());
				txtName.setText(model.getValueAt(index, 1).toString());
				txtAge.setText(model.getValueAt(index, 2).toString());
				txtCity.setText(model.getValueAt(index, 3).toString());
			}
		});
		table.setRowHeight(30);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		frmCrudOperationSwing.setBounds(100, 100, 1186, 760);
		frmCrudOperationSwing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
