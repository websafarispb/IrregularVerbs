import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class All_Verbs  {
    private int width;
    private int height;
    private  JFrame frame;
    private JPanel panel;
    private JTextArea verbs_area;
    private JButton closeButton;
    private JScrollPane vScroller;

    public All_Verbs(int width, int height){
        this.width = width;
        this.height = height;
        frame = new JFrame("All verbs list.");
        frame.setLocationRelativeTo(null);

        panel = new JPanel();
        verbs_area = new JTextArea(10,50);
        verbs_area.setLineWrap(true); // перенос на другцю строки при достижении конца окна
        verbs_area.setWrapStyleWord(true); // слово целиком переноситься

        vScroller = new JScrollPane(verbs_area);
        vScroller.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        vScroller.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        panel.add(vScroller);
        closeButton = new JButton("Close");
        closeButton.addActionListener(new ButtonClose());
        panel.add(closeButton);

     //   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(width,height);
    }

    public class ButtonClose implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
        }
    }

    public void visible(boolean show){
        frame.setVisible(show);
    }

    public void fill(List <IrrVerb> list){
        StringBuilder str = new StringBuilder();
        if(list.size()>0) {
            for (IrrVerb verb : list) {
                str.append(verb.toString() + "\n");
            }
            verbs_area.setText(str.toString());
        }
        else
            verbs_area.setText("Список пуст!!!");

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextArea getVerbs_area() {
        return verbs_area;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JScrollPane getvScroller() {
        return vScroller;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void setVerbs_area(JTextArea verbs_area) {
        this.verbs_area = verbs_area;
    }

    public void setCloseButton(JButton closeButton) {
        this.closeButton = closeButton;
    }

    public void setvScroller(JScrollPane vScroller) {
        this.vScroller = vScroller;
    }
}
