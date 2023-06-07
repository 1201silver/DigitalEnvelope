package wcs.main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.crypto.NoSuchPaddingException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import wcs.key.KeyManagement;
import wcs.signing.DigitEnvelope;
import wcs.signing.DigitSign;

public class MainFrame extends JFrame implements ActionListener{

	private JPanel panel1;
	private JPanel panel2;
	
	private JButton genSenderKeyBtn;
	private JButton genReceiverKeyBtn;
	private JButton getDataFileBtn;
	private JButton genSignBtn;
	private JButton genEnvelopeBtn;
	private JButton verifyBtn;
	
	private JLabel lb1;
	private JLabel lb2;
	
	private JLabel reskey1;
	private JLabel reskey2;
	private JTextField dataFileText;
	private JLabel ressign;
	private JLabel resenv;
	private JLabel result;
	
	private StringBuffer dataFilePath;
	private StringBuffer senderPublicFile = new StringBuffer();
	private StringBuffer senderPrivateFile = new StringBuffer();
	private StringBuffer secretKeyFile = new StringBuffer();
	private StringBuffer receiverPublicFile;
	private StringBuffer receiverPrivateFile;
	private StringBuffer signDataFile; 
	private StringBuffer encryptEnvelopeFile;
	private StringBuffer encryptObjFile;
	
