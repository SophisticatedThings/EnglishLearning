package artem.strelcov.corporativeapplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerWrapper {
    private ArrayList<String> answers = new ArrayList<>();
    private String defaultValue = "No answer";
    public ArrayList<String> getAnswerList(){
        return answers;
    }
    public void setAnswerList(ArrayList<String> answers){
        this.answers = answers;
    }

}
