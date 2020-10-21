import java.io.IOException;

public class DictionaryCommandLine extends DictionaryManagement{

    public void showAllWords() {
        System.out.printf(" No \t |Endlish \t  |Vietnamese \n");
        for (int i = 0; i < dictionary.size(); i++) {
            System.out.printf((i + 1) + "      \t |" + dictionary.get(i).word_target + "     \t\t |" + dictionary.get(i).word_explain+"\n");
        }
    }
    public void dictionaryBasic(){
        insertFromCommandline();
        showAllWords();
    }

    public void dictionaryAdvanced(){
        showAllWords();
    }
    public void show() {
        System.out.printf(" No \t |Endlish \t  |Vietnamese \n");

        System.out.printf((1+ 1) + "      \t |" + dictionary.get(1).word_target + "     \t\t |" + dictionary.get(1).word_explain+"\n");

    }
}