package www.softedgenepal.com.softedgenepalschool.Model.Cache;

import java.io.Serializable;
import java.util.List;

public class ReportCardCache implements Serializable {
    public String Position;
    public String Result;
    public List<Marks> Marks;

    public static class Marks implements Serializable{
        public String SubjectCode;
        public String Subject;

        public String FullMarks;
        public String PassMarks;
        public String ObtainedMarks;

        public String PracticalFullMarks;
        public String PracticalPassMarks;
        public String PracticalObtainedMarks;

        public String IsAbsentPractical;
        public String IsAbsentTheory;

        public String Highest;
        public String Grade;
        public String GradePoint;
        public String CGPA;

        public Marks(String subjectCode, String subject,
                     String fullMarks, String passMarks, String obtainedMarks,
                     String practicalFullMarks, String practicalPassMarks, String practicalObtainedMarks,
                     String isAbsentPractical, String isAbsentTheory,
                     String highest, String grade, String gradePoint, String cgpa) {
            SubjectCode = subjectCode;
            Subject = subject;
            FullMarks = fullMarks;
            PassMarks = passMarks;
            ObtainedMarks = obtainedMarks;
            PracticalFullMarks = practicalFullMarks;
            PracticalPassMarks = practicalPassMarks;
            PracticalObtainedMarks = practicalObtainedMarks;
            IsAbsentPractical = isAbsentPractical;
            IsAbsentTheory = isAbsentTheory;
            Highest = highest;
            Grade = grade;
            GradePoint = gradePoint;
            CGPA = CGPA;
        }
    }

    public ReportCardCache(String position, String result, List<ReportCardCache.Marks> marks) {
        Position = position;
        Result = result;
        Marks = marks;
    }
}
