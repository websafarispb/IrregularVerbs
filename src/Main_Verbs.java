import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main_Verbs {
    public JFrame frame;
    public JFrame addFrame;
    public JPanel addPanel;
    public JPanel panel;
    public JPanel panel2;
    public JButton button;
    public JButton button2;
    public JButton add_button;
    public JTextArea text1;
    public JTextArea text2;
    public JTextArea text3;
    public JTextArea text4;
    public All_Verbs show_verb;
    public Training_Verbs training_verbs;
    public List <IrrVerb> list;
    public JOptionPane dialog;
    public ArrayList<String> tempList = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("This is EnglishVerb appliacations for training irregular verbs");
        Main_Verbs win = new Main_Verbs();
        win.Gui_Start();
    }
    public void Gui_Start(){
        frame = new JFrame("Irregular verbs");

        addFrame = new JFrame("Adding new irregular verb");
        addFrame.setLocation(400, 400);
        list = new ArrayList<>();

        show_verb = new All_Verbs(600,400);
        training_verbs = new Training_Verbs(300,300);


        addFrame.setSize(300,300);

        addPanel = new JPanel();
      //  addPanel.setLayout(new BoxLayout(addPanel,BoxLayout.Y_AXIS));
        text1 = new JTextArea(1,20);
        text2 = new JTextArea(1,20);
        text3 = new JTextArea(1,20);
        text4 = new JTextArea(1,20);
        add_button = new JButton("Add");
        add_button.addActionListener(new AddWordListButton());

        addPanel.add(text1);
        addPanel.add(text2);
        addPanel.add(text3);
        addPanel.add(text4);
        addPanel.add(add_button);

        addFrame.getContentPane().add(BorderLayout.CENTER,addPanel);


        button = new JButton("Start");
        button.addActionListener(new StartButtonListner());

        button2 = new JButton("GO home!!");
        button2.addActionListener(new ButtonListner2());
        panel2 = new JPanel();
        panel2.add(button2);
        panel = new JPanel();
        panel.add(button);
        frame.getContentPane().add(panel);
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem addMenuItem = new JMenuItem("Add");
        addMenuItem.addActionListener(new AddMenuListner());
        JMenuItem showMenuItem = new JMenuItem("Show");
        showMenuItem.addActionListener(new ShowMenuListner());
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(new SaveMenuListner());
        JMenuItem loadMenuItem = new JMenuItem("Load");
        loadMenuItem.addActionListener(new LoadMenuListner());
        JMenuItem loadFileMenuItem = new JMenuItem("Loadfile");
        loadFileMenuItem.addActionListener(new LoadFileMenuListner());
        JMenuItem SaveFileMenuItem = new JMenuItem("Savefile");
        SaveFileMenuItem.addActionListener(new SaveFileMenuListner());

        JMenuItem DeletMenuItem = new JMenuItem("Delet_List");
        DeletMenuItem.addActionListener(new DeletListMenuListner());

        fileMenu.add(addMenuItem);
        fileMenu.add(showMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        fileMenu.add(loadFileMenuItem);
        fileMenu.add(SaveFileMenuItem);
        fileMenu.add(DeletMenuItem);
        menuBar.add(fileMenu);
        frame.setJMenuBar(menuBar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

    }

    public class DeletListMenuListner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            list.removeAll(list);
            training_verbs.remove_list_ver();
        }
    }

    public class StartButtonListner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            if(list.size()>0) {
                if(training_verbs.setList_verb(list))
                    training_verbs.visible(true);
                else {
                    Object[] options = { "Да", "Нет!" };
                    int n = JOptionPane
                            .showOptionDialog(frame, "Сбросить прогресс?",
                                    "Подтверждение", JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE, null, options,
                                    options[0]);
                    if(n==0){
                        for(IrrVerb verb : list){
                            verb.getV1().setKnowlage(0);
                            verb.getV2().setKnowlage(0);
                            verb.getV3().setKnowlage(0);
                        }
                    }
                }
            }
            else
                dialog.showConfirmDialog(panel," Список пуст!!!");
        }
    }

    public class ButtonListner2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            button.setText("Смена");
            frame.setVisible(true);
            training_verbs.visible(false);
        }
    }

    public class AddMenuListner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            addFrame.setVisible(true);

        }
    }

    public class ShowMenuListner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
           // show_verb.fill(list);
            show_verb.visible(true);
            show_verb.fill(list);

        }
    }
    public class SaveMenuListner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("MenuSave!!!");
            if (list.size() > 0) {
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream("ser.ser");
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(list.size());
                    for (IrrVerb verb : list)
                        objectOutputStream.writeObject(verb);
                    objectOutputStream.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else
                dialog.showConfirmDialog(panel, " Список пуст!!!");


        }
    }
    public class SaveFileMenuListner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {


            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            System.out.println("MenuSaveFile!!!");
            if (list.size() > 0) {
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(fileSave.getSelectedFile());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(list.size());
                    for (IrrVerb verb : list)
                        objectOutputStream.writeObject(verb);
                    objectOutputStream.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else
                dialog.showConfirmDialog(panel, " Список пуст!!!");


        }
    }

    public class LoadMenuListner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("MenuLoad!!!");
            if(list.size()<=0) {
                try {
                    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("ser.ser"));
                    int size = (int) objectInputStream.readObject();
                    System.out.println(size);
                    for (int i = 0; i < size; i++) {
                        IrrVerb verb = (IrrVerb) objectInputStream.readObject();
                        System.out.println(verb.toString());
                        list.add(verb);
                    }
                    training_verbs.setList_verb(list);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else
                dialog.showConfirmDialog(panel," Список уже загружен!!!");

        }
    }

    public class LoadFileMenuListner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("MenuFileLoad!!!");
            if(list.size()<=0) {
                JFileChooser fileOpen = new JFileChooser();
                fileOpen.showOpenDialog(frame);
                loadFile(fileOpen.getSelectedFile());
                int countV = 0;
                for (String s : tempList) {
                    System.out.println(s);
                    switch (countV) {
                        case 0:
                            list.add(new IrrVerb(s, "", "", ""));
                            break;
                        case 1:
                            list.get(list.size() - 1).setV2(s);
                            break;
                        case 2:
                            list.get(list.size() - 1).setV3(s);
                            break;
                        case 3:
                            list.get(list.size() - 1).setTranslation(s);
                            break;
                        default:
                            System.out.println("ERROR!!! LOADFILE!!!");
                            break;
                    }
                    countV++;
                    if (countV == 4)
                        countV = 0;

                }
                training_verbs.setList_verb(list);
            } else
                dialog.showConfirmDialog(panel," Список уже загружен!!!");

        }
    }
    public void loadFile(File file){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if(line.length() > 1) {
                    tempList.add(line);

                }
            }
            reader.close();

        } catch(Exception ex) {
            System.out.println("couldn't read the card file");
            ex.printStackTrace();
        }

    }

    public class AddWordListButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(text1.getText() + " " + text2.getText() + " " + text3.getText() + " " + text4.getText());
            list.add(new IrrVerb(text1.getText(),text2.getText(),text3.getText(),text4.getText()));
            text4.setText("");
            text3.setText("");
            text2.setText("");
            text1.setText("");
        }
    }
}
