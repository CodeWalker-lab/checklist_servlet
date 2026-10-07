package beans;

import java.io.Serializable;

public class ProgressBeans implements Serializable {
    private static final long serialVersionUID = 1L;

    private String questionNo;
    private int userId;
    private boolean completed;

    public ProgressBeans() {}

    public ProgressBeans(String questionNo, int userId, boolean completed) {
        this.questionNo = questionNo;
        this.userId = userId;
        this.completed = completed;
    }

    public String getQuestionNo() { return questionNo; }
    public void setQuestionNo(String questionNo) { this.questionNo = questionNo; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}