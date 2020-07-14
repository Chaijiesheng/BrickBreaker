package BreakBreaker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainFrame extends JFrame {

    public MainFrame() {

        //set the background picture
        Container mainContainer = this.getContentPane();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setBackground(Color.WHITE);

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel jlb = new JLabel();
        jlb.setIcon(new ImageIcon(getClass().getResource("/images.png")));

        JPanel panelDisplay = new JPanel(new FlowLayout());
        panelDisplay.setFont(new Font("serif", Font.BOLD, 50));
        panelDisplay.add(new Label("Brick Breaker"));
        panelDisplay.setOpaque(false);

        //set the button
        JPanel panelButtons = new JPanel(new FlowLayout());
        BoxLayout boxlayout = new BoxLayout(panelButtons, BoxLayout.Y_AXIS);
        panelButtons.setLayout(boxlayout);
        panelButtons.setBorder(new EmptyBorder(new Insets(100, 250, 50, 80)));
        panelButtons.setOpaque(false);

        JButton playButton = new JButton("PLAY");
        playButton.setFont(new Font("serif", Font.BOLD, 30));
        add(playButton);
        playButton.add(Box.createRigidArea(new Dimension(180, 80)));
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                JFrame frame = new JFrame();
                Gameplay gamePlay = new Gameplay();
                frame.setBounds(10, 10, 700, 600);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.setTitle("Brick Breaker");
                frame.setLocationRelativeTo(null);
                frame.add(gamePlay);
            }
        });

        JButton highscoresButton = new JButton("HighScores");
        add(highscoresButton);
        highscoresButton.add(Box.createRigidArea(new Dimension(180, 80)));
        highscoresButton.setFont(new Font("serif", Font.BOLD, 30));
        highscoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HighScoresGUI(null,0);

            }
        });

        JButton exitButton = new JButton("Exit");
        add(exitButton);
        exitButton.add(Box.createRigidArea(new Dimension(180, 80)));
        exitButton.setFont(new Font("serif", Font.BOLD, 30));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        panelButtons.add(playButton);
        panelButtons.add(Box.createVerticalGlue());
        panelButtons.add(highscoresButton);
        panelButtons.add(Box.createVerticalGlue());
        panelButtons.add(exitButton);
        setBounds(10, 10, 700, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Brick Breaker");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());  // "super" Frame sets to BorderLayout
        add(jlb);
        jlb.setLayout(new FlowLayout());
        jlb.add(panelDisplay, BorderLayout.NORTH);
        jlb.add(panelButtons, BorderLayout.CENTER);

    }
}