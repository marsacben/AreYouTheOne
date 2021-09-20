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
}
