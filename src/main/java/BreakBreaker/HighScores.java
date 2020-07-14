package BreakBreaker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class HighScores extends JFrame implements KeyListener {

    private JButton submit ;
    private JTextField nameTf ,scoreTf;
    private JLabel title;
    ArrayList<Player> arrayList ;
    private DefaultTableModel model;


    HighScores(final int scoreOfTheGame) {

        arrayList = new ArrayList<>();
        model = new DefaultTableModel();
        setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel jlb = new JLabel();
        jlb.setIcon(new ImageIcon(getClass().getResource("/HighscoresImage.jpg")));

        JPanel panelTitle = new JPanel(new FlowLayout());
        title = new JLabel("Save your highscores");
        title.setFont(new Font("Serif", Font.BOLD, 25));
        title.setBackground(Color.white);
        panelTitle.add(title);
        panelTitle.setOpaque(false);

        JPanel panelDisplay = new JPanel(new FlowLayout());
        BoxLayout boxlayout = new BoxLayout(panelDisplay, BoxLayout.Y_AXIS);
        panelDisplay.setLayout(boxlayout);
        panelDisplay.add(new JLabel("Enter your Name : "));
        panelDisplay.add(new JLabel("Your Score      : "));

        JPanel panelTextfield = new JPanel(new FlowLayout());
        BoxLayout boxlayout2 = new BoxLayout(panelTextfield, BoxLayout.Y_AXIS);
        panelTextfield.setLayout(boxlayout2);
        nameTf =  new JTextField(10);
        panelTextfield.add(nameTf);
        scoreTf = new JTextField(scoreOfTheGame+"",10);
        scoreTf.setEditable(false);
        panelTextfield.add(scoreTf);

        JPanel panelButton = new JPanel(new FlowLayout());
        submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    String name = nameTf.getText();
                    //arrayList.add(new Player(name,scoreOfTheGame));
                    writeData();
                    new HighScoresGUI(name , scoreOfTheGame);
                    dispose();
                }catch(Exception el){
                    //   JOptionPane.showMessageDialog(null,"Please enter a name");

                }
            }
        });
        panelButton.add(submit);
        panelButton.setOpaque(false);

        add(jlb);
        jlb.setLayout(new FlowLayout());
        jlb.add(panelTitle , BorderLayout.NORTH);
        jlb.add(panelDisplay,BorderLayout.WEST);
        jlb.add(panelTextfield,BorderLayout.EAST);
        jlb.add(panelButton,BorderLayout.SOUTH);

        setBounds(10, 10, 270, 300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Brick Breaker");
        setLocationRelativeTo(null);
    }

    private void writeData() {
        try (FileWriter f = new FileWriter("Score.txt")) {

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < arrayList.size(); i++) {
                sb.append(arrayList.get(i).name + "," + arrayList.get(i).score);
            }
            f.write(sb.toString());
            f.close();
        } catch (IOException e) {
            return;
        }
        model.setRowCount(0);//reset data model
        for (int i = 0; i < arrayList.size(); i++) {
            Object[] objs = {arrayList.get(i).name, arrayList.get(i).score};
            model.addRow(objs);
        }
    }

    private void loadData() {
        try {
            File file = new File("Score.txt"); //create file
            file.createNewFile();//if not exit
            FileReader f = new FileReader("Score.txt");
            StringBuffer sb = new StringBuffer();
            while (f.ready()) {
                char c = (char) f.read();
                if (c == '-') {
                    String[] arr = sb.toString().split(",");
                    arrayList.add(new Player(arr[0], Integer.parseInt(arr[1])));
                    sb = new StringBuffer();
                } else {
                    sb.append(c);
                }
            }
        } catch (IOException e) {
            return;
        }
        model.setRowCount(0);//reset data model
        for (int i = 0; i <= arrayList.size(); i++) {
            Object[] objs = {arrayList.get(i).name, arrayList.get(i).score};
            model.addRow(objs);
        }

    }


    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}



