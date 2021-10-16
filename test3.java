import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JSpinner;
//import MigLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

//import Match;
//import Person;
//import Picks;
//import Season;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SpinnerListModel;
import java.awt.Component;

public class test3 extends JFrame implements ChangeListener{

    private JPanel contentPane;
    private JTable tableCeremony;
    private JTable tableTB;
    private static int numPairs = 5;
    private static String[] contestants = {"M1", "M2", "M3", "M4", "M5", "F1", "F2", "F3", "F4", "F5"};
    private static SpinnerListModel inputModel = new SpinnerListModel(contestants);
    private static SpinnerListModel inputModel2 = new SpinnerListModel(contestants);
    private static SpinnerListModel[] inputModels = new SpinnerListModel[numPairs*2];
    private static JSpinner[][] inputs = new JSpinner[2][numPairs];
    private static String[][] inputNames = new String[2][numPairs];
    static JSpinner tbInput1 = new JSpinner(inputModel);
    static JSpinner tbInput2 = new JSpinner(inputModel2);
    static String tbNames[]= {"null", "null"};
    static JButton btnTruthBooth = new JButton("Truth Booth");
    static JButton btnSubmitCeremony = new JButton("Submit Ceremony");
    static DefaultTableModel modelTB = new DefaultTableModel();
    static DefaultTableModel modelCeremony = new DefaultTableModel();
    static LinkedList<Person> people = new LinkedList();
    static Boolean isTruthBooth = true;
	/*private JSpinner m1 = new JSpinner(inputModel);
	private JSpinner m2 = new JSpinner(inputModel);
	private JSpinner m3 = new JSpinner(inputModel);
	private JSpinner m4 = new JSpinner(inputModel);
	private JSpinner m5 = new JSpinner(inputModel);
	private JSpinner f1 = new JSpinner(inputModel);
	private JSpinner f2 = new JSpinner(inputModel);
	private JSpinner f3 = new JSpinner(inputModel);
	private JSpinner f4 = new JSpinner(inputModel);
	private JSpinner f5 = new JSpinner(inputModel);*/
    //private JSpinner[][] inputs = {{m1,m2,m3,m4,m5}, {f1,f2,f3,f4,f5}};

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    test3 frame = new test3();
                    //create season, create matches
                    String[][] names = {{"F1","F2","F3", "F4", "F5"},
                            {"M1","M2","M3","M4","M5"}};
                    boolean[][] gender= {{false,false,false,false,false,false,false,false,false,false},
                            {true,true,true,true,true,true,true,true,true,true}};
                    Season s = new Season(names, gender);
                    people = s.getContestants();

                    //LinkedList<Person> unConfirmedContestants = new LinkedList<>();
                    //unConfirmedContestants.addAll(contestants);
                    //TestUtil t = new TestUtil();
                    //LinkedList<Match> confirmedMatch = new LinkedList<Match>();
                    //truth Booth
			        /*ChangeListener listener = new ChangeListener() {
			        	  public void stateChanged(ChangeEvent e) {
			        	    System.out.println(e.getSource());
			        	    System.out.println(e);
			        	    int i[] = getJspinnerIndex((JSpinner) e.getSource());
			        	    inputNames[i[0]][i[1]]= e.getSource().getValue();
			        	  }
			        };*/
                    tbInput1.addChangeListener(frame);
                    tbInput2.addChangeListener(frame);
                    btnTruthBooth.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            Match m = getTruthBoothSelection();
                            boolean isMatch = s.truthBoth(m);
                            Vector row = new Vector();
                            row.add(m.getP1().getName());
                            row.add(m.getP2().getName());
                            row.add(isMatch);
                            modelTB.addRow(row);
                            isTruthBooth = false;
                        }
                    });
                    btnSubmitCeremony.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            Picks p = getCeremonySelection();
                            int numMatch = s.ceremony(p);
                            Vector row = new Vector();
                            for(int i=0; i<numPairs; i++) {
                                String s = p.getPair(i).getP1().getName() + " & " + p.getPair(i).getP2().getName();
                                row.add(s);
                            }
                            row.add(numMatch);
                            modelCeremony.addRow(row);
                            isTruthBooth = true;
                        }
                    });
                    for(int i=0; i<10; i++){


                        //boolean isMatch = s.truthBoth(m);
                        //System.out.println("TruthBoth: " + m.getP1().getName() + ", " + m.getP2().getName() + " -" + isMatch);
                        ///if(isMatch){
                        //confirmedMatch.add(m);
                        //unConfirmedContestants.remove(m.getP1());
                        //unConfirmedContestants.remove(m.getP1());
                    }
                    //Picks p = t.randomSelection(contestants, 10,false, confirmedMatch);
                    //int numCorrect = s.ceremony(p);
                    ///System.out.println("Ceremony: " +  p.toString() + "NumCorrect: " +  numCorrect);
                    //}
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //get input for ceremony
    public static Picks getCeremonySelection() {
        LinkedList<Match> pairs = new LinkedList();
        for(int i =0; i<numPairs; i++) {
            Person p1;
            try {
                inputs[0][i].commitEdit();
                inputs[1][i].commitEdit();
            } catch ( java.text.ParseException e ) {
                System.out.println(e + "problem getting input from spinner");
            }
            String name = (String) inputs[0][i].getValue();
            String name2 = (String) inputs[1][i].getValue();
            Match m = new Match(getPerson(name),getPerson(name2));
            pairs.add(m);
        }
        System.out.println(new Picks(pairs).toString());
        return new Picks(pairs);
    }

    //get input for truth booth
    public static Match getTruthBoothSelection() {
        try {
            tbInput1.commitEdit();
            tbInput2.commitEdit();
        } catch ( java.text.ParseException e ) {
            System.out.println(e + "problem getting input from spinner fot tb");
        }
        String name = (String) tbInput1.getValue();
        String name2 = (String) tbInput2.getValue();
        System.out.println(name);
        System.out.println(name2);
        return new Match(getPerson(name), getPerson(name2));
		/*System.out.println(tbNames[0]);
		System.out.println(tbNames[1]);
		return new Match(new Person(tbNames[0]), new Person(tbNames[1]));*/
    }

    //find index for Jspinner
