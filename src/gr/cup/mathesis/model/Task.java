package gr.cup.mathesis.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * 1. Υλοποιήστε αυτήν την κλάση με βάση το διάγραμμα κλάσεων
 * 
 * @author Mathesis
 */
public class Task implements Serializable {

    /** Auto index id */
    private static int autoIndex = 0;
    /** Task id. Must be unique. */
    private int id;
    /** Task short description. */
    private String description;
    /** 1: top, 10: lowest */
    private int priority = 10;  
    /** Task must be completed by this date. */
    private LocalDate dueDate = LocalDate.now();
    /** Alert when due date approaches? */
    private boolean alert;
    /** How many days before to alert? */
    private int daysBefore = 0;
    /** Comments */
    private String comments;
    /** Task has been completed or not. */
    private boolean completed;

    public Task(String descr, int prio, LocalDate due) {
        this(descr, prio, due, false, 0);
    }

    public Task(String descr, int prio, LocalDate due, boolean alert, int daysBefore) {
        this(descr, prio, due, alert, daysBefore, "", false);
    }

    public Task(String descr, int prio, LocalDate due, boolean alert, int daysBefore, String comments, boolean compltd) {
        this.id = ++autoIndex;
        this.description = descr;
        this.priority = prio;
        this.dueDate = due;
        this.alert = alert;
        this.daysBefore = daysBefore;
        this.comments = comments;
        this.completed = compltd;
    }
    
    public Task(int id, String descr, int prio, LocalDate due, boolean alert, int daysBefore, String comments, boolean compltd) {
        this.id = id;
        this.description = descr;
        this.priority = prio;
        this.dueDate = due;
        this.alert = alert;
        this.daysBefore = daysBefore;
        this.comments = comments;
        this.completed = compltd;
    }    

    /**
     * @return number of days the task is late; a negative value means that the task is overdue 
     */
    public int isLate() {
        LocalDate dateDue = getDueDate();
        if (dateDue == null) {
            return Integer.MAX_VALUE;
        } else {
            return (int) ChronoUnit.DAYS.between(LocalDate.now(), dateDue);
        }
    }

    /**
     * @return {@code true} if the task has an alert 
     */
    public boolean hasAlert() {
        LocalDate dateDue = getDueDate();
        if (!getAlert() || dateDue == null) {
            return false;
        } else {
            int dias = dateDue.getDayOfYear() - LocalDate.now().getDayOfYear();
            return dias <= getDaysBefore();
        }
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        if (priority <= 0 || priority > 10) {
            throw new IllegalArgumentException("Priority must be between 1-10");
        }
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dateDue) {
        this.dueDate = dateDue;
    }

    public boolean getAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public int getDaysBefore() {
        return daysBefore;
    }

    /**
     * @param daysBefore up to a year (365 days)
     */
    public void setDaysBefore(int daysBefore) {
        if (daysBefore < 0 || daysBefore > 365) {
            throw new IllegalArgumentException("Days before must be between 0-365");
        }        
        this.daysBefore = daysBefore;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.description);
        hash = 47 * hash + this.priority;
        hash = 47 * hash + Objects.hashCode(this.dueDate);
        hash = 47 * hash + (this.alert ? 1 : 0);
        hash = 47 * hash + this.daysBefore;
        hash = 47 * hash + Objects.hashCode(this.comments);
        hash = 47 * hash + (this.completed ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Task other = (Task) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.priority != other.priority) {
            return false;
        }
        if (this.alert != other.alert) {
            return false;
        }
        if (this.daysBefore != other.daysBefore) {
            return false;
        }
        if (this.completed != other.completed) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.comments, other.comments)) {
            return false;
        }
        if (!Objects.equals(this.dueDate, other.dueDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(toShortString());
        sb.append("Comments: ").append(getComments()).append("\n");
        sb.append("Completed? ").append(isCompleted()).append("\n");
        return sb.toString();
    }
    
    public String toShortString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(getId()).append("\n");
        sb.append("Priority: ").append(getPriority()).append("\n");
        sb.append("Description: ").append(getDescription()).append("\n");
        if (getDueDate() != null) {
            sb.append("Due Date: ").append(getDueDate().format(DateTimeFormatter.ISO_DATE)).append("\n");
            sb.append("Alert? ").append(getAlert()).append("\n");
            if (getAlert()) {
                sb.append("Days Before: ").append(getDaysBefore()).append("\n");
            }
        } else {
            sb.append("No Due Date").append("\n");
        }
        return sb.toString();
    }
}
