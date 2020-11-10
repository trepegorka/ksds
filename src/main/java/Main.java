import Images.ImageEditor;import general.logics.AdvantageGenerator;import hltv.Hltv;import hltv.matches.Match;import hltv.matches.teams.Team;import org.apache.commons.math3.util.Precision;import telegramBot.Bot;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStreamReader;import java.util.LinkedHashMap;import java.util.List;import java.util.Map;public class Main {    private static double leftPerc;    private static double rightPerc;    public static void main(String[] args) throws Exception {        List<String> lifeMatchesLinks = Hltv.getHltv().getLiveMatchesLinks();        update(lifeMatchesLinks);    }    public static void update(List<String> lifeMatchesLinks) throws Exception {        for (String lifeMatchesLink : lifeMatchesLinks) {            Match match = new Match(lifeMatchesLink);            Team leftTeam;            Team rightTeam;            double[] attributes = new double[8];            Map<String, String[]> map_perWin = new LinkedHashMap<>();            for (String map : match.mapPick()) {                System.out.println("First team download...");                leftTeam = new Team(match.getFirstTeamLink(), map); // Team 0 downloading left                System.out.println("Second team download...");                rightTeam = new Team(match.getSecondTeamLink(), map); // Team 1 downloading right                AdvantageGenerator generator = new AdvantageGenerator(leftTeam, rightTeam);                System.out.println("Generating attitude...");                attributes[0] = Precision.round(generator.KDRatioAttitude(), 3);                attributes[1] = Precision.round(generator.headshotAttitude(), 3);                attributes[2] = Precision.round(generator.damagePerRoundAttitude(), 3);                attributes[3] = Precision.round(generator.assistsPerRoundAttitude(), 3);                attributes[4] = Precision.round(generator.impactAttitude(), 3);                attributes[5] = Precision.round(generator.kastAttitude(), 3);                attributes[6] = Precision.round(generator.openingKillRatioAttitude(), 3);                attributes[7] = Precision.round(generator.rating3mAttitude(), 3);                System.out.println("Python...");                callPython(attributes);                System.out.println("Next Map");                map_perWin.put(map, new String[]{String.valueOf(getLeftPerc()), String.valueOf(getRightPerc())});            }            ImageEditor.fillImage(match, map_perWin);            Bot.getBot().sendPhoto("src/main/java/Images/imageLibrary/result.png");        }        System.out.println("Reloading...");        reloadList(lifeMatchesLinks);    }    //new list for comparison    private static void reloadList(List<String> lifeMatchesLinks) throws Exception {        System.out.println("ReloadMatches");        //new list for comparison        List<String> nextlifeMatchesLinks = Hltv.getHltv().getLiveMatchesLinks();        // удалить старые линки с нового листа        for (String oldLifeLink : lifeMatchesLinks) {            nextlifeMatchesLinks.remove(oldLifeLink);        }        // если новых матчей не появилось - перезапустить метод        if (nextlifeMatchesLinks.isEmpty()) {            System.out.println("No one new match. Sleep...");            Thread.sleep(30000);            reloadList(lifeMatchesLinks);        }        //если линки появились, присвоить старому листу новые линки        else {            update(nextlifeMatchesLinks);        }    }    private static void callPython(double[] attributes) {        String caller = "sh src/main/java/bashscript.sh "                + attributes[0] + " "                + attributes[1] + " "                + attributes[2] + " "                + attributes[3] + " "                + attributes[4] + " "                + attributes[5] + " "                + attributes[6] + " "                + attributes[7];        if (attributes[0] == 0) {            setLeftPerc("0.0");            setRightPerc("0.0");        } else {            ProcessBuilder processBuilder = new ProcessBuilder();            processBuilder.command("bash", "-c", caller);            try {                Process process = processBuilder.start();                StringBuilder output = new StringBuilder();                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));                String line;                while ((line = reader.readLine()) != null) {                    output.append(line).append("\n");                }                int exitVal = process.waitFor();                if (exitVal == 0) {                    System.out.println(output);                    setLeftPerc(output.toString());                    setRightPerc(output.toString());                }            } catch (IOException | InterruptedException e) {                e.printStackTrace();            }        }    }    private static void setLeftPerc(String pyOutput) {        leftPerc = Double.parseDouble(pyOutput.substring(0, pyOutput.indexOf(':')));    }    private static void setRightPerc(String pyOutput) {        rightPerc = Double.parseDouble(pyOutput.substring(pyOutput.indexOf(':') + 1));    }    private static double getLeftPerc() {        return leftPerc;    }    private static double getRightPerc() {        return rightPerc;    }}