package sample;

public class StudentRecord {
    private String SID;
    private float Assignments, Midterm, FinalExam, FinalMark;
    private char LetterGrade;

    public StudentRecord(String sid, float assignments, float midterm, float finalexam)
    {
        SID = sid;
        Assignments = assignments;
        Midterm = midterm;
        FinalExam = finalexam;
        FinalMark = assignments * 0.2f + midterm * 0.3f + finalexam * 0.5f;
        if(FinalMark >= 80)
        {
            LetterGrade = 'A';
        }
        else if(FinalMark >= 70)
        {
            LetterGrade = 'B';
        }
        else if(FinalMark >= 60)
        {
            LetterGrade = 'C';
        }
        else if (FinalMark >= 50)
        {
            LetterGrade = 'D';
        }
        else
        {
            LetterGrade = 'F';
        }
    }

    public String getSID()
    {
        return SID;
    }

    public void setSID(String sid)
    {
        SID = sid;
    }

    public char getLetterGrade()
    {
        return LetterGrade;
    }

    public float getAssignments()
    {
        return Assignments;
    }

    public float getMidterm()
    {
        return Midterm;
    }

    public float getFinalExam()
    {
        return FinalExam;
    }

    public float getFinalMark()
    {
        return FinalMark;
    }
}
