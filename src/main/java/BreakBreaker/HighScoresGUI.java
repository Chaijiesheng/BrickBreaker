package BreakBreaker;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
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
import java.util.regex.PatternSyntaxException;

public class HighScoresGUI extends MenuItem {

    public ArrayList<Player> arrayList;
    private int row, column;
    private JButton add, update, back, search, delete;
    private DefaultTableModel model;
    private JTable table;
    private JLabel searchLb ,searchLb2;
    private JTextField searchTf ,searchTf2;



    public HighScoresGUI(String name , int score) {


        arrayList = new ArrayList<>();
        arrayList.add(new Player(name, score));

        setButton();
        setLabel();
        setTable();
        writeData();
        loadData();


        setLayout(null);
        setTitle("Total Score");
        setBounds(0, 0, 700, 610);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }



    private void setTable() {

        table = new JTable();
        Object[] columns = {"Name ", "Score"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        table.setModel(model);
        table.setBackground(Color.LIGHT_GRAY);
        table.setForeground(Color.black);

        Font font = new Font("Arial", 1, 14);
        table.setFont(font);
        table.setRowHeight(20);
//        table.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                row = table.getSelectedRow();
//                column = table.getColumnCount();
//                nameTf.setText(model.getValueAt(row, 0).toString());
//                scoreTf.setText(model.getValueAt(row, 1).toString());
//
//
//            }
//        });
        JScrollPane pane = new JScrollPane(table);

        pane.setBounds(0, 0, 700, 610);
        add(pane);

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
               }else {
                sb.append(c);
               }
            }
        } catch (IOException e) {
            return;
        }
        model.setRowCount(0);//reset data model
        for (int i = 0; i < arrayList.size(); i++) {
            Object[] objs = {arrayList.get(i).name, arrayList.get(i).score};
            model.addRow(objs);
        }
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

    private void setButton() {

        update = new JButton("Update");
        update.setBounds(50, 220, 100, 40);
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        add(update);
        delete = new JButton("Delete");
        delete.setBounds(350,220,100,40);
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Delete this data", "Delete", dialogButton);
                if (dialogResult == 0) {

                    try {
                        arrayList.remove(row);
                    } catch (IndexOutOfBoundsException ex) {
                        JOptionPane.showMessageDialog(null, "Nothing to be deleted, Please choose again!", "Record couldn't found", dialogButton);
                    }


                    writeData();
                } else {
                }
                clearField();
            }
        });
        add(delete);
        search = new JButton("Search");
        search.setBounds(500,220,100,40);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                table.setRowSorter(sorter);
                String text = searchTf.getText();
                if(text.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter(text));
                    } catch(PatternSyntaxException pse) {
                    }
                }
            }
        });
        add(search);
        back = new JButton("Back");
        back.setBounds(200, 220, 100, 40);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainFrame();
            }
        });
        add(back);

    }

    private void setLabel(){
        Font font1 = new Font("Arial", PROPERTIES, 16);
        searchLb = new JLabel("Please enter the name to search:");
        searchLb.setBounds(50, 190, 250, 200);
        searchLb.setFont(font1);
        add(searchLb);

        searchTf = new JTextField();
        searchTf.setBounds(50, 310, 250, 40);
        add(searchTf);

        searchLb2 = new JLabel("Please enter the score to search:");
        searchLb2.setBounds(330, 190, 250, 200);
        searchLb2.setFont(font1);
        add(searchLb2);

        searchTf2 = new JTextField();
        searchTf2.setBounds(330, 310, 250, 40);
        add(searchTf2);
    }
    private void clearField() {
        searchTf.setText("");
        searchTf2.setText("");
    }

}


class MenuItem extends JFrame {

    /**
     * This constructor inherits the constructor of parent class
     * This constructor shows all the item in the menubar.
     */
    public MenuItem() {

        //creating menuItem
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu file = new JMenu("File");
        menuBar.add(file);
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("C:");
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.open(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JMenuItem setting = new JMenuItem("Settings");
        menuBar.add(setting);
        setting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Runtime.getRuntime().exec("cmd /c start control");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        JMenuItem close = new JMenuItem("Close");
        menuBar.add(close);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        file.add(open);
        file.add(setting);
        file.add(close);

        JMenu edit = new JMenu("Edit");
        menuBar.add(edit);

        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem copy = new JMenuItem("Copy");
        JMenuItem paste = new JMenuItem("Paste");
        JMenuItem delete = new JMenuItem("Delete");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(delete);

        JMenu help = new JMenu("Help");
        menuBar.add(help);
        JMenuItem about = new JMenuItem("About");
        help.add(about);
    }



}


//    public static List<String> readFileIntoList(String file) {
//        ArrayList<Player> arrayList = Collections.emptyList();
//        try {
//            arrayList = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//        return lines;
//    }