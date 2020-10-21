import com.sun.speech.freetts.*;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.ScrollPane;


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.sound.midi.VoiceStatus;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.event.ListSelectionListener;

//import com.sun.org.apache.bcel.internal.classfile.SourceFile;

//import jdk.internal.joptsimple.internal.Strings;

import javax.swing.event.ListSelectionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class DictionaryApplication extends JFrame {
    private static final String VOICENAME ="kevin16";

    private JPanel contentPane;
    private JTextField input;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DictionaryApplication frame = new DictionaryApplication();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     * @throws IOException
     */
    public DictionaryApplication() throws IOException {
        DictionaryCommandLine dc = new DictionaryCommandLine();

        dc.insertFromFile();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(Color.PINK);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        input = new JTextField();

        input.setBounds(7, 73, 122, 35);
        contentPane.add(input);
        input.setColumns(10);
        JList list = new JList();
        list.setBounds(10, 119, 108, 131);
        contentPane.add(list);
        //ScrollPane srPane = new ScrollPane();
        //srPane.setBounds(10,42,108,183);
        //contentPane.add(srPane);
        //srPane.add(list);

        JTextPane output = new JTextPane();
        output.setBounds(248, 56, 160, 68);
        contentPane.add(output);
        JButton buttonEnter = new JButton("Enter");

        JButton buttonAdd = new JButton("add");
        buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String string=JOptionPane.showInputDialog(" nhap tu");
                while(string.length()==0)
                    string=JOptionPane.showInputDialog(" nhap tu");
                String  a =JOptionPane.showInputDialog(" nhap nghia");
                while(a.length()==0)
                    a=JOptionPane.showInputDialog(" nhap nghia");
                if(dc.addWord(string, a))
                    JOptionPane.showMessageDialog(null, "da co tu nay");
                else
                    JOptionPane.showMessageDialog(null, "nhap ui");
            }
        });
        buttonAdd.setBounds(149, 202, 89, 23);
        contentPane.add(buttonAdd);


        JButton buttonRemove = new JButton("Remove");
        buttonRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String t=JOptionPane.showInputDialog("nhap tu");
                while(t.length()==0)
                    t=JOptionPane.showInputDialog("nhap tu");

                if(dc.removeWord(t)) JOptionPane.showMessageDialog(null, "xoa ui");
                else JOptionPane.showMessageDialog(null, "k co tu nay");
            } 
        });
        buttonRemove.setBounds(248, 202, 89, 23);
        contentPane.add(buttonRemove);

        JButton buttonFix = new JButton("Fix");
        buttonFix.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String string=JOptionPane.showInputDialog(" nhap tu");
                while(string.length()==0)
                    string=JOptionPane.showInputDialog(" nhap tu");
                String  a =JOptionPane.showInputDialog(" nhap nghia");
                while(a.length()==0)
                    a=JOptionPane.showInputDialog(" nhap nghia");
                if(dc.fixWord(string, a))
                    JOptionPane.showMessageDialog(null, "da fix");
                else JOptionPane.showMessageDialog(null, "k co tu nay");
            }
        });
        buttonFix.setBounds(248, 168, 89, 23);
        contentPane.add(buttonFix);

        JButton buttonSave = new JButton("Save");
        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    dc.exportToFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        buttonSave.setBounds(149, 168, 89, 23);
        contentPane.add(buttonSave);

        JButton buttontest = new JButton("test");
        buttontest.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		  System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
                  Voice voice;
                  VoiceManager vm= VoiceManager.getInstance();
                  voice=vm.getVoice("kevin16");
                  voice.allocate();
                  try{
                      voice.speak(input.getText());
                  }
                  catch(Exception e){}
//              
        	}
        });
       
       

        buttontest.setBounds(373, 11, 38, 23);
        contentPane.add(buttontest);
        ImageIcon icon = new ImageIcon("images/loa1.png");
        buttontest.setIcon(icon);
        
        buttonEnter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String word = input.getText();
                output.setText("");
                output.setText(dc.dictionaryLookup(word));
            }
        });
        buttonEnter.setBounds(139, 85, 74, 23);
        contentPane.add(buttonEnter);

        JLabel lblNewLabel = new JLabel("T\u1EEA \u0110I\u1EC2N");
        lblNewLabel.setBackground(Color.BLACK);
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblNewLabel.setForeground(Color.BLUE);
        lblNewLabel.setBounds(7, 11, 132, 28);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Nh\u1EADp t\u1EEB");
        lblNewLabel_1.setBackground(Color.WHITE);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1.setBounds(17, 49, 91, 23);
        contentPane.add(lblNewLabel_1);
        
        JButton btnNewButton = new JButton("Translate");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0)  {
        		String text=input.getText();
        		try {
					String a= dc .api("en", "vi", text);
					output.setText(a);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnNewButton.setForeground(Color.BLUE);
        btnNewButton.setBounds(139, 39, 103, 35);
        contentPane.add(btnNewButton);
        
        JScrollBar scrollBar = new JScrollBar();
        scrollBar.setBounds(116, 119, 17, 131);
        contentPane.add(scrollBar);
        


        input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                DefaultListModel model =new DefaultListModel();
                LinkedList<String> s =new LinkedList<String>();
                String text = input.getText();
                if((int)e.getKeyChar()==8 && text.length()>=1)
                    text=text.substring(0,text.length());
                else
                    text+=e.getKeyChar();
                s= dc.searchWord(text);
                for(String a:s)
                    model.addElement(a);
                list.setModel(model);

            }
        });
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent arg0) {
                String s = (String)list.getSelectedValue();
                input.setText(s);
                output.setText("");
            }
        });
//        buttontest.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent arg0) {
//                Voice voice;
//                VoiceManager vm= VoiceManager.getInstance();
//                voice=vm.getVoice("kevin16");
//                voice.allocate();
//                try{
//                    voice.speak(input.getText());
//                }
//                catch(Exception e){}
//            }
//        });
       /* buttontest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
				Voice voice;
				voice = VoiceManager.getInstance().getVoice("Kevin16");
				if(voice != null) {
					voice.allocate();
					voice.speak(input.getText());
				}
				
			}
		});*/
    }
}
