import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;


public class TestSeason {
    @Test
    public void testCreateCast(){
        //create season, create matches
        String[][] names = {{"F1","F2","F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10"},
                {"M1","M2","M3","M4","M5","M6","M7","M8","M9","M10"}};
        boolean[][] gender= {{false,false,false,false,false,false,false,false,false,false},
                {true,true,true,true,true,true,true,true,true,true}};
        Season s = new Season(names, gender);
        LinkedList<Person> contestants = s.getContestants();
        assertEquals(contestants.size(), names[0].length + names[1].length);
        for(int i=0; i<contestants.size(); i++){
            boolean correct = false;
            for(int j=0; j<2; j++){
                for(int k=0; k<10; k++) {
                    if (contestants.get(i).getName() == names[j][k] && contestants.get(i).isMale() == gender[j][k]){
                        correct = true;
                    }
                }
            }
            assertTrue(correct);
        }
    }

    @Test
    public void TestgetNewBatch(){
        //create season, create matches
        String[][] names = {{"F1","F2","F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10"},
                {"M1","M2","M3","M4","M5","M6","M7","M8","M9","M10"}};
        boolean[][] gender= {{false,false,false,false,false,false,false,false,false,false},
                {true,true,true,true,true,true,true,true,true,true}};
        Season s = new Season(names, gender);
        LinkedList<Person> contestants = s.getContestants();
        DivideAndConquer dv = new DivideAndConquer(contestants, false, 10);
        //System.out.println("testingPairs:"+ dv.toString());
        assertEquals(10, dv.getPicks().size());

    }

    @Test
    public void testBruteForceStraight(){
        //create season, create matches
        String[][] names = {{"F1","F2","F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10"},
                {"M1","M2","M3","M4","M5","M6","M7","M8","M9","M10"}};
        boolean[][] gender= {{false,false,false,false,false,false,false,false,false,false},
                {true,true,true,true,true,true,true,true,true,true}};
        Season s = new Season(names, gender);
        LinkedList<Person> contestants = s.getContestants();
        LinkedList<Person> unConfirmedContestants = new LinkedList<>();
        unConfirmedContestants.addAll(contestants);
        TestUtil t = new TestUtil();
        LinkedList<Match> confirmedMatch = new LinkedList<Match>();
        for(int i=0; i<10; i++){
            Match m = t.randomPairStraight(unConfirmedContestants);
            boolean isMatch = s.truthBoth(m);
            System.out.println("TruthBoth: " + m.getP1().getName() + ", " + m.getP2().getName() + " -" + isMatch);
            if(isMatch){
                confirmedMatch.add(m);
                unConfirmedContestants.remove(m.getP1());
                unConfirmedContestants.remove(m.getP1());
            }
            Picks p = t.randomSelection(contestants, 10,false, confirmedMatch);
            int numCorrect = s.ceremony(p);
            System.out.println("Ceremony: " +  p.toString() + "NumCorrect: " +  numCorrect);
        }
    }

    @Test
    public void testBruteForceQueer(){
        //create season, create matches
        String[][] names = {{"a1","a2","a3", "a4", "a5", "a6", "a7", "a8"},
                {"b1","b2","b3","b4","b5","b6","b7","b8"}};
        boolean[][] gender= {{false,false,false,false,false,false,false,false,false,false},
                {true,true,true,true,true,true,true,true,true,true}};
        Season s = new Season(names);
        LinkedList<Person> contestants = s.getContestants();
        LinkedList<Person> unConfirmedContestants = new LinkedList<>();
        unConfirmedContestants.addAll(contestants);
        TestUtil t = new TestUtil();
        LinkedList<Match> confirmedMatch = new LinkedList<Match>();
        for(int i=0; i<10; i++){
            Match m = t.randomPairQueer(unConfirmedContestants);
            boolean isMatch = s.truthBoth(m);
            System.out.println("TruthBoth: " + m.getP1().getName() + ", " + m.getP2().getName() + " -" + isMatch);
            if(isMatch){
                confirmedMatch.add(m);
                unConfirmedContestants.remove(m.getP1());
                unConfirmedContestants.remove(m.getP1());
            }
            Picks p = t.randomSelection(contestants, 8,true, confirmedMatch);
            int numCorrect = s.ceremony(p);
            System.out.println("Ceremony: " +  p.toString() + "NumCorrect: " +  numCorrect);
        }
    }


