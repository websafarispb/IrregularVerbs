import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class Training_Verbs {
    private int width;
    private int height;
    private JFrame frame;
    private JPanel panel;
    private JLabel verbLabel;
    private JLabel answerLabel;
    private JTextField answer;
    private JButton checkAnswer;
    private JButton nextVerb;
    private List <IrrVerb> list_verb;
    private List <Verb> list_Irr_verb;
    private int num_verbs;
    private int time;
    int num; // used as index in list_Irr_verb for getting curent verb
    private List<Integer> num_verb;// contains index of verb that has been showen
    private static int max_knowlage = 2;

    public void remove_list_ver(){
        list_Irr_verb.removeAll(list_Irr_verb);
    }

    public Training_Verbs(int width, int height) {
        this.width = width;
        this.height = height;
        num_verb = new ArrayList<>();
        list_Irr_verb = new ArrayList<>();
        num_verbs = 0;
        time = 0;
        frame = new JFrame("Training Verbs");
        frame.addWindowListener(new WinLis());
        frame.setLocationRelativeTo(null);
        //frame.setLocation(300, 400);
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(0,50,0,50));
        verbLabel = new JLabel("some verb");
        answerLabel = new JLabel("");
        answer = new JTextField(20);
        checkAnswer = new JButton("Check answer");
        checkAnswer.addActionListener(new CheckButton());
        nextVerb = new JButton("Next Verb");
        nextVerb.addActionListener(new NextButton());
        panel.add(verbLabel);
        panel.add(answer);
        panel.add(answerLabel);
        panel.add(checkAnswer);
        panel.add(nextVerb);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setSize(width,height);


    }

    public void visible(boolean show){
        frame.setVisible(show);
    }
    public void refresh(){

    }

    public List<IrrVerb> getList_verb() {
        return list_verb;
    }

    public void ListSort(){
        System.out.println("List Sort Working!!!");
        for(IrrVerb verb : list_verb){
            System.out.println(verb.toString());
        }
        for(int i = list_verb.size()-1; i >0;i--){
            for(int j = 0; j < i;j++){
                if(list_verb.get(j).getKnowlage() > list_verb.get(j+1).getKnowlage()){
                    IrrVerb temp = list_verb.get(j);
                    list_verb.set(j,list_verb.get(j+1));
                    list_verb.set(j+1,temp);
                }
            }
        }

    }

    public boolean setList_verb(List<IrrVerb> list_verb) {

        this.list_verb = list_verb;
        ListSort();

        if(list_Irr_verb.isEmpty()) {
            for (IrrVerb verb : list_verb) {
                if(verb.getV1().getKnowlage()< max_knowlage)
                    list_Irr_verb.add(verb.getV1());
                if(verb.getV2().getKnowlage()< max_knowlage)
                    list_Irr_verb.add(verb.getV2());
                if(verb.getV3().getKnowlage()< max_knowlage)
                    list_Irr_verb.add(verb.getV3());
            }
        }
        int size = list_Irr_verb.size();
        if(size != 0) {
            num = (int) (Math.random() * size);
            this.verbLabel.setText(list_Irr_verb.get(num).getTranslation() + " - " + list_Irr_verb.get(num).getTime());
            return true;
        }else{
            return false;
        }
    }

    public class CheckButton implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            check_Verb();
        }
    }
    public void check_Verb(){
        System.out.println(answer.getText() + " _ " + list_Irr_verb.get(num).getValue());
        String answerStr = answer.getText();
        String []checkStr = list_Irr_verb.get(num).getValue().split(";");
        boolean check = false;
        for(int i = 0; i < checkStr.length; i++){
            if(checkStr[i].trim().equals(answerStr)){
                list_Irr_verb.get(num).IncKnowlage();
                answerLabel.setText("Yes " + list_Irr_verb.get(num).getValue() + "  " +  list_verb.get(num_verbs).getKnowlage()+" %");
                check = true;
            }
        }


        if(!check) {
            list_Irr_verb.get(num).DecKnowlage();
            answerLabel.setText("No " + list_Irr_verb.get(num).getValue() + "  " + list_verb.get(num_verbs).getKnowlage() + " %");
        }

    }
    public void check_Verb2(){
        System.out.println(answer.getText() + " _ " + list_verb.get(num_verbs).getTime(time));
        String answerStr = answer.getText();
        String []checkStr = list_verb.get(num_verbs).getTime(time).split(";");
        boolean check = false;
        for(int i = 0; i < checkStr.length; i++){
            if(checkStr[i].trim().equals(answerStr)){
                list_verb.get(num_verbs).IncKnowlage();
                answerLabel.setText("Yes " + list_verb.get(num_verbs).getTime(time) + "  " +  list_verb.get(num_verbs).getKnowlage()+" %");
                check = true;
            }
        }


        if(!check) {
            list_verb.get(num_verbs).DecKnowlage();
            answerLabel.setText("No " + list_verb.get(num_verbs).getTime(time) + "  " + list_verb.get(num_verbs).getKnowlage() + " %");
        }


    }
    public class NextButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int size = list_Irr_verb.size();
            boolean flag = true;
            while(size!=0&&flag){
                num = (int)(Math.random()*size);
                if(list_Irr_verb.get(num).getKnowlage()>=max_knowlage){
                    list_Irr_verb.remove(num);
                }else{
                    answer.setText("");
                    answerLabel.setText("");
                    verbLabel.setText(list_Irr_verb.get(num).getTranslation() + " " + list_Irr_verb.get(num).getTime());
                    flag = false;
                }
                size = list_Irr_verb.size();
            }
            if(size==0) {
                Object[] options = { "Да", "Нет!" };
                int n = JOptionPane
                        .showOptionDialog(frame, "Сбросить прогресс? Заново?",
                                "Подтверждение", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE, null, options,
                                options[0]);
                if(n==0){
                    for(IrrVerb verb : list_verb){
                        verb.getV1().setKnowlage(0);
                        verb.getV2().setKnowlage(0);
                        verb.getV3().setKnowlage(0);
                    }
                    setList_verb( list_verb);
                }
            }


        }
    }
    public void next_verb(){
        answer.setText("");
        answerLabel.setText("");
        if(num_verbs == list_verb.size()-1&& time == 2){
            JOptionPane.showConfirmDialog(panel,"You finished");
            num_verbs = -1;
            ListSort();
        }
        if (time == 2) {
            time = 0;
            if (num_verbs < list_verb.size())
                num_verbs++;

        } else
            time++;
        String t;
        switch (time) {
            case 0:
                t = "v1";
                break;
            case 1:
                t = "v2";
                break;
            case 2:
                t = "v3";
                break;
            default:
                t = "error";
                break;
        }
        System.out.println(" TIME - "  + time);
        System.out.println("NUM_VERBS - " + num_verbs);
        System.out.println("list_verb.size()" + list_verb.size());

        verbLabel.setText(list_verb.get(num_verbs).getTranslation() + " - " + t);

    }
    public class ListHelper {
        private int num_verb;
        private int time;

        public ListHelper(int num_verb, int time) {
            this.num_verb = num_verb;
            this.time = time;
        }

        public int getNum_verb() {
            return num_verb;
        }

        public void setNum_verb(int num_verb) {
            this.num_verb = num_verb;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
    public class WinLis implements WindowListener {
        @Override
        public void windowOpened(WindowEvent e) {

        }

        @Override
        public void windowClosing(WindowEvent e) {
            Object[] options = { "Да", "Нет!" };
            int n = JOptionPane
                    .showOptionDialog(e.getWindow(), "Закрыть окно?",
                            "Подтверждение", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null, options,
                            options[0]);
            if (n == 0) {
                e.getWindow().setVisible(false);
               // System.exit(0);
                ListSort();
                num_verbs = 0;
                time = 0;

            }

        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    }


}
