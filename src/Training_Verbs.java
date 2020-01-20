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
        answerLabel = new JLabel("Введите ответ!!!");
        answer = new JTextField(20);
        checkAnswer = new JButton("Check answer");
        checkAnswer.addActionListener(new CheckButton());
        nextVerb = new JButton("Next Verb");
        nextVerb.addActionListener(new NextButton());
        nextVerb.setEnabled(false);
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

        if(answer.getText().length() > 1) {
            checkAnswer.setEnabled(false);
            System.out.println(answer.getText() + " _ " + list_Irr_verb.get(num).getValue());
            String answerStr = answer.getText();
            String[] checkStr = list_Irr_verb.get(num).getValue().split(";");
            boolean check = false;
            for (int i = 0; i < checkStr.length; i++) {
                if (checkStr[i].trim().equals(answerStr)) {
                    list_Irr_verb.get(num).IncKnowlage();
                    answerLabel.setText("Yes " + list_Irr_verb.get(num).getValue() + "  " +  list_Irr_verb.get(num).getKnowlage() + " %");
                    check = true;
                }
            }


            if (!check) {
                list_Irr_verb.get(num).DecKnowlage();
                answerLabel.setText("No " + list_Irr_verb.get(num).getValue() + "  " +  list_Irr_verb.get(num).getKnowlage() + " %");
            }
            nextVerb.setEnabled(true);
        }
        else
            answerLabel.setText("Введите ответ!!!");

    }
    public class NextButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            checkAnswer.setEnabled(true);
            nextVerb.setEnabled(false);
            int size = list_Irr_verb.size();
            boolean flag = true;
            while(size!=0&&flag){
                num = nextelement();

                if(list_Irr_verb.get(num).getKnowlage()>=max_knowlage){
                    list_Irr_verb.remove(num);
                }else{
                    answer.setText("");
                    answerLabel.setText("Введите ответ!!!");
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
                    answer.setText("");
                    answerLabel.setText("");
                    for(IrrVerb verb : list_verb){
                        verb.getV1().setKnowlage(0);
                        verb.getV2().setKnowlage(0);
                        verb.getV3().setKnowlage(0);
                    }
                    setList_verb( list_verb);
                }
                else
                    checkAnswer.setEnabled(false);
            }


        }
        public int nextelement(){
            int size = list_Irr_verb.size();
            while(true) {
                int num = (int) (Math.random() * size);
                // you have to wtite there !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                if(!num_verb.contains(num)) {
                    num_verb.add(num);
                    if(num_verb.size()==size)
                        num_verb.removeAll(num_verb);
                    return num;
                }
            }
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