    @Test
    public void testDivideAndConquerStraight(){
        //create season, create matches
        String[][] names = {{"F1","F2","F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10"},
                {"M1","M2","M3","M4","M5","M6","M7","M8","M9","M10"}};
        boolean[][] gender= {{false,false,false,false,false,false,false,false,false,false},
                {true,true,true,true,true,true,true,true,true,true}};
        Season s = new Season(names, gender);
        LinkedList<Person> contestants = s.getContestants();
        LinkedList<Person> unConfirmedContestants = new LinkedList<>();
        unConfirmedContestants.addAll(contestants);
        DivideAndConquer dv = new DivideAndConquer(contestants, false, 10);
        LinkedList<Match> confirmedMatch = new LinkedList<Match>();
        System.out.println("DV:");
        for(int i=0; i<10; i++){
            System.out.println(".");
            System.out.println("Week " + i);
            System.out.println("-------------------------------------------------------------------");
            Match m = dv.getTruthBoth();
            boolean isMatch = s.truthBoth(m);
            System.out.println("TruthBoth: " + m.getP1().getName() + ", " + m.getP2().getName() + " -" + isMatch);
            dv.recordTruthBoth(isMatch);
            Picks p = dv.getPicks();
            System.out.println("unkown:" + p.getNumUnkown() + " matches:" + p.getNumMatch() + " nonmatches:" + p.getNumNonMatch());
            System.out.println("-");
            System.out.println("Ceremony: " +  p.toString() );
            int numCorrect = s.ceremony(p);
            System.out.println( "NumCorrect: " +  numCorrect);
            dv.recordCeremony(numCorrect);
            System.out.println("unkown:" + p.getNumUnkown() + " matches:" + p.getNumMatch() + " nonmatches:" + p.getNumNonMatch() ); //+ " numBeams: "+dv.getNumBeams());
        }
    }

    public LinkedList<Person> getContestantByName(LinkedList<String> p, LinkedList<Person> l){
        LinkedList<Person> o = new LinkedList<>();
        for(int i=0; i<l.size(); i++){
            for(int j=0; j<p.size(); j++) {
                if (l.get(i).getName().equals(p.get(j))) {
                    o.add(l.get(i));
                }
            }
        }
        return o;
    }

    @Test
    public void testGetNewMatches(){
        String[][] names = {{"F1","F2","F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10"},
                {"M1","M2","M3","M4","M5","M6","M7","M8","M9","M10"}};
        boolean[][] gender= {{false,false,false,false,false,false,false,false,false,false},
                {true,true,true,true,true,true,true,true,true,true}};
        Season s = new Season(names, gender);
        LinkedList<Person> contestants = s.getContestants();
        DivideAndConquer dv = new DivideAndConquer(contestants, false, 10);
        LinkedList<Person> people = new LinkedList<>();
        LinkedList<String> pname = new LinkedList<>();
        pname.add("F1");
        pname.add("M1");
        people =getContestantByName(pname,contestants);
        LinkedList match = dv.getUntestedMatches(9, people);
        assertEquals(match.size(), 9);
        pname.add("F2");
        pname.add("M2");
        people =getContestantByName(pname,contestants);
        LinkedList match2 = dv.getUntestedMatches(10, people);
        assertEquals(match2.size(), 8);
        pname.add("F8");
        pname.add("M8");
        people =getContestantByName(pname,contestants);
        LinkedList match3 = dv.getUntestedMatches(10, people);
        assertEquals(match3.size(), 7);
    }

