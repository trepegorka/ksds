package proxy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.*;

public class UserParser{

    public static String userAgent = "";


    static {
        try {
            userAgent = getRandomAgent();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Document getProxyListNetHtml() throws IOException {
        return Jsoup.connect("https://free-proxy-list.net")
                .userAgent(userAgent)
                .referrer("http://www.google.com")
                .get();
    }

    public static String getRandomProxy() throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        Document doc = getProxyListNetHtml();
        for (int i = 1; i < 70; i++) {

            Elements ipAndPort = doc.select("#proxylisttable > tbody > tr:nth-child(" + i + ")");

            if(ipAndPort.select("tr:nth-child("+i+") >td.hx").text().equals("no")){
               list.add(ipAndPort.select("td:nth-child(1)").text() + ":" + ipAndPort.select("td:nth-child(2)").text());
           }
        }
        Random random = new Random();
        int a = random.nextInt((list.size() - 1));
        return list.get(a);
    }

    private static void getProxyByApi() throws IOException {

        String url = "https://gimmeproxy.com/api/getProxy";
        Document doc = Jsoup.connect(url)
                .userAgent(userAgent)
                .referrer("http://www.google.com")
                //.header("Content-Type","text/*, application/xml, or application/*+xml. Mimetype=application/json; charset=utf-8,"+url)
                .get();

        String content = doc.text();
        System.out.println(content);

    }



    public static void setUserAgent(String userAgent) {
        UserParser.userAgent = userAgent;
    }

    public static String getRandomAgent() throws FileNotFoundException {
        File f = new File("src/main/java/proxy/UserAgents");
        String result = null;
        Random rand = new Random();
        int n = 0;
        for (Scanner sc = new Scanner(f); sc.hasNext(); ) {
            ++n;
            String line = sc.nextLine();
            if (rand.nextInt(n) == 0)
                result = line;
        }
        return result;
    }

    public static void stripDuplicatesFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        Set<String> lines = new HashSet<String>(10000); // maybe should be bigger
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (String unique : lines) {
            writer.write(unique);
            writer.newLine();
        }
        writer.close();
    }

    public static void deleteDuplicatesMap(String filename) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        ArrayList<String> lines = new ArrayList<String>();
        ArrayList<String> write = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            String map = line.substring(0,line.lastIndexOf(" Rating:"));
            if (lines.contains(map)){
            } else {
                write.add(line);
                lines.add(map);
            }
        }
        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (String unique : write) {
            writer.write(unique);
            writer.newLine();
        }
        writer.close();
    }
}
