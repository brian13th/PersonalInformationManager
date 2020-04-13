package gr.cup.mathesis.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * 2. Υλοποιήστε αυτή την κλάση 
 * 
 * @author mathesis
 */
public final class TaskManager implements TaskManagerInterface {

    private static TaskManager INSTANCE;

    private TaskManager() {
    }
    
    

    public static TaskManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TaskManager();
        }
        return INSTANCE;
    }
    private ArrayList tasks = new ArrayList<>();

    @Override
    public List<Task> listAllTasks(final boolean priorityOrDate) {
        
        Comparator<Task> comByDueDate = new Comparator<>(){
        @Override
        public int compare(Task task1, Task task2) {
            if (ChronoUnit.DAYS.between(LocalDate.now(), task1.getDueDate()) > ChronoUnit.DAYS.between(LocalDate.now(), task2.getDueDate())){
                return 1;
            } else {
                return -1;
            }
      }
        
    };
        
        Comparator<Task> comByPriority = new Comparator<>(){
        @Override
        public int compare(Task task1, Task task2) {
           if(task1.getPriority() > task2.getPriority()){
            return 1;
        } else {
            return -1;
        }
      }
        
    };
        if (priorityOrDate){
            Collections.sort(tasks, comByDueDate);
            return Collections.unmodifiableList(tasks);
        } else {
            Collections.sort(tasks, comByPriority);
            return Collections.unmodifiableList(tasks);
        }
        
    }

//    public ArrayList getTasks() {
//        return tasks;
//    }

    @Override
    public List<Task> listTasksWithAlert() {
        List<Task> listWithAlert = new ArrayList<>();
        Iterator<Task> it = tasks.iterator();
        while(it.hasNext()){
            Task nextTask = it.next();
            if (nextTask.getAlert()){
                listWithAlert.add(nextTask);
            }
        }
        return listWithAlert;
    }
    
    @Override
    public List<Task> listCompletedTasks() {
        
        List<Task> completedTasks = new ArrayList<>();
        Iterator<Task> it = tasks.iterator();
        while(it.hasNext()){
            Task nextTask = it.next();
            if (nextTask.isCompleted()){
                completedTasks.add(nextTask);
            }
        }
        return completedTasks;
    }

    @Override
    public void addTask(final Task task) {
        if (validate(task)) {
            // TODO
            
            tasks.add(task);
        } else {
            Logger.getLogger(TaskManager.class.getName()).log(Level.WARNING, "Task validation failed.");
        }
    }

    @Override
    public void updateTask(final Task task) {
        if (validate(task)) {
            // TODO
        } else {
            Logger.getLogger(TaskManager.class.getName()).log(Level.WARNING, "Task validation failed.");
        }
    }

    @Override
    public void markAsCompleted(final int id, final boolean completed) {
        
        Iterator<Task> it = tasks.iterator();
        while(it.hasNext()){
            Task nextTask = it.next();
            if (nextTask.getId() == id){
                nextTask.setCompleted(completed);
            }
        }
        
    }

    @Override
    public void removeTask(final int id) {
        Iterator<Task> it = tasks.iterator();
        while(it.hasNext()){
            Task nextTask = it.next();
            if (nextTask.getId() == id){
                it.remove();
            }
        }
    }

    private boolean validate(final Task task) {
        return !task.getDescription().isEmpty();
    }

    @Override
    public Task findTask(final int id) {
        Iterator<Task> it = tasks.iterator();
        
        while(it.hasNext()){
            Task t = it.next();
            if(t.getId() == id){
                return t;
            }
        }
        return null;
    }
}