    @Test
    public void testMinMaxGetCeremony(){
        LinkedList<Person> l1 = new LinkedList<>();
        l1.add(new Person("F1"));
        l1.add(new Person("F2"));
        l1.add(new Person("F3"));
        l1.add(new Person("F4"));

        LinkedList<Person> l2 = new LinkedList<>();
        l2.add(new Person("M1"));
        l2.add(new Person("M2"));
        l2.add(new Person("M3"));
        l2.add(new Person("M4"));

        System.out.println("group1:" + l1.toString());
        System.out.println("group2:" + l2.toString());
        MiniMax miniMax = new MiniMax(l2,l1);
        String[][] names = {{"M1", "M2", "M3", "M4"},
                            {"M1", "M2", "M4", "M3"},
                            {"M1", "M3", "M2", "M4"},
                            {"M1", "M3", "M4", "M2"},
                            {"M1", "M4", "M2", "M3"},
                            {"M1", "M4", "M3", "M2"}};
        for(int i=0; i<6; i++){
            if(miniMax.on == null){
                System.out.println("on is null! 201");
            }
            else{
                System.out.println("on is not null! 201");
            }
            Picks p = miniMax.getCeremony();
            System.out.println("test" + p.toString());
            if(miniMax.on != null){
                System.out.println("on is not null! 205");
            }
            else{
                System.out.println("on is null! 201");
            }
            System.out.println("i=" + i);
            for(int j=0; j<4; j++) {

                Match m = p.getPair(j);
                Person person = m.getP2();
                String n = person.getName();
                System.out.println("///////////////////");
                System.out.println("///////////////////");
                System.out.println("calc: " + n + " input: " + names[i][j]);
                System.out.println("///////////////////");
                System.out.println("///////////////////");
                assertEquals(n, names[i][j]);
            }
        }

    }

    @Test
    public void testRecordCeremony(){
        LinkedList<Person> l1 = new LinkedList<>();
        Person F1 = new Person("F1");
        Person F2 =new Person("F2");
        Person F3 =new Person("F3");
        Person F4 =new Person("F4");

        l1.add(F1);
        l1.add(F2);
        l1.add(F3);
        l1.add(F4);

        LinkedList<Person> l2 = new LinkedList<>();
        l2.add(new Person("M1"));
        l2.add(new Person("M2"));
        l2.add(new Person("M3"));
        l2.add(new Person("M4"));

        System.out.println("group1:" + l1.toString());
        System.out.println("group2:" + l2.toString());
        MiniMax miniMax = new MiniMax(l2,l1);
        miniMax.selected = new LinkedList<>();
        miniMax.selected.add(F1);
        miniMax.selected.add(F2);
        miniMax.selected.add(F3);
        miniMax.selected.add(F4);
        miniMax.recordCeremony(0);
        LinkedList<Person> ans1 = new LinkedList<>();
        LinkedList<Person> ans2 = new LinkedList<>();
        LinkedList<Person> ans3 = new LinkedList<>();
        LinkedList<Person> ans4 = new LinkedList<>();
        ans1.add(F1);
        ans2.add(F2);
        ans3.add(F3);
        ans4.add(F4);
        assertEquals(miniMax.ruledOut.get(0), ans1);
        assertEquals(miniMax.ruledOut.get(1), ans2);
        assertEquals(miniMax.ruledOut.get(2), ans3);
        assertEquals(miniMax.ruledOut.get(3), ans4);
    }

    @Test
    public void testSkipInSeason(){
        String[][] names = {{"F1","F2","F3", "F4"},
                {"M1","M2","M3","M4"}};
        LinkedList<Integer> order = new LinkedList<>();
        order.add(3);
        order.add(2);
        order.add(4);
        order.add(1);
        boolean[][] gender= {{false,false,false,false},
                {true,true,true,true}};
        String[][] ans = {{"M1", "M2", "M3", "M4"},
                {"M1", "M2", "M4", "M3"},
                {"M1", "M3", "M2", "M4"},
                {"M2", "M1", "M4", "M3"},
                {"M2", "M4", "M1", "M3"},
                {"M3", "M1", "M4", "M2"}};
        Season s = new Season(names, gender, order);
        LinkedList<Person>[] contestants = s.getContestantsSplit();
        System.out.println("group1:" + contestants[0]);
        System.out.println("group2:" + contestants[1]);
        MiniMax util = new MiniMax(contestants[0], contestants[1]);
        LinkedList<Picks> picks = new LinkedList<>();

        for(int i=0; i<6; i++) {
            Picks p = util.getCeremony();
            System.out.println("ruledOut: " + util.ruledOut.toString());
            System.out.println("data: " + util.incompleateInfo.toString());
            System.out.println("data depth: " + util.depthIndex.toString());
            System.out.println("data numunkown: " + util.numUnkown.toString());

            picks.add(p);
            System.out.println("Pick: " + p.toString());
            int beams = s.ceremony(p);
            System.out.println("beams: " + beams);
            util.recordCeremony(beams);
            System.out.println("------");
        }
        testCorrectPicks(4,ans,picks);
    }

