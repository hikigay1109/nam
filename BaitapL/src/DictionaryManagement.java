import javax.sound.midi.VoiceStatus;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.UnsupportedEncodingException;

public class DictionaryManagement extends Dictionary {
    final static String fileName = "dictionaries.txt";


    public void insertFromCommandline() {
        Scanner s = new Scanner(System.in);
        System.out.printf("nhap so luonh tu");
        int n = s.nextInt();
        s.nextLine();
        System.out.printf("nhap cac tu \n");
        for (int i = 0; i < n; i++) {
            String a = s.nextLine();
            //s.nextLine();
            String b = s.nextLine();
            Word w = new Word(a, b);
            dictionary.add(w);
        }
    }

    public void exportToFile() throws IOException {
        File file = new File("./src/DictEV.dic");
        OutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);

        for (Word item : dictionary) {
            outputStreamWriter.write(item.toString());
            outputStreamWriter.write("\n");
        }
        outputStreamWriter.close();
    }


    public String dictionaryLookup(String word) {
        for (int i = 0; i < dictionary.size(); i++) {
            if (word.equalsIgnoreCase(dictionary.get(i).word_target) == true) {
                return "nghia " + dictionary.get(i).word_explain;
            }
        }
        return "khong tim thay tu";

    }

    public boolean addWord(String a,String b) {
        boolean t=false;
        a=a.toLowerCase();
        b=b.toLowerCase();

        for (Word w : dictionary) {
            if (a.equals(w.word_target) == true) {
                t= true;
                break;
            }
        }
        if (t == false) {
            Word d = new Word(a, b);
            dictionary.add(d);

        }
        return t;
    }
    public void xepWord(String a,String b) {
        int t=0;
        for (int i = 0; i < dictionary.size()-1;i++){
            if(a.compareTo(dictionary.get(i).word_target)<0){
                t=i;
                break;
            }
        }
        for(int m=dictionary.size()-1; m>t ;m--){
            dictionary.get(m).setWord_target(dictionary.get(m-1).word_target);
            dictionary.get(m).setWord_explain(dictionary.get(m-1).word_explain);
        }
        dictionary.get(t).setWord_explain(a);
        dictionary.get(t).setWord_target(b);
    }
    public boolean fixWord(String a,String b) {
        a=a.toLowerCase();
        b=b.toLowerCase();

        for (int i = 0; i < dictionary.size(); i++) {
            if (a.equals(dictionary.get(i).word_target)) {
                dictionary.get(i).setWord_explain(b);
                return true;
            }
        }
        return false;
    }
    public void addWord1(Word w) {
        boolean b = false;
        for (Word a : dictionary) {
            if (w.equals(a) == true) {

                b = true;
            }
        }
        if (b == false)
            dictionary.add(w);
    }

    public boolean removeWord(String a) {


        int temp = 0;
        for (Word w : dictionary) {
            if (a.equals(w.word_target) == true) {
                dictionary.remove(temp);
                return true;
            }
            temp ++;
        }
        return false;
    }
    // public void sapxep

    public void insertFromFile() throws IOException {
        try{

            Scanner scanner = new Scanner(Paths.get("./src/DictEV.dic"),"UTF-8");

            while (scanner.hasNextLine()) {
                String[] word = scanner.nextLine().split("=");
                Word newWord = new Word(word[0], word[1]);

                dictionary.add(newWord);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error");
        }

    }
    public LinkedList<String> searchWord(String a) {
        LinkedList<String> s= new LinkedList<>();
        int t=0;
        for(int i=0;i<dictionary.size();i++){
            if(dictionary.get(i).word_target.contains(a)) {
                String b= dictionary.get(i).word_target;
                s.add(b);
                t++;
            }
        }
        return s;
    }
    public String api(String langFrom,String langTo,String text) throws IOException{
        String urlStr="https://script.google.com/macros/s/AKfycbyWY7bMlLkt3MGwkbaD3Fujt_0mfFgujogqPfh0aYk8rV8rzwM/exec"+
        "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url =new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con= (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent","Mozilla/5.0");
        BufferedReader in= new BufferedReader((new InputStreamReader(con.getInputStream())));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

}