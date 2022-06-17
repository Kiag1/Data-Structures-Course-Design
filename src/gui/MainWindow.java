package gui;

import algorithm.Dijkstra;
import algorithm.Floyd;
import com.sun.tools.javac.Main;
import model.Graph;
import stl.Array;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow extends JPanel {
    private Graph graph;
    private GraphPanel graphPanel;

    public MainWindow() {
        super.setLayout(new BorderLayout());
        loadGraphPanel();
    }

    private void loadGraphPanel() {
        graph = new Graph();
        graphPanel = new GraphPanel(graph);
        graphPanel.setPreferredSize(new Dimension(200, 200));

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setViewportView(graphPanel);
        jScrollPane.setPreferredSize(new Dimension(750, 500));
        jScrollPane.getViewport().setViewPosition(new Point(4100, 0));
        this.add(jScrollPane, BorderLayout.CENTER);
        setButton();
    }

    public void setButton() {
        JButton dijkstra = new JButton();
        JButton floyd = new JButton();
        JButton reset = new JButton();
        JButton help = new JButton();

        setIcon(dijkstra, "run");
        setIcon(floyd, "run");
        setIcon(reset, "reset");
        setIcon(help, "help");

        JPanel buttonJPanel = new JPanel();
        buttonJPanel.setBackground(new Color(241, 240, 240, 255));
        buttonJPanel.add(reset);
        buttonJPanel.add(dijkstra);
        buttonJPanel.add(floyd);
        buttonJPanel.add(help);


        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null,
                        "鼠标点击空白区域创建新的城市\n" +
                                "鼠标点击已有的城市至另一个城市可创建两个城市之间的路径\n" +
                                "鼠标点击已有的边可以修改路径的长度\n" +
                                "快捷键:\n" +
                                "Shift + 左键                 : 设置起点\n" +
                                "Shift + 右键                 : 设置重点\n" +
                                "Ctrl + 长按左键             : 可拖动节点移动位置\n" +
                                "Ctrl + Shift + 左键        : 可删除指定节点");
            }
        });

        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.clear();
            }
        });

        dijkstra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Dijkstra dijkstra = new Dijkstra(graph);
                try {
                    dijkstra.run();
                    graphPanel.setPath(dijkstra.getNodes());
                } catch (IllegalStateException illegalStateException) {
                    JOptionPane.showMessageDialog(null, illegalStateException.getMessage());
                }
            }
        });

        floyd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Floyd floyd = new Floyd(graph);
                floyd.run();

                Array<Array<String>> table = floyd.getDistanceTable();

                int nodeNumber = graph.size();

                String[] tableName = new String[nodeNumber + 1];
                for (int i = 0; i <= nodeNumber; i++)
                    tableName[i] = table.get(0).get(i);

                Object[][] tableBody = new Object[nodeNumber][nodeNumber + 1];
                for (int i = 1; i <= nodeNumber; i++) {
                    for (int j = 0; j <= nodeNumber; j++) {
                        tableBody[i - 1][j] = table.get(i).get(j);
                    }
                }

                JTable jTable = new JTable(tableBody, tableName);
                JScrollPane jScrollPane = new JScrollPane(jTable);
                jScrollPane.setBounds(400, 250, 500, 500);

                JDialog jDialog = new JDialog();
                jDialog.setBounds(400, 250, 500, 500);
                jDialog.add(jScrollPane);
                jDialog.setVisible(true);
            }
        });

        this.add(buttonJPanel, BorderLayout.SOUTH);
    }

    public void setIcon(JButton jButton, String name) {
        try {
            Image image = ImageIO.read(getClass().getResource("/resource/" + name + ".png"));
            ImageIcon icon = new ImageIcon(image);
            jButton.setIcon(icon);
            jButton.setBorderPainted(false);
            jButton.setFocusPainted(false);
            jButton.setContentAreaFilled(false);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

}