    public void testCorrectPicks(int numPairs, String[][] ans, LinkedList<Picks> picks){
        for(int i=0; i<picks.size(); i++) {
            Picks p = picks.get(i);
            for (int j = 0; j < numPairs; j++) {
                Match m = p.getPair(j);
                Person person = m.getP2();
                String n = person.getName();
                assertEquals(n, ans[i][j]);
            }
        }
    }
    @Test
    public void testSkip(){
        /*String[][] names = {{"F1","F2","F3", "F4"},
                {"M1","M2","M3","M4"}};
        boolean[][] gender= {{false,false,false,false},
                {true,true,true,true}};
        Season s = new Season(names, gender);
        LinkedList<Person>[] contestants = s.getContestantsSplit();
        MiniMax util = new MiniMax(contestants[0], contestants[1]);*/
        LinkedList<Person> l1 = new LinkedList<>();
        Person F1 = new Person("F1");
        Person F2 =new Person("F2");
        Person F3 =new Person("F3");
        Person F4 =new Person("F4");

        l1.add(F1);
        l1.add(F2);
        l1.add(F3);
        l1.add(F4);

        LinkedList<Person> l2 = new LinkedList<>();
        l2.add(new Person("M1"));
        l2.add(new Person("M2"));
        l2.add(new Person("M3"));
        l2.add(new Person("M4"));
        System.out.println("group1:" + l1.toString());
        System.out.println("group2:" + l2.toString());
        MiniMax util = new MiniMax(l2,l1);
        String[][] ans = {{"M1", "M2", "M3", "M4"},
                {"M2", "M1", "M4", "M3"}};
        LinkedList<Picks> picks = new LinkedList<>();

        Picks p = util.getCeremony();
        picks.add(p);
        System.out.println("Pick: " + p.toString());
        //int beams = s.ceremony(p);
        //System.out.println("beams: " + beams);
        util.recordCeremony(0);
        System.out.println("------");

        Picks p2 = util.getCeremony();
        picks.add(p2);
        System.out.println("Pick: " + p2.toString());
        //int beams2 = s.ceremony(p2);
        //System.out.println("beams: " + beams2);
        util.recordCeremony(1); //correct 3241

        System.out.println("------");
        System.out.println("------");
        System.out.println("------");
        System.out.println("p1: " + p.toString());
        System.out.println("p2: " + p2.toString());
        testCorrectPicks(4,ans,picks);
    }
    @Test
    public void testHumanStraight(){
        //create season, create matches
        String[][] names = {{"F1","F2","F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10"},
                {"M1","M2","M3","M4","M5","M6","M7","M8","M9","M10"}};
        boolean[][] gender= {{false,false,false,false,false,false,false,false,false,false},
                {true,true,true,true,true,true,true,true,true,true}};
        Season s = new Season(names, gender);
        LinkedList<Person> contestants = s.getContestants();
        LinkedList<Person> unConfirmedContestants = new LinkedList<>();
        unConfirmedContestants.addAll(contestants);
        TestUtil t = new TestUtil();
        LinkedList<Match> confirmedMatch = new LinkedList<Match>();
        for(int i=0; i<10; i++){
            Match m = t.randomPairStraight(unConfirmedContestants);
            boolean isMatch = s.truthBoth(m);
            System.out.println("TruthBoth: " + m.getP1().getName() + ", " + m.getP2().getName() + " -" + isMatch);
            if(isMatch){
                confirmedMatch.add(m);
                unConfirmedContestants.remove(m.getP1());
                unConfirmedContestants.remove(m.getP1());
            }
            Picks p = t.randomSelection(contestants, 10,false, confirmedMatch);
            int numCorrect = s.ceremony(p);
            System.out.println("Ceremony: " +  p.toString() + "NumCorrect: " +  numCorrect);
        }
    }
}
