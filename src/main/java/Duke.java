import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Duke {
    static File saveFile = new File("list.txt");
    public static void save(List<Task> taskList) {
        try {
            FileWriter fw = new FileWriter(saveFile.getPath());
            for (Task task : taskList) {
                fw.write(task.getSaveString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Something is wrong nya! I can't seem to save your task list");
        }
    }

    public static List<Task> load() {
        List<Task> taskList = new ArrayList<>();
        try {
            Scanner sc = new Scanner(saveFile);
            while (sc.hasNextLine()) {
                String nextLine = sc.nextLine();
                String[] taskInfo = nextLine.split(" \\| ");
                Task curr = null;
                switch (taskInfo[0]) {
                case "T" :
                    curr = new ToDo(taskInfo[2]);
                    taskList.add(curr);
                    break;
                case "D":
                    curr = new Deadline(taskInfo[2], taskInfo[3]);
                    taskList.add(curr);
                    break;
                case "E":
                    curr = new Event(taskInfo[2], taskInfo[3]);
                    taskList.add(curr);
                    break;
                }
                if (taskInfo[1].equals("1")) {
                    curr.markAsDone();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Sorry! I can't find your file nya");
        }
        return taskList;
    }
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Task> taskArr = saveFile.exists() ? load() : new ArrayList<>();
        String input = "";
        System.out.print("Hi I'm catBot!\nHow can I help you nya?\n");

        while (!input.equals("bye")) {

            input = sc.nextLine();
            String[] splitInput = input.split(" ", 2);
            String command = splitInput[0];

            switch(command) {
                case "bye":
                    break;

                case "todo": {
                    try {
                        String[] splitDes = splitInput[1].split("/", 2);
                        String des = splitDes[0];
                        Task task = new ToDo(des);

                        System.out.println("Roger nya! Added this task:\n  " + task.toString());
                        taskArr.add(task);
                        System.out.println("Now you have " + taskArr.size() + " task(s) in the list nya.");
                        save(taskArr);
                        break;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Sorry nya, the description of a todo cannot be empty :3");
                        break;
                    }
                }

                case "deadline": {
                    try {
                        String[] desAndWhen = splitInput[1].split(" /by ", 2);
                        String[] dateAndTime = desAndWhen[1].split(" ", 2);
                        String des = desAndWhen[0];
                        String date = dateAndTime[0];
                        Task task = new Deadline(des, date);

                        System.out.println("Roger nya! Added this task:\n  " + task.toString());
                        taskArr.add(task);
                        System.out.println("Now you have " + taskArr.size() + " task(s) in the list nya.");
                        save(taskArr);
                        break;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Sorry nya, the description of a deadline cannot be empty :3");
                        break;
                    }

                }

                case "event": {
                    try {
                        String[] desAndWhen = splitInput[1].split(" /at ", 2);
                        String[] dateAndTime = desAndWhen[1].split(" ", 2);
                        String des = desAndWhen[0];
                        String date = dateAndTime[0];
                        Task task = new Event(des, date);

                        System.out.println("Roger nya! Added this task:\n  " + task.toString());
                        taskArr.add(task);
                        System.out.println("Now you have " + taskArr.size() + " task(s) in the list nya.");
                        save(taskArr);
                        break;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Sorry nya, the description of an event cannot be empty :3");
                        break;
                    }

                }

                case "mark": {
                    String[] splitTask = splitInput[1].split("/", 2);
                    String task = splitTask[0];

                    int index = Integer.parseInt(task);
                    taskArr.get(index - 1).markAsDone();
                    System.out.println("I've marked this task as done. Great job nya!");
                    save(taskArr);
                    break;
                }

                case "unmark": {
                    String[] splitTask = splitInput[1].split("/", 2);
                    String task = splitTask[0];
                    int index = Integer.parseInt(task);
                    taskArr.get(index - 1).markAsNotDone();
                    System.out.println("Roger nya! I've marked this task as not done.");
                    save(taskArr);
                    break;
                }

                case "delete": {
                    String[] splitTask = splitInput[1].split("/", 2);
                    String task = splitTask[0];
                    int index = Integer.parseInt(task);
                    Task target = taskArr.get(index -1);
                    System.out.println("Roger nya! I've removed this task:\n  " + target.toString());
                    taskArr.remove(index - 1);
                    System.out.println("Now you have " + taskArr.size() + " task(s) left in the list.");
                    save(taskArr);
                    break;
                }

                case "list":
                    System.out.println("Here are the tasks in your list nya:");
                    int index = 1;
                    for (Task t : taskArr) {
                        System.out.println(index + "." + t.toString());
                        index++;
                    }
                    break;

                default:
                    System.out.println("Sorry nya, I don't understand what that means :3");
                    break;
            }
        }

        System.out.print("Bye nya! Hope to see you again nya.");

    }
}