	/**
	 * Create the frame.
	 */
	public MainFrame() {
	
		setTitle("���� ����");
		setSize(1280, 720);
		
		setLayout(new GridLayout(1, 2));
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		panel1 = new JPanel();
		panel2 = new JPanel();
		
		panel1.setLayout(null);
		
		lb1 = new JLabel();
		lb1.setText("�߽���");
		lb1.setBounds(250, 40, 150, 20);
		lb1.setFont(new Font(null, Font.BOLD, 20));
		panel1.add(lb1);
		
		genSenderKeyBtn = new JButton("�߽��� Ű ����");
		genSenderKeyBtn.setBounds(30, 100, 150, 40);
		genSenderKeyBtn.setBackground(new Color(233, 247, 222));
		genSenderKeyBtn.setActionCommand("genKeyBtn1");
		genSenderKeyBtn.addActionListener(this);
		panel1.add(genSenderKeyBtn);
		
		reskey1 = new JLabel();
		reskey1.setBounds(190, 110, 200, 20);
		reskey1.setFont(new Font(null, Font.PLAIN, 15));
		panel1.add(reskey1);
		
		dataFileText = new JTextField();
		dataFileText.setBounds(30, 200, 350, 40);
		panel1.add(dataFileText);

		getDataFileBtn = new JButton("������ ���� ����");
		getDataFileBtn.setBounds(390, 200, 150, 40);
		getDataFileBtn.setBackground(new Color(233, 247, 222));
		getDataFileBtn.setActionCommand("getDataFileBtn");
		getDataFileBtn.addActionListener(this);
		panel1.add(getDataFileBtn);
		
		genSignBtn = new JButton("���ڼ��� ����");
		genSignBtn.setBounds(30, 300, 150, 40);
		genSignBtn.setBackground(new Color(233, 247, 222));
		genSignBtn.setActionCommand("genSignBtn");
		genSignBtn.addActionListener(this);
		panel1.add(genSignBtn);
		
		ressign = new JLabel();
		ressign.setBounds(190, 310, 200, 20);
		panel1.add(ressign);
		
		genEnvelopeBtn = new JButton("���ں��� ����");
		genEnvelopeBtn.setBounds(30, 400, 150, 40);
		genEnvelopeBtn.setBackground(new Color(233, 247, 222));
		genEnvelopeBtn.setActionCommand("genEnvelopeBtn");
		genEnvelopeBtn.addActionListener(this);
		panel1.add(genEnvelopeBtn);
		
		resenv = new JLabel();
		resenv.setBounds(190, 410, 200, 20);
		panel1.add(resenv);
		
		panel2.setLayout(null);
		
		lb2 = new JLabel();
		lb2.setText("������");
		lb2.setBounds(250, 40, 150, 20);
		lb2.setFont(new Font(null, Font.BOLD, 20));
		panel2.add(lb2);
		
		JSeparator s = new JSeparator();
        s.setOrientation(SwingConstants.VERTICAL);
        s.setBounds(0, 10, 1, 650);
        s.setForeground(Color.GRAY);
        panel2.add(s);
        
        genReceiverKeyBtn = new JButton("������ Ű ����");
        genReceiverKeyBtn.setBounds(30, 130, 150, 40);
        genReceiverKeyBtn.setBackground(new Color(201, 231, 248));
        genReceiverKeyBtn.setActionCommand("genKeyBtn2");
        genReceiverKeyBtn.addActionListener(this);
		panel2.add(genReceiverKeyBtn);
		
		reskey2 = new JLabel();
		reskey2.setBounds(190, 140, 200, 20);
		panel2.add(reskey2);
        
		verifyBtn = new JButton("�����ϱ�");
		verifyBtn.setBounds(30, 350, 150, 40);
		verifyBtn.setBackground(new Color(201, 231, 248));
		verifyBtn.setActionCommand("verifyBtn");
		verifyBtn.addActionListener(this);
		panel2.add(verifyBtn);
		
		result = new JLabel();
		result.setBounds(190, 360, 200, 20);
		panel2.add(result);

		add(panel1);
		add(panel2);
	    
	    setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		String btnSrc = ae.getActionCommand();
		
//		senderPublicFile = "publicS.key";
//		senderPrivateFile = "privateS.key";
//		secretKeyFile = "secret.key";
//		signDataFile = "signature.bin";
//		receiverPublicFile = "publicR.key";
//		receiverPrivateFile = "privateR.key";
//		encryptEnvelopeFile = "encryptSecretKeyFile.bin";
//		encryptObjFile = "encryptObjFile.bin";
		
		DigitEnvelope digitEnvelope = new DigitEnvelope();
		
		switch(btnSrc) {
			case "genKeyBtn1":
				
				try {
					senderPublicFile = new StringBuffer(JOptionPane.showInputDialog("����Ű ���� �̸�"));
					senderPrivateFile = new StringBuffer(JOptionPane.showInputDialog("�缳Ű ���� �̸�"));
					secretKeyFile = new StringBuffer(JOptionPane.showInputDialog("���Ű ���� �̸�"));

					KeyManagement.genAsymmetricKey(senderPrivateFile, senderPublicFile);
					KeyManagement.genSymmetricKey(secretKeyFile);
					
					reskey1.setText("Ű ������ �����Ͽ����ϴ�.");
					
				} catch (NullPointerException e) {
					System.out.println("���� �Է��� ����߽��ϴ�.");
					reskey1.setText("���� �Է��� ����߽��ϴ�.");
					return;
					
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				
				break;
			case "genKeyBtn2":
				
				try {
					receiverPrivateFile = new StringBuffer(JOptionPane.showInputDialog("����Ű ���� �̸�"));
					receiverPublicFile = new StringBuffer(JOptionPane.showInputDialog("�缳Ű ���� �̸�"));
					
					KeyManagement.genAsymmetricKey(receiverPrivateFile, receiverPublicFile);
					
					reskey2.setText("Ű ������ �����Ͽ����ϴ�.");
					
				} catch (NullPointerException e) {
					reskey2.setText("���� �Է��� ����߽��ϴ�.");
					System.out.println("���� �Է��� ����߽��ϴ�.");
					return;
					
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				
				break;
			case "getDataFileBtn":
				
				JFileChooser fileChoose = new JFileChooser();
				
				int res = fileChoose.showOpenDialog(null);
				
				if(res == JFileChooser.APPROVE_OPTION) {
					dataFilePath = new StringBuffer(fileChoose.getSelectedFile().getPath());
//					dataFile = fileChoose.getSelectedFile().getName();
					System.out.println(dataFilePath);
					dataFileText.setText(dataFilePath.toString());
					
				} else {
					JOptionPane.showMessageDialog(null, "������ �������� �ʾҽ��ϴ�.");
					return;
				}
				
				break;
			case "genSignBtn":
				
				try {
					signDataFile = new StringBuffer(JOptionPane.showInputDialog("���ڼ��� ���� �̸�"));
					
					DigitSign.sign(dataFilePath, senderPrivateFile, signDataFile);
					ressign.setText("���ڼ��� ������ �����Ͽ����ϴ�.");
					
				} catch (NullPointerException e) {
					ressign.setText("���� �Է��� ����߽��ϴ�.");
					System.out.println("���� �Է��� ����߽��ϴ�.");
					return;
					
				} catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException e) {
					e.printStackTrace();
					ressign.setText("���ڼ��� ������ �����Ͽ����ϴ�.");
				}
				break;
			case "genEnvelopeBtn":
				
				try {
					encryptObjFile = new StringBuffer(JOptionPane.showInputDialog("��ȣȭ�� ������ ���� �̸�"));
					encryptEnvelopeFile = new StringBuffer(JOptionPane.showInputDialog("��ȣȭ�� ���ں��� ���� �̸�"));
					
					digitEnvelope.encryption(dataFilePath, signDataFile, senderPublicFile, secretKeyFile, receiverPublicFile,
												encryptObjFile, encryptEnvelopeFile);
					resenv.setText("���ں��� ������ �����Ͽ����ϴ�.");
					
				} catch(NullPointerException e) {
					resenv.setText("���� �Է��� ����߽��ϴ�.");
					System.out.println("���� �Է��� ����߽��ϴ�.");
					
				} catch (InvalidKeyException e) {
					e.printStackTrace();
					resenv.setText("���ڼ��� ������ �����Ͽ����ϴ�.");
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
					resenv.setText("���ڼ��� ������ �����Ͽ����ϴ�.");
				} catch (NoSuchPaddingException e) {
					e.printStackTrace();
					resenv.setText("���ڼ��� ������ �����Ͽ����ϴ�.");
				}
				
				break;
			case "verifyBtn":
				
				try {
					
					if(encryptEnvelopeFile == null || encryptObjFile == null || receiverPrivateFile == null || senderPublicFile == null) {
						result.setText("������ �������� �ʽ��ϴ�.");
						
					} else {
						digitEnvelope.decryption(encryptEnvelopeFile, encryptObjFile, receiverPrivateFile, senderPublicFile);
						result.setText("������ �����Ͽ����ϴ�.");
					}
					
				} catch (InvalidKeyException e) {
					result.setText("������ �����Ͽ����ϴ�.");
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					result.setText("������ �����Ͽ����ϴ�.");
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					result.setText("������ �����Ͽ����ϴ�.");
					e.printStackTrace();
				} catch (SignatureException e) {
					result.setText("������ �����Ͽ����ϴ�.");
					e.printStackTrace();
				}
				
				try {
					senderPublicFile.delete(0, senderPublicFile.length());
					senderPrivateFile.delete(0, senderPrivateFile.length());
					secretKeyFile.delete(0, secretKeyFile.length());
					receiverPublicFile.delete(0, receiverPublicFile.length());
					receiverPrivateFile.delete(0, receiverPrivateFile.length());
					signDataFile.delete(0, signDataFile.length());
					encryptEnvelopeFile.delete(0, encryptEnvelopeFile.length());
					encryptObjFile.delete(0, encryptObjFile.length());
					
				} catch (NullPointerException e) {
					result.setText("������ �������� �ʽ��ϴ�.");
				}
				
				
				break;
		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