/*	public static int[] getJspinnerIndex(JSpinner js) {
		int index[]= new int[2];
		for(int i=0; i<2;i++) {
			for(int j=0; j<numPairs;j++) {
				if(js.equals(inputs[i][j])) {
					index[0]=i;
					index[1]=j;
				}
			}
		}
		return index;
	}*/

    /**
     * Create the frame.
     */
    public test3() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 730, 529);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel TtitlePanel = new JPanel();
        contentPane.add(TtitlePanel, BorderLayout.NORTH);

        JLabel lblAreYouThe = new JLabel("Are You The One");
        TtitlePanel.add(lblAreYouThe);

        JPanel CeremonyPanel = new JPanel();
        contentPane.add(CeremonyPanel, BorderLayout.WEST);
        CeremonyPanel.setLayout(new BoxLayout(CeremonyPanel, BoxLayout.Y_AXIS));

        JPanel CeremonyInputPanel = new JPanel();
        CeremonyInputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        CeremonyPanel.add(CeremonyInputPanel);
        //CeremonyInputPanel.setLayout(new MigLayout("", "[][]", "[][][][][]"));

        for(int i=0; i<2; i++) {
            for(int j=0; j<numPairs; j++) {
                SpinnerListModel aninputModel = new SpinnerListModel(contestants);
                inputs[i][j] = new JSpinner(aninputModel);
                String s = "cell " + i + " " + j;
                System.out.println(s);
                CeremonyInputPanel.add(inputs[i][j], s);
            }
        }

		/*CeremonyInputPanel.add(m1, "cell 0 0");
		CeremonyInputPanel.add(f1, "cell 1 0");
		CeremonyInputPanel.add(m2, "cell 0 1");
		CeremonyInputPanel.add(f2, "cell 1 1");
		CeremonyInputPanel.add(m3, "cell 0 2");
		CeremonyInputPanel.add(f3, "cell 1 2");
		CeremonyInputPanel.add(m4, "cell 0 3");
		CeremonyInputPanel.add(f4, "cell 1 3");
		CeremonyInputPanel.add(m5, "cell 0 4");
		CeremonyInputPanel.add(f5, "cell 1 4");*/
        btnSubmitCeremony.setAlignmentX(Component.CENTER_ALIGNMENT);

        CeremonyPanel.add(btnSubmitCeremony);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel panel_1 = new JPanel();
        panel_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panel.add(panel_1);


        panel_1.add(tbInput1);
        panel_1.add(tbInput2);
        btnTruthBooth.setAlignmentY(Component.TOP_ALIGNMENT);
        btnTruthBooth.setAlignmentX(Component.CENTER_ALIGNMENT);


        panel.add(btnTruthBooth);

        for(int i=0; i<numPairs; i++) {
            modelCeremony.addColumn("Pair");
        }
        modelCeremony.addColumn("Num Correct");
        tableCeremony = new JTable(modelCeremony);
        CeremonyPanel.add(tableCeremony);

        modelTB.addColumn("Person 1");
        modelTB.addColumn("Person 1");
        modelTB.addColumn("Are Match");
        tableTB = new JTable(modelTB);
        panel.add(tableTB);
        JTableHeader header = tableTB.getTableHeader();
        header.setBackground(Color.yellow);
    }

    public static Person getPerson(String name) {
        Person p = null;
        for(int i=0; i<people.size(); i++) {
            if(people.get(i).getName().equals(name)) {
                p = people.get(i);
            }
        }
        return p;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        // TODO Auto-generated method stub
        tbNames[0] = (String) tbInput1.getValue();
        tbNames[1] = (String) tbInput2.getValue();
    }
}

